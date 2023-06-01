package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.connection.zset.Aggregate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import redis.clients.jedis.search.aggr.AggregationResult;

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
        System.out.println(">>Inserting:" + newDoc.toString());
        Document returnDoc = mongoTemplate.insert(newDoc, "orders");
    }

    // Task 6
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for getPendingOrdersByEmail()
    // db.orders.aggregate(
    // [{$match:{email: "benjamin.ftc@gmail.com"},},
    // {$match:{delivered: {$exists: false},},},
    // {$project:{orderId: 1,total: 1,date: 1,},},
    // {$sort:{date: -1,},}]);

    public List<PizzaOrder> getPendingOrdersByEmail(String email) {
        MatchOperation matchEmail = Aggregation.match(Criteria.where("email").is(email));
        MatchOperation matchDelivered = Aggregation.match(Criteria.where("delivered").exists(false));
        ProjectionOperation projectFields = Aggregation.project("orderId", "total", "date");
        SortOperation sortByDate = Aggregation.sort(Sort.Direction.DESC, "date");

        Aggregation agg = Aggregation.newAggregation(matchEmail, matchDelivered, projectFields, sortByDate);
        AggregationResults<Document> resultDocs = mongoTemplate.aggregate(agg, "orders", Document.class);

        List<PizzaOrder> resultList = new ArrayList<>();
        for (Document doc : resultDocs) {
            PizzaOrder newOrder = new PizzaOrder();
            newOrder.setOrderId(doc.get("_id").toString());
            newOrder.setDate(doc.getDate("date"));
            newOrder.setTotal(doc.getDouble("total").floatValue());
            resultList.add(newOrder);
        }
        System.out.println(">>Aggregation Result:" + resultList);
        return resultList;
    }

    // TODO: Task 7
    // WARNING: Do not change the method's signature.
    // Write the native MongoDB query in the comment below
    // Native MongoDB query here for markOrderDelivered()
    // db.orders.updateOne({_id:"4e483fa59a"},{$set:{delivered:true}});
    public boolean markOrderDelivered(String orderId) {
        Query query = Query.query(Criteria.where("_id").is(orderId));
        Update updateField = new Update().set("delivered", true);
        UpdateResult result = mongoTemplate.updateFirst(query, updateField, Document.class, "orders");
        if (result != null) {
            System.out.println(">>Mongo Delivered");
            return true;
        }
        System.out.println(">>Mongo Not Found");
        return false;
    }

}
