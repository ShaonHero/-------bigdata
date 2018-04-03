import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.{SparkConf, SparkContext}
object count {
def count(info:info): Unit ={
  val config = HBaseConfiguration.create();
  config.set("hbase.zookeeper.quorum", "192.168.232.101,192.168.232.102,192.168.232.103,192.168.232.104");//zookeeper地址
  config.set("hbase.zookeeper.property.clientPort", "2181");
  val connection = ConnectionFactory.createConnection(config)
          val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
          val get = new Get(Bytes.toBytes(info.getuserID+":"+info.getDay))
          val result = table.get(get)
          println(result)
          if(result.equals("keyvalues=NONE")){
            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_1_time))
            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_2),Bytes.toBytes(info.getPackage_2_time))
            table.put(put)

          } else {
            val Row1= result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+info.getPackage_1))
            val Row2= result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+info.getPackage_2))
            println(Row1)
            println(Row2)
            if(Row1 != null){
              val value = (Bytes.toString(Row1).toInt+info.getPackage_1_time).toString
              val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
              val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
              put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(value))
              table.put(put)
            }else{
              val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
              put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_1_time))
              table.put(put)
            }
            if(Row2 != null){
              val value = (Bytes.toString(Row1).toInt+info.getPackage_1_time).toString
              val table =connection.getTable(TableName.valueOf("behavior_user_app_201702"))
              val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
              put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(value))
              table.put(put)
            }else{
            val put = new Put(Bytes.toBytes(info.getuserID+":"+info.getDay))
           put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes( "timeLen."+info.getPackage_1),Bytes.toBytes(info.getPackage_2_time))
           table.put(put)
            }
            table.get(get)
          }

}
}
