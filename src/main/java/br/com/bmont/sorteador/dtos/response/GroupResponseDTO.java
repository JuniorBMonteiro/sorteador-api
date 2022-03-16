package br.com.bmont.sorteador.dtos.response;

import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class GroupResponseDTO {
    private Long id;
    private String name;
    private List<ParticipantResponseDTO> participants;

    public GroupResponseDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.participants = group.getParticipants() != null
                ? convertParticipants(group.getParticipants())
                : null ;
    }

    public static Page<GroupResponseDTO> convert(Page<Group> group){
        List<GroupResponseDTO> groups = group.stream().map(GroupResponseDTO::new).collect(Collectors.toList());
        return new PageImpl<>(groups);
    }

    public static List<ParticipantResponseDTO> convertParticipants(List<Participant> participants){
        return participants.stream()
                .map(e -> new ParticipantResponseDTO(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }
}
