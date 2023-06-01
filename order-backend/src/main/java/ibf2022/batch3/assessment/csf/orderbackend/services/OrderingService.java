package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.utils.Utils;

@Service
public class OrderingService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private PendingOrdersRepository pendingOrdersRepo;

    @Value("${pricing.service.url}")
    private String pricingSvcURL;

    // Task 5
    // WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
    public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> req = RequestEntity.post(pricingSvcURL).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(Utils.parsePRxurl(order));
        System.out.println(">>PostReq:" + req.toString());
        try {
            ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
            System.out.println(">>PricingSvcResp:" + resp.getBody());
            String[] respArr = resp.getBody().split(",");
            order.setOrderId(respArr[0]);
            order.setDate(new Date(Long.parseLong(respArr[1])));
            order.setTotal(Float.parseFloat(respArr[2]));
            System.out.println(">>FullOrder:" + order.toString());

            ordersRepo.add(order);
            pendingOrdersRepo.add(order);

            return order;
        } catch (HttpStatusCodeException httpErr) {
            System.out.println(httpErr);
            throw new OrderException("Http: " + httpErr.getStatusCode().toString() + " Error");
        }
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

}
