package br.com.bmont.sorteador.dtos.response;

import br.com.bmont.sorteador.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
        this.participants = group.getParticipants().stream()
                .map(e -> new ParticipantResponseDTO(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }

    public static List<GroupResponseDTO> convert(List<Group> group){
        return group.stream().map(GroupResponseDTO::new).collect(Collectors.toList());
    }
}
