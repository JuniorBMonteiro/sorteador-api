package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.UserRequestDTO;
import br.com.bmont.sorteador.dtos.response.UserResponseDTO;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User userSaved = userRepository.findByUsername(userRequestDTO.getUsername());
        if (userSaved != null)
            throw new BadRequestException("User is already registered ");

        User user = User.builder()
                .name(userRequestDTO.getName())
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .authorities("USER")
                .build();
        userRepository.save(user);
        return new UserResponseDTO(user.getName(), user.getUsername());
    }
}
