package org.centauri.cloud.rest.to.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionTO {

	private String stacktrace;
	private String message;

}
