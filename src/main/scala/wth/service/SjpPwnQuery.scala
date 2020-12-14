package wth.service

import org.jsoup._
import zio._

object SjpPwnQuery {
  val jsoupService: ULayer[QueryService.QueryService[nodes.Document]] =
    ZLayer.succeed { (toQuery: String) =>
      Task.effect(Jsoup.connect(s"https://sjp.pwn.pl/szukaj/$toQuery").get())
    }
}
