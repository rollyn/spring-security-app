package com.mycompany.repository;

import com.mycompany.entities.Role;
import com.mycompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);
    User findByRole(Role admin);
}
