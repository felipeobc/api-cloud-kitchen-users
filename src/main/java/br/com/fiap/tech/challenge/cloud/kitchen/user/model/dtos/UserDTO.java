package br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos;

import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String address;
    private boolean active;
    private boolean owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getAddress(),
                user.isActive(),
                user.isOwner(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}