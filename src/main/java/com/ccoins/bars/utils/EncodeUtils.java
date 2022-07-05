package com.ccoins.bars.utils;

import com.ccoins.bars.exceptions.BadRequestException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class EncodeUtils {

    public static String generateCode(){

        try {
            LocalDateTime date = LocalDateTime.now();

            String toEncode = String.valueOf(date.hashCode());

            return Hashing.sha256()
                    .hashString(toEncode, StandardCharsets.UTF_8)
                    .toString();
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.QR_CODE_GENERATION_ERROR_CODE,
                    EncodeUtils.class, ExceptionConstant.QR_CODE_GENERATION_ERROR);
        }
    }

    private EncodeUtils() {
    }
}
