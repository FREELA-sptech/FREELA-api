package freela.api.FREELAAPI.domain.repositories.Specification;

import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<Users> nameLike(String name){
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Users> usernameLike(String userName){
        return (root, query, builder) -> builder.like(builder.lower(root.get("userName")), "%" + userName.toLowerCase() + "%");
    }

//
//    public static Specification<Users> usernameLike(String userName){
//        return (root, query, builder) -> builder.like(builder.lower(root.get("userName")), "%" + userName.toLowerCase() + "%");
//    }


}
