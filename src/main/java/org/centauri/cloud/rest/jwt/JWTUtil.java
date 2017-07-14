package org.centauri.cloud.rest.jwt;

public class JWTUtil {

//	private static Algorithm algorithm;
//	private static JWTVerifier adminVerifier;
//	private static JWTVerifier userVerifier;
//	private static char[] chars = "QWERTZUIOPASDFGHJKLYXCVBNMqwertzuiopasdfghjklyxcvbnm1234567890".toCharArray();
//
//	public static void init() {
//		try {
//			algorithm = Algorithm.HMAC512(JWTUtil.generateSecret());
//			adminVerifier = JWT.require(algorithm).withClaim("role", LoginFilter.UserType.ADMIN.getStringed()).build();
//			userVerifier = JWT.require(algorithm).withClaim("role", LoginFilter.UserType.USER.getStringed()).build();
//		} catch (UnsupportedEncodingException e) {
//			Cloud.getLogger().error("Encoding exception", e);
//		}
//	}
//
//	public static String generateToken(LoginFilter.UserType type, Request request) {
//		return JWT.create().withClaim("role", type.getStringed()).withClaim("ip", getIp(request)).withExpiresAt(Date.from(Instant.now().plus(Duration.ofHours(2)))).sign(algorithm);
//	}
//
//	public static String generateSecret() {
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i < 32; i++) {
//			builder.append(chars[(int) Math.round(Math.random() * (chars.length - 1))]);
//		}
//		return builder.toString();
//	}
//
//
//	public static DecodedJWT validateJWT(String token, LoginFilter.UserType type) throws AuthException {
//		switch (type) {
//			case ADMIN:
//				return adminVerifier.verify(token);
//			case USER:
//				return userVerifier.verify(token);
//		}
//		throw new AuthException("No type found", 403);
//	}
//
//	public static void validateIp(Request request, DecodedJWT jwt) throws AuthException {
//		if (!getIp(request).equals(jwt.getClaim("ip").asString())) {
//			throw new AuthException("Wrong ip", 403);
//		}
//	}
//
//	private static String getIp(Request request) {
//		String ip = request.headers("X-Forwarded-For");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.headers("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.headers("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.headers("HTTP_CLIENT_IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.headers("HTTP_X_FORWARDED_FOR");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.ip();
//		}
//		return ip;
//	}
}
