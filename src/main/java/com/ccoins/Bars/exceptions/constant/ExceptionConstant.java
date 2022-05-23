package com.ccoins.Bars.exceptions.constant;

public class ExceptionConstant {

    //LABELS
    public static final String ERROR_LABEL = "Error when trying to ";
    public static final String GET_ERROR_LABEL = ERROR_LABEL.concat("get ");
    public static final String CREATE_NEW_ERROR_LABEL = ERROR_LABEL.concat("create new ");

    public static final String CREATE_OR_REPLACE_ERROR_LABEL = ERROR_LABEL.concat("create or replace ");

    //ERRORS
    public static final String GENERIC_ERROR_CODE = "0001";
    public static final String GENERIC_ERROR = "Something went wrong! Check with your administrator";

    public static final String BAR_CREATE_OR_UPDATE_ERROR_CODE = "0002";
    public static final String BAR_CREATE_OR_UPDATE_ERROR = CREATE_NEW_ERROR_LABEL.concat("bar");

    public static final String BAR_FIND_BY_OWNER_ERROR_CODE = "0003";
    public static final String BAR_FIND_BY_OWNER_ERROR = GET_ERROR_LABEL.concat("bars by owner");

    public static final String BAR_FIND_BY_ID_ERROR_CODE = "0004";
    public static final String BAR_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("bar by id");


}
