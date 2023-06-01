package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderException;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import ibf2022.batch3.assessment.csf.orderbackend.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Controller
public class OrderController {
    @Autowired
    OrderingService orderSvc;

    // Task 3 - POST /api/order
    @PostMapping(path = "api/order", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> placeOrder(@RequestBody String orderJson) {
        System.out.println(">>Recieved:" + orderJson);
        try {
            PizzaOrder processedOrder = orderSvc.placeOrder(Utils.createOrder(Utils.getJSONObj(orderJson)));

            JsonObject processedJson = Json.createObjectBuilder()
                    .add("orderId", processedOrder.getOrderId())
                    .add("date", processedOrder.getDate().getTime())
                    .add("name", processedOrder.getName())
                    .add("email", processedOrder.getEmail())
                    .add("total", processedOrder.getTotal())
                    .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(processedJson.toString());
        } catch (OrderException orderErr) {
            System.out.println(orderErr);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderErr.toString());
        }
    }

    // TODO: Task 6 - GET /api/orders/<email>
    @GetMapping(path = "api/orders/{email}")
    @ResponseBody
    public ResponseEntity<String> getOrdersByEmail(@PathVariable String email) {
        System.out.println(">>GET orders By:" + email);

        List<PizzaOrder> userOrders = orderSvc.getPendingOrdersByEmail(email);
        JsonArrayBuilder ordersAB = Json.createArrayBuilder();
        for (PizzaOrder order : userOrders) {
            JsonObject orderJson = Json.createObjectBuilder()
                    .add("orderId", order.getOrderId())
                    .add("total", order.getTotal())
                    .add("date", Long.toString(order.getDate().getTime()))
                    .build();
            ordersAB.add(orderJson);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersAB.build().toString());
    }

    // TODO: Task 7 - DELETE /api/order/<orderId>
    @DeleteMapping(path = "/api/order/{orderId}")
    @ResponseBody
    public ResponseEntity<String> deliveredOrder(@PathVariable String orderId) {

        if (orderSvc.markOrderDelivered(orderId))
            return ResponseEntity.ok().build();
        JsonObject error = Json.createObjectBuilder().add("404 Error", "Order Not Found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());

    }

}
