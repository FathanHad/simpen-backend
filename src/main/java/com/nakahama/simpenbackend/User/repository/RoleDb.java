package com.nakahama.simpenbackend.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nakahama.simpenbackend.User.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<Role, Long> {
    List<Role> findAll();

    Optional<Role> findByRole(String role);
}
