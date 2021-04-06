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

object JourneyNewRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def pause = new PauseBuilder(8 seconds, None)
  def uploadWait = new PauseBuilder(10 seconds, None)

  def getPreLandingPage: HttpRequestBuilder = {
    http("Get temporary start page")
      .get(baseUrlRead)
      .check(status.is(303))
      .check(header("Location").is("/send-documents-for-customs-check"))
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
      .check(header("Location").is("/send-documents-for-customs-check"))
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
      .check(header("Location").is(traderUrl + entryDetailsUrl))
  }

    //Entry Details
  def getEntryDetailsPage: HttpRequestBuilder = {
    http("Get the entry details page")
      .get(baseUrlRead + entryDetailsUrl)
      .check(status.is(200))
  }

  def postImportEntryDetails: HttpRequestBuilder = {
    http("Post import entry details")
      .post(baseUrlRead + entryDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "314")
      .formParam("entryNumber", "012456X")
      .formParam("entryDate.day", "01")
      .formParam("entryDate.month", "01")
      .formParam("entryDate.year", "2021")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + requestType))
  }

  def postExportEntryDetails: HttpRequestBuilder = {
    http("Post export entry details")
      .post(baseUrlRead + entryDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "291")
      .formParam("entryNumber", "A12456J")
      .formParam("entryDate.day", "01")
      .formParam("entryDate.month", "12")
      .formParam("entryDate.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + requestType))
  }

  //Request Type
  def getImportRequestPage: HttpRequestBuilder = {
    http("Get import request type page")
      .get(baseUrlRead + importPrefix + requestType)
      .check(status.is(200))
  }

  def getExportRequestPage: HttpRequestBuilder = {
    http("Get export request type page")
      .get(baseUrlRead + exportPrefix + requestType)
      .check(status.is(200))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post import request type")
      .post(baseUrlRead + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post export request type")
      .post(baseUrlRead + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "C1601")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + routeType))
  }

  //Route Type
  def getImportRoutePage: HttpRequestBuilder = {
    http("Get route type page - import")
      .get(baseUrlRead + importPrefix + routeType)
      .check(status.is(200))
  }

  def getExportRoutePage: HttpRequestBuilder = {
    http("Get route type page - export")
      .get(baseUrlRead + exportPrefix + routeType)
      .check(status.is(200))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post route type - import")
      .post(baseUrlRead + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post route type - export")
      .post(baseUrlRead + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + priorityYN))
  }


  //Priority Goods
  def getImportHasPriorityPage: HttpRequestBuilder = {
    http("Get has-priority-goods page - import")
      .get(baseUrlRead + importPrefix + priorityYN)
      .check(status.is(200))
  }

  def getExportHasPriorityPage: HttpRequestBuilder = {
    http("Get has-priority-goods page - export")
      .get(baseUrlRead + exportPrefix + priorityYN)
      .check(status.is(200))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post yes to priority - import")
      .post(baseUrlRead + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post no to priority - export")
      .post(baseUrlRead + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "no")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + transport))
  }

  def getImportPriorityGoodsPage: HttpRequestBuilder = {
    http("Get which-priority-goods page - import")
      .get(baseUrlRead + importPrefix + whichPriority)
      .check(status.is(200))
  }

  def getExportPriorityGoodsPage: HttpRequestBuilder = {
    http("Get which-priority-goods page - export")
      .get(baseUrlRead + exportPrefix + whichPriority)
      .check(status.is(200))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post priority goods - import")
      .post(baseUrlRead + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post priority goods - export")
      .post(baseUrlRead + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + transport))
  }

  //ALVS (Import Only)
  def getALVSPage: HttpRequestBuilder = {
    http("Get ALVS page")
      .get(baseUrlRead + importPrefix + hasALVS)
      .check(status.is(200))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS")
      .post(baseUrlRead + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + transport))
  }

  //Transport
  def getImportTransportPage: HttpRequestBuilder = {
    http("Get transport page - import")
      .get(baseUrlRead + importPrefix + transport)
      .check(status.is(200))
  }

  def getExportTransportPage: HttpRequestBuilder = {
    http("Get transport page - export")
      .get(baseUrlRead + exportPrefix + transport)
      .check(status.is(200))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post transport - import")
      .post(baseUrlRead + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post transport - export")
      .post(baseUrlRead + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + vesselMandatory))
  }

  //Optional
  def getImportVesselMandatoryPage: HttpRequestBuilder = {
    http("Get mandatory vessel details page - import")
      .get(baseUrlRead + importPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getImportVesselOptionalPage: HttpRequestBuilder = {
    http("Get optional vessel details page - import")
      .get(baseUrlRead + importPrefix + vesselOptional)
      .check(status.is(200))
  }

  def getExportVesselMandatoryPage: HttpRequestBuilder = {
    http("Get mandatory vessel details page - export")
      .get(baseUrlRead + exportPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getExportVesselOptionalPage: HttpRequestBuilder = {
    http("Get optional vessel details page - export")
      .get(baseUrlRead + exportPrefix + vesselOptional)
      .check(status.is(200))
  }

  def postImportVesselOptional: HttpRequestBuilder = {
    http("Post vessel details - import")
      .post(baseUrlRead + importPrefix + vesselOptional)
      .formParam("csrfToken", "${csrfToken}")
//      .formParam("vesselName", "Abcdef")
//      .formParam("dateOfArrival.day", "20")
//      .formParam("dateOfArrival.month", "01")
//      .formParam("dateOfArrival.year", "2021")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + contactDetails))
  }

  //Mandatory
  def postExportVesselMandatory: HttpRequestBuilder = {
    http("Post vessel details - export")
      .post(baseUrlRead + exportPrefix + vesselMandatory)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfDeparture.day", "01")
      .formParam("dateOfDeparture.month", "02")
      .formParam("dateOfDeparture.year", "2021")
      .formParam("timeOfDeparture.hour", "01")
      .formParam("timeOfDeparture.minutes", "23")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + contactDetails))
  }


  //Contact details
  def getImportContactDetailsPage: HttpRequestBuilder = {
    http("Get contact details page - import")
      .get(baseUrlRead + importPrefix + contactDetails)
      .check(status.is(200))
  }

  def getExportContactDetailsPage: HttpRequestBuilder = {
    http("Get contact details page - export")
      .get(baseUrlRead + exportPrefix + contactDetails)
      .check(status.is(200))
  }

  def postImportContact: HttpRequestBuilder = {
    http("Post contact details - import")
      .post(baseUrlRead + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + fileUploadUrl))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post contact details - export")
      .post(baseUrlRead + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + fileUploadUrl))
  }

  //CYA
  def getImportCYAPage: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(baseUrlRead + importPrefix + CYA)
      .check(status.is(200))
  }

  def postImportCYA: HttpRequestBuilder = {
    http("Post Import CYA page - submit to create-case")
      .post(baseUrlRead + "/new/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + confirmationUrl))
  }

  def getExportCYAPage: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(baseUrlRead + exportPrefix + CYA)
      .check(status.is(200))
  }

  def postExportCYA: HttpRequestBuilder = {
    http("Post Export CYA page - submit to create-case")
      .post(baseUrlRead + "/new/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + confirmationUrl))
  }

  //Confirmation page
  def getConfirmationPage: HttpRequestBuilder = {
    http("Get confirmation page")
      .get(baseUrlRead + "/new" + confirmationUrl)
      .check(status.is(200))
  }
}


