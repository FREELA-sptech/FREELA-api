package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.domain.repositories.*;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    private UsersRepository usersRepository;
    private ProposalRepository proposalRepository;
    private CategoryRepository categoryRepository;
    private OrderInterrestService orderInterrestService;
    private OrderPhotoRepository orderPhotoRepository;
    private SubCategoryRepository subCategoryRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UsersRepository usersRepository,
            ProposalRepository proposalRepository,
            CategoryRepository categoryRepository,
            OrderInterrestService orderInterrestService,
            OrderPhotoRepository orderPhotoRepository,
            SubCategoryRepository subCategoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.usersRepository = usersRepository;
        this.proposalRepository = proposalRepository;
        this.categoryRepository = categoryRepository;
        this.orderInterrestService = orderInterrestService;
        this.orderPhotoRepository = orderPhotoRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public Orders create(OrderRequest orderRequest, Integer userId) {
        try {
            Optional<Users> user = this.usersRepository.findById(userId);
            ArrayList<Integer> subCategoryIds = orderRequest.getSubCategoryId();


            Orders newOrder = orderRepository.save(
                    new Orders(
                            orderRequest.getDescription(),
                            orderRequest.getTitle(),
                            orderRequest.getMaxValue(),
                            orderRequest.getExpirationTime(),
                            user.get()
                    )
            );

            this.orderInterrestService.createOrderInterest(subCategoryIds, newOrder);

            return newOrder;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao criar order com o id: " + ex.getMessage());
        }
    }

    @Override
    public Orders updatePictures(List<MultipartFile> images, Integer orderId, Integer userId) throws IOException {
        Optional<Orders> order = this.orderRepository.findById(orderId);

        try {
            List<byte[]> newPictures = new ArrayList<>();

            for (MultipartFile file : images) {
                byte[] newPicture = file.getBytes();

                var a = file.getBytes();

                newPictures.add(newPicture);
                orderPhotoRepository.save(
                        new OrderPhotos(
                                order.get(),
                                file.getBytes()
                        )
                );
            }


            return order.get();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao criar order com o id: " + ex.getMessage());
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

    public OrderResponse update(OrderUpdateRequest orderUpdateRequest, Integer orderId) {
        Optional<Orders> order = this.orderRepository.findById(orderId);

        if (!(orderUpdateRequest.getDescription() == null)) {
            order.get().setDescription(orderUpdateRequest.getDescription());
        }

        if (!(orderUpdateRequest.getMaxValue() == null)) {
            order.get().setMaxValue(orderUpdateRequest.getMaxValue());
        }

        if (!(orderUpdateRequest.getTitle() == null)) {
            order.get().setTitle(orderUpdateRequest.getTitle());
        }

        if (!(orderUpdateRequest.getSubCategoriesIds() == null)) {
            if (!(orderUpdateRequest.getSubCategoriesIds().isEmpty())) {
                orderInterrestService.updateOrderInterest(orderUpdateRequest.getSubCategoriesIds(), order.get());
            }
        }

        List<OrderPhotos> orderPhotos = orderPhotoRepository.findAllByOrder(order.get());
        List<byte[]> totalPhotos = new ArrayList<>();

        for (OrderPhotos photo : orderPhotos) {
            totalPhotos.add(photo.getPhoto());
        }

        Orders changedOrder = this.orderRepository.save(order.get());

        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(order.get().getId());
        //maldita listaObj
        List<SubCategory> listToReturn = new ArrayList<>();

        for (int i = 0; i <= subCategories.getTamanho(); i++) {
            listToReturn.add(subCategories.getElemento(i));
        }

        return new OrderResponse(
                changedOrder.getDescription(),
                changedOrder.getTitle(),
                changedOrder.getMaxValue(),
                changedOrder.getExpirationTime(),
                listToReturn,
                totalPhotos
        );

    }

    public OrderResponse edit(Orders orders) {
        ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(orders.getId());
        //maldita listaObj
        List<SubCategory> listToReturn = new ArrayList<>();

        for (int i = 0; i < subCategories.getTamanho(); i++) {
            listToReturn.add(subCategories.getElemento(i));
        }

        List<OrderPhotos> orderPhotos = orderPhotoRepository.findAllByOrder(orders);
        List<byte[]> totalPhotos = new ArrayList<>();

        for (OrderPhotos photo : orderPhotos) {
            totalPhotos.add(photo.getPhoto());
        }

        return new OrderResponse(
                orders.getDescription(),
                orders.getTitle(),
                orders.getMaxValue(),
                orders.getExpirationTime(),
                listToReturn,
                totalPhotos
        );
    }

    public ListaObj<Orders> bubbleSort(ListaObj<Orders> lista) {
        int n = lista.getTamanho();
        boolean trocou;
        do {
            trocou = false;
            for (int i = 0; i < n - 1; i++) {
                if (lista.getElemento(i).getMaxValue() > lista.getElemento(i + 1).getMaxValue()) {
                    lista.trocar(i, i + 1);
                    trocou = true;
                }
            }
            n--;
        } while (trocou);
        return lista;
    }

    public List<Orders> getConcludedOrders(Users users) {
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

        for (Orders order : listOrder) {
            listObjOrder.adiciona(order);
        }


        return this.bubbleSort(listObjOrder);
    }

    public Boolean delete(Orders orders) {
        this.orderInterrestService.deleteOrderInterest(orders);
        this.orderRepository.delete(orders);
        return true;
    }

    @Override
    public List<OrderResponse> getOrderByUser(Users user) {
        List<OrderResponse> response = new ArrayList<>();
        List<Orders> orders = this.orderRepository.findAllByUser(user);

        for (Orders order : orders) {
            ListaObj<SubCategory> subCategories = this.orderInterrestService.findByOrder(order.getId());
            List<OrderPhotos> orderPhotos = this.orderPhotoRepository.findAllByOrder(order);
            List<byte[]> photos = new ArrayList<>();

            for (OrderPhotos photo : orderPhotos) {
                photos.add(photo.getPhoto());
            }

            //maldita listaObj
            List<SubCategory> listToReturn = new ArrayList<>();

            for (int i = 0; i < subCategories.getTamanho(); i++) {
                listToReturn.add(subCategories.getElemento(i));
            }

            response.add(
                    new OrderResponse(
                            order.getDescription(),
                            order.getTitle(),
                            order.getMaxValue(),
                            order.getExpirationTime(),
                            listToReturn,
                            photos
                    )
            );
        }

        return response;
    }
}