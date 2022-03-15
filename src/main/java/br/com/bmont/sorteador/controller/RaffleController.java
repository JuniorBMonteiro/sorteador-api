package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/raffles")
public class RaffleController {
    private final RaffleService raffleService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<Participant>> getClassifiedParticipants(@PathVariable Long groupId){
        return new ResponseEntity<>(raffleService.getClassifiedParticipants(groupId), HttpStatus.OK);
    }

    @GetMapping("/active/{groupId}")
    public ResponseEntity<List<Participant>> getClassifiedActiveParticipants(@PathVariable Long groupId){
        return new ResponseEntity<>(raffleService.getClassifiedActiveParticipants(groupId), HttpStatus.OK);
    }
}
