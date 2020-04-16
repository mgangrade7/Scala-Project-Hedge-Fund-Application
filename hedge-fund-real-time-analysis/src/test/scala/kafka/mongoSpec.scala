package kafka

import mongo.Helpers._
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.{FlatSpec, Matchers}

class mongoSpec extends FlatSpec with Matchers {

  val dbname = "test"
  val collectionName = "test"

  val mongoClient: MongoClient = MongoClient()

  val database: MongoDatabase = mongoClient.getDatabase(dbname)

  val collection: MongoCollection[Document] = database.getCollection(collectionName)

  val doc: Document = Document(
    "Name" -> "John",
    "Age" -> 29,
    "City" -> "Boston"
  )
  //Push data into MongoDB

  collection.insertOne(doc).results()

  // check count for single insert
  collection.count().results().head shouldBe 1


  val documents: IndexedSeq[Document] = (1 to 100) map { i: Int => Document("i" -> i) }
  val insertObservable = collection.insertMany(documents)

  val insertAndCount = for {
    insertResult <- insertObservable
    countResult <- collection.countDocuments()
  } yield countResult

  // check count for bulk insert
  insertAndCount.headResult() shouldBe 101

  // check collection drop result
  collection.drop().headResult().toString() shouldBe "The operation completed successfully"

  mongoClient.close()

}
