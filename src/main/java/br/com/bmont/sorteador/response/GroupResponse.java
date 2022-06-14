package br.com.bmont.sorteador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupResponse {
    private Long id;
    private String name;
    private List<ParticipantResponse> participants;
}
