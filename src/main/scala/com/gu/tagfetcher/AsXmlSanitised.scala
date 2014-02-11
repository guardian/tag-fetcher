package com.gu.tagfetcher

import dispatch._
import scala.xml._

object AsXmlSanitised extends (Res => scala.xml.Elem) {

  override def apply(res: Res) = {
    val sanitisedResponseBody = sanitiseXml(res.getResponseBody)
    XML.withSAXParser(factory.newSAXParser).loadString(sanitisedResponseBody)
  }

  private def sanitiseXml(xml: String) = {
    xml.replaceAll("[^\\x20-\\x7e]", "")
  }

  private lazy val factory = {
    val spf = javax.xml.parsers.SAXParserFactory.newInstance()
    spf.setNamespaceAware(false)
    spf
  }

}