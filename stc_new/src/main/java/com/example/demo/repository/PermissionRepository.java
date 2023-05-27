package com.example.demo.repository;

import com.example.demo.data.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
     Permission findByUserEmail(String email);
}
