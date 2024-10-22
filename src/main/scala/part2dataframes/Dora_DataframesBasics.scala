package part2dataframes

import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object Dora_DataframesBasics extends App {
  //create a spark session
  val spark=SparkSession.builder()
     .appName("DataFrames Basics")
     .config("spark.master","local")
     .getOrCreate()

  val firstDF = spark.read
    .format("json")
    .option("inferSchema","true")
    .load("src/main/resources/data/cars.json")

  firstDF.show()
  firstDF.printSchema()
  firstDF.take(10).foreach(println)

  // spark types
  val longType=LongType
  //schema
  val carsSchema=StructType ( //should define schema manully, not inferSchema
    Array(
      StructField("Name",StringType),
      StructField("Miles_per_Gallon", DoubleType),
      StructField("Cylinders", LongType),
      StructField("Displacement", DoubleType),
      StructField("Horsepower", LongType),
      StructField("Weight_in_lbs", LongType),
      StructField("Acceleration", DoubleType),
      StructField("Year", StringType),
      StructField("Origin", StringType)
    ))

  //showing a DF
  val carsDFSchema=firstDF.schema
  //println(carsDFSchema)
  val carsDFWithSchema=spark.read
    .format("json")
    .schema(carsDFSchema)
    .load("src/main/resources/data/cars.json")

  //create rows by hand
  val myRow=Row("chevrolet chevelle malibu",18,8,307,130,3504,12.0,"1970-01-01","USA")

  //create DF from tuples
  val cars= Seq(
    ("chevrolet chevelle malibu", 18, 8, 307, 130, 3504, 12.0, "1970-01-01", "USA"),
    ("buick skylark 320", 15, 8, 350, 165, 3693, 11.5, "1970-01-01", "USA"),
    ("plymouth satellite", 18, 8, 318, 150, 3436, 11.0, "1970-01-01", "USA"),
    ("amc rebel sst", 16, 8, 304, 150, 3433, 12.0, "1970-01-01", "USA"),
    ("ford torino", 17, 8, 302, 140, 3449, 10.5, "1970-01-01", "USA"),
    ("ford galaxie 500", 15, 8, 429, 198, 4341, 10.0, "1970-01-01", "USA"),
    ("chevrolet impala", 14, 8, 454, 220, 4354, 9.0, "1970-01-01", "USA"),
    ("plymouth fury iii", 14, 8, 440, 215, 4312, 8.5, "1970-01-01", "USA"),
    ("pontiac catalina", 14, 8, 455, 225, 4425, 10.0, "1970-01-01", "USA"),
    ("amc ambassador dpl", 15, 8, 390, 190, 3850, 8.5, "1970-01-01", "USA")
  )
  val manualCarsDF=spark.createDataFrame(cars)     //schema auto-inferred
  //note: DFs have schemas, rows do not
  //create DFs with implicits
  import spark.implicits._
  val manualCarsDFWithImplicits=cars.toDF("Name","MPG","Cylinders","Displacement","HP","Weight","Accelleration","Year","Country")
  manualCarsDF.printSchema()
  manualCarsDFWithImplicits.printSchema()

  /**
    *
    * Excercise
    * 1) Create a manual DF describing smartphones
    * -make
    * -model
    * -screen simension
    * - camera megapixels
    *
    * 2) read another files from the data folder: movies.json
    * -print its schema
    * -count the 
    */

    //exercise 1)
  val smartphones=Seq(
    ("Samsung", "Galaxy S10", "Android",150),
    ("Apple","iPhone X","IOS",150),
    ("Nokia","3310","The Best",0)
  )
  val smartphonesDF=smartphones.toDF("Make","Model","Platforms","Pixels")
  smartphonesDF.show()
  smartphonesDF.printSchema()
  smartphonesDF.count()

  //exercise 2)
  val moviesDF=spark.read
    .format("json")
    .option("inferSchema","true")
    .load("src/main/resources/data/movies.json")
  moviesDF.printSchema()
  println(s"the movies DF has ${moviesDF.count()} rows")
}
