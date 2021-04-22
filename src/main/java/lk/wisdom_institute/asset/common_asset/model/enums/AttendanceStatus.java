package lk.wisdom_institute.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceStatus {
  PRE("Present"),
  AB("Absent");

  private final String attendanceStatus;
}
