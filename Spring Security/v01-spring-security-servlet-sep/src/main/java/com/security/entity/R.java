package com.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class R<T> implements Serializable {
    private final static int OK = 200;
    private final static int NO = -1;

    private int status;
    private String message;
    private T data;

    public static <T> R<T> success(T body) {
        return new R<>(OK, null, body);
    }

    public static <T> R<T> success(String message, T body) {
        return new R<>(OK, message, body);
    }

    public static R<String> error(String message) {
        return new R<>(NO, message, null);
    }

    public static R<String> error(int status, String message) {
        return new R<>(status, message, null);
    }
}
