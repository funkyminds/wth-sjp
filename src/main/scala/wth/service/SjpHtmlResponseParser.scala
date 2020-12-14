package wth.service

import org.jsoup.nodes.Document
import wth.service.ResponseParser._
import zio._

object SjpHtmlResponseParser {

  /**
   * sjp.pwn.pl doesn't provide any rest api.
   *      ¯\_(ツ)_/¯
   * Parse a pure html response.
   */
  val jsoupSjpHtmlParser: TaskLayer[ResponseParser[Document]] = ZLayer.succeed { (phrase: String, raw: Document) =>
    Task.effect {
      import scala.jdk.CollectionConverters._
      val definitions = raw
        .body()
        .getElementsByClass("ribbon-element type-187126")
        .asScala

      val definitionsHtmlPart = for {
        definition <- definitions
        result: String = definition.toString
        if isKeyword(result, phrase)
        if result.contains("«")
      } yield result.split("\n")

      val defsFlatten: Seq[String] = definitionsHtmlPart.toSeq.flatten

      if (defsFlatten.isEmpty) {
        println(s"Error: not found definitions for: $phrase")
      }

      defsFlatten
        .filter(_.contains("«"))
        .map(d => d.split("«")(1).split("»")(0)) // definitions are defined between '«' and '»' characters
        .map(translation => phrase -> translation)
        .toSet
    }
  }

  def isKeyword(result: String, phrase: String): Boolean =
    Seq(
      s"""title="${phrase.toLowerCase}"""",
      s"""title="$phrase"""",
      s"""title="$phrase&nbsp; I"""",
      s"""title="${phrase.toLowerCase}&nbsp; I""""
    ).foldLeft(false)((res, attr) => res || result.contains(attr))
}
