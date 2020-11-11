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
    http("Get Landing page")
      .get(landingpageUrl)
      .check(status.is(303))
      .check(header("Location").is("/trader-services"))
  }

  def loadLandingpage: HttpRequestBuilder = {
    http("Load landing page")
      .get(landingpageUrl)
      .check(status.is(200))
      .check(regex("Trader Services"))
  }

  def getDecdetails: HttpRequestBuilder = {
    http("Load dec details page")
      .get(landingpageUrl + decDetailsUrl)
      .check(status.is(200))

  }


  def postImportDecdetails: HttpRequestBuilder = {
    http("Post input dec details")
      .post(landingpageUrl + "/pre-clearance/declaration-details")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "123456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + "/request-type"))
  }

  def postExportDecdetails: HttpRequestBuilder = {
    http("Post export dec details")
      .post(landingpageUrl + "/pre-clearance/declaration-details")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "123")
      .formParam("entryNumber", "A23456B")
      .formParam("entryDate.day", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + requestType))
  }

  def postImportRequestType: HttpRequestBuilder = {
    http("Post Import request")
      .post(landingpageUrl + importPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "New")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + routeType))
  }

  def postExportRequestType: HttpRequestBuilder = {
    http("Post Export request")
      .post(landingpageUrl + exportPrefix + requestType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("requestType", "Cancellation")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + routeType))
  }

  def postImportRouteType: HttpRequestBuilder = {
    http("Post Import route")
      .post(landingpageUrl + importPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Route1")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + priorityYN))
  }

  def postExportRouteType: HttpRequestBuilder = {
    http("Post Export route")
      .post(landingpageUrl + exportPrefix + routeType)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("routeType", "Hold")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + priorityYN))
  }

  def postImportPriorityYN: HttpRequestBuilder = {
    http("Post Import YN priority")
      .post(landingpageUrl + importPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + whichPriority))
  }

  def postExportPriorityYN: HttpRequestBuilder = {
    http("Post Export YN priority")
      .post(landingpageUrl + exportPrefix + priorityYN)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasPriorityGoods", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + whichPriority))
  }

  def postImportPriorityGoods: HttpRequestBuilder = {
    http("Post Import priority goods")
      .post(landingpageUrl + importPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "LiveAnimals")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + hasALVS))
  }

  def postExportPriorityGoods: HttpRequestBuilder = {
    http("Post Export priority goods")
      .post(landingpageUrl + exportPrefix + whichPriority)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("priorityGoods", "HighValueArt")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + transport))
  }

  def postALVS: HttpRequestBuilder = {
    http("Post ALVS (Import only)")
      .post(landingpageUrl + importPrefix + hasALVS)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("hasALVS", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + transport))
  }

  def postImportTransport: HttpRequestBuilder = {
    http("Post Import transport")
      .post(landingpageUrl + importPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Air")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + vesselOptional))
  }

  def postExportTransport: HttpRequestBuilder = {
    http("Post Export transport")
      .post(landingpageUrl + exportPrefix + transport)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("freightType", "Maritime")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + vesselMandatory))
  }

  //optional for now
  def postImportVessel: HttpRequestBuilder = {
    http("Post Import vessel details")
      .post(landingpageUrl + importPrefix + vesselOptional)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "Abcdef")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "11")
      .formParam("dateOfArrival.year", "2020")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + contactDetails))
  }

  //mandatory for now
  def postExportVessel: HttpRequestBuilder = {
    http("Post Export vessel details")
      .post(landingpageUrl + exportPrefix + vesselMandatory)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("vesselName", "fedcba")
      .formParam("dateOfArrival.day", "01")
      .formParam("dateOfArrival.month", "10")
      .formParam("dateOfArrival.year", "2020")
      .formParam("timeOfArrival.hour", "01")
      .formParam("timeOfArrival.minutes", "23")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + contactDetails))
  }

  def postImportContact: HttpRequestBuilder = {
    http("Post Import contact details")
      .post(landingpageUrl + importPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mrs. Test")
      .formParam("contactEmail", "abc@a.com")
      .formParam("contactNumber", "01234567891")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + importPrefix + CYA))
  }

  def postExportContact: HttpRequestBuilder = {
    http("Post Export contact details")
      .post(landingpageUrl + exportPrefix + contactDetails)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("contactName", "Mr. Test")
      .formParam("contactEmail", "cba@a.com")
      .check(status.is(303))
      .check(header("Location").is(traderLanding + exportPrefix + CYA))
  }

  def getImportCYA: HttpRequestBuilder = {
    http("Get Import CYA page")
      .get(landingpageUrl + importPrefix + CYA)
      .check(status.is(200))
      .check(regex("Review your pre-clearance case details"))
  }

  def getExportCYA: HttpRequestBuilder = {
    http("Get Export CYA page")
      .get(landingpageUrl + exportPrefix + CYA)
      .check(status.is(200))
      .check(regex("Review your pre-clearance case details"))

  }
}


