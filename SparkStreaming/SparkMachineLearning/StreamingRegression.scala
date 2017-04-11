package sparkStreamingUD

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.SQLContext
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD

import Utilities._

object StreamingRegression {
  
  def main(args: Array[String]) {

    val streamContext = new StreamingContext("local[*]", "StreamingRegression", Seconds(1))
    setupLogging()
    val trainingLines = streamContext.socketTextStream("127.0.0.1", 9999, StorageLevel.MEMORY_AND_DISK_SER)
    val testingLines = streamContext.socketTextStream("127.0.0.1", 7777, StorageLevel.MEMORY_AND_DISK_SER)
    val trainingData = trainingLines.map(LabeledPoint.parse).cache()
    val testData = testingLines.map(LabeledPoint.parse)
    trainingData.print()
    val numFeatures = 1
    val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures))
    model.algorithm.setIntercept(true) 
    model.trainOn(trainingData)
    model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
    streamContext.checkpoint("Users/apurvaanand/Desktop/")
    streamContext.start()
    streamContext.awaitTermination()
  }
  
}