package br.com.bmont.sorteador.mapper;

import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.response.GroupResponse;
import br.com.bmont.sorteador.response.ParticipantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupResponse toGroupResponse(Group group){
        List<ParticipantResponse> participants = null;
        if (Objects.nonNull(group.getParticipants())){
            participants = ParticipantMapper.toParticipantResponse(group.getParticipants());
        }
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .participants(participants)
                .build();
    }

    public static Page<GroupResponse> toGroupResponse(Page<Group> groups){
        List<GroupResponse> participantResponses = groups.stream()
                .map(GroupMapper::toGroupResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(participantResponses);
    }
}
