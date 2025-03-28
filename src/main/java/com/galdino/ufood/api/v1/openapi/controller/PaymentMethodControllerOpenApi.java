package com.galdino.ufood.api.v1.openapi.controller;

import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.v1.model.PaymentMethodInput;
import com.galdino.ufood.api.v1.model.PaymentMethodModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Payment Method")
public interface PaymentMethodControllerOpenApi {
    @ApiOperation("List all payment methods")
    public ResponseEntity<List<PaymentMethodModel>> list(ServletWebRequest request);

    @ApiOperation("Find payment method by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Parameter", response = Problem.class),
            @ApiResponse(code = 404, message = "Payment Method not found", response = Problem.class)
    })
    public ResponseEntity<PaymentMethodModel> findById(@ApiParam(value = "Payment Method id", example = "1") Long id, ServletWebRequest request);

    @ApiOperation("Create Payment Method")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Payment Method created")
    })
    public PaymentMethodModel add(@ApiParam(name = "body", value = "New payment method representation") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Update payment method by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment Method updated"),
            @ApiResponse(code = 404, message = "Payment Method not found", response = Problem.class)
    })
    public PaymentMethodModel update(@ApiParam(value = "Payment Method id", example = "1") Long id,
                                     @ApiParam("New payment method representation with new data") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Remove payment method by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Payment Method removed"),
            @ApiResponse(code = 404, message = "Payment Method not found", response = Problem.class)
    })
    public void delete(@ApiParam(value = "Payment Method id", example = "1") Long id);
}
