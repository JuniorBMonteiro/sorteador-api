package br.com.bmont.sorteador.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequestDTO {
    @NotEmpty(message = "The participant name cannot be empty")
    private String name;
    @NotNull(message = "The group id cannot be null")
    private Long groupId;
}
