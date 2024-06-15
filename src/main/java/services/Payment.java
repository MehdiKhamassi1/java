package services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import models.Don;

public class Payment {
    public static void main(String[] args) {
        //insert api key here:
        Stripe.apiKey = "";

        try {

            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());


        } catch (StripeException e) {
            e.printStackTrace();
        }

    }


}
