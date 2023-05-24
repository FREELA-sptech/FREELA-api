package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.response.OrderCreatedResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserOrderResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserResponse;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.List;

public class OrderMapper {
    public static OrderResponse response(Orders order, List<byte[]> orderPhotos, List<SubCategory> orderSubCategories, List<Proposals> proposals) {
        UserOrderResponse userOrderResponse = UserOrderResponse.builder()
                .id(order.getUser().getId())
                .name(order.getUser().getName())
                .profilePhoto(order.getUser().getProfilePhoto())
                .rate(order.getUser().getRate())
                .build();

        return OrderResponse.builder()
                .id(order.getId())
                .description(order.getDescription())
                .title(order.getTitle())
                .maxValue(order.getMaxValue())
                .user(userOrderResponse)
                .expirationTime(order.getExpirationTime())
                .subCategories(orderSubCategories)
                .photos(orderPhotos)
                .proposals(proposals)
                .build();
    }

    public static OrderCreatedResponse createdResponse(Orders order) {
        return OrderCreatedResponse.builder()
                .id(order.getId())
                .build();
    }
}
