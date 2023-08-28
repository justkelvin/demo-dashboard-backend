package com.demo.application.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.demo.application.model.DataModel;
@Service
public class DataService { 
 @Autowired
 DemoMongoDBRepository repository;
 
 public String getLast30DaysCarSalesData() {  
  JSONObject jsonResponse = new JSONObject();
  try {   
   LocalDate to = LocalDate.now();
   LocalDate from = to.minusDays(30);     
   String a = (from.format(DateTimeFormatter.ofPattern("yyyyMMdd"))); 
   
   List<DataModel> sales = new ArrayList<DataModel>();    
   repository.findSalesGreaterThanDate(a).forEach(sales::add);
   String totalSales = "";
   
   if (!sales.isEmpty()) { 
    //Total Sales Amount
    double totalAmount = sales.stream().mapToDouble(DataModel::getPriceDouble).sum();
    totalSales = ("$"+ String.format("%.2fM", totalAmount/ 1000000.0));
    jsonResponse.put("totalSales", totalSales);
    
    //Car Sales by Model 
// Sample result   datasets: [
//               { data: [ 65 ], label: 'Corolla' },
//               { data: [ 28], label: 'Camry' },
//               { data: [ 21], label: 'Highlander' },
//               { data: [ 31], label: 'Sienna' },
//               { data: [ 30], label: 'Supra' }
//             ]
    JSONArray carModelSales = new JSONArray();    
    Map<String, Long> salesCollection = sales.stream()
               .collect(Collectors.groupingBy(DataModel::getModel, Collectors.counting()));
    salesCollection.keySet().forEach(key->{
     JSONObject sale = new JSONObject();
     sale.put("label", key);
     JSONArray array = new JSONArray();
     array.put(salesCollection.get(key));
     sale.put("data", array);
     carModelSales.put(sale);     
    });
    jsonResponse.put("carModelSales", carModelSales);
    
    //Sales by Dealership
// Sample result    labels: [ "Cowboy Toyota", "Texas Toyota", "New York Toyota" ],
//       datasets: [ {
//         data: [ 300, 500, 100 ]
//       } ]
    JSONObject dealersales = new JSONObject();
    JSONArray dealerNames = new JSONArray();
    JSONArray dealerData = new JSONArray();
    JSONArray dealerDataSet = new JSONArray();
    Map<String, Long> dealers = sales.stream()
               .collect(Collectors.groupingBy(DataModel::getDealerName, Collectors.counting()));
    dealers.keySet().forEach(key->{     
     dealerNames.put(key);      
     dealerData.put(dealers.get(key));         
    });
    JSONObject data = new JSONObject();
    data.put("data", dealerData);
    dealerDataSet.put(data);
    dealersales.put("labels", dealerNames);
    dealersales.put("datasets", dealerDataSet);
    jsonResponse.put("dealerSales", dealersales);    
    
    //Sales by State
// Sample result  labels: [ "Texas","New York","Florida","New Jersey" ],
//        datasets: [ {
//          data: [ 300, 500, 100,300 ]
//        } ]
    JSONObject statesales = new JSONObject();
    JSONArray stateNames = new JSONArray();
    JSONArray stateData = new JSONArray();
    JSONArray stateDataSet = new JSONArray();
    Map<String, Long> states = sales.stream()
               .collect(Collectors.groupingBy(DataModel::getState, Collectors.counting()));
    states.keySet().forEach(key->{     
     stateNames.put(key);      
     stateData.put(states.get(key));         
    });
    JSONObject state = new JSONObject();
    state.put("data", stateData);
    stateDataSet.put(state);
    statesales.put("labels", stateNames);
    statesales.put("datasets", stateDataSet);
    jsonResponse.put("stateSales", statesales);   
   }   
  }catch (Exception e) {
       e.printStackTrace();    
  }
  return jsonResponse.toString();
 }
}