package com.rined.justtalk.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.stream.Collectors;

public final class ControllerUtils {
    private ControllerUtils(){}

    @SuppressWarnings("ConstantConditions")
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                DefaultMessageSourceResolvable::getDefaultMessage
        ));
    }
}
