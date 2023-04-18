package com.fsck.k9.backend;

public class RequestBody {
    private String key;
    private String message;

    public RequestBody(String key, String content) {
        this.key = key;
        this.message = content;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
