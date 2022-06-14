package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.mapper.ParticipantMapper;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.GroupRepository;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import br.com.bmont.sorteador.response.ParticipantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RaffleService {
    private final ParticipantRepository participantRepository;
    private final GroupRepository groupRepository;

    public Page<ParticipantResponse> getClassifiedParticipants(Long groupId, Pageable pageable, UserDetails userDetails) {
        User user = (User) userDetails;
        Optional.ofNullable(groupRepository.findGroupById(groupId, user.getId()))
                .orElseThrow(() -> new BadRequestException("Group not found"));
        Page<Participant> participants = participantRepository.findParticipantsByGroupIdOrderByRandom(groupId, pageable);
        return ParticipantMapper.toParticipantResponse(participants);
    }

    public Page<ParticipantResponse> getClassifiedActiveParticipants(Long groupId, Pageable pageable, UserDetails userDetails) {
        User user = (User) userDetails;
        Optional.ofNullable(groupRepository.findGroupById(groupId, user.getId()))
                .orElseThrow(() -> new BadRequestException("Group not found"));
        Page<Participant> participants = participantRepository.findActiveParticipantsByGroupIdOrderByRandom(groupId, pageable);
        return ParticipantMapper.toParticipantResponse(participants);
    }
}