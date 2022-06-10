package com.ccoins.Bars.utils;

import com.ccoins.Bars.exceptions.BadRequestException;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class QRUtils {

    public static String generateCode(){

        try {
            LocalDateTime date = LocalDateTime.now();

            String toEncode = String.valueOf(date.hashCode());

            return Hashing.sha256()
                    .hashString(toEncode, StandardCharsets.UTF_8)
                    .toString();
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.QR_CODE_GENERATION_ERROR_CODE,
                    QRUtils.class, ExceptionConstant.QR_CODE_GENERATION_ERROR);
        }
    }
}
