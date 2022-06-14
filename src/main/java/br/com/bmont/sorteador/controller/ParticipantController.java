package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.ParticipantRequest;
import br.com.bmont.sorteador.response.GroupResponse;
import br.com.bmont.sorteador.response.ParticipantResponse;
import br.com.bmont.sorteador.service.ParticipantService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @Operation(summary = "Get All Participants Paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @GetMapping("/{groupId}")
    public ResponseEntity<Page<ParticipantResponse>> getAllParticipants(@PathVariable Long groupId,
                                                                        @ParameterObject Pageable pageable,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(participantService.getAllParticipants(groupId, pageable, userDetails), HttpStatus.OK);
    }

    @Operation(summary = "Add Participant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the object sent in the request is not valid")
    })
    @PostMapping
    public ResponseEntity<ParticipantResponse> addParticipant(@RequestBody @Valid ParticipantRequest participantRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(participantService.addParticipant(participantRequest, userDetails), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Participant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When participant does not exist")
    })
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable Long participantId,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        participantService.removeParticipant(participantId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update Participant Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When participant does not exist")
    })
    @PatchMapping("/{participantId}")
    public ResponseEntity<Void> updateParticipantName(@PathVariable Long participantId,
                                                      @RequestBody @Valid ParticipantRequest participantRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        participantService.updateParticipant(participantId, participantRequest, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Change Participant Active Attribute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When participant does not exist")
    })
    @PatchMapping("/active/{participantId}")
    public ResponseEntity<Void> changeActive(@PathVariable Long participantId,
                                             @AuthenticationPrincipal UserDetails userDetails){
        participantService.changeActive(participantId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}