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

import scala.reflect.io.File

object UploadRequests extends ServicesConfiguration with SaveToGatlingSessions {

  val fileUploadFrontendBaseUrl = baseUrlFor("trader-services-route-one-frontend")
  val fileUploadBackendBaseUrl = baseUrlFor("trader-services-route-one")
  val file = File.makeTemp()

  def getFileUploadPage: HttpRequestBuilder = {
    http("Navigate to file upload page")
      .get(traderBaseNew + fileUploadUrl)
      .check(saveFileUploadurl)
      .check(saveCallBack)
      .check(saveAmazonDate)
      .check(saveAmazonCredential)
//      .check(saveUpscanIniateResponse)
//      .check(saveUpscanInitiateRecieved)
//      .check(saveAmazonMetaOriginalFileName)
      .check(saveAmazonAlgorithm)
      .check(saveKey)
      .check(saveAmazonSignature)
      .check(savePolicy)
      .check(saveSuccessRedirect)
      .check(saveErrorRedirect)
      .check(status.is(200))
  }

  def uploadFile: HttpRequestBuilder = {
    http("Upload File")
      .post("www.development.upscan.tax.service.gov.uk/v1/uploads/fus-inbound-759b74ce43947f5f4c91aeddc3e5bad3")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
      .formParam("csrfToken", "${csrfToken}")
      .headers(Map("X-Requested-With" -> "someRequestedWith"))
      .bodyPart(StringBodyPart("foo", "bar"))
      .bodyPart(RawFileBodyPart("fileToUpload", file.path))
      .check(status.is(303))
      .check(header("Location").is(traderUrlNew + fileUploaded))
  }
}
//text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8

