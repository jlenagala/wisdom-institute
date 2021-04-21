package lk.wisdom_institute.asset.discount.dao;


import lk.wisdom_institute.asset.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountDao extends JpaRepository< Discount, Integer> {
}
