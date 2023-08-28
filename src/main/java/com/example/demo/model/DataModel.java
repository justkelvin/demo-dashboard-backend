package com.demo.application.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "carsales")
public class DataModel {
 
 @Id
 private String id; 
 String make;
 String model;
 String year;
 String saleTimestamp;
 String dealerName;
 String state;
 String price; 
 
 public String getMake() {
  return make;
 }
 public void setMake(String make) {
  this.make = make;
 }
 public String getModel() {
  return model;
 }
 public void setModel(String model) {
  this.model = model;
 }
 public String getYear() {
  return year;
 }
 public void setYear(String year) {
  this.year = year;
 }
 public String getSaleTimestamp() {
  return saleTimestamp;
 }
 public void setSaleTimestamp(String saleTimestamp) {
  this.saleTimestamp = saleTimestamp;
 }
 public String getDealerName() {
  return dealerName;
 }
 public void setDealerName(String dealerName) {
  this.dealerName = dealerName;
 }
 public String getState() {
  return state;
 }
 public void setState(String state) {
  this.state = state;
 }
 public String getPrice() {
  return price;
 }
 public void setPrice(String price) {
  this.price = price;
 }
 public double getPriceDouble() {
    return Double.valueOf(this.price);
  }
}