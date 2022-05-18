package com.ccoins.Bars.utils;

import org.modelmapper.ModelMapper;

public class MapperUtils {

    public static Object map(Object obj, Class<?> cla){
        ModelMapper mapper =  new ModelMapper();
        return mapper.map(obj,cla);
    }
}
