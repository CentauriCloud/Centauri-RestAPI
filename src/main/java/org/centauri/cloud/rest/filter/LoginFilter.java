package org.centauri.cloud.rest.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.centauri.cloud.rest.jwt.AuthException;
import org.centauri.cloud.rest.jwt.JWTUtil;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

	private final UserType type;

	@Override
	public void handle(Request request, Response response) throws Exception {

		try {
			String token = request.cookie("Badge");
			if (token == null)
				throw new AuthException("Token null", 401);
			DecodedJWT jwt = JWTUtil.validateJWT(token, type);
			JWTUtil.validateIp(request, jwt);
		} catch (AuthException e) {
			halt(e.getStatus(), e.getMessage());
		} catch (JWTVerificationException e) {
			halt(403);
		}
	}

	public enum UserType {
		ADMIN("admin"), USER("user");

		@Getter private String stringed;

		UserType(String stringed) {
			this.stringed = stringed;
		}
	}
}
