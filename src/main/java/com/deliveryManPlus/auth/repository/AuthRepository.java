package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<BasicAuth,Long> {

    @Query("select b from BasicAuth b where b.user = :user")
    Optional<BasicAuth> findByUser(User user);

    Optional<BasicAuth> findByEmail(String email);
}
