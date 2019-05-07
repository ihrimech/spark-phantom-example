package com.production

import java.util.UUID

import com.model.ValeursFoncieres

object Unmarshaller {
  /**
    * this method unmarshalls the given string to a ValeursFonciere object by lifting each value to the case class
    * @param immo a list of the marshalled object
    * @return a ValeursFoncieres case class
    */
  def unmarshallValeursFoncieres(immo: List[String]): ValeursFoncieres = {
    def liftStringWithEmpty(index: Int, immo: List[String]): String = immo.lift(index).getOrElse("")

    ValeursFoncieres(UUID.randomUUID(),
      liftStringWithEmpty(1, immo),
      liftStringWithEmpty(2, immo),
      liftStringWithEmpty(3, immo),
      liftStringWithEmpty(4, immo),
      liftStringWithEmpty(5, immo),
      liftStringWithEmpty(6, immo),
      liftStringWithEmpty(7, immo),
      liftStringWithEmpty(8, immo),
      liftStringWithEmpty(9, immo),
      liftStringWithEmpty(10,immo),
      liftStringWithEmpty(11,immo),
      liftStringWithEmpty(12,immo),
      liftStringWithEmpty(13,immo),
      liftStringWithEmpty(14,immo),
      liftStringWithEmpty(15,immo),
      liftStringWithEmpty(16,immo),
      liftStringWithEmpty(17,immo),
      liftStringWithEmpty(18,immo),
      liftStringWithEmpty(19,immo),
      liftStringWithEmpty(20,immo),
      liftStringWithEmpty(21,immo),
      liftStringWithEmpty(22,immo),
      liftStringWithEmpty(23,immo),
      liftStringWithEmpty(24,immo),
      liftStringWithEmpty(25,immo),
      liftStringWithEmpty(26,immo),
      liftStringWithEmpty(27,immo),
      liftStringWithEmpty(28,immo),
      liftStringWithEmpty(29,immo),
      liftStringWithEmpty(30,immo),
      liftStringWithEmpty(31,immo),
      liftStringWithEmpty(32,immo),
      liftStringWithEmpty(33,immo),
      liftStringWithEmpty(34,immo),
      liftStringWithEmpty(35,immo),
      liftStringWithEmpty(36,immo),
      liftStringWithEmpty(37,immo),
      liftStringWithEmpty(38,immo),
      liftStringWithEmpty(39,immo))
  }
}
