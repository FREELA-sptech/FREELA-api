package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.exceptions.AmbiguousProposalCreationUser;
import freela.api.FREELAAPI.domain.exceptions.CreateOrderException;
import freela.api.FREELAAPI.domain.exceptions.DataAccessException;
import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UsersRepository usersRepository;
    private final ProposalRepository proposalRepository;
    private final CategoryRepository categoryRepository;
    private final OrderInterrestService orderInterrestService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UsersRepository usersRepository,
            ProposalRepository proposalRepository,
            CategoryRepository categoryRepository,
            OrderInterrestService orderInterrestService
    ) {
        this.orderRepository = orderRepository;
        this.usersRepository = usersRepository;
        this.proposalRepository = proposalRepository;
        this.categoryRepository = categoryRepository;
        this.orderInterrestService = orderInterrestService;
    }

    @Override
    public Orders create(OrderRequest orderRequest, Integer userId) {
        try {
            Users user = findUserById(userId);
            Category category = findCategoryById(orderRequest.getCategory());
            ArrayList<Integer> subCategoryIds = orderRequest.getSubCategoryIds();
            byte[] photo = extractPhotoBytes(orderRequest.getPhoto());

            Orders newOrder = createNewOrder(orderRequest, user, category, photo);
            orderInterrestService.createOrderInterest(subCategoryIds, newOrder);

            return newOrder;
        } catch (CreateOrderException ex) {
            String errorMessage = "Erro ao criar pedido com o id: " + userId + ". " + ex.getMessage();
            throw new CreateOrderException(errorMessage, ex.getStatusCode());
        }
    }

    @Override
    public Orders addProposalToOrder(Integer orderId, Integer proposalId) {
        try {
            Orders order = findOrderById(orderId);
            Proposals proposals = findProposalById(proposalId);

            if (Objects.equals(order.getUser().getId(), proposals.getOriginUser().getId())){
                throw new AmbiguousProposalCreationUser(
                        "Usuário de criação de proposta ambiguo",
                        HttpStatus.BAD_REQUEST);
            }

            order.setProposals(proposals);
            order.setAccepted(true);
            return orderRepository.save(order);

        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao adicionar proposta com id" + proposalId + ex.getMessage());
        }
    }
//    public List<Orders> getProposalByOrder(){
//
//    }

    public OrderResponse update(OrderUpdateRequest orderUpdateRequest, Integer orderId) {
        Orders order = findOrderById(orderId);

        if (!(orderUpdateRequest.getDescription() == null)) {
            order.setDescription(orderUpdateRequest.getDescription());
        }

        if (!(orderUpdateRequest.getMaxValue() == null)) {
            order.setMaxValue(orderUpdateRequest.getMaxValue());
        }

        if (!(orderUpdateRequest.getTitle() == null)) {
            order.setTitle(orderUpdateRequest.getTitle());
        }

        if (!(orderUpdateRequest.getCategory() == null)) {
            if (this.categoryRepository.existsById(orderUpdateRequest.getCategory())) {

                order.setCategory(this.categoryRepository.findById(orderUpdateRequest.getCategory()).get());
            }
        }

        if (!(orderUpdateRequest.getSubCategoriesIds() == null)) {
            if (!(orderUpdateRequest.getSubCategoriesIds().isEmpty())) {
                orderInterrestService.updateOrderInterest(orderUpdateRequest.getSubCategoriesIds(), order);
            }
        }

        Orders changedOrder = this.orderRepository.save(order);

        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(order.getId());
        //maldita listaObj
        List<SubCategory> listToReturn = IntStream.range(0, subCategories.getTamanho())
                .mapToObj(subCategories::getElemento)
                .collect(Collectors.toList());

        return new OrderResponse(
                changedOrder.getDescription(),
                changedOrder.getTitle(),
                changedOrder.getMaxValue(),
                changedOrder.getCategory(),
                listToReturn,
                changedOrder.getPhoto()
        );

    }

    public OrderResponse edit(Integer orderId) {
        Orders order = findOrderById(orderId);

        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(order.getId());
        //maldita listaObj
        List<SubCategory> listToReturn = IntStream.range(0, subCategories.getTamanho())
                .mapToObj(subCategories::getElemento)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getDescription(),
                order.getTitle(),
                order.getMaxValue(),
                order.getCategory(),
                listToReturn,
                order.getPhoto()
        );
    }



    public List<Orders> getConcludedOrders(Users users) {
        return orderRepository.findALlByUserAndIsAcceptedTrue(users);
    }

    @Override
    public List<Orders> getAll() {
        List<Orders> orders = orderRepository.findAll();

        if (orders.isEmpty()){
            throw new DataAccessException("Nenhum dado encontrado", HttpStatus.NOT_FOUND);
        }

        return orders;
    }


    public Boolean delete(Integer orderId) {
        Orders order = findOrderById(orderId);

        this.orderInterrestService.deleteOrderInterest(order);
        this.orderRepository.delete(order);
        return true;
    }

    private Users findUserById(Integer userId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataAccessException("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    private Proposals findProposalById(Integer proposalId) {
        Optional<Proposals> proposal = this.proposalRepository.findById(proposalId);
        if (proposal.isEmpty()) {
            throw new DataAccessException("Proposta não encontrado.", HttpStatus.NOT_FOUND);
        }
        return proposal.get();
    }

    private Orders findOrderById(Integer orderId) {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new DataAccessException("Pedido não encontrado.", HttpStatus.NOT_FOUND);
        } else if (order.get().isAccepted()) {
            throw new DataAccessException("Pedido já foi aceito.", HttpStatus.BAD_REQUEST);
        }
        return order.get();
    }

    private Category findCategoryById(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new DataAccessException("Categoria não encontrada.", HttpStatus.NOT_FOUND);
        }
        return category.get();
    }

    private Orders createNewOrder(OrderRequest orderRequest, Users user, Category category, byte[] photo) {
        Orders newOrder = new Orders(
                orderRequest.getDescription(),
                orderRequest.getTitle(),
                category,
                orderRequest.getMaxValue(),
                user,
                photo
        );
        return orderRepository.save(newOrder);
    }

    private byte[] extractPhotoBytes(MultipartFile photo) {
        try {
            return photo.isEmpty() ? null : photo.getBytes();
        } catch (IOException e) {
            return null;
        }
    }
}
