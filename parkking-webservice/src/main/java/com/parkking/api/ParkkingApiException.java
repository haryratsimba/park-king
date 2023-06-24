package com.parkking.api;

import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.Getter;
import lombok.Setter;

public class ParkkingApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private int httpCode;
    
    public ParkkingApiException(int httpCode) {
        super();
        this.httpCode = httpCode;
    }
    
    public ParkkingApiException(String s, int httpCode) {
        super(s);
        this.httpCode = httpCode;
    }
    
    /**
     * Gets an exception description and stack trace in a string.
     * @param e the exception
     * @return the string
     */
    public static String getExceptionString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
        return sw.toString();
    }
    
}
