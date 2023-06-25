package cn.fzu.edu.sm2020.rootsaleb.repository;

import cn.fzu.edu.sm2020.rootsaleb.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Integer> {
    // 可以在此定义特定的查询方法或自定义的数据库操作方法
    // ...
}
