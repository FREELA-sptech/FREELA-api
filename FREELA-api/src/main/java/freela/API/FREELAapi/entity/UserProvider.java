package freela.API.FREELAapi.entity;

import freela.API.FREELAapi.services.User;

import java.util.ArrayList;
import java.util.List;

public class UserProvider extends User {
    private Integer totalSells;
    private Double assessment;
    private List<User> usersProvider = new ArrayList<>();

    @Override
    public User register(User user) {
        usersProvider.add(user);
        return user;
    }

    public UserProvider(Integer id, String name, String userName, String email, String password, String cpf, Integer totalSells, Double assessment) {
        super(id, name, userName, email, password, cpf);
        this.totalSells = totalSells;
        this.assessment = assessment;
    }

    public UserProvider() {
    }

    public Integer getTotalSells() {
        return totalSells;
    }

    public void setTotalSells(Integer totalSells) {
        this.totalSells = totalSells;
    }

    public Double getAssessment() {
        return assessment;
    }

    public void setAssessment(Double assessment) {
        this.assessment = assessment;
    }

    public List<User> getUsersProvider() {
        return usersProvider;
    }

    public void setUsersProvider(List<User> usersProvider) {
        this.usersProvider = usersProvider;
    }
}
