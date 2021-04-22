package lk.wisdom_institute.asset.batch_student_exam_result.dao;


import lk.wisdom_institute.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStudentExamResultDao extends JpaRepository< BatchStudentExamResult, Integer > {

  BatchStudentExamResult findFirstByOrderByIdDesc();

}
