package com.jb.couponsystem.model;

public class ClientSession {
    private final long clientId;
    private long lastAccessMillis;
    private int logType;

    public ClientSession(long clientId, long lastAccessMillis, int logType) {
        this.clientId = clientId;
        this.lastAccessMillis = lastAccessMillis;
        this.logType = logType;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public long getClientId() {
        return clientId;
    }

    public void access() {
        lastAccessMillis = System.currentTimeMillis();
    }

    public long getLastAccessMillis() {
        return lastAccessMillis;
    }

    public static ClientSession create(long clientId, int logType) {
        return new ClientSession(clientId, System.currentTimeMillis(), logType);
    }
}
