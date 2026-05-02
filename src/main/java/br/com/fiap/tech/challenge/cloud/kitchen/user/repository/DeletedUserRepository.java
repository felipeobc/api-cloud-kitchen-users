package br.com.fiap.tech.challenge.cloud.kitchen.user.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}
