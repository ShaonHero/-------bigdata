

object test {
  def set(i:Int,j:Int):Int={
    val s = j - i
    s
  }

  def main(args: Array[String]): Unit = {
    val sss = new HbaseTest
    val name = "44444444"
    sss.insertData("user","hhh","info1","name",name)
//    name asInstanceOf[String]
    val i = "666"
    val j = "999"
    val value = set(i.toInt,j.toInt)
//    val str = "2018-03-23 10:20:29 LogProduce [INFO] {\"userId\":\"32\",\"day\":\"2018-03-23 10:20:29\",\"beginningtime\":\"1521771629256\",\"endingtime\":\"1521771629342\",\"data\":[{\"package1\":\"com.browser86\",\"activetime1\":\"32\" },{ \"package2\":\"com.browser12\",\"activetime2\":\"37\"}]}\n2018-03-23 10:20:34 LogProduce [INFO] {\"userId\":\"26\",\"day\":\"2018-03-23 10:20:34\",\"beginningtime\":\"1521771634267\",\"endingtime\":\"1521771634303\",\"data\":[{\"package1\":\"com.browser36\",\"activetime1\":\"26\" },{ \"package2\":\"com.browser9\",\"activetime2\":\"22\"}]}\n2018-03-23 10:20:39 LogProduce [INFO] {\"userId\":\"67\",\"day\":\"2018-03-23 10:20:39\",\"beginningtime\":\"1521771639271\",\"endingtime\":\"1521771639289\",\"data\":[{\"package1\":\"com.browser18\",\"activetime1\":\"67\" },{ \"package2\":\"com.browser68\",\"activetime2\":\"80\"}]}\n2018-03-23 10:20:44 LogProduce [INFO] {\"userId\":\"32\",\"day\":\"2018-03-23 10:20:44\",\"beginningtime\":\"1521771644277\",\"endingtime\":\"1521771644323\",\"data\":[{\"package1\":\"com.browser46\",\"activetime1\":\"32\" },{ \"package2\":\"com.browser65\",\"activetime2\":\"82\"}]}"
    //    val str2 = JSONObject.formatted(str)
    //    str2
//
//    while(a==b)
//      {
//        id =r()
//        day=r()
//        for(i<- 1-24)
//           o
//          {
//
//          }
//      }
//string = last
//    if day =day[i]
//    i++
//
    println(s"$value")
  }
}
