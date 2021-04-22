package lk.wisdom_institute.asset.batch.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    GRADE_6("Grade-6"),
    GRADE_7("Grade-7"),
    GRADE_8("Grade-8"),
    GRADE_9("Grade-9"),
    GRADE_10("Grade-10"),
    GRADE_11("Grade-11");


    private final String grade;

}
