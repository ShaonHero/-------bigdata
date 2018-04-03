



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;


public class HbaseTest {
    static Configuration config = null;
    private Connection connection = null;
    private Table table = null;


    //@Before
    public void inid(String name) throws Exception {
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.232.101,192.168.232.102,192.168.232.103,192.168.232.104");//zookeeper地址
        config.set("hbase.zookeeper.property.clientPort", "2181");
        connection = ConnectionFactory.createConnection(config);
        table = connection.getTable(TableName.valueOf(name));
    }

    /**
     * 创建一个表
     */

    public void createTable(String tName,String fName) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(config);
        TableName tableName = TableName.valueOf(tName);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        HColumnDescriptor family = new HColumnDescriptor(fName);
        desc.addFamily(family);
//        HColumnDescriptor family2 = new HColumnDescriptor("info2");
//        desc.addFamily(family2);
//        admin.createTable(desc);
    }
    /**
     * 删除一个表
     */

    @SuppressWarnings("deprecation")
    public void deleteTable(String name) throws MasterNotRunningException,ZooKeeperConnectionException,Exception{
        HBaseAdmin admin = new HBaseAdmin(config);
        admin.disableTable(name);
        admin.deleteTable(name);
        admin.close();
    }
    /**
     * 插入数据
     */


    @SuppressWarnings({"deprecation","resource"})
    public void insertData(String name,String tableName,String familyName,String LieName,String Values) throws Exception{
            inid(name);
        Put put = new Put(Bytes.toBytes(tableName));
        put.add(Bytes.toBytes(familyName),Bytes.toBytes(LieName), Bytes.toBytes(Values));
//        put.add(Bytes.toBytes("info1"),Bytes.toBytes("age"), Bytes.toBytes("16"));
//        put.add(Bytes.toBytes("info1"),Bytes.toBytes("sex"), Bytes.toBytes("man"));
//        put.add(Bytes.toBytes("info1"),Bytes.toBytes("adress"), Bytes.toBytes("guanghandadao"));
//        Put put1 = new Put(Bytes.toBytes("test4"));
//        put1.add(Bytes.toBytes("infor2"),Bytes.toBytes("name"), Bytes.toBytes("dd"));
//        put1.add(Bytes.toBytes("infor2"),Bytes.toBytes("age"), Bytes.toBytes("11"));
//        put1.add(Bytes.toBytes("infor2"),Bytes.toBytes("sex"), Bytes.toBytes("man"));
//        put1.add(Bytes.toBytes("infor2"),Bytes.toBytes("adress"), Bytes.toBytes("guanghandadao"));
        table.put(put);
    }

    /**
     * 删除数据
     */

    public void deleteData( String tableName,String familyName) throws Exception{
        Delete delete =  new Delete(Bytes.toBytes(tableName));
        delete.addFamily(Bytes.toBytes(familyName));
        table.delete(delete);
    }

    /**
     * 单条查询
     */

    public void queryData(String tableName,String familyName,String LieName) throws Exception{
        Get get = new Get(Bytes.toBytes(tableName));
        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes(familyName),Bytes.toBytes(LieName));
        System.out.println(Bytes.toString(value));
    }
    /**
     * 全表查询
     */

    public void scanData(String familyName,String LieName) throws Exception{
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner){
            byte[] value = result.getValue(Bytes.toBytes(familyName),Bytes.toBytes(LieName));
//            byte[] sex = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("sex"));
//            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
//            byte[] adress = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("adress"));
//            System.out.print(Bytes.toString(value)+" ");
//            System.out.print(Bytes.toString(sex)+" ");
//            System.out.print(Bytes.toString(age)+" ");
            System.out.print(Bytes.toString(value)+" ");
//            byte[] value1 = result.getValue(Bytes.toBytes("infor2"),Bytes.toBytes("name"));
//            System.out.println(Bytes.toString(value1));
        }
    }
    /**
     * 过滤查询，列名过滤器，可以通过正则表达式
     */

    public void scanDataByFilter1() throws Exception{
        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("name"));
        // SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("zhangshan"));
        Scan scan = new Scan();
        scan.setFilter(columnPrefixFilter);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner){
            byte[] value = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("name"));
            byte[] sex = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("sex"));
            byte[] age = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("age"));
            byte[] adress = result.getValue(Bytes.toBytes("info1"),Bytes.toBytes("adress"));
            System.out.print(Bytes.toString(value)+" ");
            System.out.print(Bytes.toString(sex)+" ");
            System.out.print(Bytes.toString(age)+" ");
            System.out.print(Bytes.toString(adress)+" ");
        }
    }
//    public void main(String[] args) {
//
//    }
}
