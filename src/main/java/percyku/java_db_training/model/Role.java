package percyku.java_db_training.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> user_role  =new ArrayList<>();;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id,String name) {
        this.id=id;
        this.name=name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser_role(UserRole userRole){
        if(user_role== null){
            user_role=new ArrayList<>();
        }

        user_role.add(userRole);
    }



    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
