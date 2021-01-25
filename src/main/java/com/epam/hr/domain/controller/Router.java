package com.epam.hr.domain.controller;

import java.util.Map;

/**
 * Used as {@link com.epam.hr.domain.controller.command.Command} return type
 * represents the destination page ond route type for Controller
 */
public class Router {
    private final RouteType type;
    private final String path;

    private Router(RouteType type, String path) {
        this.type = type;
        this.path = path;
    }

    /**
     * Static generation method to create router of forward type
     *
     * @param path destination
     * @return Router with parameters added to path
     */
    public static Router forward(String path) {
        return new Router(RouteType.FORWARD, path);
    }

    /**
     * Static generation method to create router of redirect type
     *
     * @param path destination
     * @return Router with parameters added to path
     */
    public static Router redirect(String path) {
        return new Router(RouteType.REDIRECT, path);
    }

    /**
     * Static generation method
     * in case there is a need to pass parameters with redirect
     *
     * @param path       destination
     * @param parameters the parameters
     * @return Router with parameters added to path
     */
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

    /**
     * Convenience method
     *
     * @return true if route type is forward
     */
    public boolean isForward() {
        return type == RouteType.FORWARD;
    }

    /**
     * Convenience method
     *
     * @return true if route type is redirect
     */
    public boolean isRedirect() {
        return type == RouteType.REDIRECT;
    }

    /**
     * @return route type
     */
    public RouteType getType() {
        return type;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * represents route type
     */
    public enum RouteType {
        REDIRECT,
        FORWARD
    }
}

