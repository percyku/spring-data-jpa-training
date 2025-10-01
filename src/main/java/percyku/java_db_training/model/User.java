package percyku.java_db_training.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> user_role =new ArrayList<>();


    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password ,String email) {
        this.userName = userName;
        this.password = password;
        this.email= email;
    }

    public User(String userName, String password ,String email,String firstName,String lastName) {
        this.userName = userName;
        this.password = password;
        this.email= email;
        this.firstName= firstName;
        this.lastName= lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getUser_role() {
        return user_role;
    }

    public void setUser_role(List<UserRole> user_role) {
        this.user_role = user_role;
    }


    public void addUser_role(UserRole userRole){
        if(user_role== null){
            user_role=new ArrayList<>();
        }

        user_role.add(userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
//                ", user_role=" + user_role +
                '}';
    }
}