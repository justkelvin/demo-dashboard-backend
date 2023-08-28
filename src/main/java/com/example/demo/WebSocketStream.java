package com.demo.application.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableScheduling
public class WebSocketStream {
 
 @Value("${stomp.topic}")
 private String stompTopic;
 
 @Autowired
 private SimpMessagingTemplate messagingTemplate;
 
 @Autowired
 private DataService service;
 
 @Scheduled(fixedRate = 2000)
 public void streamCarSalesData() {
  String jsonObject = service.getLast30DaysCarSalesData();  
  messagingTemplate.convertAndSend(stompTopic,jsonObject);   
 }
}