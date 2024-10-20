package com.azadeh.azwallet.repository;
import com.azadeh.azwallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {

    }

