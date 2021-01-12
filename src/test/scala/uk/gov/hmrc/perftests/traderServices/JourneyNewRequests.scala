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

object JourneyNewRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def getLandingpage: HttpRequestBuilder = {
    http("Get start page")
      .get(baseUrlRead)
      .check(status.is(303))
      .check(header("Location").is("/send-documents-for-customs-check"))
  }

  def loadLandingpage: HttpRequestBuilder = {
    http("Load start page")
      .get(baseUrlRead + "/new-or-existing")
      .check(status.is(200))
      .check(regex("What do you want to do?"))
  }

  def postjourneyNew: HttpRequestBuilder = {
    http("Post New journey response")
      .post(baseUrlRead + "/new-or-existing")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + decDetailsUrl))
  }

    //Declaration Details
  def getDecdetails: HttpRequestBuilder = {
    http("Load dec details page")
      .get(baseUrlRead + "/new" + decDetailsUrl)
      .check(status.is(200))
  }

  def postImportDecdetails: HttpRequestBuilder = {
    http("Post import dec details")
      .post(baseUrlRead + "/new" + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "123456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + "/request-type"))
  }

  def postExportDecdetails: HttpRequestBuilder = {
    http("Post export dec details")
      .post(baseUrlRead + "/new" + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "A23456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + requestType))
  }

  //Request Type
  def getImportRequestPage: HttpRequestBuilder = {
    http("Load request type page - Import")
      .get(baseUrlRead + "/new" + importPrefix + requestType)
      .check(status.is(200))
  }

  def getExportRequestPage: HttpRequestBuilder = {
    http("Load request type page - Import")
      .get(baseUrlRead + "/new" + exportPrefix + requestType)
      .check(status.is(200))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post Import request")
      .post(baseUrlRead + "/new" + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post Export request")
      .post(baseUrlRead + "/new" + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "Cancellation")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + routeType))
  }

  //Route Type
  def getImportRoute: HttpRequestBuilder = {
    http("Load route page - Import")
      .get(baseUrlRead + "/new" + importPrefix + routeType)
      .check(status.is(200))
  }

  def getExportRoute: HttpRequestBuilder = {
    http("Load route page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + routeType)
      .check(status.is(200))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post Import route")
      .post(baseUrlRead + "/new"+ importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post Export route")
      .post(baseUrlRead + "/new" + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + priorityYN))
  }


  //Priority Goods
  def getImportPriorityYN: HttpRequestBuilder = {
    http("Load priority YN page - Import")
      .get(baseUrlRead + "/new" + importPrefix + priorityYN)
      .check(status.is(200))
  }

  def getExportPriorityYN: HttpRequestBuilder = {
    http("Load priority YN page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + priorityYN)
      .check(status.is(200))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post Import YN priority")
      .post(baseUrlRead + "/new" + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post Export YN priority")
      .post(baseUrlRead + "/new" + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + whichPriority))
  }

  def getImportPriority: HttpRequestBuilder = {
    http("Load priority goods page - Import")
      .get(baseUrlRead + "/new" + importPrefix + whichPriority)
      .check(status.is(200))
  }

  def getExportPriority: HttpRequestBuilder = {
    http("Load priority goods page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + whichPriority)
      .check(status.is(200))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post Import priority goods")
      .post(baseUrlRead + "/new" + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post Export priority goods")
      .post(baseUrlRead + "/new" + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + transport))
  }


  //ALVS (Import Only)
  def getALVS: HttpRequestBuilder = {
    http("Get ALVS page")
      .get(baseUrlRead + "/new" + importPrefix + hasALVS)
      .check(status.is(200))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS")
      .post(baseUrlRead + "/new" + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + transport))
  }

  //Transport
  def getImportTransport: HttpRequestBuilder = {
    http("Get transport page - Import")
      .get(baseUrlRead + "/new" + importPrefix + transport)
      .check(status.is(200))
  }

  def getExportTransport: HttpRequestBuilder = {
    http("Get transport page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + transport)
      .check(status.is(200))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post Import transport")
      .post(baseUrlRead + "/new" + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post Export transport")
      .post(baseUrlRead + "/new" + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + vesselMandatory))
  }

  //Optional
  def getImportVesselMandatory: HttpRequestBuilder = {
    http("Get mandatory vessel details page - Import")
      .get(baseUrlRead + "/new" + importPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getImportVesselOptional: HttpRequestBuilder = {
    http("Get optional vessel details page - Import")
      .get(baseUrlRead + "/new" + importPrefix + vesselOptional)
      .check(status.is(200))
  }

  def getExportVesselMandatory: HttpRequestBuilder = {
    http("Get mandatory vessel details page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + vesselMandatory)
      .check(status.is(200))
  }

  def getExportVesselOptional: HttpRequestBuilder = {
    http("Get optional vessel details page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + vesselOptional)
      .check(status.is(200))
  }

  def postImportVesselOptional: HttpRequestBuilder = {
    http("Post Import vessel details")
      .post(baseUrlRead + "/new" + importPrefix + vesselOptional)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "Abcdef")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "11")
      .formParam("dateOfArrival.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + contactDetails))
  }

  //Mandatory
  def postExportVesselMandatory: HttpRequestBuilder = {
    http("Post Export vessel details")
      .post(baseUrlRead + "/new" + exportPrefix + vesselMandatory)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "10")
      .formParam("dateOfArrival.year", "2020")
      .formParam("timeOfArrival.hour", "01")
      .formParam("timeOfArrival.minutes", "23")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + contactDetails))
  }


  //Contact details
  def getImportContactDetails: HttpRequestBuilder = {
    http("Get contact details page - Import")
      .get(baseUrlRead + "/new" + importPrefix + contactDetails)
      .check(status.is(200))
  }

  def getExportContactDetails: HttpRequestBuilder = {
    http("Get contact details page - Export")
      .get(baseUrlRead + "/new" + exportPrefix + contactDetails)
      .check(status.is(200))
  }

  def postImportContact: HttpRequestBuilder = {
    http("Post Import contact details")
      .post(baseUrlRead + "/new" + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + CYA))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post Export contact details")
      .post(baseUrlRead + "/new" + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + CYA))
  }

  //CYA
  def getImportCYA: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(baseUrlRead + "/new" + importPrefix + CYA)
      .check(status.is(200))
  }

  def getExportCYA: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(baseUrlRead + "/new" + exportPrefix + CYA)
      .check(status.is(200))
  }

  //Confirmation page
  def getConfirmationPage: HttpRequestBuilder = {
    http("Get confirmation page")
      .get(baseUrlRead + "/new" + confirmUrl)
      .check(status.is(200))
  }
}


