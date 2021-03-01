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

import uk.gov.hmrc.performance.conf.ServicesConfiguration

object JourneyUrls extends ServicesConfiguration with SaveToGatlingSessions {

  //Base
  val redirectUrl: String = if (runLocal) {
    "http://localhost:9379/send-documents-for-customs-check"
  } else {
    "/send-documents-for-customs-check"
  }

  val baseUrlRead: String = readProperty("baseUrlTrader")

  val baseUrlTS: String = baseUrlFor("trader-services-route-one-frontend")
  val traderBase:String = s"$baseUrlTS/send-documents-for-customs-check"

  val traderUrl:String = "/send-documents-for-customs-check"
  val traderUrlStart:String = "/start"
  val traderUrlLanding:String = "/new-or-existing"
  val traderUrlNew:String = "/send-documents-for-customs-check/new"
  val traderAmendUrl:String = "/send-documents-for-customs-check/add"

  //New
  val decDetailsUrl: String = "/new/declaration-details"
  val exportPrefix: String = "/new/export"
  val importPrefix: String = "/new/import"
  val requestType: String = "/request-type"
  val routeType: String = "/route-type"
  val priorityYN: String = "/has-priority-goods"
  val whichPriority: String = "/which-priority-goods"
  val hasALVS: String = "/automatic-licence-verification"
  val transport: String = "/transport-type"
  val vesselMandatory: String = "/transport-information-required"
  val vesselOptional: String = "/transport-information"
  val contactDetails: String = "/contact-information"

  //Amend
  val caseRefUrl: String = "/add/case-reference-number"
  val whichAmendUrl:String = "/add/type-of-amendment"
  val writeResponseUrl: String = "/add/write-response"

  //Common
  val CYA: String = "/check-your-answers"
  val fileUploadUrl: String = "/file-upload"
  val fileUploadedUrl: String = "/file-uploaded"
  val confirmationUrl: String = "/confirmation"

  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"
}
