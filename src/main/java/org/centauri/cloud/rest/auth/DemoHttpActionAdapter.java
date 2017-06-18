package org.centauri.cloud.rest.auth;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;
import org.pac4j.sparkjava.SparkWebContext;

import static spark.Spark.halt;

public class DemoHttpActionAdapter extends DefaultHttpActionAdapter {
	@Override
	public Object adapt(int code, SparkWebContext webContext) {
		if (code == HttpConstants.UNAUTHORIZED) {
			halt(401);
		} else if (code == HttpConstants.FORBIDDEN) {
			halt(403);
		} else {
			return super.adapt(code, webContext);
		}
		return null;
	}
}
