package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.GroupRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupByIdOrThrowBadRequestException(Long id){
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found"));
    }

    public List<GroupResponseDTO> getAllGroups(){
        return GroupResponseDTO.convert(groupRepository.findAll());
    }

    public Group createGroup(GroupRequestDTO groupRequestDTO){
        Group group = new Group();
        group.setName(groupRequestDTO.getName());
        return groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.delete(getGroupByIdOrThrowBadRequestException(groupId));
    }

    public void updateGroupName(Long groupId, GroupRequestDTO groupRequestDTO){
        Group group = getGroupByIdOrThrowBadRequestException(groupId);
        group.setName(groupRequestDTO.getName());
        groupRepository.save(group);
    }
}