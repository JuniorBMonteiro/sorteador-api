package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.LoginRequest;
import br.com.bmont.sorteador.response.TokenResponse;
import br.com.bmont.sorteador.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<TokenResponse> authentication(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(loginRequest), HttpStatus.OK);
    }
}
