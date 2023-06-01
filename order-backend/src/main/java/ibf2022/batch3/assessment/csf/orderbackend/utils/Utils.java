package ibf2022.batch3.assessment.csf.orderbackend.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {

    public static PizzaOrder createOrder(JsonObject orderJson) {
        PizzaOrder newOrder = new PizzaOrder();
        newOrder.setComments(orderJson.getString("comments"));
        newOrder.setEmail(orderJson.getString("email"));
        newOrder.setName(orderJson.getString("name"));
        newOrder.setSauce(orderJson.getString("sauce"));
        newOrder.setSize(orderJson.getInt("size"));
        newOrder.setThickCrust(orderJson.getString("crust").equals("thick") ? true : false);

        JsonArray toppingsArr = orderJson.getJsonArray("toppings");
        List<String> toppingsList = new LinkedList<>();
        for (JsonValue topping : toppingsArr) {
            toppingsList.add(topping.toString());
        }
        newOrder.setTopplings(toppingsList);
        // Total, OrderId, Date is not initialised
        return newOrder;
    }

    public static String parsePRxurl(PizzaOrder order) {
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
        System.out.println(">>Request XURL:" + encodedPR);
        return encodedPR;
    }

    public static JsonObject parsePRjson(PizzaOrder order) {
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
        System.out.println(">>Request Json:" + jsonPR);
        return jsonPR;
    }

    public static String parseToppings(List<String> toppingList) {
        StringBuilder toppingStr = new StringBuilder();
        for (int i = 0; i < toppingList.size(); i++) {
            toppingStr.append(toppingList.get(i));
            if (i == toppingList.size() - 1)
                break;
            toppingStr.append(',');
        }
        System.out.println(">>ToppingStr:" + toppingStr.toString());
        return toppingStr.toString();
    }

    public static JsonObject getJSONObj(String jsonString) {
        StringReader sr = new StringReader(jsonString.toString());
        JsonReader jsr = Json.createReader(sr);
        JsonObject jsonObj = (JsonObject) jsr.read();
        System.out.println(">>JsonConverted:" + jsonObj.toString());
        return jsonObj;
    }
}
