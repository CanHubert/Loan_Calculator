package org.example.loanCalculator.loanParameters;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {

    private final BigDecimal value;
    private final Currency currency;

    public Money(String amount, Currency currency) {
        this.value = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public int compareTo(Money money) {
        if(!this.currency.equals(money.currency)) {
            throw new RuntimeException("Can't compare different currencies");
        }
        return this.value.compareTo(money.value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
