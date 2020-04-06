package com.rined.gossip.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {

    @JsonAlias("success")
    private boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;

}
