import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;

public class CarSalesProducer {
    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092"; // Update with your Kafka broker configuration
        String topic = "demo-topic";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        Random random = new Random();
        String[] models = { "Supra", "Camry", "Sienna" };
        String[] dealers = { "Price Motors", "Cowboy Toyota" };
        String[] states = { "New York", "New Jersey" };

        while (true) {
            String carSaleData = generateRandomCarSaleData(models, dealers, states, random);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, carSaleData);

            producer.send(record);

            try {
                Thread.sleep(1000); // Send a new message every second
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        producer.close();
    }

    private static String generateRandomCarSaleData(String[] models, String[] dealers, String[] states, Random random) {
        String make = "Toyota";
        String model = models[random.nextInt(models.length)];
        String year = String.valueOf(2010 + random.nextInt(12)); // Years from 2010 to 2021
        String saleTimestamp = String.format("%d%02d%02d", 2022, random.nextInt(12) + 1, random.nextInt(28) + 1);
        String dealerName = dealers[random.nextInt(dealers.length)];
        String state = states[random.nextInt(states.length)];
        String price = String.valueOf(10000 + random.nextInt(10001)); // Prices from 10000 to 20000

        return String.format("{\"make\":\"%s\",\"model\":\"%s\",\"year\":\"%s\",\"saleTimestamp\":\"%s\",\"dealerName\":\"%s\",\"state\":\"%s\",\"price\":\"%s\"}",
                             make, model, year, saleTimestamp, dealerName, state, price);
    }
}
