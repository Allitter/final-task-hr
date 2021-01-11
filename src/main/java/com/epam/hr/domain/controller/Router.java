package com.epam.hr.domain.controller;

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

