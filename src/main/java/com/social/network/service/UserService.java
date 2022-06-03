package com.social.network.service;

import com.social.network.entities.User;
import com.social.network.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        Optional<User> user1 = userRepository.findById(id);

        if (user1.isPresent()) {
            User findUser = user1.get();
            findUser.setUserName(user.getUserName());
            findUser.setPassword(user.getPassword());
            userRepository.save(findUser);
            return findUser;
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
