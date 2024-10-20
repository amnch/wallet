package com.azadeh.azwallet.controller;

import com.azadeh.azwallet.entity.User;
import com.azadeh.azwallet.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated


    public class UserController {
        @Autowired
        private UserService userService;

        //ایجاد یک کاربر جدید
        @PostMapping
        public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }

        //دریافت لیست تمامی کاربران
        @GetMapping
        public ResponseEntity<List<User>> ListAllUsers() {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }

        //دریافت اطلاعات کاربر با استفاده از id
         @GetMapping("/{id}")
         public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "کاربر مورد نظر یافت نشد"));
        return ResponseEntity.ok(user);
         }

        //به روزرسانی اطلاعات کاربر
        @PutMapping("/{id}")
         public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
        }

        //حذف کاربر با استفاده از id
        @DeleteMapping("/{id}")
         public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
        }
    }


