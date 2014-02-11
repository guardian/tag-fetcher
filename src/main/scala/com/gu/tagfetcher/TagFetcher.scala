package com.gu.tagfetcher

import dispatch._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Tag(
    id: Long,
    `type`: String,
    internalName: String,
    externalName: String,
    slug: String,
    section: Section,
    parentIds: List[Long]
)

case class Section(
    id: Long,
    name: String,
    pathPrefix: Option[String],
    slug: String
)

class TagFetcher(tagStoreUri: String) {
  var Tags: List[Tag] = List();

  def getTags: Future[List[Tag]] = Future {
    def tagStore = url(tagStoreUri)
    val headers = Map("user-agent" -> "curl") // bypass newspaper integration security
    val request = tagStore <:< headers

    val response = Http(request).apply()

    val responseXml = AsXmlSanitised(response)
      parseTags(responseXml)
  }

  def parseTags(tagsResponse: xml.Elem): List[Tag] = {
    val tagsXml = tagsResponse \\ "tag"
    val tags = tagsXml map { tag =>
      Tag(
        id           = (tag \ "@id").text.toLong,
        `type`       = (tag \ "@type").text,
        internalName = (tag \ "@internalname").text,
        externalName = (tag \ "@externalname").text,
        slug         = (tag \ "@words-for-url").text,
        section      =
          Section(
            id         = (tag \ "@section-id").text.toLong,
            name       = (tag \ "@section").text,
            pathPrefix = parseCmsPath((tag \ "@section-cms-path-prefix").text),
            slug       = (tag \ "@section-words-for-url").text
          ),
        parentIds    = (tag \\ "parent").map(parent => (parent \ "@id").text.toLong).toList
      )
    }
    tags.toList
  }

  private def parseCmsPath(cmsPath: String): Option[String] = {
    val cmsPathPattern = """/Guardian/(.*)""".r
    cmsPath match {
      case cmsPathPattern(path) => Some(path)
      case _ => None
    }
  }
}

