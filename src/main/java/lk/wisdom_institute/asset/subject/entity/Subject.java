package lk.wisdom_institute.asset.subject.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch_exam.entity.BatchExam;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.time_table.entity.TimeTable;
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
@JsonFilter( "Subject" )
public class Subject extends AuditEntity {

  @Column( unique = true )
  private String code;

  @Column( unique = true )
  private String name;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @OneToMany(mappedBy = "subject")
  private List< BatchExam > batchExams;

  @OneToMany(mappedBy = "subject")
  private List< TimeTable > timeTables;

  @ManyToMany(mappedBy = "subjects")
  private List< Batch > batches;

}
