package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;

@Controller
public class OrderController {
    @Autowired
    OrderingService orderSvc;

    // TODO: Task 3 - POST /api/order
    @PostMapping(path = "api/order", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> placeOrder(@RequestBody String orderJson) {
        System.out.println("Recieved:" + orderJson);

        // TODO: CHANGE ME
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderJson.toString());
    }

    // TODO: Task 6 - GET /api/orders/<email>

    // TODO: Task 7 - DELETE /api/order/<orderId>

}
