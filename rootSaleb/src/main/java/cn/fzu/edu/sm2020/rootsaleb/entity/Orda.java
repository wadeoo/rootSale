package cn.fzu.edu.sm2020.rootsaleb.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
public class Orda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_Id")
    private Long userId;
    @Column(name = "artwork_name")
    private String artworkName;
    @Column(name = "artwork_image_path")
    private String artworkImagePath;
    private Integer quantity;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "is_cart")
    private Integer isCart;
    @Column(name = "order_date")
    private String orderDate;

    // 构造函数、Getter和Setter方法


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getArtworkName() {
        return artworkName;
    }

    public void setArtworkName(String artworkName) {
        this.artworkName = artworkName;
    }

    public String getArtworkImagePath() {
        return artworkImagePath;
    }

    public void setArtworkImagePath(String artworkImagePath) {
        this.artworkImagePath = artworkImagePath;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIsCart() {
        return isCart;
    }

    public void setIsCart(Integer isCart) {
        this.isCart = isCart;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}