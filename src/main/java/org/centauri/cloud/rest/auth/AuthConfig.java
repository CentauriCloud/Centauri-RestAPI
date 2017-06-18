package org.centauri.cloud.rest.auth;

import lombok.AllArgsConstructor;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;

@AllArgsConstructor
public class AuthConfig implements ConfigFactory {

	private final String salt;

	@Override
	public Config build(Object... objects) {
		final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(new UsernamePasswordAuthenticator());
		ParameterClient parameterClient = new ParameterClient("token", new JwtAuthenticator(new SecretSignatureConfiguration(salt)));
		parameterClient.setSupportGetRequest(true);
		parameterClient.setSupportPostRequest(false);

		final Clients clients = new Clients("http://centauri.jooel.ch/callback", indirectBasicAuthClient, parameterClient, new AnonymousClient());

		final Config config = new Config(clients);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
		config.setHttpActionAdapter(new DemoHttpActionAdapter());
		return config;
	}
}
