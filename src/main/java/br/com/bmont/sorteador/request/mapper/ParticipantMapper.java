package br.com.bmont.sorteador.request.mapper;

import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.response.ParticipantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantMapper {
    public static ParticipantResponse toParticipantResponse(Participant participant){
        return ParticipantResponse.builder()
                .id(participant.getId())
                .name(participant.getName())
                .build();
    }

    public static Page<ParticipantResponse> toParticipantResponse(Page<Participant> participants){
        List<ParticipantResponse> participantResponses = participants.stream()
                .map(ParticipantMapper::toParticipantResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(participantResponses);
    }

    public static List<ParticipantResponse> toParticipantResponse(List<Participant> participants){
        return participants.stream()
                .map(ParticipantMapper::toParticipantResponse)
                .collect(Collectors.toList());
    }
}
