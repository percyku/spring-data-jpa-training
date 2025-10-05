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
import percyku.java_db_training.model.UserDetail;

import java.util.ArrayList;
import java.util.List;

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
			/*
				This Jpa/Hibernate testing section Start
			 */
//			createUser(30);
//			createUserWithDetail(31);
//			createUserWithRole(21);
//			createUserWithDetailAndRole(33);

//			findUserById(33);
//			findUsers();
//			findUserWithRole();
//			findUserWithRoleById(1);

//			findUserWithDetailAndRole();
//			findUserWithDetailAndRoleById(312);



//			updateUser(32);
//			updateUserDetail(29);
//			updateUserRole(29);


//			removeUserRole(29);
//			removeUserAllRole(29);
//			removeUserDetail(29);
//			removeUser(33);

//			createRole("ROLE_STUDENT");
//			createRole("ROLE_TEACHER");
//			createRole("ROLE_DEFAULT");
//			createRole("ROLE_ADMIN");
//			createRole("ROLE_TEST1");

//			getRoles();
//			getRole("ROLE_STUDENT");

//			updateRoleName("ROLE_TEST1","ROLE_TEST");

//			removeRole("ROLE_DEFAULT");

			/*
				This Jpa/Hibernate testing section End
			 */


		};
	}

	private void findUserById(int theId){
		System.out.println("theId:"+theId);
		User user =  appDao.findUserById(theId);
		System.out.println(user);
		System.out.println(user.getUserDetail());
	}

	private void findUsers() {
		System.out.println("All User:");
		List<User> userList  = appDao.findUsers();
		for(User tmpUser : userList) {
			System.out.println(tmpUser.toString());
			System.out.println(tmpUser.getUserDetail());
		}
	}

	private void findUserWithRole() {
		System.out.println("All User:");
		List<User> userList  = appDao.findUsersWithRole();
		for(User tmpUser : userList){
			System.out.println("========tempUser: " + tmpUser.toString());
			System.out.println( tmpUser.getUserDetail());
			System.out.println(tmpUser.getUser_role().toString());
		}
	}

	private void findUserWithRoleById(int theId) {
		System.out.println("Finding User id: "+theId);

		User user =appDao.findUserWithRoleById(theId);
		System.out.println("========tempUser: " + user);
		System.out.println(user.getUser_role().toString());
	}

	private void findUserWithDetailAndRole() {
		System.out.println("Finding All User id:");

		List<User> userList  =appDao.findUserWithDetailAndRole();
		for(User user : userList) {
			System.out.println("========tempUser: " + user);
			System.out.println(user.getUserDetail());
			System.out.println(user.getUser_role());
		}
	}

	private void findUserWithDetailAndRoleById(int theId) {
		System.out.println("Finding User id: "+theId);

		User user =appDao.findUserWithDetailAndRoleById(theId);
		System.out.println("========tempUser: " + user);
		System.out.println(user.getUserDetail());
		System.out.println(user.getUser_role());
	}



	private void createUser(int theId){
		User user= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");
		appDao.save(user);
		System.out.println(user);

	}

	private void createUserWithDetail(int theId){
		User user= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");
		UserDetail userDetail =new UserDetail("Hi","Java en");
		user.setUserDetail(userDetail);
		userDetail.setUser(user);
		appDao.save(user);
		System.out.println(user);
		System.out.println(user.getUserDetail());

	}



	private void createUserWithRole(int theId){
		User theUser= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");

		appDao.createUserWithRole(theUser);

		System.out.println(theUser);

	}


	private void createUserWithDetailAndRole(int theId){
		User user= new User("test"+theId,"test"+theId,"test"+theId+"@gmail.com","","");
		UserDetail userDetail =new UserDetail("Hi","Java en");
		user.setUserDetail(userDetail);
		userDetail.setUser(user);
		appDao.createUserWithRole(user);

		System.out.println(user);
		System.out.println(user.getUserDetail());
	}

	private void updateUser(int theId){
//		User user =appDao.findUserById(theId);
		User user =appDao.findUserWithDetailAndRoleById(theId);
		user.setUserName("test_remove"+theId);
		user.setFirstName("test"+theId);
		user.setLastName("test"+theId);
		user.setEmail("test"+theId+"@gmail.com");
		appDao.saveAndUpdate(user);

	}

	private void updateUserDetail(int theId){
//		User user =appDao.findUserById(theId);
		User user =appDao.findUserWithDetailAndRoleById(theId);
		System.out.println("theUser:"+user);
		System.out.println("theUserDetail:"+user.getUserDetail());
		if(user.getUserDetail()!=null){
			user.getUserDetail().setDesc("Hello");

		}else{
			UserDetail userDetail =new UserDetail("","");
			userDetail.setUser(user);
			user.setUserDetail(userDetail);
		}
		appDao.saveAndUpdate(user);



	}

	private void updateUserRole(int theId){
		List<String> roles=new ArrayList<>();
		roles.add("ROLE_STUDENT");
		roles.add("ROLE_TEACHER");
		roles.add("ROLE_DEFAULT");
//		roles.add("ROLE_TEST");
		appDao.updateUserRole(theId,roles);
	}

	private void removeUserRole(int theId){
		appDao.removeUserRole(theId,"ROLE_TEACHER");
	}
	private void removeUserAllRole(int theId){

		appDao.removeUserAllRole(theId);
	}

	private void removeUserDetail(int theId){
		appDao.removeUserDetail(theId);
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


	private void updateRoleName(String beforeName,String afterName){

		Role updateRole=appDao.getRole(beforeName);
		updateRole.setName(afterName);

		appDao.saveAndUpdate(updateRole);
	}

	private void removeRole(String name){
		appDao.removeRole(name);
	}


}
