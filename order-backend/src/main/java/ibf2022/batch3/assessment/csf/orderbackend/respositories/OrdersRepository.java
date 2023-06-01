package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {

    // Task 3
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for add()
    /*
     * db.orders.insert( {
     * "_id": id,
     * "date": 2023-06-01,
     * "total": "99.99",
     * "name": "Benjamin Foo Toon Chian",
     * "email": "Benjamin.ftc@gmail.com",
     * "sauce": "Salsa Sauce",
     * "size": 1,
     * "comments": "Please add extra salsa",
     * "crust": "thick"
     * "toppings": ["Chicken","Seafood"]
     * }
     */

    @Autowired
    MongoTemplate mongoTemplate;

    public void add(PizzaOrder order) {
        Document newDoc = new Document();
        newDoc.append("_id", order.getOrderId());
        // note date is stored as Date Class in Object
        newDoc.append("date", order.getDate());
        newDoc.append("total", order.getTotal());
        newDoc.append("name", order.getName());
        newDoc.append("email", order.getEmail());
        newDoc.append("sauce", order.getSauce());
        newDoc.append("size", order.getSize());
        newDoc.append("comments", order.getComments());
        newDoc.append("crust", order.isThickCrust() ? "thick" : "thin");
        newDoc.append("toppings", order.getTopplings());
        System.out.println("Inserting:" + newDoc.toString());

        Document returnDoc = mongoTemplate.insert(newDoc, "orders");

    }

    // TODO: Task 6
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for getPendingOrdersByEmail()
    public List<PizzaOrder> getPendingOrdersByEmail(String email) {

        return null;
    }

    // TODO: Task 7
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for markOrderDelivered()
    public boolean markOrderDelivered(String orderId) {

        return false;
    }

}
