package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.ParticipantRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.dtos.response.ParticipantResponseDTO;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.repository.GroupRepository;
import br.com.bmont.sorteador.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<ParticipantResponseDTO> getParticipantsByGroupId(Long groupId){
        Group group = groupService.getGroupByIdOrThrowBadRequestException(groupId);
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO(group);
        return groupResponseDTO.getParticipants();
    }

    @Transactional
    public Participant addParticipant(ParticipantRequestDTO participantRequestDTO) {
        Group group = groupService.getGroupByIdOrThrowBadRequestException(participantRequestDTO.getGroupId());
        Participant participant = new Participant();
        participant.setName(participantRequestDTO.getName());
        participant.setGroup(group);
        participant.setActive(true);
        participantRepository.save(participant);
        return participant;
    }

    @Transactional
    public void removeParticipant(Long participantId) {
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        participantRepository.delete(participant);
    }

    @Transactional
    public void updateParticipant(Long participantId, ParticipantRequestDTO participantRequestDTO) {
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        Group group = groupService.getGroupByIdOrThrowBadRequestException(participantRequestDTO.getGroupId());
        participant.setName(participantRequestDTO.getName());
        participant.setGroup(group);
        participantRepository.save(participant);
    }

    @Transactional
    public void changeActive(Long participantId) {
        Participant participant = getParticipantByIdOrThrowBadRequestException(participantId);
        participant.setActive(!participant.isActive());
        participantRepository.save(participant);
    }
}