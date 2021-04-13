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
import io.gatling.core.action.builder.PauseBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._

import scala.concurrent.duration.DurationInt

object JourneyNewRequests extends ServicesConfiguration with SaveToGatlingSessions with DynamicTestData {

  def pause = new PauseBuilder(0 seconds, None)

  def uploadWait = new PauseBuilder(1 seconds, None)

  def getPreLandingPage: HttpRequestBuilder = {
    http("Get temporary start page")
      .get(readBaseUrl)
      .check(status.is(303))
      .check(header("Location").is(traderUrl))
  }

  def loadPreLandingPage: HttpRequestBuilder = {
    http("Load temporary start page")
      .get(readBaseUrl + traderStartUrl)
      .check(status.is(200))
      .check(regex("Send documents for a customs check for declarations made in CHIEF"))
  }

  def getLandingPage: HttpRequestBuilder = {
    http("Get new or existing page")
      .get(readBaseUrl + traderLandingUrl)
      .check(status.is(303))
      .check(header("Location").is(traderUrl))
  }

  def loadLandingPage: HttpRequestBuilder = {
    http("Load new or existing page")
      .get(readBaseUrl + traderLandingUrl)
      .check(status.is(200))
      .check(regex("What do you want to do?"))
  }

  def postJourney(journeyChoice:String, nextPage:String): HttpRequestBuilder = {
    http("Post new journey response")
      .post(readBaseUrl + traderLandingUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", s"$journeyChoice")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$nextPage"))
  }

  //Entry Details
  def getEntryDetailsPage: HttpRequestBuilder = {
    http("Get the entry details page")
      .get(baseNewUrl + entryDetailsUrl)
      .check(status.is(200))
  }

  def postEntryDetails(journey:String, entryNo:String): HttpRequestBuilder = {
    http(s"$journey Post entry details")
      .post(baseNewUrl + entryDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", s"$randomEPU")
      .formParam("entryNumber", s"$entryNo")
      .formParam("entryDate.day", s"$d")
      .formParam("entryDate.month", s"$m")
      .formParam("entryDate.year", s"$y")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + requestTypeUrl))
  }


  //Request Type
  def getRequestTypePage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get request type page")
      .get(baseNewUrl + s"$journey" + requestTypeUrl)
      .check(status.is(200))
  }

  def postRequestType(journey:String, requestType:String): HttpRequestBuilder = {
    http(s"$journey Post request type")
      .post(baseNewUrl + s"$journey" + requestTypeUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", s"$requestType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + routeTypeUrl))
  }

  //Route Type
  def getRoutePage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get route type page")
      .get(baseNewUrl + s"$journey" + routeTypeUrl)
      .check(status.is(200))
  }

  //todo include hold.......???
  def postRouteType(journey:String): HttpRequestBuilder = {
    http(s"$journey Post route type")
      .post(baseNewUrl + s"$journey" + routeTypeUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", s"$randomRouteType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + hasPriorityGoodsUrl))
  }


  //Priority Goods
  def getHasPriorityPage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get has-priority-goods page")
      .get(baseNewUrl + s"$journey" + hasPriorityGoodsUrl)
      .check(status.is(200))
  }

  //todo
  def postPriorityYN(journey:String, yesNo:String, nextPage:String): HttpRequestBuilder = {
    http(s"$journey Post to priority yes/no page")
      .post(baseNewUrl + s"$journey" + hasPriorityGoodsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", s"$yesNo")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + s"$nextPage"))
  }

  def getPriorityGoodsPage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get which-priority-goods page")
      .get(baseNewUrl + s"$journey" + whichPriorityGoodsUrl)
      .check(status.is(200))
  }

  def postPriorityGoods(journey:String, nextPage:String): HttpRequestBuilder = {
    http(s"$journey Post priority goods")
      .post(baseNewUrl + s"$journey" + whichPriorityGoodsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", s"$randomPriorityGoods")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + s"$nextPage"))
  }

  //ALVS (Import Only)
  def getALVSPage: HttpRequestBuilder = {
    http("Get ALVS page")
      .get(baseNewUrl + imports + hasALVSUrl)
      .check(status.is(200))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS")
      .post(baseNewUrl + imports + hasALVSUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + imports + transportTypeUrl))
  }

  //Transport
  def getTransportTypePage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get transport type page")
      .get(baseNewUrl + s"$journey" + transportTypeUrl)
      .check(status.is(200))
  }

  //todo opt v mand
  def postTransportType(journey:String, mandOrOpt:String): HttpRequestBuilder = {
    http(s"$journey Post transport type")
      .post(baseNewUrl + s"$journey" + transportTypeUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", s"$randomTransport")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + s"$journey" + s"$mandOrOpt"))
  }

  //Optional
  def getImportTransportMandatoryPage: HttpRequestBuilder = {
    http("Get mandatory transport details page - import")
      .get(baseNewUrl + imports + transportMandatoryUrl)
      .check(status.is(200))
  }

  //todo
  def getTransportOptionalPage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get optional transport details page")
      .get(baseNewUrl + s"$journey" + transportOptionalUrl)
      .check(status.is(200))
  }

  def getTransportMandatoryPage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get mandatory transport details page")
      .get(baseNewUrl + s"$journey" + transportMandatoryUrl)
      .check(status.is(200))
  }


  def postImportTransportDetails(mandOrOpt:String): HttpRequestBuilder = {
    http("Import: Post transport details - import")
      .post(baseNewUrl + imports + s"$mandOrOpt")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "S.S Test Ship")
      .formParam("dateOfArrival.day", s"$d")
      .formParam("dateOfArrival.month", s"$m")
      .formParam("dateOfArrival.year", s"$y")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + imports + contactDetailsUrl))
  }

  //Mandatory
  def postExportTransportDetails(mandOrOpt:String): HttpRequestBuilder = {
    http("Export: Post transport details - " + s"$mandOrOpt")
      .post(baseNewUrl + exports + s"$mandOrOpt")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfDeparture.day", s"$d")
      .formParam("dateOfDeparture.month", s"$m")
      .formParam("dateOfDeparture.year", s"$y")
      .formParam("timeOfDeparture.hour", s"$hr")
      .formParam("timeOfDeparture.minutes", s"$min")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exports + contactDetailsUrl))
  }


  //Contact details
  def getContactDetailsPage (journey:String): HttpRequestBuilder = {
    http(s"$journey Get contact details")
      .get(baseNewUrl + s"$journey" + contactDetailsUrl)
      .check(status.is(200))
  }

  def postContactDetails(journey:String): HttpRequestBuilder = {
    http(s"$journey Post contact details")
      .post(baseNewUrl + s"$journey" + contactDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", s"$randomEmail")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + fileUploadUrl))
  }

  //CYA
  def getCYAPage(journey:String): HttpRequestBuilder = {
    http(s"$journey Get CYA page")
      .get(baseNewUrl + s"$journey" + cyaReviewUrl)
      .check(status.is(200))
  }

  def postCYA: HttpRequestBuilder = {
    http("Post to create case")
      .post(baseNewUrl + "/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + confirmationUrl))
  }

  //Confirmation page
  def getConfirmationPage: HttpRequestBuilder = {
    http("Get confirmation page")
      .get(baseNewUrl + confirmationUrl)
      .check(status.is(200))
  }
}


