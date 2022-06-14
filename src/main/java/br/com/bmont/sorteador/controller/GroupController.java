package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.GroupRequest;
import br.com.bmont.sorteador.response.GroupResponse;
import br.com.bmont.sorteador.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
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

    @Operation(summary = "Get All Groups Paginated")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @GetMapping
    public ResponseEntity<Page<GroupResponse>> getAllGroups(@ParameterObject Pageable pageable,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.getAllGroups(pageable, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "Get Group by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long groupId,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.getGroupById(groupId, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "Create Group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the object sent in the request is not valid")
    })
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid GroupRequest groupRequest,
                                                     @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(groupService.createGroup(groupRequest, userDetails), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.deleteGroup(groupId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update Group Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @PatchMapping("/{groupId}")
    public ResponseEntity<Void> updateGroupName(@PathVariable Long groupId,
                                            @RequestBody @Valid GroupRequest groupRequest,
                                            @AuthenticationPrincipal UserDetails userDetails){
        groupService.updateGroupName(groupId, groupRequest, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
