package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.*;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.dtos.response.OrderResponse;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private OrderInterrestService orderInterrestService;


    @Override
    public OrderResponse create(OrderRequest orderRequest, Integer userId) {
        User user = this.usersRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );

        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < orderRequest.getCategoryIds().size(); i++) {
            Integer id = orderRequest.getCategoryIds().get(i);

            Category category = this.categoryRepository.findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
                    );

            categories.add(category);
        }

        List<SubCategory> subCategories = new ArrayList<>();

        for (int i = 0; i < orderRequest.getSubCategoryIds().size(); i++) {
            Integer id = orderRequest.getSubCategoryIds().get(i);

            SubCategory subCategory = this.subCategoryRepository.findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubCategoria não encontrada")
                    );

            subCategories.add(subCategory);
        }

        Order newOrder = orderRepository.save(
                new Order(
                        orderRequest.getDescription(),
                        orderRequest.getTitle(),
                        orderRequest.getMaxValue(),
                        user,
                        orderRequest.getExpirationTime()
                )
        );

        for (SubCategory subs : subCategories) {
            orderInterrestService.createOrderInterest(subs.getCategory(), subs, newOrder);
        }

        OrderResponse newOrderResponse = new OrderResponse(newOrder, categories, subCategories);

        return newOrderResponse;
    }

    @Override
    public Order addProposalToOrder(Integer orderId, Integer proposalId) {
        try {
            Optional<Order> order = this.orderRepository.findById(orderId);
            Optional<Proposals> proposals = this.proposalRepository.findById(proposalId);

            order.get().setProposals(proposals.get());
            order.get().setAccepted(true);
            return orderRepository.save(order.get());

        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao adicionar propossra com id" + proposalId);
        }
    }


    public ListaObj<Order> bubbleSort(ListaObj<Order> lista) {
        int n = lista.getTamanho();
        boolean trocou;
        do {
            trocou = false;
            for (int i = 0; i < n - 1; i++) {
                if (lista.getElemento(i).getMaxValue() > lista.getElemento(i+1).getMaxValue()) {
                    lista.trocar(i, i+1);
                    trocou = true;
                }
            }
            n--;
        } while (trocou);
        return lista;
    }

    @Override
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> ordersResponse = new ArrayList<>();

        for (Order order: orders) {
            List<OrderInterest> ordersInterest = this.orderInterrestService.findByOrder(order);
            List<Category> categories = new ArrayList<>();
            List<SubCategory> subCategories = new ArrayList<>();

            for (OrderInterest orderInterest : ordersInterest) {
                if (!categories.contains(orderInterest.getCategory())) {
                    categories.add(orderInterest.getCategory());
                }

                if (!subCategories.contains(orderInterest.getSubCategory())) {
                    subCategories.add(orderInterest.getSubCategory());
                }
            }

            ordersResponse.add(new OrderResponse(order, categories, subCategories));
        }

        return ordersResponse;
    }

    @Override
    public ListaObj<Order> orderByHigherPrice() {
        List<Order> listOrder = this.orderRepository.findAll();
        ListaObj<Order> listObjOrder = new ListaObj<>(listOrder.size());

        for (Order order : listOrder){
             listObjOrder.adiciona(order);
        }


         return this.bubbleSort(listObjOrder);
    }
}
