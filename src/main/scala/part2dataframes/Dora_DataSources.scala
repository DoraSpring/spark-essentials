package part2dataframes

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{DateType, DoubleType, LongType, StringType, StructField, StructType}

object Dora_DataSources extends App {
  val spark=SparkSession.builder()
    .appName("Data sources and Formats")
    .config("spark.master","local")
    .getOrCreate()

  val carsSchema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Gallon", DoubleType),
    StructField("Cylinders", LongType),
    StructField("Displacement", DoubleType),
    StructField("Horsepower", LongType),
    StructField("Weight_in_lbs", LongType),
    StructField("Acceleration", DoubleType),
    StructField("Year", DateType),
    StructField("Origin", StringType)
  ))

  val carsDf=spark.read
    .format("json")    //format
    .option("inferSchema","true") //schema or inferschema true, & 0 or more options
    .option("mode","failFast") //dropMalformed,permissive(default)
    .option("path","src/main.resources/data/cars.json")
    .load()

  carsDf.show()

  val carsDFWithOptionMap=spark.read
    .format("json")
    .options(Map(
      "mode" -> "failFast",
      "path" -> "src/main.resources/data/cars.json",
      "inferSchema"->"true"
    ))
    .load()

  /*
  -format
  save mode =overwrite, append,ignore, errorIfExists
  path
  zero or more options
   */
  carsDf.write
    .format("json")
    .mode(SaveMode.Overwrite)
    .save("src/main/resources/data/cars)dupe.json")
}
