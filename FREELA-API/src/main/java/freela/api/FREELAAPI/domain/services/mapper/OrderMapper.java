package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.response.*;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static OrderResponse response(Orders order, List<byte[]> orderPhotos, List<SubCategory> orderSubCategories, List<Proposals> proposals) {
        UserOrderResponse userOrderResponse = UsuarioMapper.userOrderResponse(order.getUser());

        List<ProposalsResponse> proposalsResponses = new ArrayList<>();

        for (Proposals proposalsLocal: proposals) {
            proposalsResponses.add(ProposalsMapper.response(proposalsLocal));
        }

        return OrderResponse.builder()
                .id(order.getId())
                .description(order.getDescription())
                .title(order.getTitle())
                .maxValue(order.getMaxValue())
                .user(userOrderResponse)
                .expirationTime(order.getExpirationTime())
                .subCategories(orderSubCategories)
                .photos(orderPhotos)
                .proposals(proposalsResponses)
                .build();
    }

    public static OrderCreatedResponse createdResponse(Orders order) {
        return OrderCreatedResponse.builder()
                .id(order.getId())
                .build();
    }
}
