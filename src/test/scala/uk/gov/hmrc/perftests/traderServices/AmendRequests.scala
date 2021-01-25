/*
 * Copyright 2021 HM Revenue & Customs
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
      .post(baseUrlRead + "/new-or-existing")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "Existing")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + caseRefUrl))
  }

  def getCaseRefPage: HttpRequestBuilder = {
    http("Get case reference number page")
      .get(baseUrlRead + caseRefUrl)
      .check(status.is(303))
  }

  def loadCaseRefPage: HttpRequestBuilder = {
    http("Load case reference number page")
      .get(baseUrlRead + caseRefUrl)
      .check(status.is(200))
  }

  def postCaseref: HttpRequestBuilder = {
    http("Post case ref number")
      .post(baseUrlRead + caseRefUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("caseReferenceNumber", "PC12010081330XGBNZJO04")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + whichAmendUrl))
  }

  def getWhichAmend: HttpRequestBuilder = {
    http("Get which amendment page")
      .get(baseUrlRead +  whichAmendUrl)
      .check(status.is(200))
  }

  //Journey choices
  def postWriteOnly: HttpRequestBuilder = {
    http("Post write only option")
      .post(baseUrlRead +  whichAmendUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("typeOfAmendment", "WriteResponse")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + writeResponseUrl))
  }

  def postUploadOnly: HttpRequestBuilder = {
    http("Post upload only option")
      .post(baseUrlRead +  whichAmendUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("typeOfAmendment", "UploadDocuments")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "add" + fileUploadUrl))
  }

  def postWriteAndUpload: HttpRequestBuilder = {
    http("Post write response and upload option")
      .post(baseUrlRead +  whichAmendUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("typeOfAmendment", "WriteResponseAndUploadDocuments")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + writeResponseUrl))
  }

  def getWriteResponsePage: HttpRequestBuilder = {
    http("Get write response page")
      .get(baseUrlRead + writeResponseUrl)
      .check(status.is(200))
  }

  def postFreeTextResponse: HttpRequestBuilder = {
    http("Post query response in free text field")
      .post(baseUrlRead + writeResponseUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("responseText", "Sample Text")
      .check(status.is(303))
  }

  //CYA
  def getAmendCYA: HttpRequestBuilder = {
    http("Get amend confirmation page")
      .get(baseUrlRead + "/add" + CYA)
      .check(status.is(200))
  }

  def postAmendCYA: HttpRequestBuilder = {
    http("Post Amend CYA page")
      .post(baseUrlRead + "/add/amend-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/add" + confirmationUrl))
  }


  //Confirmation
  def getConfirmationPageAmend: HttpRequestBuilder = {
    http("Get amend confirmation page")
      .get(baseUrlRead + "/add" + confirmationUrl)
      .check(status.is(200))
  }
}