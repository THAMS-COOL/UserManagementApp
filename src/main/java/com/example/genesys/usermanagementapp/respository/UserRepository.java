package com.example.genesys.usermanagementapp.respository;

import com.example.genesys.usermanagementapp.appUserModel.AppUser;
import com.example.genesys.usermanagementapp.userModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
}

