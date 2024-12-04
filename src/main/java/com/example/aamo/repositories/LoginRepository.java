package com.example.aamo.repositories;

import com.example.aamo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
