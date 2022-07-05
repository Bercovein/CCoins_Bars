package com.ccoins.Bars.utils;

import com.ccoins.Bars.utils.enums.StateEnum;

public class StateUtils {

    public static boolean isActive(String status){
        boolean state;
        if(status.equals(StateEnum.ACTIVE.getValue())){
            state = StateEnum.ACTIVE.isStatus();
        }else{
            state = StateEnum.UNACTIVE.isStatus();
        }
        return state;
    }
}
