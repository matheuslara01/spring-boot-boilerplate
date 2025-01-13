package br.com.incode.base.infrastructure.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.incode.base.application.usecases.user.get.GetUserUseCase;
import br.com.incode.base.application.usecases.user.get.UserOutput;
import br.com.incode.base.application.usecases.user.save.SaveUserInput;
import br.com.incode.base.application.usecases.user.save.SaveUserUseCase;
import br.com.incode.base.domain.repositories.UserRepository;
import br.com.incode.base.infrastructure.util.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class UserController {

    GetUserUseCase getUserUseCase;
    SaveUserUseCase saveUserUseCase;
    
    public UserController(UserRepository userRepository) {
        this.getUserUseCase = new GetUserUseCase(userRepository);
        this.saveUserUseCase = new SaveUserUseCase(userRepository);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obter registro por id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na requisicao", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserOutput.class)) }),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "504", description = "Timeout", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
    public UserOutput getUserUseCase(@Parameter(description = "Id do usuário") @PathVariable Long id) {
        return getUserUseCase.execute(id);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Salvar objeto")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Objeto criado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "504", description = "Timeout", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
    public ResponseEntity<UserOutput> save(@RequestBody SaveUserInput form) {
       UserOutput userOutput = saveUserUseCase.execute(form);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userOutput.id())
                .toUri();

        return ResponseEntity.created(location).body(userOutput);
    }
}
