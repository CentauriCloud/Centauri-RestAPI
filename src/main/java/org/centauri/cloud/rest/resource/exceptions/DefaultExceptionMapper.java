package org.centauri.cloud.rest.resource.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.centauri.cloud.rest.to.exception.ExceptionTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.centauri.cloud.rest.util.ResponseFactory.fail;

public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		return fail(new ExceptionTO(ExceptionUtils.getStackTrace(exception), exception.getMessage()));
	}
}
