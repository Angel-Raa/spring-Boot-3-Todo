package com.caja.ideal.utils;

public class AppConstants {
    public static final String[] SECURED_URLs = {"/task/**"};
    public static final String[] NO_SECURED_URLs = {"/task/{id}", "/task/all"};
    public static final String[] AUTHENTICATE = {"/auth/**", "/user/**"};

}