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

object TraderServicesRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def getLandingpage: HttpRequestBuilder = {
    http("Get start page")
      .get(traderLanding)
      .check(status.is(303))
      .check(header("Location").is("/trader-services"))
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
      .check(header("Location").is(traderUrl + decDetailsUrl))
  }

  def getDecdetails: HttpRequestBuilder = {
    http("Load dec details page")
      .get(traderBase + decDetailsUrl)
      .check(status.is(200))

  }

  def postImportDecdetails: HttpRequestBuilder = {
    http("Post import dec details")
      .post(traderBase + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "123456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + "/request-type"))
  }

  def postExportDecdetails: HttpRequestBuilder = {
    http("Post export dec details")
      .post(traderBase + decDetailsUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "A23456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + requestType))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post Import request")
      .post(traderBase + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post Export request")
      .post(traderBase + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "Cancellation")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + routeType))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post Import route")
      .post(traderBase + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post Export route")
      .post(traderBase + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + priorityYN))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post Import YN priority")
      .post(traderBase + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post Export YN priority")
      .post(traderBase + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + whichPriority))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post Import priority goods")
      .post(traderBase + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post Export priority goods")
      .post(traderBase + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "HighValueArt")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + transport))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS (Import only)")
      .post(traderBase + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + transport))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post Import transport")
      .post(traderBase + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post Export transport")
      .post(traderBase + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + vesselMandatory))
  }

  //optional for now
  def postImportVessel: HttpRequestBuilder = {
    http("Post Import vessel details")
      .post(traderBase + importPrefix + vesselOptional)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "Abcdef")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "11")
      .formParam("dateOfArrival.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + contactDetails))
  }

  //mandatory for now
  def postExportVessel: HttpRequestBuilder = {
    http("Post Export vessel details")
      .post(traderBase + exportPrefix + vesselMandatory)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "10")
      .formParam("dateOfArrival.year", "2020")
      .formParam("timeOfArrival.hour", "01")
      .formParam("timeOfArrival.minutes", "23")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + contactDetails))
  }

  def postImportContact: HttpRequestBuilder = {
    http("Post Import contact details")
      .post(traderBase + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + importPrefix + CYA))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post Export contact details")
      .post(traderBase + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + exportPrefix + CYA))
  }

  def getImportCYA: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(traderBase + importPrefix + CYA)
      .check(status.is(200))
//      .check(regex("Review your pre-clearance case details"))
  }

  def getExportCYA: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(traderBase + exportPrefix + CYA)
      .check(status.is(200))
//      .check(regex("Review your pre-clearance case details"))

  }
}


