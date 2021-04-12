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

  def uploadWait = new PauseBuilder(5 seconds, None)

  def getPreLandingPage: HttpRequestBuilder = {
    http("Get temporary start page")
      .get(baseUrlRead)
      .check(status.is(303))
      .check(header("Location").is(traderUrl))
  }

  def loadPreLandingPage: HttpRequestBuilder = {
    http("Load temporary start page")
      .get(baseUrlRead + "/start")
      .check(status.is(200))
      .check(regex("Send documents for a customs check for declarations made in CHIEF"))
  }

  def getLandingPage: HttpRequestBuilder = {
    http("Get new or existing page")
      .get(baseUrlRead + traderUrlLanding)
      .check(status.is(303))
      .check(header("Location").is(traderUrl))
  }

  def loadLandingPage: HttpRequestBuilder = {
    http("Load new or existing page")
      .get(baseUrlRead + traderUrlLanding)
      .check(status.is(200))
      .check(regex("What do you want to do?"))
  }

  def postJourneyNew: HttpRequestBuilder = {
    http("Post new journey response")
      .post(baseUrlRead + traderUrlLanding)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "New")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + entryDetailsUrl))
  }

  //Entry Details
  def getEntryDetailsPage: HttpRequestBuilder = {
    http("Get the entry details page")
      .get(baseUrlNew + entryDetailsUrl)
      .check(status.is(200))
  }

  def postImportEntryDetails: HttpRequestBuilder = {
    http("Post import entry details")
      .post(baseUrlNew + entryDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", s"$randomEPU")
      .formParam("entryNumber", s"$randomImportEN")
      .formParam("entryDate.day", s"$d")
      .formParam("entryDate.month", s"$m")
      .formParam("entryDate.year", s"$y")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + requestType))
  }

  def postExportEntryDetails: HttpRequestBuilder = {
    http("Post export entry details")
      .post(baseUrlNew + entryDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", s"$randomEPU")
      .formParam("entryNumber", s"$randomExportEN")
      .formParam("entryDate.day", s"$d")
      .formParam("entryDate.month", s"$m")
      .formParam("entryDate.year", s"$y")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + requestType))
  }

  //Request Type
  def getImportRequestPage: HttpRequestBuilder = {
    http("Get import request type page")
      .get(baseUrlNew + importPrefix + requestType)
      .check(status.is(200))
  }

  def getExportRequestPage: HttpRequestBuilder = {
    http("Get export request type page")
      .get(baseUrlNew + exportPrefix + requestType)
      .check(status.is(200))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post import request type")
      .post(baseUrlNew + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", s"$randomImportRqType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post export request type")
      .post(baseUrlNew + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", s"$randomExportRqMandatoryType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + routeType))
  }

  //Route Type
  def getImportRoutePage: HttpRequestBuilder = {
    http("Get route type page - import")
      .get(baseUrlNew + importPrefix + routeType)
      .check(status.is(200))
  }

  def getExportRoutePage: HttpRequestBuilder = {
    http("Get route type page - export")
      .get(baseUrlNew + exportPrefix + routeType)
      .check(status.is(200))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post route type - import")
      .post(baseUrlNew + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", s"$randomRouteType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post route type - export")
      .post(baseUrlNew + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", s"$randomRouteType")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + priorityYN))
  }


  //Priority Goods
  def getImportHasPriorityPage: HttpRequestBuilder = {
    http("Get has-priority-goods page - import")
      .get(baseUrlNew + importPrefix + priorityYN)
      .check(status.is(200))
  }

  def getExportHasPriorityPage: HttpRequestBuilder = {
    http("Get has-priority-goods page - export")
      .get(baseUrlNew + exportPrefix + priorityYN)
      .check(status.is(200))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post yes to priority - import")
      .post(baseUrlNew + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post no to priority - export")
      .post(baseUrlNew + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "no")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + transport))
  }

  def getImportPriorityGoodsPage: HttpRequestBuilder = {
    http("Get which-priority-goods page - import")
      .get(baseUrlNew + importPrefix + whichPriority)
      .check(status.is(200))
  }

  def getExportPriorityGoodsPage: HttpRequestBuilder = {
    http("Get which-priority-goods page - export")
      .get(baseUrlNew + exportPrefix + whichPriority)
      .check(status.is(200))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post priority goods - import")
      .post(baseUrlNew + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", s"$randomPriorityGoods")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post priority goods - export")
      .post(baseUrlNew + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", s"$randomPriorityGoods")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + transport))
  }

  //ALVS (Import Only)
  def getALVSPage: HttpRequestBuilder = {
    http("Get ALVS page")
      .get(baseUrlNew + importPrefix + hasALVS)
      .check(status.is(200))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS")
      .post(baseUrlNew + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + transport))
  }

  //Transport
  def getImportTransportPage: HttpRequestBuilder = {
    http("Get transport page - import")
      .get(baseUrlNew + importPrefix + transport)
      .check(status.is(200))
  }

  def getExportTransportPage: HttpRequestBuilder = {
    http("Get transport page - export")
      .get(baseUrlNew + exportPrefix + transport)
      .check(status.is(200))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post transport - import")
      .post(baseUrlNew + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", s"$randomTransport")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + transportOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post transport - export")
      .post(baseUrlNew + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", s"$randomTransport")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + transportMandatory))
  }

  //Optional
  def getImportVesselMandatoryPage: HttpRequestBuilder = {
    http("Get mandatory transport details page - import")
      .get(baseUrlNew + importPrefix + transportMandatory)
      .check(status.is(200))
  }

  def getImportVesselOptionalPage: HttpRequestBuilder = {
    http("Get optional transport details page - import")
      .get(baseUrlNew + importPrefix + transportOptional)
      .check(status.is(200))
  }

  def getExportVesselMandatoryPage: HttpRequestBuilder = {
    http("Get mandatory transport details page - export")
      .get(baseUrlNew + exportPrefix + transportMandatory)
      .check(status.is(200))
  }

  def getExportVesselOptionalPage: HttpRequestBuilder = {
    http("Get optional transport details page - export")
      .get(baseUrlNew + exportPrefix + transportOptional)
      .check(status.is(200))
  }

  def postImportVesselOptional: HttpRequestBuilder = {
    http("Post transport details - import")
      .post(baseUrlNew + importPrefix + transportOptional)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "S.S Test Ship")
      .formParam("dateOfArrival.day", s"$d")
      .formParam("dateOfArrival.month", s"$m")
      .formParam("dateOfArrival.year", s"$y")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + importPrefix + contactDetails))
  }

  //Mandatory
  def postExportVesselMandatory: HttpRequestBuilder = {
    http("Post vessel details - export")
      .post(baseUrlNew + exportPrefix + transportMandatory)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfDeparture.day", s"$d")
      .formParam("dateOfDeparture.month", s"$m")
      .formParam("dateOfDeparture.year", s"$y")
      .formParam("timeOfDeparture.hour", s"$hr")
      .formParam("timeOfDeparture.minutes", s"$min")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + exportPrefix + contactDetails))
  }


  //Contact details
  def getImportContactDetailsPage: HttpRequestBuilder = {
    http("Get contact details page - import")
      .get(baseUrlNew + importPrefix + contactDetails)
      .check(status.is(200))
  }

  def getExportContactDetailsPage: HttpRequestBuilder = {
    http("Get contact details page - export")
      .get(baseUrlNew + exportPrefix + contactDetails)
      .check(status.is(200))
  }

  def postImportContact: HttpRequestBuilder = {
    http("Post contact details - import")
      .post(baseUrlNew + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", s"$randomEmail")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + fileUploadUrl))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post contact details - export")
      .post(baseUrlNew + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", s"$randomString")
      .formParam("contactEmail", s"$randomEmail")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + fileUploadUrl))
  }

  //CYA
  def getImportCYAPage: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(baseUrlNew + importPrefix + CYA)
      .check(status.is(200))
  }

  def postImportCYA: HttpRequestBuilder = {
    http("Post Import CYA page - submit to create-case")
      .post(baseUrlNew + "/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + confirmationUrl))
  }

  def getExportCYAPage: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(baseUrlNew + exportPrefix + CYA)
      .check(status.is(200))
  }

  def postExportCYA: HttpRequestBuilder = {
    http("Post Export CYA page - submit to create-case")
      .post(baseUrlNew + "/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderNewUrl + confirmationUrl))
  }

  //Confirmation page
  def getConfirmationPage: HttpRequestBuilder = {
    http("Get confirmation page")
      .get(baseUrlNew + confirmationUrl)
      .check(status.is(200))
  }
}


