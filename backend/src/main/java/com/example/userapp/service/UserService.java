
package com.example.userapp.service;

import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }
    
    public Optional<User> getById(String id) {
     return repo.findById(id);
    }

    public User update(String id, User user) {
        user.setId(id);
        return repo.save(user);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
