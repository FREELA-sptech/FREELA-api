package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.dtos.request.ProposalRequest;
import freela.api.FREELAAPI.application.web.dtos.request.ProposalUpdate;
import freela.api.FREELAAPI.application.web.dtos.response.ProposalsResponse;
import freela.api.FREELAAPI.application.web.enums.ProposalStatus;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.services.ProposalService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

   private final ProposalService proposalService;
    private final OrderRepository orderRepository;

    public ProposalController(ProposalService proposalService,OrderRepository orderRepository) {
        this.proposalService = proposalService;
        this.orderRepository = orderRepository;
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
    @PostMapping("/{orderId}")
    public ResponseEntity<ProposalsResponse> post(
            @PathVariable @NotNull int orderId,
            @RequestBody ProposalRequest proposal,
            Authentication authentication
    ){
        Integer originUserId = TokenDetailsDto.getUserId(authentication);

        return ResponseEntity.created(URI.create("/proposals/" + originUserId))
                .body(proposalService.create(originUserId, proposal,orderId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProposalsResponse>> findProposalsByUser(
            @RequestParam(value = "accepted", required = false) Integer accepted,
            @RequestParam(value = "refused", required = false) Integer refused,
            Authentication authentication) {

        ProposalStatus status = ProposalStatus.ALL;
        if (accepted != null) {
            status = ProposalStatus.ACCEPTED;
        } else if (refused != null) {
            status = ProposalStatus.REFUSED;
        }

        List<ProposalsResponse> proposals = proposalService.findProposalsByUser(authentication, status);

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
    public ResponseEntity<ProposalsResponse> update(@PathVariable Integer proposalId, @RequestBody ProposalUpdate proposalUpdate) {
        return ResponseEntity.ok(this.proposalService.update(proposalId, proposalUpdate));

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> findProposalsByOrder(@PathVariable Integer orderId,@RequestParam (required = false) String refused){

        
        if(!this.orderRepository.existsById(orderId)){
            return ResponseEntity.status(404).body("Order not found");
        }

        if (refused != null) {
            List<Proposals> proposals = this.proposalService.findAllRefusedProposalsByOrderId(orderId);

            if(proposals.isEmpty()){
                return ResponseEntity.status(204).body(proposals);
            }

            return ResponseEntity.status(200).body(proposals);
        }

        List<Proposals> proposals = this.proposalService.findAllProposalsByOrderId(orderId);

        if(proposals.isEmpty()){
            return ResponseEntity.status(204).body(proposals);
        }

        return ResponseEntity.status(200).body(proposals);

    }


}
