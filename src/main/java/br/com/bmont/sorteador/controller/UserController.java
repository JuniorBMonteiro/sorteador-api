package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.dtos.request.UserRequestDTO;
import br.com.bmont.sorteador.dtos.response.UserResponseDTO;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> userRegister(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userDetailsService.registerUser(userRequestDTO), HttpStatus.OK);
    }
}
