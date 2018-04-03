import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

public class ddd {
    public static void main(String[] args){
        String sparkMaster = "192.168.232.101";
        String sparkMasterPort = "7077";
        String zkQuorum = "192.168.232.101:2181,192.168.232.102:2181,192.168.232.103:2181,192.168.232.104:2181";
        String group = "test";
        String topics = "user";
        String numTreads = "1";
        String brokers ="192.168.232.101:9092";
        HbaseTest hbaseTest = new HbaseTest();
        SparkConf conf = new SparkConf().setAppName("fkhds").setMaster("local[4]");
        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(5000));
    }
}
