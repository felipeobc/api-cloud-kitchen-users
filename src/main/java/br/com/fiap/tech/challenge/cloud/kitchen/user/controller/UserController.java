package br.com.fiap.tech.challenge.cloud.kitchen.user.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos.*;
import br.com.fiap.tech.challenge.cloud.kitchen.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ProblemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(
            summary = "Realiza login do usuário",
            description = "Autentica um usuário usando login e senha."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return userService.login(request);
    }

    @GetMapping
    @Operation(
            summary = "Lista usuários",
            description = "Retorna uma lista paginada de usuários cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuários listados com sucesso"
            )
    })
    public Page<UserDTO> findAll(@ParameterObject Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Busca usuários por nome",
            description = "Retorna uma lista paginada de usuários filtrados pelo nome informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro obrigatório ausente ou inválido",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public Page<UserDTO> findByName(
            @Parameter(description = "Nome ou parte do nome do usuário", example = "João")
            @RequestParam String name,
            @ParameterObject Pageable pageable
    ) {
        return userService.findByName(name, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cria um usuário",
            description = "Cadastra um novo usuário na plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail ou login já cadastrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public UserDTO create(@RequestBody @Valid CreateUserRequestDTO request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um usuário",
            description = "Atualiza os dados cadastrais de um usuário existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail ou login já cadastrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public UserDTO update(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDTO request
    ) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Remove um usuário",
            description = "Remove logicamente um usuário da plataforma."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public void delete(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Altera senha do usuário",
            description = "Altera a senha de um usuário existente mediante senha atual."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Senha alterada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou senha atual incorreta",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public void changePassword(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordDTO request
    ) {
        userService.changePassword(id, request);
    }

    @PatchMapping("/recover-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Recupera senha do usuário",
            description = "Define uma nova senha para o usuário a partir do e-mail informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Senha recuperada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    public void recoverPassword(@RequestBody @Valid RecoverPasswordDTO request) {
        userService.recoverPassword(request);
    }
}
