package com.consommation

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util

import com.model.Tmax
import org.apache.kafka.common.serialization.{Deserializer, Serializer}



class TmaxDeserializer extends Deserializer[Tmax]{

  override def configure(configs: util.Map[String, _], isKey: Boolean):
  Unit = {
    // nothing to do
  }
  override def deserialize(topic:String,bytes: Array[Byte]) = {
    val byteIn = new ByteArrayInputStream(bytes)
    val objIn = new ObjectInputStream(byteIn)
    val obj = objIn.readObject().asInstanceOf[Tmax]
    byteIn.close()
    objIn.close()
    obj
  }

  override def close():Unit = {
    // nothing to do
  }

}
