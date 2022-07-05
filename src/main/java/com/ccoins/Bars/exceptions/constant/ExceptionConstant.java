package com.ccoins.Bars.exceptions.constant;

public class ExceptionConstant {

    //LABELS
    public static final String ERROR_LABEL = "Error when trying to ";
    public static final String GET_ERROR_LABEL = ERROR_LABEL.concat("get ");
    public static final String CREATE_NEW_ERROR_LABEL = ERROR_LABEL.concat("create new ");
    public static final String COUNT_ERROR_LABEL = ERROR_LABEL.concat("count ");
    public static final String GENERATE_ERROR_LABEL = ERROR_LABEL.concat("generate new ");

    public static final String UPDATE_ERROR_LABEL = ERROR_LABEL.concat("update ");

    public static final String CREATE_OR_REPLACE_ERROR_LABEL = ERROR_LABEL.concat("create or replace ");

    public static final String DELETE_ERROR_LABEL = ERROR_LABEL.concat("delete ");

    //ERRORS
    public static final String GENERIC_ERROR_CODE = "0001";
    public static final String GENERIC_ERROR = "Something went wrong! Check with your administrator";

    public static final String BAR_CREATE_OR_UPDATE_ERROR_CODE = "0002";
    public static final String BAR_CREATE_OR_UPDATE_ERROR = CREATE_NEW_ERROR_LABEL.concat("bar");

    public static final String BAR_FIND_BY_OWNER_ERROR_CODE = "0003";
    public static final String BAR_FIND_BY_OWNER_ERROR = GET_ERROR_LABEL.concat("bars by owner");

    public static final String BAR_FIND_BY_ID_ERROR_CODE = "0004";
    public static final String BAR_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("bar by id");

    public static final String TABLE_FIND_BAR_BY_ID_ERROR_CODE = "0005";
    public static final String TABLE_FIND_BAR_BY_ID_ERROR = BAR_FIND_BY_ID_ERROR;

    public static final String TABLE_FIND_BY_OWNER_ERROR_CODE = "0006";
    public static final String TABLE_FIND_BY_OWNER_ERROR = GET_ERROR_LABEL.concat("tables by bars");


    public static final String TABLE_FIND_BY_ID_ERROR_CODE = "0007";
    public static final String TABLE_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("table by id");

    public static final String TABLE_UPDATE_ACTIVE_ERROR_CODE = "0008";
    public static final String TABLE_UPDATE_ACTIVE_ERROR = UPDATE_ERROR_LABEL.concat("table state");

    public static final String GAME_CREATE_OR_UPDATE_ERROR_CODE = "0009";
    public static final String GAME_CREATE_OR_UPDATE_ERROR = CREATE_NEW_ERROR_LABEL.concat("game");

    public static final String GAME_FIND_BY_BAR_ERROR_CODE = "0010";
    public static final String GAME_FIND_BY_BAR_ERROR = GET_ERROR_LABEL.concat("games by bar");

    public static final String GAME_FIND_BY_ID_ERROR_CODE = "0011";
    public static final String GAME_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("game by id");

    public static final String BAR_UPDATE_ACTIVE_ERROR_CODE = "0012";
    public static final String BAR_UPDATE_ACTIVE_ERROR = UPDATE_ERROR_LABEL.concat("bar state");

    public static final String GAME_UPDATE_ACTIVE_ERROR_CODE = "0013";
    public static final String GAME_UPDATE_ACTIVE_ERROR = UPDATE_ERROR_LABEL.concat("game state");

    public static final String QR_CODE_GENERATION_ERROR_CODE = "0014";
    public static final String QR_CODE_GENERATION_ERROR = GENERATE_ERROR_LABEL.concat("qr code");

    public static final String TABLE_CREATE_LIST_ERROR_CODE = "0015";
    public static final String TABLE_CREATE_LIST_ERROR = CREATE_NEW_ERROR_LABEL.concat("table list");

    public static final String TABLE_COUNT_BY_BAR_ERROR_CODE = "0016";
    public static final String TABLE_COUNT_BY_BAR_ERROR = COUNT_ERROR_LABEL.concat("table by bar");

    public static final String TABLE_DELETE_BY_QUANTITY_ERROR_CODE = "0017";
    public static final String TABLE_DELETE_BY_QUANTITY_ERROR = DELETE_ERROR_LABEL.concat("table by quantity");

    public static final String TABLE_UPDATE_LIST_ERROR_CODE = "0018";
    public static final String TABLE_UPDATE_LIST_ERROR = UPDATE_ERROR_LABEL.concat("table by list");

    public static final String TABLE_FIND_LIST_ERROR_CODE = "0019";
    public static final String TABLE_FIND_LIST_ERROR = GET_ERROR_LABEL.concat("table by list");

    public static final String GAME_FIND_BAR_BY_ID_ERROR_CODE = "0020";
    public static final String GAME_FIND_BAR_BY_ID_ERROR = BAR_FIND_BY_ID_ERROR;
}
