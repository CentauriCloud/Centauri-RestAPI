package org.centauri.cloud.rest.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.centauri.cloud.cloud.Cloud;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class JWTUtil {

	public static final String ROLES = "roles";
	public static final String USER = "user";

	private static Algorithm algorithm;
	private static JWTVerifier verifier;
	private static char[] chars = "QWERTZUIOPASDFGHJKLYXCVBNMqwertzuiopasdfghjklyxcvbnm1234567890".toCharArray();

	public static void init() {
		try {
			algorithm = Algorithm.HMAC512(JWTUtil.generateSecret());
			verifier = JWT.require(algorithm).build();
		} catch (UnsupportedEncodingException e) {
			Cloud.getLogger().error("Encoding exception", e);
		}
	}

	public static String generateToken(List<String> roles, String username) {
		return JWT.create()
				.withArrayClaim(ROLES, roles.toArray(new String[roles.size()]))
				.withClaim(USER, username)
				.withExpiresAt(Date.from(Instant.now().plus(Duration.ofHours(1))))
				.sign(algorithm);
	}

	public static String generateSecret() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			builder.append(chars[(int) Math.round(Math.random() * (chars.length - 1))]);
		}
		return builder.toString();
	}


	public static JwtUser validateJWT(String token) throws AuthException {
		DecodedJWT jwt = verifier.verify(token);
		List<String> roles = jwt.getClaim(ROLES).asList(String.class);
		String username = jwt.getClaim(USER).asString();
		return new JwtUser(username, roles);
	}
}

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
