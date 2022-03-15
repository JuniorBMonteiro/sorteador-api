package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.GroupRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupByIdOrThrowBadRequestException(Long id){
        return groupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Group not found"));
    }

    public Page<GroupResponseDTO> getAllGroups(Pageable pageable){
        return GroupResponseDTO.convert(groupRepository.findAll(pageable));
    }

    @Transactional
    public Group createGroup(GroupRequestDTO groupRequestDTO){
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.delete(getGroupByIdOrThrowBadRequestException(groupId));
    }

    @Transactional
    public void updateGroup(Long groupId, GroupRequestDTO groupRequestDTO){
        Group group = getGroupByIdOrThrowBadRequestException(groupId);
        group.setName(groupRequestDTO.getName());
        groupRepository.save(group);
    }
}