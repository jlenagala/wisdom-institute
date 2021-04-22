package lk.wisdom_institute.asset.batch_student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.payment.entity.Payment;
import lk.wisdom_institute.asset.student.entity.Student;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "BatchStudent" )
public class BatchStudent extends AuditEntity {

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Student student;

  @OneToMany( mappedBy = "batchStudent" )
  private List< Payment > payments;

  @OneToMany( mappedBy = "batchStudent" )
  private List< BatchStudentExamResult > batchStudentExamResults;


}
