package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.OrderRepository;
import freela.api.FREELAAPI.domain.repositories.ProposalRepository;
import freela.api.FREELAAPI.domain.repositories.UsersRepository;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
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
    public Orders create(OrderRequest orderRequest, Integer userId) {
        try {
            Optional<Users> user = this.usersRepository.findById(userId);
            Optional<Category> category = this.categoryRepository.findById(orderRequest.getCategory());
            ArrayList<Integer> subCategoryIds = orderRequest.getSubCategoryIds();

            byte[] photo = null;

            try {
                if(!orderRequest.getPhoto().isEmpty()){
                     photo = orderRequest.getPhoto().getBytes();
                }
            }catch (Exception e){
            }

            Orders newOrder = orderRepository.save(
                    new Orders(
                            orderRequest.getDescription(),
                            orderRequest.getTitle(),
                            category.get(),
                            orderRequest.getMaxValue(),
                            user.get(),
                            photo
                    )
            );

            orderInterrestService.createOrderInterest(subCategoryIds, newOrder);

            return newOrder;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao criar order com o id: " + ex.getMessage()   );
        }
    }

    @Override
    public Orders addProposalToOrder(Integer orderId, Integer proposalId) {
        try {
            Optional<Orders> order = this.orderRepository.findById(orderId);
            Optional<Proposals> proposals = this.proposalRepository.findById(proposalId);

            proposals.get().setIsAccepted(true);
            this.proposalRepository.save(proposals.get());

            order.get().setProposals(proposals.get());
            order.get().setAccepted(true);
            return orderRepository.save(order.get());

        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao adicionar propossra com id" + proposalId);
        }
    }
//    public List<Orders> getProposalByOrder(){
//
//    }

    public OrderResponse update(OrderUpdateRequest orderUpdateRequest, Integer orderId){
        Optional<Orders> order = this.orderRepository.findById(orderId);

        if(!(orderUpdateRequest.getDescription() == null)){
            order.get().setDescription(orderUpdateRequest.getDescription());
        }

        if(!(orderUpdateRequest.getMaxValue() == null)){
            order.get().setMaxValue(orderUpdateRequest.getMaxValue());
        }

        if(!(orderUpdateRequest.getTitle() == null)){
            order.get().setTitle(orderUpdateRequest.getTitle());
        }

        if(!(orderUpdateRequest.getCategory() == null)){
            if(this.categoryRepository.existsById(orderUpdateRequest.getCategory())){

                order.get().setCategory(this.categoryRepository.findById(orderUpdateRequest.getCategory()).get());
            }
        }

        if(!(orderUpdateRequest.getSubCategoriesIds() == null)){
            if(!(orderUpdateRequest.getSubCategoriesIds().isEmpty())){
            orderInterrestService.updateOrderInterest(orderUpdateRequest.getSubCategoriesIds(),order.get());
            }
        }

        Orders changedOrder = this.orderRepository.save(order.get());

        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(order.get().getId());
        //maldita listaObj
        List<SubCategory>  listToReturn = new ArrayList<>();

        for(int i =0; i <= subCategories.getTamanho(); i ++){
            listToReturn.add(subCategories.getElemento(i));
        }

        return new OrderResponse(changedOrder.getDescription(),changedOrder.getTitle(),changedOrder.getMaxValue(),changedOrder.getCategory(),listToReturn, changedOrder.getPhoto());

    }

    public OrderResponse edit(Orders orders){

        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(orders.getId());
        //maldita listaObj
        List<SubCategory>  listToReturn = new ArrayList<>();

        for(int i =0; i <= subCategories.getTamanho(); i ++){
            listToReturn.add(subCategories.getElemento(i));
        }
        return new OrderResponse(
                orders.getDescription(),
                orders.getTitle(),
                orders.getMaxValue(),
                orders.getCategory(),
                listToReturn,
                orders.getPhoto()
                );
    }
    public ListaObj<Orders> bubbleSort(ListaObj<Orders> lista) {
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

    public List<Orders> getConcludedOrders(Users users){
          return orderRepository.findALlByUserAndIsAcceptedTrue(users);
    }

    @Override
    public List<Orders> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public ListaObj<Orders> orderByHigherPrice() {
        List<Orders> listOrder = this.orderRepository.findAll();
        ListaObj<Orders> listObjOrder = new ListaObj<>(listOrder.size());

        for (Orders order : listOrder){
             listObjOrder.adiciona(order);
        }


         return this.bubbleSort(listObjOrder);
    }

    public Boolean delete(Orders orders){
        this.orderInterrestService.deleteOrderInterest(orders);
        this.orderRepository.delete(orders);
        return true;
    }
}
