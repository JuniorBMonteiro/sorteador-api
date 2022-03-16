package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.ParticipantRequestDTO;
import br.com.bmont.sorteador.dtos.response.ParticipantResponseDTO;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final GroupService groupService;

    public Participant getParticipantByIdOrThrowBadRequestException(Long participantId){
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new BadRequestException("Participant not found"));
    }

    public Page<ParticipantResponseDTO> getParticipantsByGroupId(Long groupId, Pageable pageable, UserDetails userDetails){
        User user = (User) userDetails;
        Group group = groupService.getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        Page<Participant> participants = participantRepository.findParticipantsByGroupId(group.getId(), pageable);
        return ParticipantResponseDTO.convert(participants);
    }

    @Transactional
    public Participant addParticipant(ParticipantRequestDTO participantRequestDTO, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = groupService.getGroupByIdOrThrowBadRequestException(
                participantRequestDTO.getGroupId(), user.getId());
        Participant participant = Participant.builder()
                .name(participantRequestDTO.getName())
                .group(group)
                .isActive(true)
                .build();
        return participantRepository.save(participant);
    }

    @Transactional
    public void removeParticipant(Long participantId, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        List<Group> groups = user.getGroups();
        groups.stream().filter(e -> e.equals(participant.getGroup()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not Permission"));
        participantRepository.delete(participant);
    }

    @Transactional
    public void updateParticipant(Long participantId, ParticipantRequestDTO participantRequestDTO, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        user.getGroups().stream().filter(e -> e.equals(participant.getGroup()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not Permission"));
        participant.setName(participantRequestDTO.getName());
        participantRepository.save(participant);
    }

    @Transactional
    public void changeActive(Long participantId, UserDetails userDetails) {
        User user = (User) userDetails;
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        user.getGroups().stream().filter(e -> e.equals(participant.getGroup()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not Permission"));
        participant.setActive(!participant.isActive());
        participantRepository.save(participant);
    }
}