package com.lambdaschool.starthere.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    //this will need to be unique every time... ie 1L 2L etc

    public ResourceNotFoundException(String message)
    {
        super(message + " Please Try Again ");
    }

    public ResourceNotFoundException(String message, Throwable cause)
    {
        super(message + " Please Try Again ", cause);
    }

}