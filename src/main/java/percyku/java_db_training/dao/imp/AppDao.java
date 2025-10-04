package percyku.java_db_training.dao.imp;


import percyku.java_db_training.model.Role;
import percyku.java_db_training.model.User;
import percyku.java_db_training.model.UserRole;

import java.util.List;

public interface AppDao {

    List<User> findUsers();

    List<User> findUsersWithRole();
    User findUserById(int theId);
    User findUserWithRoleById(int theId);

    void save(User theUser);
    void saveAndUpdate(User theUser);
    void createUserWithRole(User theUser);

    void updateUserRole(int theId,List<String> roles);

    void removeUserRole(int theId,String roleName);

    void removeUserAllRole(int theId);

    void removeUser(int theId);

    void save(Role role);

    void saveAndUpdate(Role role);

    List<Role> getRoles();

    Role getRole(String name);

    void removeRole(String name);


}
