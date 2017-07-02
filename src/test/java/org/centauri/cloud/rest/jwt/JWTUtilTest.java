package org.centauri.cloud.rest.jwt;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class JWTUtilTest {

	@Test
	public void generateToken() throws Exception {
		String secret = JWTUtil.generateSecret();
		System.out.println(secret);
		String secret2 = JWTUtil.generateSecret();
		System.out.println(secret2);
		assertFalse(secret.equals(secret2));

	}

}