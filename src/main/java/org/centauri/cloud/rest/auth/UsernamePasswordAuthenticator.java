package org.centauri.cloud.rest.auth;

import org.centauri.cloud.rest.database.DatabaseManager;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.jwt.JwtClaims;
import org.pac4j.jwt.profile.JwtProfile;

import java.util.Date;

public class UsernamePasswordAuthenticator implements Authenticator<UsernamePasswordCredentials> {

	@Override
	public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction, CredentialsException {
		if (credentials == null)
			throwsException("not credentials");
		String username = credentials.getUsername();
		String password = credentials.getPassword();
		if (!new DatabaseManager().validate(username, password))
			throwsException("Wrong credentials");
		final JwtProfile profile = new JwtProfile();
		profile.setId(username);
		profile.addAttribute(JwtClaims.EXPIRATION_TIME, new Date(2018, 10, 10));
		profile.addAttribute(Pac4jConstants.USERNAME, username);
		profile.addPermission("bread.eat");
		credentials.setUserProfile(profile);
	}


	protected void throwsException(final String message) throws CredentialsException {
		throw new CredentialsException(message);
	}
}
