package percyku.java_db_training.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import percyku.java_db_training.dao.RoleRepository;
import percyku.java_db_training.dao.UserRepository;
import percyku.java_db_training.dao.UserRoleRepository;
import percyku.java_db_training.dao.imp.AppDao;
import percyku.java_db_training.model.Role;
import percyku.java_db_training.model.User;
import percyku.java_db_training.model.UserDetail;
import percyku.java_db_training.model.UserRole;
import percyku.java_db_training.service.UserService;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AppDao appDao;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Transactional
    @Override
    public void createUserWithDetailAndRole(int theId) {
//
        User createUser= new User("testRepository"+theId,"testRepository","testRepository"+theId,"testRepository","testRepository");
        UserDetail userDetail = new UserDetail("test","test");
        createUser.setUserDetail(userDetail);
        userDetail.setUser(createUser);

        Optional<Role> roleOptional= roleRepository.findByName("ROLE_DEFAULT");

        if(roleOptional.isPresent()){
            Role defaultRole=roleOptional.get();
            UserRole userRole=new UserRole();
            userRole.setUser(createUser);
            userRole.setRole(defaultRole);

            createUser.addUser_role(userRole);


            userRepository.save(createUser);
        }

    }

    @Transactional
    @Override
    public Optional<User> updateUserInfo(int theId, User newUser) {
        Optional<User> userOptional =userRepository.findById(theId);
        if(userOptional.isPresent()){
            User existUser=userOptional.get();

            existUser.setUserName(newUser.getUserName());
            existUser.setPassword(newUser.getPassword());
            existUser.setEmail(newUser.getEmail());
            existUser.setFirstName(newUser.getFirstName());
            existUser.setLastName(newUser.getLastName());

            if(newUser.getUserDetail()!=null){
                if(existUser.getUserDetail()!=null){
                    existUser.getUserDetail().setDesc(newUser.getUserDetail().getDesc());
                    existUser.getUserDetail().setJob(newUser.getUserDetail().getJob());
                }else{
                    newUser.getUserDetail().setUser(existUser);
                    existUser.setUserDetail(newUser.getUserDetail());
                }

            }else{
                if(existUser.getUserDetail()!=null){
                    existUser.getUserDetail().setUser(null);
                }
                existUser.setUserDetail(null);
            }



            return Optional.of(userRepository.save(existUser));

        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateUserRole(int theId ,List<String> roleList) {

        System.out.println("the Id:"+theId);
        Optional<User> userOptional= userRepository.findUserWithRoleById(theId);
        if(userOptional.isPresent()){
            User existUser =userOptional.get();
            System.out.println(existUser);
            System.out.println(existUser.getUser_role());


            for(String roleName : roleList){

                if(userRepository.findUserWithRoleByIdAndRoleName(theId,roleName).isPresent())
                    continue;

                Optional<Role> roleOptional = roleRepository.findByName(roleName);
                System.out.println(roleOptional);
                if(!roleOptional.isPresent())
                    continue;

                Role newRole =roleOptional.get();


                UserRole newUserRole=new UserRole();
                newUserRole.setRole(newRole);
                newUserRole.setUser(existUser);
                existUser.addUser_role(newUserRole);



            }
        }

    }

    @Transactional
    @Override
    public void removeUserRole(int theId, String roleName) {
        Optional<User>userOptional= userRepository.findUserWithRoleById(theId);
        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        if(userOptional.isPresent() && roleOptional.isPresent()){
            User existUser =userOptional.get();

            for(UserRole userRole : existUser.getUser_role()){
                if(userRole.getRole().getName().equals(roleName)){
                    userRole.setUser(null);
                    existUser.getUser_role().remove(userRole);
                }
            }
            userRepository.save(existUser);
        }
    }

    @Transactional
    @Override
    public void removeUserAllRole(int theId) {
        Optional<User>userOptional= userRepository.findUserWithRoleById(theId);
        if(userOptional.isPresent()){
            User existUser =userOptional.get();

            for(UserRole userRole : existUser.getUser_role()){
                userRole.setUser(null);
            }

            existUser.getUser_role().clear();
            userRepository.save(existUser);
//            System.out.println(userRepository.save(existUser));
        }
    }

    @Override
    public void deleteUser(int theId) {
        Optional<User> userOptional = userRepository.findById(theId);
        if(userOptional.isPresent()){
            userRepository.delete(userOptional.get());
        }
    }

    @Transactional
    @Override
    public void updateRoleName(String beforeName, String afterName) {
        Optional<Role> roleOptional = roleRepository.findByName(beforeName);
        if(roleOptional.isPresent()){
            Role updateRole = roleOptional.get();

            updateRole.setName(afterName);
            roleRepository.save(updateRole);
        }
    }

    @Transactional
    @Override
    public void deleteRole(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if(roleOptional.isPresent()){
            roleRepository.delete(roleOptional.get());
        }
    }

    @Transactional
    @Override
    public void deleteAllRole() {
        roleRepository.deleteAll();
    }
}
