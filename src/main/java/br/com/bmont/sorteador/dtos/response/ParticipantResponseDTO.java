package br.com.bmont.sorteador.dtos.response;

import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponseDTO {
    private Long id;
    private String name;

    public ParticipantResponseDTO(Participant participant) {
        this.id = participant.getId();
        this.name = participant.getName();
    }

    public static Page<ParticipantResponseDTO> convert(Page<Participant> participants){
        List<ParticipantResponseDTO> participantResponseDTOS = participants.stream()
                .map(ParticipantResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(participantResponseDTOS);
    }
}
