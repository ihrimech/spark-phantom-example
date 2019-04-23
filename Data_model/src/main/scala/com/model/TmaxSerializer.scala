package com.production

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import com.model.Tmax
import org.apache.kafka.common.serialization.Serializer


class TmaxSerializer extends Serializer[Tmax] {

  override def close(): Unit = {
    //nothing to do
  }

  override def serialize(topic: String, data: Tmax): Array[Byte] = {
    try {
      val byteOut = new ByteArrayOutputStream()
      val objOut = new ObjectOutputStream(byteOut)
      objOut.writeObject(data)
      objOut.close()
      byteOut.close()
      byteOut.toByteArray
    } catch {
      case ex: Exception => throw new Exception(ex.getMessage)
    }
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
    //nothing to do
  }
}
