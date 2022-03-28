package com.bobalalu.blog.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Babatunde Obalalu - bobalalu@yahoo.com - +2348034627801
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
