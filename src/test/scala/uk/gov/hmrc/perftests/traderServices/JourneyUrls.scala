/*
 * Copyright 2022 HM Revenue & Customs
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

  // Base
  val redirectUrl: String = if (runLocal) {
    "http://localhost:9379/send-documents-for-customs-check"
  } else {
    "/send-documents-for-customs-check"
  }

  val readBaseUrl: String = readProperty("baseUrlTrader")

  val traderUrl: String        = "/send-documents-for-customs-check"
  val traderLandingUrl: String = "/new-or-existing"

  // New
  val baseNewUrl: String     = readBaseUrl + "/new"
  val baseAmendUrl: String   = readBaseUrl + "/add"
  val traderNewUrl: String   = "/send-documents-for-customs-check/new"
  val traderAmendUrl: String = "/send-documents-for-customs-check/add"

  // New
  val entryDetailsUrl: String       = "/entry-details"
  val exports: String               = "/export"
  val imports: String               = "/import"
  val requestTypeUrl: String        = "/request-type"
  val routeTypeUrl: String          = "/route-type"
  val reasonUrl: String             = "/reason"
  val hasPriorityGoodsUrl: String   = "/has-priority-goods"
  val whichPriorityGoodsUrl: String = "/which-priority-goods"
  val hasALVSUrl: String            = "/automatic-licence-verification"
  val transportTypeUrl: String      = "/transport-type"
  val transportMandatoryUrl: String = "/transport-information-required"
  val contactDetailsUrl: String     = "/contact-information"

  // Amend
  val caseRefUrl: String       = "/case-reference-number"
  val whichAmendUrl: String    = "/type-of-amendment"
  val writeResponseUrl: String = "/write-response"

  // Common
  val cyaReviewUrl: String          = "/check-your-answers"
  val multiFileUploadUrlUrl: String = "/upload-files"

  val fileUploadUrl: String   = "/file-upload"
  val fileUploadedUrl: String = "/file-uploaded"

  val confirmationUrl: String = "/confirmation"

  val usrDir: String = System.getProperty("user.dir") + "/src/test/resources/data/"
}
