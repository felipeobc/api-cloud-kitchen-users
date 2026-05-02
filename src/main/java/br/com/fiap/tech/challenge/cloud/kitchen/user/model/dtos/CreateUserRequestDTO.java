package br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos;

import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Login é obrigatório")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotBlank(message = "Endereço é obrigatório")
    private String address;

    private boolean owner;

    public User toEntity(String encryptedPassword) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(encryptedPassword);
        user.setAddress(address);
        user.setActive(true);
        user.setOwner(owner);
        return user;
    }
}