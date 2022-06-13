package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.model.Participant;
import br.com.bmont.sorteador.response.ParticipantResponse;
import br.com.bmont.sorteador.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<Page<ParticipantResponse>> getClassifiedParticipants(@PathVariable Long groupId, Pageable pageable,
                                                                               @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(raffleService.getClassifiedParticipants(groupId, pageable, userDetails), HttpStatus.OK);
    }

    @GetMapping("/active/{groupId}")
    public ResponseEntity<Page<ParticipantResponse>> getClassifiedActiveParticipants(@PathVariable Long groupId, Pageable pageable,
                                                                             @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(raffleService.getClassifiedActiveParticipants(groupId, pageable, userDetails), HttpStatus.OK);
    }
}
