package org.centauri.cloud.rest.filter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter {

//	private final UserType type;
//
//	@Override
//	public void handle(Request request, Response response) throws Exception {
//
//		try {
//			String token = request.cookie("Badge");
//			if (token == null)
//				throw new AuthException("Token null", 401);
//			DecodedJWT jwt = JWTUtil.validateJWT(token, type);
//			JWTUtil.validateIp(request, jwt);
//		} catch (AuthException e) {
//			halt(e.getStatus(), e.getMessage());
//		} catch (JWTVerificationException e) {
//			halt(403);
//		}
//	}
//
//	public enum UserType {
//		ADMIN("admin"), USER("user");
//
//		@Getter private String stringed;
//
//		UserType(String stringed) {
//			this.stringed = stringed;
//		}
//	}
}
