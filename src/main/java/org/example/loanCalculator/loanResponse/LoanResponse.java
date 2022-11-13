package org.example.loanCalculator.loanResponse;

import java.util.List;

public class LoanResponse {

    private List<Offer> offers;

    private String rejectMessage;


    public LoanResponse() {
    }

    public LoanResponse(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }


    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }
}
