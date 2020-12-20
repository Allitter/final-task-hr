package com.epam.hr.domain.logic;

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

    public RouteType getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
