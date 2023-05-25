package freela.api.FREELAAPI.domain.services.mapper;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserOrderResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UserResponse;
import freela.api.FREELAAPI.application.web.dtos.response.UsuarioTokenResponse;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;

import java.util.List;

public class UsuarioMapper {

    public static UsuarioTokenResponse login(Users usuario, String token) {
        UsuarioTokenResponse usuarioTokenResponse = new UsuarioTokenResponse();

        usuarioTokenResponse.setId(usuario.getId());
        usuarioTokenResponse.setToken(token);
        usuarioTokenResponse.setFreelancer(usuario.getIsFreelancer());

        return usuarioTokenResponse;
    }

    public static Users register(UserRequest request, String senhaCriptografada) {
        Users user = new Users();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(senhaCriptografada);
        user.setProfilePhoto(new byte[0]);
        user.setUf(request.getUf());
        user.setRate(5.0);
        user.setCity(request.getCity());
        user.setIsFreelancer(request.getIsFreelancer());

        return user;
    }

    public static UserResponse response(Users users, Double rate, List<SubCategory> subCategories) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(users.getId());
        userResponse.setName(users.getName());
        userResponse.setProfilePhoto(users.getProfilePhoto());
        userResponse.setRate(rate);
        userResponse.setUf(users.getUf());
        userResponse.setCity(users.getCity());
        userResponse.setSubCategories(subCategories);

        return userResponse;
    }

    public static FreelancerResponse freelancerResponse(Users users, Double rate, Integer closedOrders, List<SubCategory> subCategories) {
        return FreelancerResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .profilePhoto(users.getProfilePhoto())
                .description(users.getDescription())
                .rate(rate)
                .uf(users.getUf())
                .city(users.getCity())
                .closedOrders(closedOrders)
                .subCategories(subCategories)
                .build();
    }

    public static UserOrderResponse userOrderResponse(Users users) {
        return UserOrderResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .profilePhoto(users.getProfilePhoto())
                .rate(users.getRate())
                .build();
    }
}