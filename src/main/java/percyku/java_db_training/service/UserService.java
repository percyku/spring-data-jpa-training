package percyku.java_db_training.service;

import percyku.java_db_training.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUserWithDetailAndRole(int theId);

    Optional<User>updateUserInfo(int theId,User newUser);
    void updateUserRole(int theId, List<String> roleList);


    void removeUserRole(int theId,String roleName);
    void removeUserAllRole(int theId);

    void deleteUser(int theId);

    void updateRoleName(String beforeName,String afterName);

    void deleteRole(String name);
    void deleteAllRole();
}
