package br.com.bmont.sorteador.mapper;

import br.com.bmont.sorteador.request.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginMapper {
    public static UsernamePasswordAuthenticationToken toLoginAuthentication(LoginRequest loginRequest){
        return new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
