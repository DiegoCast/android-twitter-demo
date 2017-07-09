package com.diegocast.twitterapp;

/**
 * Placeholder class to retrieve constants from build config
 */
public class Constants {
    // It's good practise to retrieve api url constants from build configuration files after
    // compilation. This way we can also specify different endpoints for different builds:
    public static String APIURL = BuildConfig.APIURL;
}
