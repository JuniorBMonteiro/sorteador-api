package br.com.bmont.sorteador.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDTO {
    @NotEmpty(message = "The group name cannot be empty")
    private String name;
}
