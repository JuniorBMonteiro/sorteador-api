package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.repository.GroupRepository;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RaffleService {
    private final ParticipantRepository participantRepository;
    private final GroupService groupService;

    public List<Participant> getClassifiedParticipants(Long groupId) {
            groupService.getGroupByIdOrThrowBadRequestException(groupId);
            List<Participant> participants = participantRepository.findParticipantsByGroupId(groupId);
            Collections.shuffle(participants);
            return participants;
    }

    public List<Participant> getClassifiedActiveParticipants(Long groupId) {
        groupService.getGroupByIdOrThrowBadRequestException(groupId);
        List<Participant> participants = participantRepository.findActiveParticipantsByGroupId(groupId);
        Collections.shuffle(participants);
        return participants;
    }
}