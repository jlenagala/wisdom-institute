package lk.wisdom_institute.asset.employee.dao;



import lk.wisdom_institute.asset.employee.entity.Employee;
import lk.wisdom_institute.asset.employee.entity.enums.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeDao extends JpaRepository< Employee, Integer> {
    Employee findFirstByOrderByIdDesc();

    Employee findByNic(String nic);
  List< Employee> findByDesignation(Designation designation);
}
