package org.example.loanCalculator.loanResponse;

import org.example.loanCalculator.loanParameters.Money;

public record Offer(int period, Money monthlyRate, Money maximumLoanAmount) {

}
