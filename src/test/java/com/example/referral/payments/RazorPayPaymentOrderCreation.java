package com.example.referral.payments;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;

public class RazorPayPaymentOrderCreation extends TestCase {

    @Test
    public void testOrderCreation() throws Exception {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_yrtGf3KRPiikXt", "BvLDZUEyKWlcBBFEkkqEpIYH");
        JSONObject options = new JSONObject();
        options.put("amount", 5000);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        Order order = razorpayClient.Orders.create(options);


    }

    @Test
    public void testCreatePayment() throws Exception {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_yrtGf3KRPiikXt", "BvLDZUEyKWlcBBFEkkqEpIYH");
        JSONObject options = new JSONObject();
        options.put("amount", 1000);
        options.put("currency", "INR");
        razorpayClient.Payments.capture("order_GjHnvEcibWoAYW", options);
    }

    @Test
    public void testCheckoutProcess() throws Exception {

    }
}
