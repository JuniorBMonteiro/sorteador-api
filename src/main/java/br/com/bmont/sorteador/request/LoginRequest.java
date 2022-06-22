package br.com.bmont.sorteador.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "The username cannot be empty")
    private String username;
    @NotEmpty(message = "The password cannot be empty")
    private String password;
}
