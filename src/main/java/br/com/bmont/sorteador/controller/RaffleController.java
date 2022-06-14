package br.com.bmont.sorteador.controller;

import br.com.bmont.sorteador.response.ParticipantResponse;
import br.com.bmont.sorteador.service.RaffleService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/raffles")
public class RaffleController {
    private final RaffleService raffleService;

    @Operation(summary = "Get Classified Participants Paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @GetMapping("/{groupId}")
    public ResponseEntity<Page<ParticipantResponse>> getClassifiedParticipants(@PathVariable Long groupId,
                                                                               @ParameterObject Pageable pageable,
                                                                               @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(raffleService.getClassifiedParticipants(groupId, pageable, userDetails), HttpStatus.OK);
    }
    @Operation(summary = "Get Classified Active Participants Paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When group does not exist")
    })
    @GetMapping("/active/{groupId}")
    public ResponseEntity<Page<ParticipantResponse>> getClassifiedActiveParticipants(@PathVariable Long groupId,
                                                                                     @ParameterObject Pageable pageable,
                                                                             @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(raffleService.getClassifiedActiveParticipants(groupId, pageable, userDetails), HttpStatus.OK);
    }
}
