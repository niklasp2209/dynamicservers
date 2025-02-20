package de.bukkitnews.servermanager.service;

import de.bukkitnews.servermanager.model.Role;
import de.bukkitnews.servermanager.model.User;
import de.bukkitnews.servermanager.repository.RoleRepository;
import de.bukkitnews.servermanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final @NotNull UserRepository userRepository;
    private final @NotNull RoleRepository roleRepository;
    private final @NotNull PasswordEncoder passwordEncoder;

    public @NotNull Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public @NotNull User registerUser(String username, String password, String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found!"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
