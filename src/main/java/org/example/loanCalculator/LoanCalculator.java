package org.example.loanCalculator;

import org.example.loanCalculator.constants.LoanParameterConstants;
import org.example.loanCalculator.exceptions.BadParameterValueException;
import org.example.loanCalculator.loanParameters.Currency;
import org.example.loanCalculator.loanParameters.LoanParameters;
import org.example.loanCalculator.loanParameters.LoanPeriodParameters;
import org.example.loanCalculator.loanParameters.Money;
import org.example.loanCalculator.loanResponse.LoanResponse;
import org.example.loanCalculator.loanResponse.Offer;
import org.example.loanCalculator.validators.LoanCalculatorValidators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.example.loanCalculator.loanParameters.Currency.PLN;

public class LoanCalculator {

    private final LoanCalculatorValidators validators = new LoanCalculatorValidators();

    public LoanResponse calculate(LoanParameters loanParameters) {

        LoanResponse response = new LoanResponse();

        try {
            int maxLoanPeriod = maxLoanPeriod(loanParameters);
            List<LoanPeriodParameters.Values> loanPeriodParameters = LoanPeriodParameters.getLoanPeriodParameters(maxLoanPeriod);
            List<Offer> offers = new ArrayList<>();
            loanPeriodParameters.forEach(values -> {
                int loanPeriod = Math.min(maxLoanPeriod, values.getMaxMonthsRange());
                offers.add(new Offer(loanPeriod, maxMonthlyInstallment(loanParameters, values),
                        getMaxLoanAmount(loanParameters, loanPeriod, values)));
            });
            response.setOffers(offers);
        } catch (BadParameterValueException e) {
            response.setRejectMessage(e.getMessage());
        }

        return response;
    }


    private int maxLoanPeriod(LoanParameters loanParameters) {
        int maxLoanPeriod = Math.min(loanParameters.employmentPeriod().getEmploymentPeriod(), LoanParameterConstants.MAX_LOAN_PERIOD);
        validators.validateMaxLoadPeriod(maxLoanPeriod);
        return maxLoanPeriod;
    }

    private Money maxMonthlyInstallment(LoanParameters loanParameters, LoanPeriodParameters.Values values) {
        BigDecimal amount1 = loanParameters.monthlyIncome().getValue()
                .subtract(loanParameters.monthlyMaintenanceCosts().getValue())
                .subtract(loanParameters.monthlySumOfCreditObligations().getValue());

        BigDecimal amount2 = ((values.getDti().multiply(loanParameters.monthlyIncome().getValue()))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))
                .subtract(loanParameters.monthlySumOfCreditObligations().getValue());

        return new Money(amount1.min(amount2), Currency.PLN);
    }

    private Money getMaxLoanAmount(LoanParameters loanParameters, int loanPeriod, LoanPeriodParameters.Values values) {
        BigDecimal amount1 = LoanParameterConstants.MAX_INVOLVEMENT.getValue()
                .subtract(loanParameters.monthlySumOfCreditObligations().getValue());

        BigDecimal amount2 = LoanParameterConstants.MAX_LOAN_AMOUNT.getValue();

        BigDecimal min = amount1.min(amount2).min(getAmount3(loanParameters, loanPeriod, values)).setScale(2, RoundingMode.UP);

        Money money = new Money(min, PLN);
        validators.validateMaxLoanAmount(money);
        return money;
    }

    private BigDecimal getAmount3(LoanParameters loanParameters, int loanPeriod, LoanPeriodParameters.Values values) {
        BigDecimal mi = values.getPercentage()
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);

        Money maxMonthlyInstallment = maxMonthlyInstallment(loanParameters, values);

        BigDecimal divide = BigDecimal.ONE.divide(BigDecimal.ONE.add(mi), 2, RoundingMode.HALF_UP);
        BigDecimal pow = divide.pow(loanPeriod);
        BigDecimal subtract = BigDecimal.ONE.subtract(pow);

        BigDecimal divide1 = subtract.divide(mi, RoundingMode.UP);
        return maxMonthlyInstallment.getValue().multiply(divide1);
    }
}
