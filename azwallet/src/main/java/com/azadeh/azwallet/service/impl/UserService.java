package com.azadeh.azwallet.service.impl;
import com.azadeh.azwallet.entity.User;
import com.azadeh.azwallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


    @Service
    public class UserService {
        @Autowired
        private UserRepository userRepository;

        //ساخت کاربر جدید
        public User createUser(User user) {
            return userRepository.save(user);
        }

        // دریافت اطلاعات تمامی کاربران
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        // دریافت اطلاعات کاربر با id مشخص
        public Optional<User> getUserById(Long id) {
            return userRepository.findById(id);
        }

        // به روز رسانی اطلاعات کاربر
        public User updateUser(Long id, User userDetails) {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            user.setFirstname(userDetails.getFirstname());
            user.setEmail(userDetails.getEmail());
            user.setLastname(userDetails.getLastname());
            user.setMilitaryservice(userDetails.getMilitaryservice());
            user.setPhonenumber(userDetails.getPhonenumber());
            return userRepository.save(user);
        }

        // پاک کردن کاربر
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
    }


