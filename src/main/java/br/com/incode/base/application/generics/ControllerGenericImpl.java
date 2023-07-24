package br.com.incode.base.application.generics;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.incode.base.application.dto.ResponseDTO;
import br.com.incode.base.infrastructure.util.BaseRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public abstract class ControllerGenericImpl<T, ID extends Serializable> {

    @Autowired
    private BaseRepositoryImpl<T, ID> genericService;

    @GetMapping("/findAll")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obter todos os registros")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na requisicao", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "504", description = "Timeout", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
    public List<T> findAll() {
        return genericService.findAll();
    }

    @GetMapping("/findById/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obter registro por id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na requisicao", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "504", description = "Timeout", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
    public T findById(@Parameter(description = "Id do objeto") @PathVariable ID id) {
        return genericService.findById(id);
    }

    @PostMapping("/save")
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
    public T save(@RequestBody T entity) {
        return genericService.saveReturnEntity(entity, "Erro ao Salvar!");
    }

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Salvar objeto")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Objeto deletado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "504", description = "Timeout", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
    public ResponseDTO delete(@PathVariable ID id) {
        return genericService.deleteById(id, "Erro ao Deletar!");
    }

}
