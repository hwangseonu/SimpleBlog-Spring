package me.mocha.blog.model.repository;

import me.mocha.blog.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
