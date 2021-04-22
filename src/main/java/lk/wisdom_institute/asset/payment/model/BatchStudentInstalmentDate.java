package lk.wisdom_institute.asset.payment.model;

import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStudentInstalmentDate {
  private BatchStudent batchStudent;
  private InstalmentDate instalmentDate;
}
