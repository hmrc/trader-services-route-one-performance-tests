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

object UploadDocRequests extends ServicesConfiguration with SaveToGatlingSessions {

  def postPDFFileUpload: HttpRequestBuilder = {
    fileInfo("testPdf.pdf", "application/pdf")
    }

  def postXLSFileUpload: HttpRequestBuilder = {
    fileInfo("testXlsx.xlsx", "application/vnd.ms-excel")
  }
  def postXLSXFileUpload: HttpRequestBuilder = {
    fileInfo("testXls.xls", "application/vnd.ms-excel")
  }

  def postDOCFileUpload: HttpRequestBuilder = {
    fileInfo("testDoc.doc", "application/msword")
  }
  def postDOCXFileUpload: HttpRequestBuilder = {
    fileInfo("testDocx.docx", "application/msword")
  }

  def postPPTFileUpload: HttpRequestBuilder = {
    fileInfo("testPpt.ppt", "application/vnd.ms-presentation")
  }
  def postPPTXFileUpload: HttpRequestBuilder = {
    fileInfo("testPptx.pptx", "application/vnd.ms-presentation")
  }

  def postODTFileUpload: HttpRequestBuilder = {
    fileInfo("testOdt.odt", "?????")
  }

  def postODSFileUpload: HttpRequestBuilder = {
    fileInfo("testOds.ods", "?????")
  }
  def postODPFileUpload: HttpRequestBuilder = {
    fileInfo("testOdp.odp", "?????")
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
  def postTIFileUpload: HttpRequestBuilder = {
    fileInfo("testTif.tif", "image/tif")
  }

  def postPNGFileUpload: HttpRequestBuilder = {
    fileInfo("testPng.png", "image/png")
  }

  def postTXTFileUpload: HttpRequestBuilder = {
    fileInfo("testTxt.txt", "?????")
  }

  def postMSGFileUpload: HttpRequestBuilder = {
    fileInfo("testMsg.msg", "?????")
  }


  def fileInfo(fileName:String, fileType:String):HttpRequestBuilder = {
    if(runLocal) {
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
    }
    else {
      fileInfo("", "")
        .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
        .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
        .bodyPart(StringBodyPart("x-amz-meta-request-id", "${requestId}"))
        .bodyPart(StringBodyPart("x-amz-meta-session-id", "${amzSessionId}"))
    }
  }
      .check(status.is(303))
      .check(header("Location").saveAs("UpscanResponseSuccess"))
  }
