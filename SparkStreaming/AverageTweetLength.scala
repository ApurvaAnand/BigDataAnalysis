package sparkStreamingUD

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import Utilities._
import java.util.concurrent._
import java.util.concurrent.atomic._

object AverageTweetLength {
  def main(args: Array[String]) {
    setupTwitter()
    val streamContext = new StreamingContext("local[*]", "AverageTweetLength", Seconds(1))
    setupLogging()
    val fetchTweets = TwitterUtils.createStream(streamContext, None)
    val statusesUpdates = fetchTweets.map(status => status.getText())
    val lengths = statusesUpdates.map(status => status.length())
    var totalTweets = new AtomicLong(0)
    var totalChars = new AtomicLong(0)
    lengths.foreachRDD((rdd, time) => {
      
      var countTweets = rdd.count()
      if (countTweets > 0) {
        totalTweets.getAndAdd(countTweets)
        totalChars.getAndAdd(rdd.reduce((x,y) => x + y))
        println("Number of tweets " + totalTweets.get()+", " + " Average length of tweets: " + totalChars.get() / totalTweets.get())
      }
    })
    streamContext.checkpoint("/Users/apurvaanand/Desktop/Trial")
    streamContext.start()
    streamContext.awaitTermination()
  }  
}
