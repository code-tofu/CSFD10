package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Repository
public class PendingOrdersRepository {

    @Autowired
    @Qualifier("pending-orders")
    RedisTemplate<String, String> redisTemplate;

    // TODO: Task 3
    // WARNING: Do not change the method's signature.
    public void add(PizzaOrder order) {
        String pendingOrderJsonStr = parsePendOrder(order);
        this.redisTemplate.opsForValue().set(order.getOrderId(), pendingOrderJsonStr);
    }

    // TODO: Task 7
    // WARNING: Do not change the method's signature.
    public boolean delete(String orderId) {
        return false;
    }

    public String parsePendOrder(PizzaOrder order) {
        JsonObject pendingOrderJson = Json.createObjectBuilder()
                .add("orderId", order.getOrderId())
                .add("date", order.getDate().toString())
                .add("name", order.getName())
                .add("email", order.getEmail())
                .build();
        return pendingOrderJson.toString();
    }

}
