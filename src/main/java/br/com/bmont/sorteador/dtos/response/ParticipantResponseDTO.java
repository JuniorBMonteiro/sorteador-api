package br.com.bmont.sorteador.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponseDTO {
    private Long id;
    private String name;
}
