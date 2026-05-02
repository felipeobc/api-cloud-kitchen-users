package br.com.fiap.tech.challenge.cloud.kitchen.user.service;

import br.com.fiap.tech.challenge.cloud.kitchen.user.exception.AuthenticationException;
import br.com.fiap.tech.challenge.cloud.kitchen.user.exception.BusinessException;
import br.com.fiap.tech.challenge.cloud.kitchen.user.exception.ConflictException;
import br.com.fiap.tech.challenge.cloud.kitchen.user.exception.ResourceNotFoundException;
import br.com.fiap.tech.challenge.cloud.kitchen.user.model.dtos.*;
import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.DeletedUser;
import br.com.fiap.tech.challenge.cloud.kitchen.user.model.entities.User;
import br.com.fiap.tech.challenge.cloud.kitchen.user.repository.DeletedUserRepository;
import br.com.fiap.tech.challenge.cloud.kitchen.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeletedUserRepository deletedUserRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new AuthenticationException("Login ou senha inválidos."));

        if (!user.isActive()) {
            throw new AuthenticationException("Usuário inativo.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean passwordValid = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordValid) {
            throw new AuthenticationException("Login ou senha inválidos.");
        }

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.isOwner(),
                "Login realizado com sucesso."
        );
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::fromEntity);
    }

    public Page<UserDTO> findByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(UserDTO::fromEntity);
    }

    public UserDTO create(CreateUserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Já existe um usuário cadastrado com este e-mail.");
        }

        String passwordHash = new BCryptPasswordEncoder().encode(request.getPassword());

        User user = request.toEntity(passwordHash);

        User savedUser = userRepository.save(user);

        return UserDTO.fromEntity(savedUser);
    }

    public UserDTO update(Long id, UpdateUserDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ConflictException("Já existe outro usuário cadastrado com este e-mail.");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setAddress(request.getAddress());
        user.setOwner(request.isOwner());

        User updatedUser = userRepository.save(user);

        return UserDTO.fromEntity(updatedUser);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if (!user.isActive()) {
            throw new BusinessException("Usuário já está inativo.");
        }

        DeletedUser deletedUser = DeletedUser.fromUser(user);
        deletedUserRepository.save(deletedUser);

        user.setActive(false);
        userRepository.save(user);
    }

    public void changePassword(Long id, ChangePasswordDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException("Senha atual inválida.");
        }

        String newPasswordHash = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(newPasswordHash);

        userRepository.save(user);
    }

    public void recoverPassword(RecoverPasswordDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o e-mail informado."));

        String newPasswordHash = new BCryptPasswordEncoder().encode(request.getNewPassword());

        user.setPassword(newPasswordHash);

        userRepository.save(user);
    }
}
