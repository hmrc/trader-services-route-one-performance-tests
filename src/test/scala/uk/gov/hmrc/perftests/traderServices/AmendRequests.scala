/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.traderServices

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._

object AmendRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def postJourneyAmend: HttpRequestBuilder = {
    http("Post existing journey response")
      .post(traderLanding)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "Existing")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + caseRefUrl))
  }

  def getCaseRefPage: HttpRequestBuilder = {
    http("Get case reference number page")
      .get(traderBase + caseRefUrl)
      .check(status.is(303))
  }

  def loadCaseRefPage: HttpRequestBuilder = {
    http("Load case reference number page")
      .get(traderBase + caseRefUrl)
      .check(status.is(200))
//      .check(regex("What's the case reference number?"))
  }

  def postCaseref: HttpRequestBuilder = {
    http("Post case ref number")
      .post(traderBase + caseRefUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("caseReferenceNumber", "PC12010081330XGBNZJO04")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + whichAmendUrl))
  }

  def getWhichAmend: HttpRequestBuilder = {
    http("Get which amendment page")
      .get(traderBase + whichAmendUrl)
      .check(status.is(200))
  }

  def postWriteOnly: HttpRequestBuilder = {
    http("Post write only option")
      .post(traderBase + whichAmendUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("typeOfAmendment", "WriteResponse")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + writeResponseUrl))
  }

  def getWriteResponsePage: HttpRequestBuilder = {
    http("Get write response page")
      .get(traderBase + writeResponseUrl)
      .check(status.is(200))
  }

  def postFreeTextResponse: HttpRequestBuilder = {
    http("Post query response in free text field")
      .post(traderBase + writeResponseUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("responseText", "Sample Text")
      .check(status.is(303))
//      .check(header("Location").is(traderUrl))
  }
}