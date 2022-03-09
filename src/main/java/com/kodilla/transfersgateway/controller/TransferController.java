package com.kodilla.transfersgateway.controller;

import com.kodilla.commons.Transfer;
import com.kodilla.transfersgateway.controller.request.TransferRequest;
import com.kodilla.transfersgateway.service.TransferProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/v1/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferProducer transferProducer;

    @PostMapping
    public void saveTransfer(@RequestBody TransferRequest transferRequest){
        if(BigDecimal.ZERO.compareTo(transferRequest.getAmount()) > 0 ){
            log.warn("value {} < 0 ", transferRequest.getAmount());
            throw new IllegalArgumentException("Value < 0");
        }
        log.info("Received transfer request: {}", transferRequest);
        Transfer transfer = new Transfer();
        transfer.setAmount(transferRequest.getAmount());
        transfer.setRecipientAccount(transferRequest.getRecipientAccount());
        transfer.setSenderAccount(transferRequest.getSenderAccount());
        transfer.setTitle(transferRequest.getTitle());

        transferProducer.sendTransfer(transfer);
    }
}
