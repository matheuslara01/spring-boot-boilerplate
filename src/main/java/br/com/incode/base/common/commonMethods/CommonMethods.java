package br.com.incode.base.common.commonMethods;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class CommonMethods {
    

    public static LocalDate getDataSemana(boolean primeiroDia) {
        LocalDate data;
        if (primeiroDia) {
            data = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        } else {
            data = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        }
        return data;
    }
}
