package com.gu.tagfetcher

import org.specs2.mutable.Specification
import com.gu.tagfetcher._

class TagFetcherSpec extends Specification {

  "TagFetcher" should {
    "parse XML" in {
      val fetcher = new TagFetcher("")
      val elem = <tags>
        <tag id="9781" internalname="Africa (Travel)" externalname="Africa" words-for-url="africa" section="Travel" section-id="183" section-cms-path-prefix="/Guardian/travel" section-words-for-url="travel" type="Keyword"></tag>
        <tag id="8974" internalname="Tunisia (Travel)" externalname="Tunisia" words-for-url="tunisia" section="Travel" section-id="183" section-cms-path-prefix="/Guardian/travel" section-words-for-url="travel" type="Keyword"><parent id="9781"></parent></tag>
        <tag id="52885" internalname="Dress code" externalname="Dress code" words-for-url="dress-code" section="Fashion" section-id="3547" section-cms-path-prefix="/Guardian/fashion" section-words-for-url="fashion" type="Series"></tag>
        <tag id="52886" internalname="PRO: Government communications (Public leaders network)" externalname="Government communications" words-for-url="government-communications" section="Public Leaders Network" section-id="2966" section-cms-path-prefix="/Guardian/public-leaders-network" section-words-for-url="public-leaders-network" type="Keyword"></tag>
      </tags>

      val parsed = fetcher.parseTags(elem)

      parsed mustEqual List(
        Tag(9781, "Keyword", "Africa (Travel)", "Africa", "africa", Section(183, "Travel", Some("travel"), "travel"), List()),
        Tag(8974, "Keyword", "Tunisia (Travel)", "Tunisia", "tunisia", Section(183, "Travel", Some("travel"), "travel"), List(9781)),
        Tag(52885, "Series", "Dress code", "Dress code", "dress-code", Section(3547, "Fashion", Some("fashion"), "fashion"), List() ),
        Tag(52886, "Keyword", "PRO: Government communications (Public leaders network)", "Government communications", "government-communications",Section(2966, "Public Leaders Network", Some("public-leaders-network"), "public-leaders-network"), List() )
      )
    }
  }
}
