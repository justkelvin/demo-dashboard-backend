package com.demo.application.service;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.demo.application.model.DataModel;
public interface DemoMongoDBRepository extends MongoRepository<DataModel, String> {
 
    //Supports native JSON query string
    @Query("{saleTimestamp:{$gt:'?0'}}")
    List<DataModel> findSalesGreaterThanDate(String submittedDate);
}