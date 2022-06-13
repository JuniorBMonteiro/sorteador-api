package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.UserRequest;
import br.com.bmont.sorteador.response.UserResponse;
import br.com.bmont.sorteador.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping
    public ResponseEntity<UserResponse> userRegister(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userDetailsService.registerUser(userRequest), HttpStatus.OK);
    }
}
