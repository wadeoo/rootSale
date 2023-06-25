package cn.edu.fzu.rootsale.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Artwork  implements Serializable {
    private Long id;
    private String name;
    private String imagePath;
    private String description;
    private String details;
    private BigDecimal price;

    // 构造函数、Getter和Setter方法


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
