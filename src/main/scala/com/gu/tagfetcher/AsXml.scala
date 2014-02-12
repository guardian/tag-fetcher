package com.gu.tagfetcher

import dispatch._
import scala.xml._

object AsXml extends (Response => scala.xml.Elem) {

  override def apply(res: Response) =
    XML.withSAXParser(factory.newSAXParser).loadString(res.body)

  private lazy val factory = {
    val spf = javax.xml.parsers.SAXParserFactory.newInstance()
    spf.setNamespaceAware(false)
    spf
  }

}