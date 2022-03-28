package com.bobalalu.blog.post;

import java.util.Date;

/**
 *
 * @author Babatunde Obalalu - bobalalu@yahoo.com - +2348034627801
 */
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
