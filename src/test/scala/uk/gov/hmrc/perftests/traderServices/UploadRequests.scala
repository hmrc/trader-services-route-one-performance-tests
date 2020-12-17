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
  val upscanInitiateBaseUrl = baseUrlFor("upscan-initiate")
  val file = File.makeTemp()


  def getFileUploadInfo: HttpRequestBuilder = {
    http("Get info from file upload page")
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

  private def headers: Map[String, String] = Map(
    "User-Agent" -> "trader-services-route-one-frontend",
    "Accept" -> "application/pdf",
    "Content-Type" -> "application/pdf",
    "Authorization" -> "Bearer ${bearerToken}"
  )

//  "Accept" -> s"application/vnd.hmrc.1.0+pdf"
//     "reference" -> "11370e18-6e24-453e-b45a-76d3e32ea33d"
//    "X-Client-ID" -> "e0bb1fcd-090b-4773-8b6c-d0a00b45a27b"
//    "X-Badge-Identifier" -> "BADGEID123"
//    "X-Eori-Identifier" -> "EORIID123"


//      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
//      .headers(Map("X-Requested-With" -> "someRequestedWith"))

  def postFileUpload(): HttpRequestBuilder = {
    http("upload file")
      .post(upscanInitiateBaseUrl + "/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
      .headers(headers)
      .asMultipartForm
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
//      .bodyPart(StringBodyPart("x-amz-meta-original-filename", "${amazonMetaOriginalFileName}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "n/a"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "n/a"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "trader-services-route-one-frontend"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(ByteArrayBodyPart("file", fileBytes("/data/test.pdf")).contentType("application/pdf"))
      .check(status.is(303))
      .check(header("Location").saveAs("upscanResponse"))
  }
}
