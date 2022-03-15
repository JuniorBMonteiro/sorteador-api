package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.dtos.request.GroupRequestDTO;
import br.com.bmont.sorteador.dtos.response.GroupResponseDTO;
import br.com.bmont.sorteador.model.Group;
import br.com.bmont.sorteador.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> getAllGroups(){
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody @Valid GroupRequestDTO groupRequestDTO){
        return new ResponseEntity<>(groupService.createGroup(groupRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long groupId, @RequestBody @Valid GroupRequestDTO groupRequestDTO){
        groupService.updateGroup(groupId, groupRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
