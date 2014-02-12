package com.gu.tagfetcher

import scala.concurrent.Future

case class Response(statusCode: Int, statusLine: String, body: String)

trait Http {
  def get(uri: String, headers: Map[String, String]): Future[Response]
}