package cn.fzu.edu.sm2020.rootsaleb.controller;

import cn.fzu.edu.sm2020.rootsaleb.entity.Orda;
import cn.fzu.edu.sm2020.rootsaleb.entity.User;
import cn.fzu.edu.sm2020.rootsaleb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 在这里编写登录逻辑
        // 可以进行用户名和密码的验证等操作
        // 例如：
        User foundUser = userRepository.findByUsername(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            int userId = Math.toIntExact(foundUser.getId()); // 获取 userId
            return ResponseEntity.ok(userId); // 登录成功，返回 200 OK 和 userId
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 登录失败，返回 401 Unauthorized
        }
    }


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // 在这里编写注册逻辑
        // 可以进行用户名是否存在的验证等操作
        // 例如：
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return "用户名已存在";
        }
        userRepository.save(user);
        return "注册成功";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") int id){
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser); // 登录成功，返回 200 OK 和 userId
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 登录失败，返回 401 Unauthorized
        }
    }
    @PatchMapping("/user/profileChange/{id}")
    public ResponseEntity<Void> changeProfile(@RequestBody User user,@PathVariable("id") int id) {
        try {
            // 返回成功状态码
            Optional<User> foundUser=userRepository.findById(id);
            if(foundUser.isPresent()){
                User user1=foundUser.get();
                user1.setUsername(user.getUsername());
                user1.setPassword(user.getPassword());
                user1.setAddress(user.getAddress());

                userRepository.save(user1);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 处理异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
