import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.Map

object PageRank {
  def main(args: Array[String]): Unit = {
    //System.setProperty("hadoop.home.dir", "/usr/local/Cellar/hadoop/3.1.1")
    //val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("Part2_PageRank"))
    val sc = new SparkContext(new SparkConf().setAppName("Part2_PageRank"))

    //    val inputFile = sc.textFile("./AirportReport.csv")
    //    val iteration = 15
    //    val directory = "./"
    val inputFile = sc.textFile(args(0))
    val iteration = args(1)
    val directory = args(2)
    val initialPR = 10.0


    val inputArray = inputFile.map(x => x.split(","))
    var airportIDs = inputArray.map(x => (x(1), x(4)))
    val firstRow = airportIDs.first
    val iDs = airportIDs.filter(x => x != firstRow)
    val totalairports = (iDs.map { case (x, y) => x }.distinct() ++ iDs.map { case (x, y) => y }.distinct()).distinct().collect()
    val outLinks = iDs.groupByKey().map(x => (x._1, x._2.size)).collect().map(x => (x._1, x._2)).toMap
    val rank = collection.mutable.Map() ++ totalairports.map(x => (x, initialPR)).toMap


    for (i <- 1 to iteration.toInt) {
      val out = collection.mutable.Map() ++ totalairports.map(x => (x, 0.0)).toMap
      rank.keys.foreach((id) => rank(id) = rank(id) / outLinks(id))
      for ((key, value) <- iDs.collect()) {
        out.put(value, out(value) + rank(key))
      }
      val out1 = collection.mutable.Map() ++ out.map(x => (x._1, ((0.15 / totalairports.size) + 0.85 * x._2)))
      out1.keys.foreach((id) => rank(id) = out1(id))
    }

    val result = rank.toSeq.sortBy(-_._2)
    sc.parallelize(result).saveAsTextFile(directory + "PageRank_Output")
  }
}
