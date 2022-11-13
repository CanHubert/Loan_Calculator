import org.example.loanCalculator.LoanCalculator;
import org.example.loanCalculator.validators.LoanCalculatorValidators;
import org.example.loanCalculator.loanParameters.EmploymentPeriod;
import org.example.loanCalculator.loanParameters.LoanParameters;
import org.example.loanCalculator.loanParameters.Money;
import org.example.loanCalculator.loanResponse.LoanResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.loanCalculator.loanParameters.Currency.PLN;

public class LoanCalculatorTest {

    private final LoanCalculator calculator = new LoanCalculator();

    public static Stream<Arguments> parametersSource() {
        return Stream.of(
                Arguments.of(buildLoanParameters(100, "15000", "2200", "300", "20000"), 4),
                Arguments.of(buildLoanParameters(60, "15000", "2200", "300", "20000"), 3),
                Arguments.of(buildLoanParameters(36, "15000", "2200", "300", "20000"), 2),
                Arguments.of(buildLoanParameters(12, "15000", "2200", "300", "20000"), 1)
        );

    }

    public static Stream<Arguments> wrongParameters() {
        return Stream.of(
                Arguments.of(buildLoanParameters(6, "2000", "2200", "300", "20000"), LoanCalculatorValidators.TOO_LOW_MAX_AMOUNT_ERROR_MESSAGE),
                Arguments.of(buildLoanParameters(5, "2000", "2200", "300", "20000"), LoanCalculatorValidators.TOO_LOW_MAX_PERIOD_ERROR_MESSAGE)
        );
    }


    @ParameterizedTest
    @MethodSource("parametersSource")
    void shouldReturnProperOffersQuantity(LoanParameters loanParameters, int expectedOffers) {
        LoanResponse response = calculator.calculate(loanParameters);

        Assertions.assertEquals(expectedOffers, response.getOffers().size());
    }

    @ParameterizedTest
    @MethodSource("wrongParameters")
    void shouldReturnRejectMessage(LoanParameters loanParameters, String message) {
        LoanResponse loanResponse = calculator.calculate(loanParameters);

        Assertions.assertEquals(message, loanResponse.getRejectMessage());
    }

    private static LoanParameters buildLoanParameters(int employmentPeriod,
                                               String monthlyIncome,
                                               String monthlyMaintenanceCosts,
                                               String monthlySumOfCreditObligations,
                                               String sumOfInstallmentsCreditBalances) {

        EmploymentPeriod employmentPeriod1 = new EmploymentPeriod(employmentPeriod);
        Money monthlyIncome1 = new Money(monthlyIncome, PLN);
        Money monthlyMaintananceCosts1 = new Money(monthlyMaintenanceCosts, PLN);
        Money monthlySumOfCreditObligations1 = new Money(monthlySumOfCreditObligations, PLN);
        Money sumOfInstallmentsCreditBalances1 = new Money(sumOfInstallmentsCreditBalances, PLN);

        return new LoanParameters(employmentPeriod1,
                monthlyIncome1,
                monthlyMaintananceCosts1,
                monthlySumOfCreditObligations1,
                sumOfInstallmentsCreditBalances1);

    }

}
