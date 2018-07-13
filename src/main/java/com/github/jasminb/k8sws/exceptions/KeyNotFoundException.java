package com.github.jasminb.k8sws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown from the API calls in case requested key is not found.
 *
 * @author jbegic
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Key not found.")
public class KeyNotFoundException extends Exception {
}
