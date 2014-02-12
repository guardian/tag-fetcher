package com.gu.tagfetcher

import dispatch._
import scala.xml._

object AsXml extends (Res => scala.xml.Elem) {

  override def apply(res: Res) =
    XML.withSAXParser(factory.newSAXParser).loadString(res.getResponseBody)
 
  private lazy val factory = {
    val spf = javax.xml.parsers.SAXParserFactory.newInstance()
    spf.setNamespaceAware(false)
    spf
  }

}