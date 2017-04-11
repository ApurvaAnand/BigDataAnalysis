
package sparkStreamingUD

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.Level
import Utilities._
object PrintTweets {
  def main(args: Array[String]) {
    setupTwitter()
    val streamContext = new StreamingContext("local[*]", "PrintTweets", Seconds(1))
    setupLogging()
    val fetchTweets = TwitterUtils.createStream(streamContext, None)
    val statuses = fetchTweets.map(status => status.getText())
    statuses.print()
    streamContext.start()
    streamContext.awaitTermination()
  }  
}