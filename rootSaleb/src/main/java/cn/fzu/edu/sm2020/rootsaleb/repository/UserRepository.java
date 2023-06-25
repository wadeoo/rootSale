package cn.fzu.edu.sm2020.rootsaleb.repository;

import cn.fzu.edu.sm2020.rootsaleb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
