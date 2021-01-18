package com.epam.hr.domain.controller;

import java.util.Map;

public class Router {
    private final RouteType type;
    private final String path;

    private Router(RouteType type, String path) {
        this.type = type;
        this.path = path;
    }

    public static Router forward(String path) {
        return new Router(RouteType.FORWARD, path);
    }

    public static Router redirect(String path) {
        return new Router(RouteType.REDIRECT, path);
    }

    public static Router redirect(String path, Map<String, String> parameters) {
        path = addParametersToPath(path, parameters);
        return new Router(RouteType.REDIRECT, path);
    }

    private static String addParametersToPath(String path, Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder(path);
        if (parameters.size() > 0) {
            if (path.contains("?")) {
                stringBuilder.append("&");
            } else {
                stringBuilder.append("?");
            }
        }

        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            stringBuilder.append(String.format("%s=%s&", parameter.getKey(), parameter.getValue()));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public boolean isForward() {
        return type == RouteType.FORWARD;
    }

    public boolean isRedirect() {
        return type == RouteType.REDIRECT;
    }

    public RouteType getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public enum RouteType {
        REDIRECT,
        FORWARD
    }
}

