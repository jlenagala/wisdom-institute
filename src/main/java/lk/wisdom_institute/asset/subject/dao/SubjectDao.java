package lk.wisdom_institute.asset.subject.dao;


import lk.wisdom_institute.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Integer> {

  Subject findFirstByOrderByIdDesc();
}
