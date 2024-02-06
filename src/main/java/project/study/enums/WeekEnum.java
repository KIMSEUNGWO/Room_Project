package project.study.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum WeekEnum {

    MON("월"),
    TUE("화"),
    WEN("수"),
    THU("목"),
    FRI("금"),
    SAT("토"),
    SUN("일");

   private String kor;

}
