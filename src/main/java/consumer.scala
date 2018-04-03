


import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object consumer {
  val sparkMaster = "192.168.232.101"
  val sparkMasterPort = "7077"
  val zkQuorum = "192.168.232.101:2181,192.168.232.102:2181,192.168.232.103:2181,192.168.232.104:2181"
  val group = "test"
  val topics = Array("users")
  val numTreads = "1"
  val brokers ="192.168.232.101:9092"



  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setAppName("fkhds").setMaster("local[4]")//(s"spark://$sparkMaster:$sparkMasterPort")
    val ssc = new StreamingContext(conf,Seconds(5))


    val kafkaParam = Map(
      "bootstrap.servers" ->  "192.168.232.101:9092",//用于初始化链接到集群的地址
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> group,
      //如果没有初始化偏移量或者当前的偏移量不存在任何服务器上，可以使用这个配置属性
      //可以使用这个配置，latest自动重置偏移量为最新的偏移量
      "auto.offset.reset" -> "latest",
      //如果是true，则这个消费者的偏移量会在后台自动提交
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val lines = KafkaUtils.createDirectStream[String,String](ssc,PreferConsistent,Subscribe[String,String](topics,kafkaParam))
     lines.foreachRDD { rdd =>
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      offsetRanges.foreach(println(_))
    }
//    val words = lines.map(_.value())
    val data = lines.foreachRDD(x => {
      x.foreach(y => {
//        def replace(s:String):String ={
//          val s1 = s.replace("\""," ")
//          val s2 = s1.replace(":"," ")
//          val s3 = s2.replace("["," ")
//          val s4 = s3.replace("]"," ")
//          val s5 = s4.replace("{"," ")
//          val s6 = s5.replace("}"," ")
//          val s7 = s6.replace(","," ")
//          return s7
//        }
        val row = y.value()
//        println(row)
//        val str = replace(row)
//        val result1 = str.split("\\s+")
        var info = new info();
        info.uni(row)
        val config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.232.101,192.168.232.102,192.168.232.103,192.168.232.104");//zookeeper地址
        config.set("hbase.zookeeper.property.clientPort", "2181");
        val connection = ConnectionFactory.createConnection(config)
        val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
//        val get = new Get(Bytes.toBytes(info.getuserID+":"+info.getDay))
//        val result = table.get(get)
//        println(result)
//        if(result.equals("keyvalues=NONE")){
//          val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
//          put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_1_time))
//          put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_2),Bytes.toBytes(info.getPackage_2_time))
//          table.put(put)
//
//        } else {
//          val Row1= result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+info.getPackage_1))
//          val Row2= result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+info.getPackage_2))
//          println(Row1)
//          println(Row2)
//          if(Row1 != null){
//            val value = (Bytes.toString(Row1).toInt+info.getPackage_1_time).toString
//            val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
//            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
//            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(value))
//            table.put(put)
//          }else{
//            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
//            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_1_time))
//            table.put(put)
//          }
//          if(Row2 != null){
//            val value = (Bytes.toString(Row1).toInt+info.getPackage_1_time).toString
//            val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
//            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
//            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(value))
//            table.put(put)
//          }else{
            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_2_time))
            table.put(put)
//          }
//          table.get(get)
//        }


        val table1 =connection.getTable(TableName.valueOf("behavior_user_day_time_201702"))
        val put1 = new Put(Bytes.toBytes(info.getuserID))
        put1.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getDay),Bytes.toBytes(info.getallTime))
        table1.put(put1)

        val table2 =connection.getTable(TableName.valueOf("behavior_user_hour_app_time_201702"))
        for(i<- 0 to 23) {
          val put2 = new Put(Bytes.toBytes(info.getuserID + ":" + info.getDay + ":" + info.getPackage_1))
          put2.addColumn(Bytes.toBytes("timeLen"), Bytes.toBytes("timeLen." + i.toString), Bytes.toBytes(info.getapp1Time))
          table2.put(put2)
        }
        for(i<- 0 to 23) {
          val put3 = new Put(Bytes.toBytes(info.getuserID + ":" + info.getDay + ":" + info.getPackage_2))
          put3.addColumn(Bytes.toBytes("timeLen"), Bytes.toBytes("timeLen." +i.toString), Bytes.toBytes(info.getapp2Time))
          table2.put(put3)
        }
        val table3 =connection.getTable(TableName.valueOf("behavior_user_hour_time_201702"))
        val put4 = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
        for(i<- 0 to 23){
          put4.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+i.toString),Bytes.toBytes(info.getavgTime))
          table3.put(put4)
        }

      })
    })



    ssc.start()
    ssc.awaitTermination()
  }
}