package lk.wisdom_institute.asset.instalment_date.dao;


import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InstalmentDateDao extends JpaRepository<InstalmentDate, Integer> {

  InstalmentDate findFirstByOrderByIdDesc();

  List<InstalmentDate> findByDateBefore(LocalDate localDate);
}
