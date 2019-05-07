package com.model
import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.util
import org.apache.kafka.common.serialization.Deserializer

class ValeursFoncieresDeserializer extends Deserializer[ValeursFoncieres] {

  override def configure(configs: util.Map[String, _], isKey: Boolean):
  Unit = {
    // nothing to do
  }

  override def deserialize(topic: String, bytes: Array[Byte]) = {
    val byteIn = new ByteArrayInputStream(bytes)
    val objIn = new ObjectInputStream(byteIn)
    val obj = objIn.readObject().asInstanceOf[ValeursFoncieres]
    byteIn.close()
    objIn.close()
    obj
  }

  override def close(): Unit = {
    // nothing to do
  }
}
