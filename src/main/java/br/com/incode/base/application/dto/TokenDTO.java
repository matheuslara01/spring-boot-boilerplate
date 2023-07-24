package br.com.incode.base.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private String tipo;
}
