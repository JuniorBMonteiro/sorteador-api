package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.request.ParticipantRequest;
import br.com.bmont.sorteador.response.ParticipantResponse;
import br.com.bmont.sorteador.service.ParticipantService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<ParticipantResponse> addParticipant(@RequestBody @Valid ParticipantRequest participantRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(participantService.addParticipant(participantRequest, userDetails), HttpStatus.CREATED);
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable Long participantId,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        participantService.removeParticipant(participantId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{participantId}")
    public ResponseEntity<Void> updateParticipantName(@PathVariable Long participantId,
                                                      @RequestBody @Valid ParticipantRequest participantRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        participantService.updateParticipant(participantId, participantRequest, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/active/{participantId}")
    public ResponseEntity<Void> changeActive(@PathVariable Long participantId,
                                             @AuthenticationPrincipal UserDetails userDetails){
        participantService.changeActive(participantId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}