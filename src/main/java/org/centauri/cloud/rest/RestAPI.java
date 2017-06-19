package org.centauri.cloud.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.plugin.AbstractModule;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.cloud.template.Template;
import org.centauri.cloud.rest.auth.AuthManager;
import org.centauri.cloud.rest.util.MapUtil;
import org.pac4j.sparkjava.SecurityFilter;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class RestAPI extends AbstractModule {

	@Getter private static RestAPI instance;
	private Gson gson = new Gson();

	@Override
	public String getName() {
		return "RestAPI";
	}

	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}

	@Override
	public String getAuthor() {
		return "Centauri Developer Team";
	}

	@Override
	public void onEnable() {
		instance = this;
		new File("files").mkdir();
		staticFiles.externalLocation("files");
		AuthManager manager = new AuthManager();
		manager.register();
		//TODO permissions
		before("/auth", new SecurityFilter(manager.getConfig(), "IndirectBasicAuthClient"));
		get("/auth", manager::jwt);

		path("/api", () -> {
			before("/*", new SecurityFilter(manager.getConfig(), "ParameterClient"));

			get("/version", (request, response) -> MapUtil.from("version", Centauri.getInstance().getCloudVersion()));
			get("/plugins", (request, response) -> {
				List<Map<String, Object>> modules = Centauri.getInstance().getModules()
						.stream()
						.map(module -> MapUtil.builder()
								.add("name", module.getName())
								.add("author", module.getAuthor())
								.add("version", module.getVersion())
								.build()).collect(Collectors.toList());
				return gson.toJson(modules);

			});
			get("/server", (request, response) -> {
				String queryParams = request.queryParams("server");
				if (queryParams == null) {
					response.status(404);
					return "";
				}
				Server server = Centauri.getInstance().getServer(queryParams);
				if (server == null) {
					response.status(404);
					return "";
				}
				return gson.toJson(MapUtil.builder()
						.add("name", server.getName())
						.add("prefix", server.getPrefix())
						.add("id", server.getId())
						.add("ping", server.getPing())
						.build());

			});
			get("/servers", (request, response) -> {
				Collection<Server> servers = Centauri.getInstance().getServers();
				List<Map<String, Object>> mappedServers = servers.stream().map(server -> MapUtil.builder()
						.add("name", server.getName())
						.add("prefix", server.getPrefix())
						.add("id", server.getId())
						.add("ping", server.getPing())
						.build())
						.collect(Collectors.toList());
				return gson.toJson(mappedServers);
			});
			get("/spigotserver", (request, response) -> {
				List<SpigotServer> spigotServers = Centauri.getInstance().getSpigotServers();
				List<Map<String, Object>> mappedSpigotServers = spigotServers.stream().map(server -> MapUtil.builder()
						.add("name", server.getName())
						.add("prefix", server.getPrefix())
						.add("id", server.getId())
						.add("ping", server.getPing())
						.add("port", server.getBukkitPort())
						.add("players", server.getPlayers())
						.build())
						.collect(Collectors.toList());

				return gson.toJson(mappedSpigotServers);
			});
			get("/bungeeserver", (request, response) -> {
				List<BungeeServer> bungeeServers = Centauri.getInstance().getBungeeServers();
				List<Map<String, Object>> mappedBungeeServers = bungeeServers.stream().map(server -> MapUtil.builder()
						.add("name", server.getName())
						.add("prefix", server.getPrefix())
						.add("id", server.getId())
						.add("ping", server.getPing())
						.add("players", server.getPlayers())
						.build())
						.collect(Collectors.toList());

				return gson.toJson(mappedBungeeServers);
			});
			get("/daemonserver", (request, response) -> {
				List<Daemon> daemons = Centauri.getInstance().getDaemons();
				List<Map<String, Object>> mappedDaemons = daemons.stream().map(daemon -> MapUtil.builder()
						.add("name", daemon.getName())
						.add("prefix", daemon.getPrefix())
						.add("id", daemon.getId())
						.add("ping", daemon.getPing())
						.add("servers", daemon.getServers().stream().map(server -> MapUtil.builder()
								.add("name", server.getName())
								.add("prefix", server.getPrefix())
								.add("id", server.getId())
								.add("ping", server.getPing())
								.build())
								.collect(Collectors.toList()))
						.build())
						.collect(Collectors.toList());
				return gson.toJson(mappedDaemons);
			});
			get("/templates", (request, response) -> {
				List<Template> templates = Centauri.getInstance().getTemplates();
				List<Map<String, Object>> mappedTemplates = templates.stream().map(template -> MapUtil.builder()
						.add("name", template.getName())
						.add("minServers", template.getMinServersFree())
						.add("maxPlayer", template.getMaxPlayers())
						.build())
						.collect(Collectors.toList());

				return gson.toJson(mappedTemplates);
			});
			get("/template", (request, response) -> {
				String queryParam = request.queryParams("name");
				if (queryParam == null) {
					response.status(404);
					return "";
				}
				List<String> lines = Centauri.getInstance().getConfigFromTemplate(queryParam);
				return gson.toJson(lines);
			});
			get("/templateupload", (request, response) -> {
				try {
					List<String> lines = gson.fromJson(request.body(), new TypeToken<List<String>>() {
					}.getType());
					String queryParam = request.queryParams("path");
					if (lines == null || queryParam == null) {
						response.status(404);
						return "";
					}
					Centauri.getInstance().setConfigFromTemplate(queryParam, lines);
					return gson.toJson(MapUtil.from("status", "OK"));
				} catch (JsonSyntaxException e) {
					//Ignore
					response.status(404);
					return "";
				}
			});
			get("/libs", (request, response) -> {
				List<File> libs = Centauri.getInstance().getLibs();
				return gson.toJson(libs.stream()
						.map(File::getName)
						.collect(Collectors.toList()));

			});
			get("/file", (request, response) -> {
				String path = request.queryParams("path");
				if (path == null) {
					response.status(404);
					return "";
				}
				List<String> lines = Centauri.getInstance().getFileContent(path);
				return gson.toJson(lines);
			});
			put("/fileupload", (request, response) -> {
				try {
					List<String> lines = gson.fromJson(request.body(), new TypeToken<List<String>>() {
					}.getType());
					String queryParam = request.queryParams("path");
					if (lines == null || queryParam == null) {
						response.status(404);
						return "";
					}
					Centauri.getInstance().setFileContent(queryParam, lines);
					return gson.toJson(MapUtil.from("status", "OK"));
				} catch (JsonSyntaxException e) {
					//Ignore
					response.status(404);
					return "";
				}
			});
			get("/command", (request, response) -> {
				String command = request.queryParams("cmd");
				String server = request.queryParams("server");
				if (command == null || server == null) {
					response.status(404);
					return "";
				}
				boolean fine = Centauri.getInstance().sendCommandToServer(command, server);
				if (!fine) {
					response.status(404);
					return "";
				}
				return gson.toJson(MapUtil.from("status", "OK"));
			});
			get("/log", (request, response) -> gson.toJson(MapUtil.from("log", "NOT SUPPORTED YET")));
			get("/path", (request, response) -> {
				String path = request.queryParams("path");
				if (path == null) {
					response.status(404);
					return "";
				}
				List<File> files = Centauri.getInstance().getDirContent(path);
				if (files == null) {
					response.status(404);
					return "";
				}
				List<Map<String, Object>> mappedFiles = files.stream().map(file -> MapUtil.builder()
						.add("filename", file.getName())
						.add("isDir", file.isDirectory())
						.build())
						.collect(Collectors.toList());
				return gson.toJson(mappedFiles);
			});


		});

		after((request, response) -> {
			response.type("application/json");
			response.header("Content-Encoding", "gzip");
		});
	}

	@Override
	public void onDisable() {
		stop();
	}
}
