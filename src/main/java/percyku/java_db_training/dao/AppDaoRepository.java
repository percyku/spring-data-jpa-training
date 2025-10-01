package percyku.java_db_training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import percyku.java_db_training.model.User;

public interface AppDaoRepository extends JpaRepository<User,Long> {


}
