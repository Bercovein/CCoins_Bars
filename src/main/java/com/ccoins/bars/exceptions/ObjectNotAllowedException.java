package com.ccoins.bars.exceptions;

import static java.text.MessageFormat.format;

public class ObjectNotAllowedException extends CustomException {

    public ObjectNotAllowedException(String code, Class<?> object) {
        super(code, format("{0}NotAllowed", object.getSimpleName()));
    }


}
