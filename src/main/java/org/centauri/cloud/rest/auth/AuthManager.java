package org.centauri.cloud.rest.auth;

import com.google.gson.Gson;
import lombok.Getter;
import org.centauri.cloud.rest.util.MapUtil;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.sparkjava.CallbackRoute;
import org.pac4j.sparkjava.SparkWebContext;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static spark.Spark.get;
import static spark.Spark.post;

public class AuthManager {

	@Getter private Config config;

	public boolean validate(String user) {
		return true;
	}

	public boolean hasPermission(String site, String user) {
		return true;
	}


	public void register() {
		config = new AuthConfig("12345678901234567890123456789012").build();
		final CallbackRoute callback = new CallbackRoute(config, null, true);
		get("/callback", callback);
		post("/callback", callback);
	}

	public String jwt(final Request request, final Response response) {
		final SparkWebContext context = new SparkWebContext(request, response);
		final ProfileManager manager = new ProfileManager(context);
		final Optional<CommonProfile> profile = manager.get(true);
		String token = "";
		if (profile.isPresent()) {
			JwtGenerator generator = new JwtGenerator(new SecretSignatureConfiguration("12345678901234567890123456789012"));
			token = generator.generate(profile.get());
		}
		return new Gson().toJson(MapUtil.from("token", token));
	}


}
