package org.example.loanCalculator.loanParameters;

public record LoanParameters(EmploymentPeriod employmentPeriod,
                             Money monthlyIncome,
                             Money monthlyMaintenanceCosts,
                             Money monthlySumOfCreditObligations,
                             Money sumOfInstallmentCreditBalances) {

}
