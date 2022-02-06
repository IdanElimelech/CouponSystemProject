package com.jb.couponsystem.model;

public class ServerErrorResponse {
    private final String message;
    private final long timestamp;

    private ServerErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ServerErrorResponse ofNow(String message) {
        return new ServerErrorResponse(message, System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
