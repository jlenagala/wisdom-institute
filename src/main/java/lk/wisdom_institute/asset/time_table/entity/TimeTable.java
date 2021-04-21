package lk.wisdom_institute.asset.time_table.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.subject.entity.Subject;
import lk.wisdom_institute.asset.time_table.entity.enums.TimeTableStatus;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "TimeTable" )
public class TimeTable extends AuditEntity {

  @Column( unique = true )
  private String code;

  private String lesson;

  private String remark;

  @Enumerated( EnumType.STRING )
  private TimeTableStatus timeTableStatus;

  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime startAt;

  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime endAt;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Subject subject;

}
