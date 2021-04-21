package lk.wisdom_institute.asset.payment.dao;


import lk.wisdom_institute.asset.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository< Payment, Integer> {

}
