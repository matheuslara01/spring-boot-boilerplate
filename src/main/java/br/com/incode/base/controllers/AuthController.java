package br.com.incode.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.incode.base.common.generics.Response;
import br.com.incode.base.models.dto.LoginDTO;
import br.com.incode.base.models.dto.TokenDTO;
import br.com.incode.base.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	@Operation(summary = "Login de Usuários na API")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Usuário ou senha Inválidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "401", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public TokenDTO authenticator(@RequestBody LoginDTO form) {
		return authenticationService.login(form);
	}

	
}
