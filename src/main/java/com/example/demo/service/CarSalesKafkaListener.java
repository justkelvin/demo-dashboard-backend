package com.demo.application.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.demo.application.model.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class CarSalesKafkaListener {
 
 @Autowired
 DemoMongoDBRepository demoRepository;
    
 @KafkaListener(topics = "${spring.kafka.topic.demo}")
 public void readDataStream(@Payload String record) {  
  //MongoDB
  if(record!=null && record.length()>0) {    
   DataModel object = null;
   try {
    object = new ObjectMapper().readValue(record, DataModel.class);
    demoRepository.save(object);
   }catch(Exception e) {
      e.printStackTrace();
   }
  }  
 }
}