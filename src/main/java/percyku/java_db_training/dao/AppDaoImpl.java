package percyku.java_db_training.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import percyku.java_db_training.dao.imp.AppDao;
import percyku.java_db_training.model.Role;
import percyku.java_db_training.model.User;
import percyku.java_db_training.model.UserDetail;
import percyku.java_db_training.model.UserRole;

import java.util.ArrayList;
import java.util.HashSet;
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
                "select u from User u "
                        +" LEFT JOIN FETCH u.userDetail d"
                ,User.class);

        //execute query
        List<User> userList =null;
        try {

            userList= query.getResultList();

        } catch (NoResultException e) {
            userList=new ArrayList<>();
        }


        return userList;
    }

    @Override
    public List<User> findUsersWithRole() {
        //create query
        TypedQuery<User> query = entityManager.createQuery(
                "select distinct u from User u "
                        +" LEFT JOIN FETCH u.userDetail d"
                        +" LEFT JOIN FETCH u.user_role t"
                        +" LEFT JOIN FETCH t.role"
//                        +" where u.id = :data"
                ,User.class);

        List<User> userList =null;
        try {

            userList= query.getResultList();

        } catch (NoResultException e) {
            userList=new ArrayList<>();
        }


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
                "select distinct u from User u "
                        +" LEFT JOIN FETCH u.userDetail d"
                        +" LEFT JOIN FETCH u.user_role t"
                        +" LEFT JOIN FETCH t.role"
                        +" where u.id = :data",User.class);
        query.setParameter("data",theId);

        User user =null;
        try {

            user= query.getSingleResult();

        } catch (NoResultException e) {
            user=new User();
        }


        return user;

    }

    @Override
    public List<User> findUserWithDetailAndRole() {
        //create query
        TypedQuery<User> query = entityManager.createQuery(
                "select distinct u from User u "
                        +" LEFT JOIN FETCH u.userDetail d"
                        +" LEFT JOIN FETCH u.user_role t"
                        +" LEFT JOIN FETCH t.role"
//                        +" where u.id = :data"
                ,User.class);
//        query.setParameter("data",theId);

        List<User>  userList =null;
        try {

            userList= query.getResultList();

        } catch (NoResultException e) {
            userList=new ArrayList<>();
        }


        return userList;

    }

    @Override
    public User findUserWithDetailAndRoleById(int theId) {
        //create query
        TypedQuery<User> query = entityManager.createQuery(
                "select distinct u from User u "
                        +" LEFT JOIN FETCH u.userDetail d"
                        +" LEFT JOIN FETCH u.user_role t"
                        +" LEFT JOIN FETCH t.role"
                        +" where u.id = :data"
                ,User.class);
        query.setParameter("data",theId);

        User user =null;
        try {

            user= query.getSingleResult();

        } catch (NoResultException e) {
            user=new User();
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
    public void createUserWithRole(User theUser) {

        Role defaultRole =getRole("ROLE_DEFAULT");

        UserRole user_role =new UserRole();
        user_role.setUser(theUser);
        user_role.setRole(defaultRole);
        user_role.setCommon("initial create");
        theUser.addUser_role(user_role);
        defaultRole.addUser_role(user_role);

        entityManager.merge(theUser);
    }


    @Transactional
    @Override
    public void updateUserRole(int theId,List<String> roles) {


        User user = findUserWithDetailAndRoleById(theId);
//        System.out.println(user);
//        System.out.println(user.getUser_role());


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
//                System.out.println(printRole);
                roleList.add(printRole);
            }
        }

        for(Role role :roleList){
            UserRole tmpUserRole= new UserRole();
            tmpUserRole.setRole(role);
            tmpUserRole.setUser(user);
            tmpUserRole.setCommon("by someone");
            user.addUser_role(tmpUserRole);
        }


        entityManager.merge(user);


    }



    @Transactional
    @Override
    public void removeUserRole(int theId, String roleName) {
        User user = findUserWithRoleById(theId);
//        System.out.println(user);
//        System.out.println(user.getUser_role());



        /*

        if not using orphanRemoval = true like below ,you must control like below logic in User.class
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
        private List<UserRole> user_role =new ArrayList<>();

        */
//        List<UserRole> newUserRole =new ArrayList<>();
//        for(UserRole userRole: user.getUser_role()){
//            if(userRole.getRole().getName().equals(roleName)){
//                entityManager.remove(userRole);
//
//            }else{
//                newUserRole.add(userRole);
//            }
//        }
//        user.setUser_role(newUserRole);

        /*
        using orphanRemoval = true to control
         */

//        int i =0;
//        List<Integer> removeIdx=new ArrayList<>();
//        for(UserRole userRole: user.getUser_role()){
//            if(userRole.getRole().getName().equals(roleName)){
//                removeIdx.add(i);
//            }
//            i++;
//        }
//
//        for(int a : removeIdx){
//            UserRole removeUserRole = user.getUser_role().get(a);
//            removeUserRole.setUser(null);
//            user.getUser_role().remove(a);
//        }


        /*
        using orphanRemoval = true to control
        using HashSet to control collection
         */
        Set<UserRole> removeUserRoles=new HashSet<>();
        for(UserRole userRole: user.getUser_role()){
            if(userRole.getRole().getName().equals(roleName)){
                removeUserRoles.add(userRole);
            }
        }

        for(UserRole removeUserRole : removeUserRoles){

            removeUserRole.setUser(null);
            user.getUser_role().remove(removeUserRole);
        }





    }

    @Transactional
    @Override
    public void removeUserAllRole(int theId) {

        User user=  findUserWithRoleById(theId);
//        System.out.println(user.getUser_role());

         /*

        if not using orphanRemoval = true like below ,you must control like below logic in User.class
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
        private List<UserRole> user_role =new ArrayList<>();

        */

//        for(UserRole userRole : user.getUser_role()){
//            entityManager.remove(userRole);
//        }
//
//        user.setUser_role(null);
////        entityManager.merge(user);



       /*
        using orphanRemoval = true to control
        */


//        int len=user.getUser_role().size();
//        for(int i =0;i< len;i++){
//
//            UserRole removeUserRole = user.getUser_role().get(0);
//            removeUserRole.setUser(null);
//            user.getUser_role().remove(0);
//        }



        /*
        using orphanRemoval = true to control
        using HashSet to control collection
         */
        for(UserRole removeUserRole : user.getUser_role()){
            removeUserRole.setUser(null);

        }

        user.getUser_role().clear();


    }

    @Transactional
    @Override
    public void removeUserDetail(int theId) {
        User user = entityManager.find(User.class,theId);

//        System.out.println(user);
//        System.out.println(user.getUserDetail());

        UserDetail removeDetail = user.getUserDetail();

        if(removeDetail!=null){
            removeDetail.setUser(null);
            user.setUserDetail(null);
        }


        /*
        if not using orphanRemoval = true like below ,you must control like below logic in User.class
        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
        private UserDetail userDetail;
        */
//        entityManager.remove(removeDetail)

    }


    @Transactional
    @Override
    public void removeUser(int theId) {
//        User user = entityManager.find(User.class,theId);
        User user = findUserWithDetailAndRoleById(theId);
        entityManager.remove(user);
    }



    @Transactional
    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Transactional
    @Override
    public void saveAndUpdate(Role role) {
        entityManager.merge(role);
    }

    @Override
    public List<Role> getRoles() {
        TypedQuery<Role> query =entityManager.createQuery(
                "select r from Role r ",Role.class);

        //execute query
        List<Role> roleList=null;

        try {
            roleList = query.getResultList();

        } catch (NoResultException e) {
            roleList=new ArrayList<>();
        }



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


    @Transactional
    @Override
    public void removeRole(String name) {
        Role removeRole =getRole(name);
//        System.out.println(removeRole);
        entityManager.remove(removeRole);
    }


}
