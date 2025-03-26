package com.workbot.workbot.telegram.newapi.context.pagination;

public class Paginations {
    private Paginations() {}

    public static final String PAGINATION_CALLBACK = "PAGINATION";

    public static final int PAGE_SIZE = 5;

    public static String generateCallback(int page) {
        return PAGINATION_CALLBACK + " " + page;
    }

    public static boolean isPagination(String callback) {
        return callback.split(" ")[0].equals(PAGINATION_CALLBACK);
    }

    public static int getPage(String callback) {
        return Integer.parseInt(callback.split(" ")[1]);
    }
}
