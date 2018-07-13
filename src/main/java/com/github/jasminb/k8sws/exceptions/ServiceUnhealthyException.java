package com.github.jasminb.k8sws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown from the API calls in case service is not ready to serve requests.
 *
 * @author jbegic
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Service is not ready to accept requests.")
public class ServiceUnhealthyException extends Exception {
}
