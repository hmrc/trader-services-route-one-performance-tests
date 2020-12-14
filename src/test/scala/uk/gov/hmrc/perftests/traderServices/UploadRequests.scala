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

object UploadRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def getFileUploadPage: HttpRequestBuilder = {
    http("Get file upload page")
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

  def postFileUpload: HttpRequestBuilder = {
    http("Upload a file")
      .post(traderBaseNew + fileUploadUrl)
     .formParam( "downloadUrl", "https://bucketName.s3.eu-west-2.amazonaws.com?1235676")
     .formParam("uploadTimestamp", "2018-04-24T09:30:00Z")
      .formParam("checksum", "396f101dd52e8b2ace0dcf5ed09b1d1f030e608938510ce46e7a5c7a4e775100")
      .formParam("fileName", "test.pdf")
    .formParam("fileMimeType", "application/pdf")

  }


  val authBaseUrl = baseUrlFor("auth")
  val apiBaseUrl = baseUrlFor("customs-file-upload")

  val SuccessfulUploadSubmissionStatusCode = 200
  val UpscanInitiate = StringBody(ExampleUpscanIntiate.valid.toString())

  val UploadStatus = "UpscanInitiateStatus"
  val UpscanInitiateSubmissionId = "UpscanInitiateSubmissionId"

  private def headers(): Map[String, String] = Map(
    "Accept" -> s"application/vnd.hmrc.1.0+xml",
    "Content-Type" -> "application/xml",
    "Authorization" -> s"Bearer ${}",
    "X-Client-ID" -> "e0bb1fcd-090b-4773-8b6c-d0a00b45a27b",
    "X-Badge-Identifier" -> "BADGEID123",
    "X-Eori-Identifier" -> "EORIID123"
  )

  private def submitUpscanInitiate(): HttpRequestBuilder = http("Submit File Upload")
    .post(apiBaseUrl + "/upload": String)
    .headers(headers())
    .body(UpscanInitiate)
    .check(status.is(SuccessfulUploadSubmissionStatusCode))

}


//  def postFileUpload: HttpRequestBuilder = {
//    http("Upload a file")
//      .post("https://www.staging.upscan.tax.service.gov.uk/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
//      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
//      .asMultipartForm
//      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
//      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
//      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
//      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
//      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
//      .bodyPart(StringBodyPart("x-amz-meta-request-id", "n/a"))
//      .bodyPart(StringBodyPart("x-amz-meta-original-filename", "tradingAddress.pdf"))
//      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
//      .bodyPart(StringBodyPart("key", "${key}"))
//      .bodyPart(StringBodyPart("acl", "private"))
//      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
//      .bodyPart(StringBodyPart("x-amz-meta-session-id", "n/a"))
//      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "customs-declarations"))
//      .bodyPart(StringBodyPart("policy", "${policy}"))
//      .bodyPart(ByteArrayBodyPart("file", fileBytes("/data/PDFile.pdf")).contentType("application/pdf"))
//      .check(status.is(303))
//      .check(header("Location").saveAs("upscanResponse"))
//  }

object ExampleUpscanIntiate {
  val valid = <FileUploadRequest xmlns="hmrc:fileupload" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <DeclarationID>DEC123</DeclarationID>
    <FileGroupSize>3</FileGroupSize>
    <Files>
      <File>
        <FileSequenceNo>1</FileSequenceNo>
        <DocumentType>document type 1</DocumentType>
      </File>
      <File>
        <FileSequenceNo>3</FileSequenceNo>
        <DocumentType>document type 2</DocumentType>
      </File>
      <File>
        <FileSequenceNo>2</FileSequenceNo>
        <DocumentType>document type 2</DocumentType>
      </File>
    </Files>
  </FileUploadRequest>
}



