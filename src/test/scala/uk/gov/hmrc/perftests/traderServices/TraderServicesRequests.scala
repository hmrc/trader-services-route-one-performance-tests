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

object TraderServicesRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def getLandingpage: HttpRequestBuilder = {
    http("Get start page")
      .get(traderLanding)
      .check(status.is(303))
      .check(header("Location").is("/send-documents-for-customs-check"))
  }

  def loadLandingpage: HttpRequestBuilder = {
    http("Load start page")
      .get(traderLanding)
      .check(status.is(200))
      .check(regex("What do you want to do?"))
  }

  def postjourneyNew: HttpRequestBuilder = {
    http("Post New journey response")
      .post(traderLanding)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrExistingCase", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + decDetailsUrl))
  }

  def getDecdetails: HttpRequestBuilder = {
    http("Load dec details page")
      .get(traderBaseNew + decDetailsUrl)
      .check(status.is(200))
  }

  def postImportDecdetails: HttpRequestBuilder = {
    http("Post import dec details")
      .post(traderBaseNew + decDetailsUrl)
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
      .post(traderBaseNew + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "A23456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + requestType))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post Import request")
      .post(traderBaseNew + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post Export request")
      .post(traderBaseNew + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "Cancellation")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + routeType))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post Import route")
      .post(traderBaseNew + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post Export route")
      .post(traderBaseNew + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + priorityYN))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post Import YN priority")
      .post(traderBaseNew + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post Export YN priority")
      .post(traderBaseNew + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + whichPriority))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post Import priority goods")
      .post(traderBaseNew + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post Export priority goods")
      .post(traderBaseNew + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + transport))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS (Import only)")
      .post(traderBaseNew + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + transport))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post Import transport")
      .post(traderBaseNew + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post Export transport")
      .post(traderBaseNew + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + vesselMandatory))
  }

  //optional for now
  def postImportVessel: HttpRequestBuilder = {
    http("Post Import vessel details")
      .post(traderBaseNew + importPrefix + vesselOptional)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "Abcdef")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "11")
      .formParam("dateOfArrival.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + contactDetails))
  }

  //mandatory for now
  def postExportVessel: HttpRequestBuilder = {
    http("Post Export vessel details")
      .post(traderBaseNew + exportPrefix + vesselMandatory)
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

  def postImportContact: HttpRequestBuilder = {
    http("Post Import contact details")
      .post(traderBaseNew + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + importPrefix + CYA))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post Export contact details")
      .post(traderBaseNew + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + exportPrefix + CYA))
  }

  def getImportCYA: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(traderBaseNew + importPrefix + CYA)
      .check(status.is(200))
  }

  def getExportCYA: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(traderBaseNew + exportPrefix + CYA)
      .check(status.is(200))
  }

  def postNoMoreUpload: HttpRequestBuilder = {
    http("Last upload - complete journey")
      .post(traderBaseNew + fileUploadedUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("uploadAnotherFile", "no")
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + confirmUrl))
  }

  def getConfirmationPage: HttpRequestBuilder = {
    http("Get confirmation page")
      .get(traderBaseNew + confirmUrl)
      .check(status.is(200))
  }
}


