package models;


import models.figures.AuthorizedUser;

import javax.servlet.http.HttpSession;

public class SystemUser {
    private static AuthorizedUser user;
    private static HttpSession session;

    private SystemUser() {
    }

    public static void init(AuthorizedUser user, HttpSession session) {
        SystemUser.user = user;
        SystemUser.session = session;
    }

    public static void exit() {
        SystemUser.user = null;
        SystemUser.session = null;
    }

    public static boolean isPresent() {
        return user != null && session != null;
    }

    public static AuthorizedUser getUser() {
        return user;
    }

    public static HttpSession getSession() {
        return session;
    }
}
