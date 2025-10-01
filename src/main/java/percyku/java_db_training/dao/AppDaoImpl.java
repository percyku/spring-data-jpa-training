package percyku.java_db_training.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import percyku.java_db_training.dao.imp.AppDao;
import percyku.java_db_training.model.Role;
import percyku.java_db_training.model.User;
import percyku.java_db_training.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Repository
public class AppDaoImpl implements AppDao {

    private EntityManager entityManager;

    public AppDaoImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }


    @Override
    public List<User> findUsers() {


        TypedQuery<User> query =entityManager.createQuery(
                "select i from User i ",User.class);

        //execute query
        List<User> userList = query.getResultList();

        return userList;
    }

    @Override
    public User findUserById(int theId) {
        return entityManager.find(User.class,theId);
    }

    @Override
    public User findUserWithRoleById(int theId) {


        //create query
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u "
                        +" JOIN FETCH u.user_role"
                        +" where u.id = :data",User.class);
        query.setParameter("data",theId);
        //execute query
        User user =null;
        try {

            user= query.getSingleResult();

        } catch (NoResultException e) {
            user= entityManager.find(User.class,theId);
            if(user!= null){
                user.setUser_role(new ArrayList<>());
            }
        }


        return user;

    }


    @Transactional
    @Override
    public void save(User theUser) {
        entityManager.persist(theUser);
    }

    @Transactional
    @Override
    public void saveAndUpdate(User theUser) {
        entityManager.merge(theUser);
    }


    @Transactional
    @Override
    public void saveAndUpdate(UserRole userRole) {
        entityManager.merge(userRole);
    }

    @Transactional
    @Override
    public void updateUserRole(int theId,List<String> roles) {
        User user = findUserWithRoleById(theId);
        System.out.println(user);
        System.out.println(user.getUser_role());


        List<Role>roleList=new ArrayList<>();
        List<String>existRoleList=new ArrayList<>();

        for(UserRole userRole : user.getUser_role()){

            if(roles.contains(userRole.getRole().getName())){
                existRoleList.add(userRole.getRole().getName());
            }
        }

        for(String roleName: roles){
            if(existRoleList.contains(roleName))
                continue;
            else{
                Role printRole=getRole(roleName);
                System.out.println(printRole);
                roleList.add(printRole);
            }
        }

        for(Role role :roleList){
            UserRole tmpUserRole= new UserRole();
            tmpUserRole.setRole(role);
            tmpUserRole.setUser(user);
            user.addUser_role(tmpUserRole);
        }


        entityManager.merge(user);
    }



    @Transactional
    @Override
    public void removeUserRole(int theId, String roleName) {
        User user = findUserWithRoleById(theId);
        System.out.println(user);
        System.out.println(user.getUser_role());
        List<UserRole> newUserRole =new ArrayList<>();

        for(UserRole userRole: user.getUser_role()){
            if(userRole.getRole().getName().equals(roleName)){
                entityManager.remove(userRole);

            }else{
                newUserRole.add(userRole);
            }
        }
        user.setUser_role(newUserRole);


    }

    @Transactional
    @Override
    public void removeUserAllRole(int theId) {

        User user=  findUserWithRoleById(theId);
        System.out.println(user.getUser_role());
        for(UserRole userRole : user.getUser_role()){
            entityManager.remove(userRole);
        }

        user.setUser_role(null);
//        entityManager.merge(user);

    }



    @Transactional
    @Override
    public void removeUser(int theId) {
        User user = entityManager.find(User.class,theId);
        entityManager.remove(user);
    }



    @Transactional
    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getRoles() {
        TypedQuery<Role> query =entityManager.createQuery(
                "select r from Role r ",Role.class);

        //execute query
        List<Role> roleList = query.getResultList();

        return roleList;
    }

    @Override
    public Role getRole(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
                "from Role  "
                        +" where name = :data",Role.class);
        query.setParameter("data",name);
        //execute query
        Role role =null;
        try {

            role= query.getSingleResult();

        } catch (NoResultException e) {
            return new Role();
        }


        return role;
    }


}
