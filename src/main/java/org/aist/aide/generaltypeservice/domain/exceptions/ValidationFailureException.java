package org.aist.aide.generaltypeservice.domain.exceptions;

public class ValidationFailureException extends Exception {
    public ValidationFailureException(String str) {
        super(str);
    }
}
