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

  def pause = new PauseBuilder(1 seconds, None)
  def uploadWait = new PauseBuilder(1 seconds, None)

  def getPreLandingpage: HttpRequestBuilder = {
    http("Get pre landing page")
      .get(baseUrlRead)
      .check(status.is(303))
      .check(header("Location").is("/send-documents-for-customs-check/start"))
  }

  def loadPreLandingpage: HttpRequestBuilder = {
    http("Load pre landing page")
      .get(baseUrlRead + "/start")
      .check(status.is(200))
      .check(regex("Send documents for a customs check for declarations made in CHIEF"))
  }

  def getLandingpage: HttpRequestBuilder = {
    http("Get new or existing page")
      .get(baseUrlRead + traderUrlLanding)
      .check(status.is(303))
      .check(header("Location").is("/send-documents-for-customs-check/new-or-existing"))
  }

  def loadLandingpage: HttpRequestBuilder = {
    http("Load new or existing page")
      .get(baseUrlRead + traderUrlLanding)
      .check(status.is(200))
      .check(regex("What do you want to do?"))
  }

  def postjourneyNew: HttpRequestBuilder = {
    http("Post New journey response")
      .post(baseUrlRead + traderUrlLanding)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + decDetailsUrl))
  }

    //Declaration Details
  def getDecdetails: HttpRequestBuilder = {
    http("New: Load dec details page")
      .get(baseUrlRead + decDetailsUrl)
      .check(status.is(200))
  }

  def postImportDecdetails: HttpRequestBuilder = {
    http("New: Post import dec details")
      .post(baseUrlRead + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "314")
      .formParam("entryNumber", "012456X")
      .formParam("entryDate.day", "01")
      .formParam("entryDate.month", "01")
      .formParam("entryDate.year", "2021")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + requestType))
  }

  def postExportDecdetails: HttpRequestBuilder = {
    http("New: Post export dec details")
      .post(baseUrlRead + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "515")
      .formParam("entryNumber", "A12456J")
      .formParam("entryDate.day", "01")
      .formParam("entryDate.month", "12")
      .formParam("entryDate.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + requestType))
  }

  //Request Type
  def getImportRequestPage: HttpRequestBuilder = {
    http("New: Load request type page - Import")
      .get(baseUrlRead + importPrefix + requestType)
      .check(status.is(200))
  }

  def getExportRequestPage: HttpRequestBuilder = {
    http("New: Load request type page - Import")
      .get(baseUrlRead + exportPrefix + requestType)
      .check(status.is(200))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("New: Post Import request")
      .post(baseUrlRead + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("New: Post Export request")
      .post(baseUrlRead + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "Cancellation")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + routeType))
  }

  //Route Type
  def getImportRoute: HttpRequestBuilder = {
    http("New: Load route page - Import")
      .get(baseUrlRead + importPrefix + routeType)
      .check(status.is(200))
  }

  def getExportRoute: HttpRequestBuilder = {
    http("New: Load route page - Export")
      .get(baseUrlRead + exportPrefix + routeType)
      .check(status.is(200))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("New: Post Import route")
      .post(baseUrlRead + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("New: Post Export route")
      .post(baseUrlRead + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + priorityYN))
  }


  //Priority Goods
  def getImportPriorityYN: HttpRequestBuilder = {
    http("New: Load priority YN page - Import")
      .get(baseUrlRead + importPrefix + priorityYN)
      .check(status.is(200))
  }

  def getExportPriorityYN: HttpRequestBuilder = {
    http("New: Load priority YN page - Export")
      .get(baseUrlRead + exportPrefix + priorityYN)
      .check(status.is(200))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("New: Post Import YN priority")
      .post(baseUrlRead + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("New: Post Export YN priority")
      .post(baseUrlRead + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + whichPriority))
  }

  def getImportPriority: HttpRequestBuilder = {
    http("New: Load priority goods page - Import")
      .get(baseUrlRead + importPrefix + whichPriority)
      .check(status.is(200))
  }

  def getExportPriority: HttpRequestBuilder = {
    http("New: Load priority goods page - Export")
      .get(baseUrlRead + exportPrefix + whichPriority)
      .check(status.is(200))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("New: Post Import priority goods")
      .post(baseUrlRead + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("New: Post Export priority goods")
      .post(baseUrlRead + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + transport))
  }


  //ALVS (Import Only)
  def getALVS: HttpRequestBuilder = {
    http("New: Get ALVS page")
      .get(baseUrlRead + importPrefix + hasALVS)
      .check(status.is(200))
  }

  def postALVS: HttpRequestBuilder = {
    http("New: Post ALVS")
      .post(baseUrlRead + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + transport))
  }

  //Transport
  def getImportTransport: HttpRequestBuilder = {
    http("New: Get transport page - Import")
      .get(baseUrlRead + importPrefix + transport)
      .check(status.is(200))
  }

  def getExportTransport: HttpRequestBuilder = {
    http("New: Get transport page - Export")
      .get(baseUrlRead + exportPrefix + transport)
      .check(status.is(200))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("New: Post Import transport")
      .post(baseUrlRead + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("New: Post Export transport")
      .post(baseUrlRead + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + vesselMandatory))
  }

  //Optional
  def getImportVesselMandatory: HttpRequestBuilder = {
    http("New: Get mandatory vessel details page - Import")
      .get(baseUrlRead + importPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getImportVesselOptional: HttpRequestBuilder = {
    http("New: Get optional vessel details page - Import")
      .get(baseUrlRead + importPrefix + vesselOptional)
      .check(status.is(200))
  }

  def getExportVesselMandatory: HttpRequestBuilder = {
    http("New: Get mandatory vessel details page - Export")
      .get(baseUrlRead + exportPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getExportVesselOptional: HttpRequestBuilder = {
    http("New: Get optional vessel details page - Export")
      .get(baseUrlRead + exportPrefix + vesselOptional)
      .check(status.is(200))
  }

  def postImportVesselOptional: HttpRequestBuilder = {
    http("New: Post Import vessel details")
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
    http("New: Post Export vessel details")
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
  def getImportContactDetails: HttpRequestBuilder = {
    http("New: Get contact details page - Import")
      .get(baseUrlRead + importPrefix + contactDetails)
      .check(status.is(200))
  }

  def getExportContactDetails: HttpRequestBuilder = {
    http("New: Get contact details page - Export")
      .get(baseUrlRead + exportPrefix + contactDetails)
      .check(status.is(200))
  }

  def postImportContact: HttpRequestBuilder = {
    http("New: Post Import contact details")
      .post(baseUrlRead + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + fileUploadUrl))
  }

  def postExportContact: HttpRequestBuilder = {
    http("New: Post Export contact details")
      .post(baseUrlRead + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + fileUploadUrl))
  }

  //CYA
  def getImportCYA: HttpRequestBuilder = {
    http("New: Get Import CYA page")
      .get(baseUrlRead + importPrefix + CYA)
      .check(status.is(200))
  }

  def postImportCYA: HttpRequestBuilder = {
    http("New: Post Import CYA page")
      .post(baseUrlRead + "/new/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + confirmationUrl))
  }

  def getExportCYA: HttpRequestBuilder = {
    http("New: Get Export CYA page")
      .get(baseUrlRead + exportPrefix + CYA)
      .check(status.is(200))
  }

  def postExportCYA: HttpRequestBuilder = {
    http("New: Post Export CYA page")
      .post(baseUrlRead + "/new/create-case")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + confirmationUrl))
  }

  //Confirmation page
  def getConfirmationPage: HttpRequestBuilder = {
    http("New: Get confirmation page")
      .get(baseUrlRead + "/new" + confirmationUrl)
      .check(status.is(200))
  }
}


