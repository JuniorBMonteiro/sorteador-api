package br.com.bmont.sorteador.response;

import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class GroupResponse {
    private Long id;
    private String name;
    private List<ParticipantResponse> participants;
}
