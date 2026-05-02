package br.com.fiap.tech.challenge.cloud.kitchen.user.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
