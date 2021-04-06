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

object UploadRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def getFileInfoNew: HttpRequestBuilder =
    if (runLocal) {
      fileUploadInfoLocal("/new")
    } else
      fileUploadInfoFull("/new")

  def getFileInfoAmend: HttpRequestBuilder =
    if (runLocal) {
      fileUploadInfoLocal("/add")
    } else
      fileUploadInfoFull("/add")


  def fileUploadInfoLocal(journey: String): HttpRequestBuilder = {
    http("New: Get info from" + s" $journey " + "file upload page")
      .get(baseUrlRead + s"$journey" + fileUploadUrl)
      .check(saveFileUploadUrl)
      .check(saveCallBack)
      .check(saveAmazonDate)
      .check(saveSuccessRedirect)
      .check(saveAmazonCredential)
      .check(saveAmazonAlgorithm)
      .check(saveKey)
      .check(saveAmazonSignature)
      .check(saveErrorRedirect)
      .check(saveContentType)
      .check(savePolicy)
      .check(status.is(200))
  }

  def fileUploadInfoFull(journey: String): HttpRequestBuilder = {
    fileUploadInfoLocal("")
      .check(saveUpscanIniateResponse)
      .check(saveUpscanInitiateRecieved)
      .check(saveRequestId)
      .check(saveAmzSessionID)
  }

  def getSuccessUrl: HttpRequestBuilder = {
    http("Get success url")
      .get("${successRedirect}")
      .check(status.is(303))
  }

  def getFileUploadedPage: HttpRequestBuilder = {
    http("New: Get file uploaded page")
      .get(baseUrlRead + "/new" + fileUploadedUrl)
      .check(status.is(200))
  }

  def getAmendFileUploadedPage: HttpRequestBuilder = {
    http("Amend: Get file uploaded page")
      .get(baseUrlRead + "/add" + fileUploadedUrl)
      .check(status.is(200))
  }

  def postNoMoreUpload: HttpRequestBuilder = {
    http("New: Last upload - complete journey")
      .post(baseUrlRead + "/new" + fileUploadedUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("uploadAnotherFile", "no")
      .check(status.is(303))
  }

  def postAmendNoMoreUpload: HttpRequestBuilder = {
    http("Amend: Last upload - complete journey")
      .post(baseUrlRead + "/add" + fileUploadedUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("uploadAnotherFile", "no")
      .check(status.is(303))
  }

  def postYesMoreUpload: HttpRequestBuilder = {
    http("New: Upload another")
      .post(baseUrlRead + "/new" + fileUploadedUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("uploadAnotherFile", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/new" + fileUploadUrl))
  }

  def postAmendYesMoreUpload: HttpRequestBuilder = {
    http("Amend: Upload another")
      .post(baseUrlRead + "/add" + fileUploadedUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("uploadAnotherFile", "yes")
      .check(status.is(303))
      .check(header("Location").is(traderUrl + "/add" + fileUploadUrl))
  }
}

