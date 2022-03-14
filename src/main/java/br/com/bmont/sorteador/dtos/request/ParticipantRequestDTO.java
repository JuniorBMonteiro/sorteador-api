package br.com.bmont.sorteador.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequestDTO {
    private String name;
    private Long groupId;
}
