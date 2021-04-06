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

object UploadFileRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def postPDFFileUpload: HttpRequestBuilder = {
    fileInfo("testPdf.pdf", "application/pdf")
  }

  def postXLSXFileUpload: HttpRequestBuilder = {
    fileInfo("testXls.xls", "application/vnd.ms-excel")
  }

  def postXLSFileUpload: HttpRequestBuilder = {
    fileInfo("testXlsx.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
  }

  def postDOCXFileUpload: HttpRequestBuilder = {
    fileInfo("testDocx.docx", "application/msword")
  }

  def postDOCFileUpload: HttpRequestBuilder = {
    fileInfo("testDoc.doc", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
  }

  def postPPTXFileUpload: HttpRequestBuilder = {
    fileInfo("testPptx.pptx", "application/vnd.ms-presentation")
  }

  def postPPTFileUpload: HttpRequestBuilder = {
    fileInfo("testPpt.ppt", "application/vnd.openxmlformats-officedocument.presentationml.presentation")
  }

  def postODTFileUpload: HttpRequestBuilder = {
    fileInfo("testOdt.odt", "application/vnd.oasis.opendocument.text")
  }

  def postODSFileUpload: HttpRequestBuilder = {
    fileInfo("testOds.ods", "application/vnd.oasis.opendocument.spreadsheet")
  }

  def postODPFileUpload: HttpRequestBuilder = {
    fileInfo("testOdp.odp", "application/vnd.oasis.opendocument.presentation")
  }


  def postJPEGFileUpload: HttpRequestBuilder = {
    fileInfo("testJpeg.jpeg", "image/jpeg")
  }

  def postJPGFileUpload: HttpRequestBuilder = {
    fileInfo("testJpg.jpeg", "image/jpg")
  }

  def postTIFFFileUpload: HttpRequestBuilder = {
    fileInfo("testTiff.tiff", "image/tiff")
  }

  def postTIFFileUpload: HttpRequestBuilder = {
    fileInfo("testTif.tif", "image/tif")
  }

  def postPNGFileUpload: HttpRequestBuilder = {
    fileInfo("testPng.png", "image/png")
  }

  def postTXTFileUpload: HttpRequestBuilder = {
    fileInfo("testTxt.txt", "text/plain")
  }

  def postMSGFileUpload: HttpRequestBuilder = {
    fileInfo("testMsg.msg", "application/vnd.ms-outlook")
  }

  def fileInfo(fileName: String, fileType: String): HttpRequestBuilder = if (runLocal) {
    fileInfoLocal(s"$fileName", s"$fileType")
  } else {
    fileInfoFull(s"$fileName", s"$fileType")
  }

  def fileInfoLocal(fileName: String, fileType: String): HttpRequestBuilder = {
    http("Upload file")
      .post("${fileUploadAmazonUrl}")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
      .asMultipartForm
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("Content-Type", "${contentType}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "trader-services-route-one-frontend"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(StringBodyPart("x-amz-meta-original-filename", s"$fileName"))
      .bodyPart(RawFileBodyPart("file", "data/" + s"$fileName").contentType(s"$fileType"))
      .check(status.is(303))
      .check(header("Location").saveAs("UpscanResponseSuccess"))
  }

  def fileInfoFull(fileName: String, fileType: String): HttpRequestBuilder = {
    fileInfoLocal(s"$fileName", s"$fileType")
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "${requestId}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "${amzSessionId}"))
  }
}
