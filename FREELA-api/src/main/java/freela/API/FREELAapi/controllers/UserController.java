package freela.API.FREELAapi.controllers;

import freela.API.FREELAapi.entity.UserClient;
import freela.API.FREELAapi.entity.UserProvider;
import freela.API.FREELAapi.services.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/registerClient")
    public User clientRegister(@RequestBody UserClient userClient){
        return userClient.register(userClient);
    }

    @PostMapping("/registerProvider")
    public User providerRegister(@RequestBody UserProvider userProvider){
        return userProvider.register(userProvider);
    }

}
