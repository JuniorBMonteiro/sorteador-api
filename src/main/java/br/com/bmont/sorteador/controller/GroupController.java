package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.GroupRequest;
import br.com.bmont.sorteador.response.GroupResponse;
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

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<Page<GroupResponse>> getAllGroups(Pageable pageable,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.getAllGroups(pageable, userDetails), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long groupId,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.getGroupById(groupId, userDetails), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid GroupRequest groupRequest,
                                                     @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.createGroup(groupRequest, userDetails), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.deleteGroup(groupId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable Long groupId,
                                            @RequestBody @Valid GroupRequest groupRequest,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.updateGroup(groupId, groupRequest, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
