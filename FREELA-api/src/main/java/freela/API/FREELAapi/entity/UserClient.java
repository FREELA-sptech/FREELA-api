package freela.API.FREELAapi.entity;

import freela.API.FREELAapi.services.User;

import java.util.ArrayList;
import java.util.List;

public class UserClient extends User {
    private List<User> userClients = new ArrayList<>();

    public UserClient(Integer id, String name, String userName, String email, String password, String cpf) {
        super(id, name, userName, email, password, cpf);
    }

    public UserClient() {
    }

    @Override
    public User register(User user) {
        userClients.add(user);
        return user;
    }

    public List<User> getUserClients() {
        return userClients;
    }

    public void setUserClients(List<User> userClients) {
        this.userClients = userClients;
    }
}
