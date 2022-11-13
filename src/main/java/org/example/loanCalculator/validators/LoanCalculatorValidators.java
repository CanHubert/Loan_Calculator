package org.example.loanCalculator.validators;

import org.example.loanCalculator.constants.LoanParameterConstants;
import org.example.loanCalculator.exceptions.BadParameterValueException;
import org.example.loanCalculator.loanParameters.Money;

public class LoanCalculatorValidators {

    public static String TOO_LOW_MAX_PERIOD_ERROR_MESSAGE= "Loan period for given parameters is too low";
    public static String TOO_LOW_MAX_AMOUNT_ERROR_MESSAGE= "Loan max amount is too low";

    public void validateMaxLoadPeriod(int maxLoanPeriod) {
        if (maxLoanPeriod < LoanParameterConstants.MIN_LOAN_PERIOD) {
            throw new BadParameterValueException(TOO_LOW_MAX_PERIOD_ERROR_MESSAGE);
        }
    }

    public void validateMaxLoanAmount(Money maxLoanAmount) {
        if(maxLoanAmount.compareTo(LoanParameterConstants.MIN_LOAN_AMOUNT) == -1) {
            throw new BadParameterValueException(TOO_LOW_MAX_AMOUNT_ERROR_MESSAGE);
        }
    }
}
