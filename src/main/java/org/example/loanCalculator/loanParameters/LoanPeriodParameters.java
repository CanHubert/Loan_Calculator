package org.example.loanCalculator.loanParameters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LoanPeriodParameters {


    public static List<Values> getLoanPeriodParameters(int loanPeriod) {
        return Arrays.stream(Values.values()).filter(v -> v.minMonthsRange <= loanPeriod)
                .sorted(Comparator.comparing(Values::getMinMonthsRange).reversed())
                .collect(Collectors.toList());
    }


    public enum Values {
        UPTO1YEAR("60", "2", 6,12),
        UPTO3YEARS("60", "3", 13, 36),
        UPTO5YEARS("50", "3", 37, 60),
        OVER5YEARS("55", "3",61, 100);


        final BigDecimal dti;
        final BigDecimal percentage;

        final int minMonthsRange;
        final int maxMonthsRange;

        Values(String dti, String percentage, int minMonthsRange, int maxMonthsRange) {
            this.dti = new BigDecimal(dti);
            this.percentage = new BigDecimal(percentage);
            this.minMonthsRange = minMonthsRange;
            this.maxMonthsRange = maxMonthsRange;
        }

        public BigDecimal getDti() {
            return dti;
        }

        public BigDecimal getPercentage() {
            return percentage;
        }

        public int getMinMonthsRange() {
            return minMonthsRange;
        }

        public int getMaxMonthsRange() {
            return maxMonthsRange;
        }
    }
}
