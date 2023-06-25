package cn.fzu.edu.sm2020.rootsaleb.controller;

import cn.fzu.edu.sm2020.rootsaleb.entity.Orda;
import cn.fzu.edu.sm2020.rootsaleb.repository.OrdaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrdaController {
    private final OrdaRepository ordaRepository;

    @Autowired
    public OrdaController(OrdaRepository ordaRepository) {
        this.ordaRepository = ordaRepository;
    }

    @PostMapping("/addToOrderOrCart")
    public ResponseEntity<Void> addToOrder(@RequestBody Orda orda) {
        // 将订单存储到数据库或执行其他相关操作
        try {
            ordaRepository.save(orda); // 假设使用 JPA Repository 保存订单
            return ResponseEntity.ok().build(); // 返回 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回 500 Internal Server Error
        }
    }

    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrdersByUserId(@RequestParam("userId") int userId) {
        try {
            // 根据userId从数据库或其他数据源获取对应的订单列表
            List<Optional<Orda>> orders = ordaRepository.findOrdasByUserIdAndIsCart(userId,0);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法获取订单列表");
        }
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<?> getCartItemsByUserId(@RequestParam("userId") int userId) {
        try {
            // 根据userId从数据库或其他数据源获取对应的订单列表
            List<Optional<Orda>> orders = ordaRepository.findOrdasByUserIdAndIsCart(userId,1);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法获取订单列表");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("id") int orderId) {
        try {
            // 根据orderId从数据库或其他数据源删除对应的订单
            ordaRepository.deleteById(orderId);
            // 返回成功状态码
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 处理删除订单的异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> deleteCartItemById(@PathVariable("id") int cartItemId) {
        try {
            // 根据orderId从数据库或其他数据源删除对应的订单
            ordaRepository.deleteById(cartItemId);
            // 返回成功状态码
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 处理删除订单的异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/cart/checkOut")
    public ResponseEntity<Void> checkOut(@RequestParam("userId") int userId) {
        try {
            List<Optional<Orda>> cartItems= ordaRepository.findOrdasByUserIdAndIsCart(userId,1);
            for (Optional<Orda> optionalCartItem : cartItems) {
                if (optionalCartItem.isPresent()) {
                    Orda cartItem = optionalCartItem.get();
                    // 处理每个订单项的逻辑
                    // 访问 orda 中的属性或执行其他操作
                    cartItem.setIsCart(0);
                    ordaRepository.save(cartItem);
                }
            }
            // 返回成功状态码
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 处理删除订单的异常情况
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
