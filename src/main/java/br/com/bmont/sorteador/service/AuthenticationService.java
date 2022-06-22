package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.mapper.LoginMapper;
import br.com.bmont.sorteador.request.LoginRequest;
import br.com.bmont.sorteador.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse authenticate(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken login = LoginMapper.toLoginAuthentication(loginRequest);
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            return tokenService.createToken(authentication);
        }catch(AuthenticationException e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
