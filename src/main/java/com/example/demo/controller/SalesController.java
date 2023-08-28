package com.demo.application.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/carsales")
public class SalesController {
 
 @Autowired
 DataService service;
 
 @GetMapping("/last30days")
 public ResponseEntity<String> findLast30DaysSales(){
  try {    
   return new ResponseEntity<String>(service.getLast30DaysCarSalesData(), HttpStatus.OK);   
  }catch (Exception e) {
   return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
 }
}