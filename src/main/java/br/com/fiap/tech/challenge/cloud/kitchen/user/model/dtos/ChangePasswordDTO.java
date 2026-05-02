package br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    @NotBlank(message = "Senha atual é obrigatória")
    private String currentPassword;

    @NotBlank(message = "Nova senha é obrigatória")
    private String newPassword;
}
