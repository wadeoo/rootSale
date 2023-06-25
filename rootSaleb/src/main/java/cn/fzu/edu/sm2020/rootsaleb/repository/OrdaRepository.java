package cn.fzu.edu.sm2020.rootsaleb.repository;

import cn.fzu.edu.sm2020.rootsaleb.entity.Orda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdaRepository extends JpaRepository<Orda, Integer> {
    List<Orda> findByUserId(int userId);

    List<Optional<Orda>> findOrdasByUserIdAndIsCart(int userId, int isCart);

    void deleteOrdaByIdAndIsCart(int id,int isCart);


}