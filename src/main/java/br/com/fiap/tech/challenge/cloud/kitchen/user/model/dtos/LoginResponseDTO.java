package br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String login;
    private boolean owner;
    private String message;
}
