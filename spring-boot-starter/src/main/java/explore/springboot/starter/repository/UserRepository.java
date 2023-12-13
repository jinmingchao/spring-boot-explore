package explore.springboot.starter.repository;

import explore.springboot.starter.mdm.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
