package br.com.bmont.sorteador.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequest {
    @NotEmpty(message = "The participant name cannot be empty")
    private String name;
    @NotNull(message = "The group id cannot be null")
    private Long groupId;
}
