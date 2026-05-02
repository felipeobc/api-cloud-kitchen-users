package br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private String name;

    private String email;

    private String login;

    private String address;

    private boolean owner;
}
