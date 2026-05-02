package br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_deleted_users")
public class DeletedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_user_id", nullable = false)
    private Long originalUserId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean owner;

    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;

    @Column(name = "user_updated_at")
    private LocalDateTime userUpdatedAt;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    public static DeletedUser fromUser(User user) {
        DeletedUser deletedUser = new DeletedUser();
        deletedUser.setOriginalUserId(user.getId());
        deletedUser.setName(user.getName());
        deletedUser.setEmail(user.getEmail());
        deletedUser.setLogin(user.getLogin());
        deletedUser.setAddress(user.getAddress());
        deletedUser.setOwner(user.isOwner());
        deletedUser.setUserCreatedAt(user.getCreatedAt());
        deletedUser.setUserUpdatedAt(user.getUpdatedAt());
        deletedUser.setDeletedAt(LocalDateTime.now());
        return deletedUser;
    }
}
