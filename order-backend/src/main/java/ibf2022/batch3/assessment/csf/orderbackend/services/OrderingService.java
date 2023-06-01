package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class OrderingService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private PendingOrdersRepository pendingOrdersRepo;

    @Value("${pricing.service.url}")
    private String pricingSvcURL;

    // TODO: Task 5
    // WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
    public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> req = RequestEntity.post(pricingSvcURL).contentType(MediaType.APPLICATION_JSON)
                .body(parsePRxurl(order));

        return null;
    }

    // For Task 6
    // WARNING: Do not change the method's signature or its implemenation
    public List<PizzaOrder> getPendingOrdersByEmail(String email) {
        return ordersRepo.getPendingOrdersByEmail(email);
    }

    // For Task 7
    // WARNING: Do not change the method's signature or its implemenation
    public boolean markOrderDelivered(String orderId) {
        return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
    }

    public String parsePRxurl(PizzaOrder order) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", order.getName());
        requestParams.put("email", order.getEmail());
        requestParams.put("sauce", order.getSauce());
        requestParams.put("size", Integer.toString(order.getSize()));
        requestParams.put("thickCrust", Boolean.toString(order.isThickCrust()));
        requestParams.put("toppings", parseToppings(order.getTopplings()));
        requestParams.put("comments", order.getComments());

        String encodedPR = requestParams.keySet()
                .stream()
                .map(key -> key + "=" + requestParams.get(key))
                .collect(Collectors.joining("&"));
        System.out.println(encodedPR);
        return encodedPR;
    }

    public JsonObject parsePRjson(PizzaOrder order) {
        JsonArrayBuilder toppingsAB = Json.createArrayBuilder(order.getTopplings());
        // for (String topping : order.getTopplings()) {
        // toppingsAB.add(topping);
        // }
        JsonObject jsonPR = Json.createObjectBuilder()
                .add("name", order.getName())
                .add("email", order.getEmail())
                .add("sauce", order.getSauce())
                .add("size", order.getSize())
                .add("thickCrust", order.isThickCrust())
                .add("toppings", toppingsAB)
                .add("comments", order.getComments())
                .build();
        System.out.println(jsonPR);
        return jsonPR;
    }

    public String parseToppings(List<String> toppingList) {
        StringBuilder toppingStr = new StringBuilder();
        for (int i = 0; i < toppingList.size(); i++) {
            toppingStr.append(toppingList.get(i));
            if (i == toppingList.size() - 1)
                break;
            toppingStr.append(',');
        }
        System.out.println(toppingStr.toString());
        return toppingStr.toString();
    }

}
