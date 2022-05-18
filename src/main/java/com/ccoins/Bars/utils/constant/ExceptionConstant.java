package com.ccoins.Bars.utils.constant;

public class ExceptionConstant {

    //LABELS
    public static final String ERROR_LABEL = "Error when trying to ";
    public static final String LOGIN_WITH_ERROR_LABEL = ERROR_LABEL.concat("login with ");
    public static final String GET_ERROR_LABEL = ERROR_LABEL.concat("get ");
    public static final String CREATE_NEW_ERROR_LABEL = ERROR_LABEL.concat("create new ");

    public static final String CREATE_OR_REPLACE_ERROR_LABEL = ERROR_LABEL.concat("create or replace ");

    //ERRORS
    public static final String GENERIC_ERROR_CODE = "0001";
    public static final String GENERIC_ERROR = "Something went wrong! Check with your administrator";

    public static final String BAR_CREATE_OR_UPDATE_ERROR_CODE = "0002";
    public static final String BAR_CREATE_OR_UPDATE_ERROR = CREATE_NEW_ERROR_LABEL.concat("bar");


}
