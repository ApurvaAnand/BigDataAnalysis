package sparkStreamingUD

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._
import Utilities._

object PopularHashtags {
 
  def main(args: Array[String]) {
    setupTwitter()
    val streamContext = new StreamingContext("local[*]", "PopularHashtags", Seconds(1))
    setupLogging()
    val fetchTweets = TwitterUtils.createStream(streamContext, None)
    val statuses = fetchTweets.map(status => status.getText())
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))
    val hashtags = tweetwords.filter(word => word.startsWith("#"))
    val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))
    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow( (x,y) => x + y, (x,y) => x - y, Seconds(300), Seconds(1))
    val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))
    sortedResults.print
    
    
    streamContext.checkpoint("Users/apurvaanand/Desktop/Trial")
    streamContext.start()
    streamContext.awaitTermination()
  }  
}
