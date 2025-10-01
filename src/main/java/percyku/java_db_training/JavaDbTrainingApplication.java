package percyku.java_db_training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import percyku.java_db_training.dao.AppDaoRepository;
import percyku.java_db_training.dao.imp.AppDao;
import percyku.java_db_training.model.Role;
import percyku.java_db_training.model.User;
import percyku.java_db_training.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JavaDbTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaDbTrainingApplication.class, args);
	}

	@Autowired
	AppDao appDao;
	@Autowired
	AppDaoRepository appDaoRepository;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return runner->{
			System.out.println("Hi");

//			createUser(1);
//			createUserWithRole(9);

//			findUsers();
//			findUserById(1);
//			findUserWithRoleById(3);






//			updateUser(7);
//			updateUserRole(1);

//			removeUserRole(1);
//			removeUserAllRole(4);
//			removeUser(8);

//			createRole("ROLE_STUDENT");
//			createRole("ROLE_TEACHER");
//			createRole("ROLE_DEFAULT");
//			createRole("ROLE_ADMIN");

//			getRoles();
//			getRole("ROLE_STUDENT");




		};
	}


	private void findUsers() {
		System.out.println("All User:");
		List<User> userList  = appDao.findUsers();
		for(User tmpUser : userList){
			System.out.println(tmpUser.toString());
		}
	}

	private void findUserById(int theId) {
		System.out.println("Finding User id: "+theId);

		User user =appDao.findUserById(theId);
		System.out.println("tempUser: " + user);

	}

	private void findUserWithRoleById(int theId) {
		System.out.println("Finding User id: "+theId);

		User user =appDao.findUserWithRoleById(theId);
		System.out.println("tempUser: " + user);
		System.out.println(user.getUser_role());
	}



	private void createUser(int theId){
		User user= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");
		appDao.save(user);
		System.out.println(user);

	}

	private void createUserWithRole(int theId){
		User user= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");
		Role roleStudent =new Role(((long) 3),"ROLE_DEFAULT");

		UserRole user_role =new UserRole();
		user_role.setUser(user);
		user_role.setRole(roleStudent);
		user.addUser_role(user_role);
		roleStudent.addUser_role(user_role);

		appDao.saveAndUpdate(user);

		System.out.println(user_role);

	}

	private void updateUser(int theId){
		User user =appDao.findUserById(theId);
		user.setUserName("test"+theId);
		user.setFirstName("test"+theId);
		user.setLastName("test"+theId);
		user.setEmail("test"+theId+"@gmail.com");
		appDao.saveAndUpdate(user);

	}

	private void updateUserRole(int theId){
		List<String> roles=new ArrayList<>();
		roles.add("ROLE_STUDENT");
		roles.add("ROLE_Teacher");
		appDao.updateUserRole(theId,roles);
	}

	private void removeUserRole(int theId){
		appDao.removeUserRole(theId,"ROLE_TEACHER");
	}

	private void removeUserAllRole(int theId){

		appDao.removeUserAllRole(theId);
	}


	private void removeUser(int theId){
		appDao.removeUser(theId);
	}

	private void createRole(String role){
		Role newRole=new Role(role);

		appDao.save(newRole);
	}

	private void getRoles(){
		System.out.println(appDao.getRoles());
	}

	private void getRole(String name) {
		System.out.println(appDao.getRole(name));
	}



}
