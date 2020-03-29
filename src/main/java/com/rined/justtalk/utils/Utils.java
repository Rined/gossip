package com.rined.justtalk.utils;

import com.rined.justtalk.model.User;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Utils {
    private Utils() {
    }

    @SuppressWarnings("ConstantConditions")
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                DefaultMessageSourceResolvable::getDefaultMessage
        ));
    }

    public static String getAuthorName(User user) {
        return Objects.isNull(user) ? "<none>" : user.getUsername();
    }
}
