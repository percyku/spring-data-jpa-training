package percyku.java_db_training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import percyku.java_db_training.model.UserRole;

import java.util.List;

public interface UserRoleRepository  extends JpaRepository<UserRole,Integer> {


    @Query("select t from UserRole t LEFT JOIN FETCH t.user u LEFT JOIN FETCH t.role where u.id = :user_id")
    List<UserRole> findUserAndRoleById(@Param("user_id")int user_id);

}
