package org.centauri.cloud.rest;

import lombok.Getter;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.cloud.module.AbstractModule;

public class RestAPI extends AbstractModule{

	@Getter private static RestAPI instance;


	public String getName() {
		return "CentauriRestAPI";
	}


	public String getVersion() {
		return "1.0-SNAPSHOT";
	}


	public String getAuthor() {
		return "Centauri Developer Team";
	}


	public void onEnable() {
		try {
			Cloud.getLogger().info("onEnable");
			new RestApplication().run("server " + "/config.yml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	@Override
//	public void onEnable() {
//		instance = this;
//		JWTUtil.init();
//		enableCORS();
//		new File(getModuleDirectory().getPath() + "/files/").mkdir();
//		staticFiles.externalLocation(getModuleDirectory().getPath() + "/files/");
//		post("/auth", "application/json", (request, response) -> {
//			String username = request.headers("username");
//			String password = request.headers("password");
//			boolean verified = true;
//			if (!verified) {
//				halt(401, "Wrong credentials");
//				return "";
//			}
//			LoginFilter.UserType type = LoginFilter.UserType.USER;
//			response.cookie("Badge", JWTUtil.generateToken(type, request));
//			return "";
//		}, gson::toJson);
//		path("/api", () -> {
//			before("*", "application/json", new LoginFilter(LoginFilter.UserType.USER));
//			get("/version", (request, response) -> MapUtil.from("version", Centauri.getInstance().getCloudVersion()), gson::toJson);
//			get("/plugins", (request, response) ->
//					Centauri.getInstance().getModules()
//							.stream()
//							.map(module -> MapUtil.builder()
//									.add("name", module.getName())
//									.add("author", module.getAuthor())
//									.add("version", module.getVersion())
//									.build()).collect(Collectors.toList()), gson::toJson);
//			get("/server", (request, response) -> {
//				String queryParams = request.queryParams("server");
//				if (queryParams == null) {
//					response.status(404);
//					return "";
//				}
//				Server server = Centauri.getInstance().getServer(queryParams);
//				if (server == null) {
//					response.status(404);
//					return "";
//				}
//				return MapUtil.builder()
//						.add("name", server.getName())
//						.add("prefix", server.getPrefix())
//						.add("id", server.getId())
//						.add("ping", server.getPing())
//						.build();
//
//			}, gson::toJson);
//			get("/servers", (request, response) -> {
//				Collection<Server> servers = Centauri.getInstance().getServers();
//				return servers.stream().map(server -> MapUtil.builder()
//						.add("name", server.getName())
//						.add("prefix", server.getPrefix())
//						.add("id", server.getId())
//						.add("ping", server.getPing())
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//			get("/spigotserver", (request, response) -> {
//				List<SpigotServer> spigotServers = Centauri.getInstance().getSpigotServers();
//
//				return spigotServers.stream().map(server -> MapUtil.builder()
//						.add("name", server.getName())
//						.add("prefix", server.getPrefix())
//						.add("id", server.getId())
//						.add("ping", server.getPing())
//						.add("port", server.getBukkitPort())
//						.add("players", server.getPlayers())
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//			get("/bungeeserver", (request, response) -> {
//				List<BungeeServer> bungeeServers = Centauri.getInstance().getBungeeServers();
//
//				return bungeeServers.stream().map(server -> MapUtil.builder()
//						.add("name", server.getName())
//						.add("prefix", server.getPrefix())
//						.add("id", server.getId())
//						.add("ping", server.getPing())
//						.add("players", server.getPlayers())
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//			get("/daemonserver", (request, response) -> {
//				List<Daemon> daemons = Centauri.getInstance().getDaemons();
//				return daemons.stream().map(daemon -> MapUtil.builder()
//						.add("name", daemon.getName())
//						.add("prefix", daemon.getPrefix())
//						.add("id", daemon.getId())
//						.add("ping", daemon.getPing())
//						.add("servers", daemon.getServers().stream().map(server -> MapUtil.builder()
//								.add("name", server.getName())
//								.add("prefix", server.getPrefix())
//								.add("id", server.getId())
//								.add("ping", server.getPing())
//								.build())
//								.collect(Collectors.toList()))
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//			get("/templates", (request, response) -> {
//				List<Template> templates = Centauri.getInstance().getTemplates();
//
//				return templates.stream().map(template -> MapUtil.builder()
//						.add("name", template.getName())
//						.add("minServers", template.getMinServersFree())
//						.add("maxPlayer", template.getMaxPlayers())
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//			get("/template", (request, response) -> {
//				String queryParam = request.queryParams("name");
//				if (queryParam == null) {
//					response.status(404);
//					return "";
//				}
//				return Centauri.getInstance().getConfigFromTemplate(queryParam);
//			}, gson::toJson);
//			get("/templateupload", (request, response) -> {
//				try {
//					List<String> lines = gson.fromJson(request.body(), new TypeToken<List<String>>() {
//					}.getType());
//					String queryParam = request.queryParams("path");
//					if (lines == null || queryParam == null) {
//						response.status(404);
//						return "";
//					}
//					Centauri.getInstance().setConfigFromTemplate(queryParam, lines);
//					return MapUtil.from("status", "OK");
//				} catch (JsonSyntaxException e) {
//					//Ignore
//					response.status(404);
//					return "";
//				}
//			}, gson::toJson);
//			get("/libs", (request, response) -> {
//				List<File> libs = Centauri.getInstance().getLibs();
//				return libs.stream()
//						.map(File::getName)
//						.collect(Collectors.toList());
//
//			}, gson::toJson);
//			get("/file", (request, response) -> {
//				String path = request.queryParams("path");
//				if (path == null) {
//					response.status(404);
//					return "";
//				}
//				return Centauri.getInstance().getFileContent(path);
//			}, gson::toJson);
//			put("/fileupload", (request, response) -> {
//				try {
//					List<String> lines = gson.fromJson(request.body(), new TypeToken<List<String>>() {
//					}.getType());
//					String queryParam = request.queryParams("path");
//					if (lines == null || queryParam == null) {
//						response.status(404);
//						return "";
//					}
//					Centauri.getInstance().setFileContent(queryParam, lines);
//					return MapUtil.from("status", "OK");
//				} catch (JsonSyntaxException e) {
//					//Ignore
//					response.status(404);
//					return "";
//				}
//			}, gson::toJson);
//			get("/command", (request, response) -> {
//				String command = request.queryParams("cmd");
//				String server = request.queryParams("server");
//				if (command == null || server == null) {
//					response.status(404);
//					return "";
//				}
//				boolean fine = Centauri.getInstance().sendCommandToServer(command, server);
//				if (!fine) {
//					response.status(404);
//					return "";
//				}
//				return MapUtil.from("status", "OK");
//			}, gson::toJson);
//			get("/log", (request, response) -> MapUtil.from("log", "NOT SUPPORTED YET"), gson::toJson);
//			get("/path", (request, response) -> {
//				String path = request.queryParams("path");
//				if (path == null) {
//					response.status(404);
//					return "";
//				}
//				List<File> files = Centauri.getInstance().getDirContent(path);
//				if (files == null) {
//					response.status(404);
//					return "";
//				}
//				return files.stream().map(file -> MapUtil.builder()
//						.add("filename", file.getName())
//						.add("isDir", file.isDirectory())
//						.build())
//						.collect(Collectors.toList());
//			}, gson::toJson);
//
//
//		});
//
//		after("*", (request, response) -> response.header("Content-Encoding", "gzip"));
//
//		notFound((request, response) -> {
//			response.type("application/json");
//			return "{\"message\":\"Custom 404\"}";
//		});
//	}
//
//	private void enableCORS() {
//
//		options("*", (request, response) -> {
//			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
//			if (accessControlRequestHeaders != null) {
//				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
//			}
//
//			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
//			if (accessControlRequestMethod != null) {
//				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
//			}
//
//			return "OK";
//		});
//
//		before((request, response) -> {
//			response.header("Access-Control-Allow-Origin", "*");
//			response.header("Access-Control-Request-Method", "*");
//			response.header("Access-Control-Allow-Headers", "*");
//			response.type("application/json");
//		});
//
//	}
//
//	@Override
//	public void onDisable() {
//		stop();
//	}

}
