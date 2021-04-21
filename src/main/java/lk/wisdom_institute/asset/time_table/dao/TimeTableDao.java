package lk.wisdom_institute.asset.time_table.dao;


import lk.wisdom_institute.asset.time_table.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableDao extends JpaRepository< TimeTable, Integer> {

}
