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

import uk.gov.hmrc.performance.conf.ServicesConfiguration

object JourneyUrls extends ServicesConfiguration with SaveToGatlingSessions {

  val redirectUrl: String = if (runLocal) {
    "http://localhost:9379/trader-services/pre-clearance/new-or-existing"
  } else {
    "/trader-services/pre-clearance/new-or-existing"
  }

  val landingTSRead: String = readProperty("baseUrl")

  val baseUrlTS: String = baseUrlFor("trader-services-route-one-frontend")
  val traderBase:String = s"$baseUrlTS/trader-services/pre-clearance"
  val traderLanding: String = s"$baseUrlTS/trader-services/pre-clearance/new-or-existing"


  val traderUrl:String = "/trader-services/pre-clearance"
  val decDetailsUrl: String = "/declaration-details"
  val exportPrefix: String = "/export-questions"
  val importPrefix: String = "/import-questions"
  val requestType: String = "/request-type"
  val routeType: String = "/route-type"
  val priorityYN: String = "/has-priority-goods"
  val whichPriority: String = "/which-priority-goods"
  val hasALVS: String = "/automatic-licence-verification"
  val transport: String = "/transport-type"
  val vesselMandatory: String = "/vessel-info-required"
  val vesselOptional: String = "/vessel-info"
  val contactDetails: String = "/contact-info"

  val CYA: String = "/check-your-answers"

  val fileUploadUrl: String = "/pre-clearance/file-upload"
  val fileUploaded: String = "/pre-clearance/file-uploaded"

  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"
}
