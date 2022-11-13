package org.example.loanCalculator.constants;

import org.example.loanCalculator.loanParameters.Currency;
import org.example.loanCalculator.loanParameters.Money;

public class LoanParameterConstants {

    public static int MAX_LOAN_PERIOD = 100;
    public static int MIN_LOAN_PERIOD = 6;


    public static Money MIN_LOAN_AMOUNT = new Money("5000", Currency.PLN);
    public static Money MAX_LOAN_AMOUNT = new Money("150000", Currency.PLN);
    public static Money MAX_INVOLVEMENT = new Money("200000", Currency.PLN);
}
