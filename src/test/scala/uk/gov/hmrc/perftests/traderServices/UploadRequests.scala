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

  def getUploadPage: HttpRequestBuilder = {
    http("Get first Upload page")
      .get(traderBase + fileUploadUrl)
      .check(status.is(200))
      .check(regex("Upload your first document"))
  }

  ///      .get(s"${JourneyUrls.baseUrl}" + "/pre-clearance" + "${fileUploadUrl}")

  def getFileUploadPage: HttpRequestBuilder = {
    http("Get file upload page")
      .get(traderBase + fileUploadUrl)
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


//  "downloadUrl" : "https://bucketName.s3.eu-west-2.amazonaws.com?1235676",
//  "uploadTimestamp" : "2018-04-24T09:30:00Z",
//  "checksum" : "396f101dd52e8b2ace0dcf5ed09b1d1f030e608938510ce46e7a5c7a4e775100",
//  "fileName" : "test.pdf",
//  "fileMimeType" : "application/pdf"

  def postFileUpload: HttpRequestBuilder = {
    http("upload file")
      .post("https://http//localhost:9570.upscan.tax.service.gov.uk/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
      .asMultipartForm
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "n/a"))
      .bodyPart(StringBodyPart("x-amz-meta-original-filename", "tradingAddress.pdf"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "n/a"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "trader-services-route-one"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(ByteArrayBodyPart("file", fileBytes("/data/PDFile.pdf")).contentType("application/pdf"))
      .check(status.is(303))
      .check(header("Location").saveAs("upscanResponse"))
  }

  //.bodyPart(StringBodyPart("x-amz-meta-consuming-service", "trader-services-route-one")) ???
  def getSuccessUrl: HttpRequestBuilder = {
    http("get success url")
      .get("${upscanResponse}")
      .check(status.is(303))
  }

//  def getReceiptPage: HttpRequestBuilder = {
//    http("get receipt page")
//      .get(s"${JourneyUrls.baseUrl}/cds-file-upload-service/upload/receipt")
//      .check(status.is(200))
//  }
}


