package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.dtos.response.ProposalsResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserOrderResponse;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.List;

public class ProposalsMapper {
    public static ProposalsResponse response(Proposals proposals) {
        UserOrderResponse userOrderResponse = UsuarioMapper.userOrderResponse(proposals.getOriginUser());

        return ProposalsResponse.builder()
                .id(proposals.getId())
                .proposalValue(proposals.getProposalValue())
                .originUser(userOrderResponse)
                .description(proposals.getDescription())
                .expirationTime(proposals.getExpirationTime())
                .destinedOrder(proposals.getDestinedOrder())
                .isAccepted(proposals.getIsAccepted())
                .isRefused(proposals.getIsRefused())
                .build();
    }

}
