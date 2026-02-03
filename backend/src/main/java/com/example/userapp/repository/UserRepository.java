
package com.example.userapp.repository;
import com.example.userapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface UserRepository extends MongoRepository<User,String> {
 boolean existsByEmail(String email);
}
