package br.com.bmont.sorteador.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameRequest {
    @NotEmpty(message = "The participant name cannot be empty")
    private String name;
}
