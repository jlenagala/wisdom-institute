package lk.wisdom_institute.asset.payment.dao;


import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.asset.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository< Payment, Integer> {

  Payment findFirstByOrderByIdDesc();



  List< Payment> findByCreatedAtIsBetween(LocalDateTime startAt, LocalDateTime endAt);


  Payment findByInstalmentDateAndBatchStudent(InstalmentDate instalmentDate, BatchStudent batchStudent);
}
