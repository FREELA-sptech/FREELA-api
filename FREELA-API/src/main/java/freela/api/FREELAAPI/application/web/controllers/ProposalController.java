package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.application.web.enums.ProposalStatus;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

   private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lista vazia."),
            @ApiResponse(responseCode = "200", description = "Lista de propostas.")
    })
    @GetMapping
    public ResponseEntity<List<Proposals>> findAll(){
        return ResponseEntity.ok(proposalService.searchAllProposals());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "User não encontrado ou ordem não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description =
                    "Pedido ja aceito.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Criado!.")
    })
    @PostMapping("/{originUserId}/{orderId}")
    public ResponseEntity<Proposals> post(
            @PathVariable @NotNull int originUserId,
            @PathVariable @NotNull int orderId,
            @RequestBody ProposalRequest proposal
    ){
        return ResponseEntity.created(URI.create("/proposals/" + originUserId))
                .body(proposalService.create(originUserId, proposal,orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Proposals>> findProposalsByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "accepted", required = false) Integer accepted,
            @RequestParam(value = "refused", required = false) Integer refused) {

        ProposalStatus status = ProposalStatus.ALL;
        if (accepted != null) {
            status = ProposalStatus.ACCEPTED;
        } else if (refused != null) {
            status = ProposalStatus.REFUSED;
        }

        List<Proposals> proposals = proposalService.findProposalsByUser(userId, status.name().toLowerCase());

        if (proposals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(proposals);
    }

    @DeleteMapping("/{proposalId}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer proposalId){
        return ResponseEntity.ok(this.proposalService.delete(proposalId));
    }

    @PutMapping("/refuse-propose/{proposalId}")
    public ResponseEntity<Boolean> refuseProposal(@PathVariable Integer proposalId){
        return ResponseEntity.ok(this.proposalService.refuse(proposalId));
    }

    @GetMapping("/edit/{proposalId}")
    public ResponseEntity<Proposals> edit(@PathVariable Integer proposalId){
        return ResponseEntity.ok(proposalService.findProposal(proposalId));
    }

    @PutMapping("/update/{proposalId}")
    public ResponseEntity<Proposals> update(@PathVariable Integer proposalId, @RequestBody ProposalUpdate proposalUpdate) {
        return ResponseEntity.ok(this.proposalService.update(proposalId, proposalUpdate));

    }
//
//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<Object> findProposalsByOrder(@PathVariable Integer orderId){
//        if(!this.usersRepository.existsById(orderId)){
//            return ResponseEntity.status(404).body("Order not found");
//        }
//
//        List<Proposals> proposals = this.proposalService.findProposalsByUser(userId);
//
//        if(proposals.isEmpty()){
//            return ResponseEntity.status(204).body(proposals);
//        }
//
//        return ResponseEntity.status(200).body(proposals);
//
//    }


}
