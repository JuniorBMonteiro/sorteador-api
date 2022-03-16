package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.dtos.request.GroupRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupByIdOrThrowBadRequestException(Long groupId, Long userId){
        return Optional.ofNullable(groupRepository.findGroupById(groupId, userId))
                .orElseThrow(() -> new BadRequestException("Group not found"));
    }

    public Page<GroupResponseDTO> getAllGroups(Pageable pageable, UserDetails userDetails){
        User user = (User) userDetails;
        Page<Group> groups = groupRepository.findAllGroupsByUser(user.getId(), pageable);
        return GroupResponseDTO.convert(groups);
    }

    @Transactional
    public GroupResponseDTO createGroup(GroupRequestDTO groupRequestDTO, UserDetails userDetails){
        User user = (User) userDetails;
        Group group = Group.builder()
                .name(groupRequestDTO.getName())
                .user(user)
                .build();
        groupRepository.save(group);
        return new GroupResponseDTO(group);
    }

    @Transactional
    public void deleteGroup(Long groupId, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        groupRepository.delete(group);
    }

    @Transactional
    public void updateGroup(Long groupId, GroupRequestDTO groupRequestDTO, UserDetails userDetails){
        User user = (User) userDetails;
        Group group = getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        group.setName(groupRequestDTO.getName());
        groupRepository.save(group);
    }
}