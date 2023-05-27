package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderCreatedResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    OrderCreatedResponse create(OrderRequest orderRequest, Authentication authentication) throws IOException;
    OrderCreatedResponse updatePictures(List<MultipartFile> images, Integer orderId) throws IOException;
    Orders addProposalToOrder(Integer orderId,  Integer proposalId);
    List<OrderResponse> getAllOrdersBySubCategoriesUser(Authentication authentication);
    ListaObj<Orders> orderByHigherPrice();
    List<Orders> getConcludedOrders(Users user);
    OrderResponse edit(Orders orderId);
    OrderResponse update(OrderUpdateRequest order, Integer id);
    OrderResponse updatePictures(List<MultipartFile> newPhotos, List<byte[]> deletedPhotos, Integer id) throws IOException;
    Boolean delete(Orders order);
    List<OrderResponse> getOrderByUser(Authentication authentication);
    byte[] getUserOrdersExtract(Authentication authentication);
}