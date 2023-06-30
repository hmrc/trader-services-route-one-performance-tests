/*
 * Copyright 2023 HM Revenue & Customs
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
import uk.gov.hmrc.perftests.traderServices.JourneyNewRequests.longString
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._

object AmendRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def loadCaseRefPage: HttpRequestBuilder =
    http("Load case reference number page")
      .get(baseAmendUrl + caseRefUrl)
      .check(status.is(200))

  def postCaseRef: HttpRequestBuilder =
    http("Post case ref number")
      .post(baseAmendUrl + caseRefUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("caseReferenceNumber", "PC12010081330XGBNZJO04")
      .check(status.is(303))
      .check(header("Location").is(traderAmendUrl + whichAmendUrl))

  def getTypeOfAmendmentPage: HttpRequestBuilder =
    http("Get the amendment type options page")
      .get(baseAmendUrl + whichAmendUrl)
      .check(status.is(200))

  def postResponse(responseType: String, nextPage: String): HttpRequestBuilder =
    http("Amend: Post write only option")
      .post(baseAmendUrl + whichAmendUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("typeOfAmendment", s"$responseType")
      .check(status.is(303))
      .check(header("Location").is(traderAmendUrl + s"$nextPage"))

  def getWriteResponsePage: HttpRequestBuilder =
    http("Amend: Get write response page")
      .get(baseAmendUrl + writeResponseUrl)
      .check(status.is(200))

  def postFreeTextResponse: HttpRequestBuilder =
    http("Amend: Post query response in free text field")
      .post(baseAmendUrl + writeResponseUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("responseText", s"$longString")
      .check(status.is(303))

  // CYA
  def getAmendCYAPage: HttpRequestBuilder =
    http("Amend: Get amend confirmation page")
      .get(baseAmendUrl + cyaReviewUrl)
      .check(status.is(200))

  def postAmendCYA: HttpRequestBuilder =
    http("Amend: Post CYA page - submit case to update-case")
      .post(baseAmendUrl + "/amend-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderAmendUrl + confirmationUrl))

  // Confirmation
  def getConfirmationPageAmend: HttpRequestBuilder =
    http("Amend: Get confirmation page")
      .get(baseAmendUrl + confirmationUrl)
      .check(status.is(200))
}
