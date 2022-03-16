package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.dtos.request.GroupRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(Pageable pageable,
                                                               @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.getAllGroups(pageable, userDetails), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody @Valid GroupRequestDTO groupRequestDTO,
                                             @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.createGroup(groupRequestDTO, userDetails), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.deleteGroup(groupId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long groupId,
                                            @RequestBody @Valid GroupRequestDTO groupRequestDTO,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.updateGroup(groupId, groupRequestDTO, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
