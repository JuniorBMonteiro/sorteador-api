package br.com.bmont.sorteador.service;

import br.com.bmont.sorteador.request.mapper.GroupMapper;
import br.com.bmont.sorteador.request.GroupRequest;
import br.com.bmont.sorteador.response.GroupResponse;
import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getGroupByIdOrThrowBadRequestException(Long groupId, Long userId){
        return Optional.ofNullable(groupRepository.findGroupById(groupId, userId))
                .orElseThrow(() -> new BadRequestException("Group not found"));
    }

    public Page<GroupResponse> getAllGroups(Pageable pageable, UserDetails userDetails){
        User user = (User) userDetails;
        Page<Group> groups = groupRepository.findAllGroupsByUser(user.getId(), pageable);
        return GroupMapper.toGroupResponse(groups);
    }

    public GroupResponse createGroup(GroupRequest groupRequest, UserDetails userDetails){
        User user = (User) userDetails;
        Group group = Group.builder()
                .name(groupRequest.getName())
                .user(user)
                .build();
        Group groupSaved = groupRepository.save(group);
        return GroupMapper.toGroupResponse(groupSaved);
    }

    public void deleteGroup(Long groupId, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        groupRepository.delete(group);
    }

    public void updateGroup(Long groupId, GroupRequest groupRequest, UserDetails userDetails){
        User user = (User) userDetails;
        Group group = getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        group.setName(groupRequest.getName());
        groupRepository.save(group);
    }

    public GroupResponse getGroupById(Long groupId, UserDetails userDetails) {
        User user = (User) userDetails;
        Group group = getGroupByIdOrThrowBadRequestException(groupId, user.getId());
        return GroupMapper.toGroupResponse(group);
    }
}