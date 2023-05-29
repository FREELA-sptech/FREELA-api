package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.Exception.UserNotFoundException;
import freela.api.FREELAAPI.application.web.dtos.request.OrderRequest;
import freela.api.FREELAAPI.application.web.dtos.request.OrderUpdateRequest;
import freela.api.FREELAAPI.application.web.dtos.response.OrderCreatedResponse;
import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;
import freela.api.FREELAAPI.application.web.helpers.ListaObj;
import freela.api.FREELAAPI.application.web.helpers.GravadorDeArquivo;
import freela.api.FREELAAPI.domain.repositories.*;
import freela.api.FREELAAPI.domain.services.OrderInterrestService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.OrderMapper;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UsersRepository usersRepository;
    private ProposalRepository proposalRepository;
    private OrderInterrestService orderInterrestService;
    private OrderPhotoRepository orderPhotoRepository;
    private UserInterestService userInterestService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UsersRepository usersRepository,
            ProposalRepository proposalRepository,
            OrderInterrestService orderInterrestService,
            OrderPhotoRepository orderPhotoRepository,
            UserInterestService userInterestService
    ) {
        this.orderRepository = orderRepository;
        this.usersRepository = usersRepository;
        this.proposalRepository = proposalRepository;
        this.orderInterrestService = orderInterrestService;
        this.orderPhotoRepository = orderPhotoRepository;
        this.userInterestService = userInterestService;
    }

    @Override
    public OrderCreatedResponse create(OrderRequest orderRequest, Authentication authentication) {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );
        ArrayList<Integer> subCategoryIds = orderRequest.getSubCategoryId();


        Orders newOrder = orderRepository.save(
                new Orders(
                        orderRequest.getDescription(),
                        orderRequest.getTitle(),
                        orderRequest.getMaxValue(),
                        orderRequest.getExpirationTime(),
                        user
                )
        );

        this.orderInterrestService.createOrderInterest(subCategoryIds, newOrder);

        return OrderMapper.createdResponse(newOrder);
    }

    @Override
    public OrderCreatedResponse updatePictures(List<MultipartFile> images, Integer orderId) throws IOException {
        Optional<Orders> order = this.orderRepository.findById(orderId);

        try {
            List<byte[]> newPictures = new ArrayList<>();

            for (MultipartFile file : images) {
                byte[] newPicture = file.getBytes();

                newPictures.add(newPicture);
                orderPhotoRepository.save(
                        new OrderPhotos(
                                order.get(),
                                file.getBytes()
                        )
                );
            }


            return OrderMapper.createdResponse(order.get());
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

        order.get().setExpirationTime(orderUpdateRequest.getExpirationTime());

        List<OrderPhotos> orderPhotos = orderPhotoRepository.findAllByOrder(order.get());
        List<byte[]> totalPhotos = new ArrayList<>();

        for (OrderPhotos photo : orderPhotos) {
            totalPhotos.add(photo.getPhoto());
        }

        Orders changedOrder = this.orderRepository.save(order.get());

        ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(order.get().getId());
        //maldita listaObj
        List<SubCategory> listToReturn = new ArrayList<>();
        List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(order.get().getId());

        for (int i = 0; i < subCategories.getTamanho(); i++) {
            listToReturn.add(subCategories.getElemento(i));
        }

        return OrderMapper.response(changedOrder, totalPhotos, listToReturn, proposals);
    }

    @Override
    public OrderResponse updatePictures(List<MultipartFile> newPhotos, List<byte[]> deletedPhotos, Integer orderId) throws IOException {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        List<OrderPhotos> pictures = this.orderPhotoRepository.findAllByOrder(order.get());

        if (deletedPhotos != null) {
            for (byte[] bytes : deletedPhotos) {
                for (OrderPhotos orderPictures : pictures) {
                    byte[] pic = orderPictures.getPhoto();
                    if (Arrays.equals(bytes, orderPictures.getPhoto())) {
                        this.orderPhotoRepository.deleteById(orderPictures.getId());
                    }
                }
            }
        }

        if (newPhotos != null) {
            for (MultipartFile file : newPhotos) {
                byte[] newPicture = file.getBytes();

                orderPhotoRepository.save(
                        new OrderPhotos(
                                order.get(),
                                file.getBytes()
                        )
                );
            }
        }

        List<OrderPhotos> orderPhotos = orderPhotoRepository.findAllByOrder(order.get());
        List<byte[]> totalPhotos = new ArrayList<>();

        for (OrderPhotos photo : orderPhotos) {
            totalPhotos.add(photo.getPhoto());
        }
        ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(order.get().getId());
        //maldita listaObj
        List<SubCategory> listToReturn = new ArrayList<>();
        List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(order.get().getId());

        return OrderMapper.response(order.get(), totalPhotos, listToReturn, proposals);
    }

    public OrderResponse edit(Orders orders) {
        ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(orders.getId());
        //maldita listaObj
        List<SubCategory> listToReturn = new ArrayList<>();

        for (int i = 0; i < subCategories.getTamanho(); i++) {
            listToReturn.add(subCategories.getElemento(i));
        }

        List<OrderPhotos> orderPhotos = orderPhotoRepository.findAllByOrder(orders);
        List<byte[]> totalPhotos = new ArrayList<>();
        List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(orders.getId());

        for (OrderPhotos photo : orderPhotos) {
            totalPhotos.add(photo.getPhoto());
        }

        return OrderMapper.response(orders, totalPhotos, listToReturn, proposals);
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
    public List<OrderResponse> getAllOrdersBySubCategoriesUser(Authentication authentication) {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        List<SubCategory> subCategoriesUser = this.userInterestService.getAllSubCategoriesByUser(user);
        List<OrderResponse> orders = new ArrayList<>();
        List<Orders> ordersTotal = orderRepository.findAll();

        for (Orders order : ordersTotal) {
            ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(order.getId());
            List<OrderPhotos> photosTotal = this.orderPhotoRepository.findAllByOrder(order);

            List<SubCategory> listToReturn = new ArrayList<>();
            List<byte[]> listPhotosToReturn = new ArrayList<>();

            for (int i = 0; i < subCategories.getTamanho(); i++) {
                SubCategory subCategory = subCategories.getElemento(i);
                if (subCategoriesUser.contains(subCategory)) {
                    listToReturn.add(subCategory);
                }
            }

            for (OrderPhotos photo : photosTotal) {
                listPhotosToReturn.add(photo.getPhoto());
            }
            List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(order.getId());

            if (!listToReturn.isEmpty()) {
                orders.add(OrderMapper.response(order, listPhotosToReturn, listToReturn, proposals));
            }
        }

        return orders;
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

    public byte[] getUserOrdersExtract(Authentication users) {
        List<OrderResponse> order = this.getOrderByUser(users);

        GravadorDeArquivo gravador = new GravadorDeArquivo();

        try {

            byte[] arquivo = gravador.gravaArquivoTxt(order);

            return arquivo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderResponse> getOrderByUser(Authentication authentication) {
        Users user = this.usersRepository.findById(TokenDetailsDto.getUserId(authentication)).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        List<OrderResponse> response = new ArrayList<>();
        List<Orders> orders = this.orderRepository.findAllByUser(user);

        for (Orders order : orders) {
            ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(order.getId());
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
            List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(order.getId());

            response.add(OrderMapper.response(order, photos, listToReturn, proposals));
        }

        return response;
    }

    @Override
    public List<OrderResponse> getOrdersByTitle(String title) {
        List<OrderResponse> response = new ArrayList<>();
        List<Orders> orders = this.orderRepository.findAllByTitleIgnoreCaseLike("%"+title+"%");
        for (Orders order : orders) {
            ListaObj<SubCategory> subCategories = this.orderInterrestService.getAllSubCategoriesByUser(order.getId());
            List<OrderPhotos> orderPhotos = this.orderPhotoRepository.findAllByOrder(order);
            List<byte[]> photos = new ArrayList<>();

            for (OrderPhotos photo : orderPhotos) {
                photos.add(photo.getPhoto());
            }
            //listaObj
            List<SubCategory> listToReturn = new ArrayList<>();
            for (int i = 0; i < subCategories.getTamanho(); i++) {
                listToReturn.add(subCategories.getElemento(i));
            }
            List<Proposals> proposals = proposalRepository.findAllByDestinedOrder(order.getId());
            response.add(OrderMapper.response(order, photos, listToReturn, proposals));
        }
        return response;
    }
}