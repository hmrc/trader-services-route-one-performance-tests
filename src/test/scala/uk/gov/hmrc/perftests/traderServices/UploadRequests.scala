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
      .check(saveChecksum)
      .check(saveCorrelationID)
      .check(saveConversationID)
      .check(saveAmazonAlgorithm)
      .check(saveKey)
      .check(saveAmazonSignature)
      .check(savePolicy)
      .check(saveSuccessRedirect)
      .check(saveErrorRedirect)
      .check(status.is(200))
  }
//
//  val correlationId = ju.UUID.randomUUID().toString()
//  val conversationId = ju.UUID.randomUUID().toString()

  private def headers: Map[String, String] = Map(
    "User-Agent" -> "trader-services-route-one-frontend",
    "x-correlation-id" -> "${correlationID}",
    "x-conversation-id" -> "${conversationID}",
    "environment" -> "stub",
    "date" -> "Fri, 06 Nov 2020 08:23:57 GMT",
    //"${amazonDate}"
    "content-type" -> "application/pdf",
    "accept" -> "application/pdf",
    "authorization" -> "Bearer ${bearerToken}",
    "customsprocesseshost" -> "Digital",
    "x-metadata" -> metadata,
    "checksum" -> "${checksum}",
    "checksumAlgorithm" -> "SHA-256"
  )

  def postFileUpload(): HttpRequestBuilder = {
    http("Upload a file")
      .post(upscanInitiateBaseUrl + "/v1/uploads/fus-inbound-830f78e090fe8aec00891405dfc14824")
      .headers(headers)
      .asMultipartForm
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
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


  def metadata = s"""<?xml version="1.0" encoding="utf-8"?>
                    |<mdg:BatchFileInterfaceMetadata
                    |xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    |xmlns:mdg="http://www.hmrc.gsi.gov.uk/mdg/batchFileInterfaceMetadataSchema"
                    |xsi:schemaLocation="http://www.hmrc.gsi.gov.uk/mdg/batchFileInterfaceMetadataSchemaBatchFileInterfaceMetadataXSD-1.0.7.xsd">
                    |    <mdg:sourceSystem>Digital</mdg:sourceSystem>
                    |    <mdg:interfaceName>traderServices</mdg:interfaceName>
                    |    <mdg:interfaceVersion>1.0</mdg:interfaceVersion>
                    |    <mdg:fileSize>1</mdg:fileSize>
                    |    <mdg:compressed>false</mdg:compressed>
                    |    <mdg:properties>
                    |        <mdg:property>
                    |            <mdg:name>case_reference</mdg:name>
                    |            <mdg:value>PCI201222538CYVJGR3R42</mdg:value>
                    |        </mdg:property>
                    |        <mdg:property>
                    |            <mdg:name>application_name</mdg:name>
                    |            <mdg:value>Route1</mdg:value>
                    |        </mdg:property>
                    |    </mdg:properties>
                    |    <mdg:sourceLocation>sourceLocation</mdg:sourceLocation>
                    |    <mdg:sourceFileName>test.jpeg</mdg:sourceFileName>
                    |    <mdg:sourceFileMimeType>image/jpeg</mdg:sourceFileMimeType>
                    |</mdg:BatchFileInterfaceMetadata>""".stripMargin.replaceAll("\n", "")

}


//      .check(saveUpscanIniateResponse)
//      .check(saveUpscanInitiateRecieved)
//      .check(saveAmazonMetaOriginalFileName)

//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
//      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
//      .bodyPart(StringBodyPart("x-amz-meta-original-filename", "${amazonMetaOriginalFileName}"))