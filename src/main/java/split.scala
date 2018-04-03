
    class info() {

      private var userID: String = ""
      private var Day: String = ""
      private var Beginningtime: String = ""
      private var Endingtime: String = ""
      private var allTime: String = ""
      private var Package_1: String = ""
      private var Package_2: String = ""
      private var Package_1_time: String = ""
      private var Package_2_time: String = ""
      private var avgTime:String = ""
      private var app1Time:String = ""
      private var app2Time:String = ""
//      val str = replace(s)
//      val result = str.split("\\s+")
//      def setall(userid: String,day:String,  package_1: String, package_2: String, package_1_time: String, package_2_time: String,alltime:String): Unit = {
//        this.userID = userid;
//        this.Day = day;
//        this.Package_1 = package_1;
//        this.Package_1_time = package_1_time;
//        this.Package_2 = package_2;
//        this.Package_2_time = package_2_time;
//
//        this.allTime=alltime
//      }
      def SetuserID(userID:String){
        this.userID = userID;
      }
      def getuserID: String = {
        return userID
      }
      def SetDay(Day:String){
        this.Day = Day;
      }
      def getDay: String = {
        return Day
      }
      def SetBeginningtime(Beginningtime:String){
        this.Beginningtime = Beginningtime;
      }
      def getBenginningtime: String = {
        return Beginningtime
      }
      def SetEndingtime(Endingtime:String){
        this.Endingtime = Endingtime;
      }
      def getEndingtime: String = {
        return Endingtime
      }
      def SetallTime(allTime:String){
        this.allTime = allTime;
      }

      def getallTime: String = {
        return allTime
      }
      def SetPackage_1(Package_1:String){
        this.Package_1 = Package_1;
      }
      def getPackage_1: String = {
        return Package_1
      }
      def SetPackage_2(Package_2:String){
        this.Package_2 = Package_2;
      }
      def getPackage_2: String = {
        return Package_2
      }
      def SetPackage_1_time(Package_1_time:String){
        this.Package_1_time= Package_1_time;
      }
      def getPackage_1_time: String = {
        return Package_1_time
      }
      def SetPackage_2_time(Package_2_time:String){
        this.Package_2_time = Package_2_time;
      }
      def getPackage_2_time: String = {
        return Package_2_time
      }
      def SetavgTime(avgTime:String){
        this.avgTime = avgTime;
      }
      def getavgTime: String = {
        return avgTime
      }
      def Setapp1Time(app1Time:String){
        this.app1Time = app1Time;
      }
      def getapp1Time: String = {
        return app1Time
      }
      def Setapp2Time(app2Time:String){
        this.app2Time = app2Time;
      }
      def getapp2Time: String = {
        return app2Time
      }
      def replace(s:String):String ={
        val s1 = s.replace("\""," ")
        val s2 = s1.replace(":"," ")
        val s3 = s2.replace("["," ")
        val s4 = s3.replace("]"," ")
        val s5 = s4.replace("{"," ")
        val s6 = s5.replace("}"," ")
        val s7 = s6.replace(","," ")
        return s7
      }


      def uni(string: String): Unit = {
        val str = replace(string)
        val result = str.split("\\s+")
            SetDay(result(4))
            SetuserID(result(2))

            SetBeginningtime(result(9))

            SetEndingtime(result(11))

            SetPackage_1(result(14))


            SetPackage_1_time(result(16))
            SetPackage_2(result(18))


            SetPackage_2_time(result(20))

            SetallTime(((result(11).toLong-result(9).toLong)*24).toString)
            SetavgTime(((result(11).toLong-result(9).toLong)/24).toString)
            Setapp1Time(((result(16).toLong)/24).toString)
            Setapp2Time(((result(20).toLong)/24).toString)

      }

    }

