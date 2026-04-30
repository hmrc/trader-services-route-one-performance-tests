/*
 * Copyright 2026 HM Revenue & Customs
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
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._
import io.gatling.commons.validation._
import io.gatling.core.session.el._

import scala.concurrent.duration.DurationInt

object JourneyNewRequests extends ServicesConfiguration with SaveToGatlingSessions with TestData {

  def pauseTest: ActionBuilder = pause(4 seconds).actionBuilders.last

  def uploadWait: ActionBuilder = pause(8 seconds).actionBuilders.last

  def getLandingPage: HttpRequestBuilder =
    http("Get new or existing page")
      .get(readBaseUrl)
      .check(status.is(303))
      .check(header("Location".el[String]).is((traderUrl + traderLandingUrl).el[String]))

  def loadLandingPage: HttpRequestBuilder =
    http("Load new or existing page")
      .get(readBaseUrl + traderLandingUrl)
      .check(status.is(200))
      .check(regex("What do you want to do?"))

  def postJourney(journeyChoice: String, nextPage: String): HttpRequestBuilder =
    http("Post new journey response")
      .post(readBaseUrl + traderLandingUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("newOrExistingCase",  _ => s"$journeyChoice")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(s"$nextPage"))

  // Entry Details
  def getEntryDetailsPage: HttpRequestBuilder =
    http("Get the entry details page")
      .get(baseNewUrl + entryDetailsUrl)
      .check(status.is(200))

  def postEntryDetails(journey: String, entryNo: String): HttpRequestBuilder =
    http("Post entry details")
      .post(baseNewUrl + entryDetailsUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("epu",  _ => s"$randomEPU")
      .formParam("entryNumber",  _ => s"$entryNo")
      .formParam("entryDate.day",  _ => s"$d")
      .formParam("entryDate.month",  _ => s"$m")
      .formParam("entryDate.year",  _ => s"$y")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + requestTypeUrl))

  // Request Type
  def getRequestTypePage(journey: String): HttpRequestBuilder =
    http(s"$journey Get request type page")
      .get(baseNewUrl + s"$journey" + requestTypeUrl)
      .check(status.is(200))

  def postRequestType(journey: String, requestType: String): HttpRequestBuilder =
    http("Post request type")
      .post(baseNewUrl + s"$journey" + requestTypeUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("requestType",  _ => s"$requestType")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + routeTypeUrl))

  // Route Type
  def getRoutePage(journey: String): HttpRequestBuilder =
    http("Get route type page")
      .get(baseNewUrl + s"$journey" + routeTypeUrl)
      .check(status.is(200))

  def postRouteType(journey: String, route: String, nextPage: String): HttpRequestBuilder =
    http("Post route type")
      .post(baseNewUrl + s"$journey" + routeTypeUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("routeType",  _ => s"$route")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + s"$nextPage"))
  // Reason page - Cancellation, Withdrawal (export only) or Route 3
  def getReasonPage(journey: String): HttpRequestBuilder                                  =
    http("Get reason page")
      .get(baseNewUrl + s"$journey" + reasonUrl)
      .check(status.is(200))

  def postReason(journey: String): HttpRequestBuilder         =
    http("Post reason")
      .post(baseNewUrl + s"$journey" + reasonUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("reasonText",  _ => s"$longString")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + hasPriorityGoodsUrl))
  // Priority YesNo & Which Goods
  def getHasPriorityPage(journey: String): HttpRequestBuilder =
    http("Get has-priority-goods page")
      .get(baseNewUrl + s"$journey" + hasPriorityGoodsUrl)
      .check(status.is(200))

  def postPriorityYN(journey: String, yesNo: String, nextPage: String): HttpRequestBuilder =
    http("Post to priority yes/no page")
      .post(baseNewUrl + s"$journey" + hasPriorityGoodsUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("hasPriorityGoods",  _ => s"$yesNo")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + s"$nextPage"))

  def getPriorityGoodsPage(journey: String): HttpRequestBuilder =
    http("Get which-priority-goods page")
      .get(baseNewUrl + s"$journey" + whichPriorityGoodsUrl)
      .check(status.is(200))

  def postPriorityGoods(journey: String, nextPage: String): HttpRequestBuilder =
    http("Post priority goods option")
      .post(baseNewUrl + s"$journey" + whichPriorityGoodsUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("priorityGoods",  _ => s"$randomPriorityGoods")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + s"$nextPage"))

  // ALVS (Import Only)
  def getALVSPage: HttpRequestBuilder =
    http("Get ALVS page")
      .get(baseNewUrl + imports + hasALVSUrl)
      .check(status.is(200))

  def postALVS: HttpRequestBuilder =
    http("Post ALVS selection")
      .post(baseNewUrl + imports + hasALVSUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("hasALVS",  _ => "yes")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + imports + transportTypeUrl))

  // Transport Type & Details
  def getTransportTypePage(journey: String): HttpRequestBuilder =
    http("Get transport type page")
      .get(baseNewUrl + s"$journey" + transportTypeUrl)
      .check(status.is(200))

  def postTransportType(journey: String, mandOrOpt: String): HttpRequestBuilder =
    http("Post transport type")
      .post(baseNewUrl + s"$journey" + transportTypeUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("freightType",  _ => s"$randomTransport")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + s"$journey" + s"$mandOrOpt"))

  def getTransportDetailsPage(journey: String, mandOrOpt: String): HttpRequestBuilder =
    http("Get transport details page - " + s"$mandOrOpt")
      .get(baseNewUrl + s"$journey" + s"$mandOrOpt")
      .check(status.is(200))

  def postArrivalTransportDetails(journey: String): HttpRequestBuilder =
    http("Post transport details")
      .post(baseNewUrl + s"$journey" + transportMandatoryUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("vesselName",  _ => "S.S Anne")
      .formParam("dateOfArrival.day",  _ => s"$d")
      .formParam("dateOfArrival.month",  _ => s"$m")
      .formParam("dateOfArrival.year",  _ => s"$y")
      .formParam("timeOfArrival.hour",  _ => s"$hr")
      .formParam("timeOfArrival.minutes",  _ => s"$min")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + imports + contactDetailsUrl))

  def postDepartureTransportDetails(): HttpRequestBuilder =
    http("Post transport details")
      .post(baseNewUrl + exports + transportMandatoryUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("vesselName",  _ => "PlanetExpress Mk -1")
      .formParam("dateOfDeparture.day",  _ => s"$d")
      .formParam("dateOfDeparture.month",  _ => s"$m")
      .formParam("dateOfDeparture.year",  _ => s"$y")
      .formParam("timeOfDeparture.hour",  _ => s"$hr")
      .formParam("timeOfDeparture.minutes",  _ => s"$min")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + exports + contactDetailsUrl))

  // Contact details
  def getContactDetailsPage(journey: String): HttpRequestBuilder =
    http("Get contact details")
      .get(baseNewUrl + s"$journey" + contactDetailsUrl)
      .check(status.is(200))

  def postContactDetails(journey: String): HttpRequestBuilder =
    http("Post contact details")
      .post(baseNewUrl + s"$journey" + contactDetailsUrl)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("contactName",  _ => "Mrs. Test")
      .formParam("contactEmail",  _ => s"$randomEmail")
      .formParam("contactNumber",  _ => "01234567891")
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + fileUploadUrl))

  // CYA
  def getCYAPage(journey: String): HttpRequestBuilder =
    http("Get CYA page")
      .get(baseNewUrl + s"$journey" + cyaReviewUrl)
      .check(status.is(200))

  def postCYA: HttpRequestBuilder =
    http("Post to create case")
      .post(baseNewUrl + "/create-case")
      .formParam("csrfToken", _("csrfToken").as[String])
      .check(status.is(303))
      .check(header(_ => "Location".success).is(traderNewUrl + confirmationUrl))

  // Confirmation page
  def getConfirmationPage: HttpRequestBuilder =
    http("Get confirmation page")
      .get(baseNewUrl + confirmationUrl)
      .check(status.is(200))
}
