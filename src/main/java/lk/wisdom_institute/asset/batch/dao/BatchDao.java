package lk.wisdom_institute.asset.batch.dao;



import lk.wisdom_institute.asset.batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface BatchDao extends JpaRepository< Batch, Integer > {

  Batch findFirstByOrderByIdDesc();

  Batch findByName(String name);


  Batch findByNameAndStartAtIsBetweenAndEndAtIsBetween(String name, LocalDate startAt, LocalDate endAt, LocalDate startAt1, LocalDate endAt1);
}
