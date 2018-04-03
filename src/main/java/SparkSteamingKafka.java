import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkSteamingKafka {
    public static void main(String[] args) throws InterruptedException {
        String brokers = "192.168.232.101:9092";
//        String topics = "topicA";
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("streaming word count");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.seconds(1));
        List<String> topicsSet = Arrays.asList("test");
//        String topicsSet = Arrays.asList("topicA", "topicB");
        //kafka相关参数，必要！缺了会报错
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", brokers) ;
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("group.id", "group1");
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //Topic分区
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(new TopicPartition("test", 0), 2L);
        //通过KafkaUtils.createDirectStream(...)获得kafka数据，kafka相关参数由kafkaParams指定
        JavaInputDStream<ConsumerRecord<Object,Object>> lines = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams, offsets)
        );
        //这里就跟之前的demo一样了，只是需要注意这边的lines里的参数本身是个ConsumerRecord对象
        JavaPairDStream<String, Integer> counts =
                lines.flatMap(x -> Arrays.asList(x.value().toString().split(" ")).iterator())
                        .mapToPair(x -> new Tuple2<String, Integer>(x, 1))
                        .reduceByKey((x, y) -> x + y);
        counts.print();
//  可以打印所有信息，看下ConsumerRecord的结构
        lines.foreachRDD(rdd -> {
            rdd.foreach(x -> {
                System.out.println(x);
            });
        });
        ssc.start();
        ssc.awaitTermination();
        ssc.close();
    }
}
