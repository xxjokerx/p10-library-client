package com.gg.proj.consumer.connectors;

import com.gg.proj.consumer.ConsumerProperties;
import com.gg.proj.consumer.wsdl.loans.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * This class performs the connection to the web service's loan endpoint
 */
public class LoanConnector extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(LoanConnector.class);

    private String serviceLocation;

    private ConsumerProperties consumerProperties;

    public LoanConnector(@Autowired ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
        this.serviceLocation = this.consumerProperties.getUri() + "/loans";
    }

    public LoanConnector(WebServiceMessageFactory messageFactory, @Autowired ConsumerProperties consumerProperties) {
        super(messageFactory);
        this.consumerProperties = consumerProperties;
        this.serviceLocation = this.consumerProperties.getUri() + "/loans";
    }

    public FindAllLoansByUserIdResponse findAllLoansByUserId(Integer userId, String tokenUUID) {
        FindAllLoansByUserIdRequest request = new FindAllLoansByUserIdRequest();
        log.info("Requesting Web service's method FindAllLoansByUserId with the userId : [" + userId + "]");
        log.debug("Service is located at : " + serviceLocation);

        request.setUserId(userId);
        request.setTokenUUID(tokenUUID);

        return (FindAllLoansByUserIdResponse) getWebServiceTemplate()
                .marshalSendAndReceive(serviceLocation, request,
                        new SoapActionCallback("http://proj.gg.com/service/library-client"));
    }


    public GetLoanResponse findById(int loanId, String tokenUUID) {
        GetLoanRequest request = new GetLoanRequest();
        log.info("Requesting Web service's method GetLoan, with the loanId = [" + loanId + "] and an UUID");
        log.debug("Service is located at : " + serviceLocation);

        request.setId(loanId);
        request.setTokenUUID(tokenUUID);

        GetLoanResponse response = (GetLoanResponse) getWebServiceTemplate()
                .marshalSendAndReceive(serviceLocation, request,
                        new SoapActionCallback("http://proj.gg.com/service/library-client"));
        return response;
    }

    public void extend(Loan loan, String tokenUUID) {
        ExtendLoanRequest request = new ExtendLoanRequest();
        log.info("Requesting Web service's method ExtendLoan with the loan object = {" + loan.getId());
        log.debug("Service is located at : " + serviceLocation);

        request.setLoan(loan);
        request.setTokenUUID(tokenUUID);

        ExtendLoanResponse response = (ExtendLoanResponse) getWebServiceTemplate()
                .marshalSendAndReceive(serviceLocation, request,
                        new SoapActionCallback("http://proj.gg.com/service/library-client"));

    }
}
