package me.mocha.blog.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRepository, String> {
}
