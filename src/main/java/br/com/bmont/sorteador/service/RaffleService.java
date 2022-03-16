package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.GroupRepository;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RaffleService {
    private final ParticipantRepository participantRepository;
    private final GroupRepository groupRepository;

    public List<Participant> getClassifiedParticipants(Long groupId, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = Optional.ofNullable(groupRepository.findGroupById(groupId, user.getId()))
                .orElseThrow(() -> new BadRequestException("Group not found"));
        List<Participant> participants = participantRepository.findParticipantsByGroupId(group.getId());
        Collections.shuffle(participants);
        return participants;
    }

    public List<Participant> getClassifiedActiveParticipants(Long groupId, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = Optional.ofNullable(groupRepository.findGroupById(groupId, user.getId()))
                .orElseThrow(() -> new BadRequestException("Group not found"));
        List<Participant> participants = participantRepository.findActiveParticipantsByGroupId(group.getId());
        Collections.shuffle(participants);
        return participants;
    }
}