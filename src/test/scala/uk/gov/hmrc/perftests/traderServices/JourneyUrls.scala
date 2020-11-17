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

  val landingpageUrl: String = readProperty("landingPageUrl")

  val baseUrl: String = baseUrlFor("trader-services-route-one-frontend")

  val traderLanding: String = "/trader-services"
  val decDetailsUrl: String = "/pre-clearance/declaration-details"
  val exportPrefix: String = "/pre-clearance/export-questions"
  val importPrefix: String = "/pre-clearance/import-questions"
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

  val CsrfPattern: String = """name="csrfToken" value="([^"]+)""""

  val redirectUrl: String = if (runLocal) {
    "http://localhost:9379/trader-services"
  } else {
    "/trader-services"
  }


  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"
}
