package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.mapper.ParticipantMapper;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.GroupRepository;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import br.com.bmont.sorteador.request.ParticipantRequest;
import br.com.bmont.sorteador.request.NameRequest;
import br.com.bmont.sorteador.response.ParticipantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    private Participant getParticipantByIdOrThrowBadRequestException(Long participantId){
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new BadRequestException("Participant not found"));
    }

    public Page<ParticipantResponse> getAllParticipants(Long groupId, Pageable pageable, UserDetails userDetails) {
        User user = (User) userDetails;
        Optional.ofNullable(groupRepository.findGroupById(groupId, user.getId()))
                .orElseThrow(() -> new BadRequestException("Group not found"));
        Page<Participant> participants = participantRepository.findParticipantsByGroupId(groupId, pageable);
        return ParticipantMapper.toParticipantResponse(participants);
    }

    public ParticipantResponse addParticipant(ParticipantRequest participantRequest, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = groupService.getGroupByIdOrThrowBadRequestException(
                participantRequest.getGroupId(), user.getId());
        Participant participant = Participant.builder()
                .name(participantRequest.getName())
                .group(group)
                .isActive(true)
                .build();
        Participant participantSaved = participantRepository.save(participant);
        return ParticipantMapper.toParticipantResponse(participantSaved);
    }

    public void removeParticipant(Long participantId, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        if (isNotPermitted(participant, user)){
            throw new BadRequestException("Participant not found");
        }
        participantRepository.delete(participant);
    }

    public void updateParticipant(Long participantId, NameRequest nameRequest, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        if (isNotPermitted(participant, user)){
            throw new BadRequestException("Participant not found");
        }
        participant.setName(nameRequest.getName());
        participantRepository.save(participant);
    }

    public void changeActive(Long participantId, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        if (isNotPermitted(participant, user)){
            throw new BadRequestException("Participant not found");
        }
        participant.setActive(!participant.isActive());
        participantRepository.save(participant);
    }

    private boolean isNotPermitted(Participant participant, User user){
        Long userIdByParticipant = participant.getGroup().getUser().getId();
        Long userId = user.getId();
        return !userIdByParticipant.equals(userId);
    }
}