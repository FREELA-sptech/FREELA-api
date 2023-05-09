package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.Order;
import freela.api.FREELAAPI.resourses.entities.Proposals;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private OrderInterrestService orderInterrestService;


    @Override
    public Order create(OrderRequest orderRequest, Integer userId) {
        try {
            Optional<User> user = this.usersRepository.findById(userId);
            Optional<Category> category = this.categoryRepository.findById(orderRequest.getCategory());
            ArrayList<Integer> subCategoryIds = orderRequest.getSubCategoryIds();

            Order newOrder = orderRepository.save(
                    new Order(
                            orderRequest.getDescription(),
                            orderRequest.getTitle(),
                            category.get(),
                            orderRequest.getMaxValue(),
                            user.get()
                    )
            );

            orderInterrestService.createOrderInterest(subCategoryIds, newOrder);

            return newOrder;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao criar order com o id: " + ex.getMessage()   );
        }
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
    public List<Order> getAll() {
        return orderRepository.findAll();
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
