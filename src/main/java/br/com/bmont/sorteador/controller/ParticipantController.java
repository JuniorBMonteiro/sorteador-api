package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.dtos.request.ParticipantRequestDTO;
import br.com.bmont.sorteador.dtos.response.ParticipantResponseDTO;
import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ParticipantResponseDTO>> getParticipantsByGroupId(@PathVariable Long groupId){
        return new ResponseEntity<>(participantService.getParticipantsByGroupId(groupId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Participant> addParticipant(@RequestBody @Valid ParticipantRequestDTO participantRequestDTO){
        return new ResponseEntity<>(participantService.addParticipant(participantRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable Long participantId){
        participantService.removeParticipant(participantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{participantId}")
    public ResponseEntity<Void> updateParticipantName(@PathVariable Long participantId, @RequestBody @Valid ParticipantRequestDTO participantRequestDTO){
        participantService.updateParticipant(participantId, participantRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/active/{participantId}")
    public ResponseEntity<Void> changeActive(@PathVariable Long participantId){
        participantService.changeActive(participantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}