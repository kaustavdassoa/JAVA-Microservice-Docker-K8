package com.example.paymentservice.service;

import com.example.paymentservice.entity.TransactionDetails;
import com.example.paymentservice.model.PaymentMode;
import com.example.paymentservice.model.PaymentRequest;
import com.example.paymentservice.model.PaymentResponse;
import com.example.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        long transactionID;


        Optional<TransactionDetails> transactionDetails= transactionDetailsRepository.findByOrderId(paymentRequest.getOrderId()).stream().findFirst();
        if (transactionDetails.isPresent()) {

            transactionDetailsRepository.save(transactionDetails.get());
            transactionID=transactionDetails.get().getId();

        }
        else {

            TransactionDetails newTransactionDetails
                    = TransactionDetails.builder()
                    .paymentDate(Instant.now())
                    .paymentMode(paymentRequest.getPaymentMode().name())
                    .paymentStatus("SUCCESS")
                    .orderId(paymentRequest.getOrderId())
                    .referenceNumber(paymentRequest.getReferenceNumber())
                    .amount(paymentRequest.getAmount())
                    .build();
            transactionDetailsRepository.save(newTransactionDetails);
            transactionID=newTransactionDetails.getId();

        }

        log.info("Transaction Completed with Id: {}", transactionID);
        return transactionID;
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the Order Id: {}", orderId);


       List<TransactionDetails> transactionDetails = transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));

       if(transactionDetails.isEmpty())
       {

            return PaymentResponse.builder().build();
        }
        else
        {
            return PaymentResponse.builder()
                    .paymentId(transactionDetails.stream().findFirst().get().getId())
                    .paymentMode(PaymentMode.valueOf(transactionDetails.stream().findFirst().get().getPaymentMode()))
                    .paymentDate(transactionDetails.stream().findFirst().get().getPaymentDate())
                    .orderId(transactionDetails.stream().findFirst().get().getOrderId())
                    .status(transactionDetails.stream().findFirst().get().getPaymentStatus())
                    .amount(transactionDetails.stream().findFirst().get().getAmount())
                    .build();


        }
    }



}
