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

import java.io.InputStream

import io.gatling.core.Predef._
import io.gatling.core.check.regex.{RegexCheckType, RegexOfType}
import io.gatling.core.check.{CheckBuilder, MultipleFindCheckBuilder}
import io.gatling.http.Predef._
import io.gatling.http.check.header.{HttpHeaderCheckType, HttpHeaderRegexCheckType}
import io.gatling.http.response.Response

trait SaveToGatlingSessions extends Patterns {

  def saveCsrfToken: CheckBuilder[RegexCheckType, String, String] =
    regex(_ => csrfPattern).saveAs("csrfToken")

  def bodyCheck(body: String): MultipleFindCheckBuilder[RegexCheckType, String, String] with RegexOfType = regex(body)

  def saveBearerToken: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(authTokenPattern).saveAs("bearerToken")

  def saveBearerTokenHeader: CheckBuilder[HttpHeaderRegexCheckType, Response, String] =
    headerRegex("Authorization", """Bearer\s([^"]+)""").saveAs("bearerToken")

  def saveSessionId: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(sessionIdPattern).saveAs("sessionId")

  def saveSessionIdHeader: CheckBuilder[HttpHeaderCheckType, Response, String] =
    header("X-Session-ID").saveAs("sessionId")

  def savePlanetIdHeader: CheckBuilder[HttpHeaderCheckType, Response, String] =
    header("X-Planet-ID").saveAs("planetId")

  def saveUserIdHeader: CheckBuilder[HttpHeaderCheckType, Response, String] =
    header("X-User-ID").saveAs("userId")

  def saveClientUserId: CheckBuilder[HttpHeaderRegexCheckType, Response, String] =
    headerRegex("Location", clientUserIdPattern).saveAs("clientUserId")

  def saveUserDetailsUrl: CheckBuilder[HttpHeaderRegexCheckType, Response, String] =
    headerRegex("Location", userDetailsUrlPattern).saveAs("userDetailsUrl")

  // Uploads
  def saveSuccessRedirect: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(successRedirectPattern).saveAs("successRedirect")

  def saveErrorRedirect: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(errorRedirectPattern).saveAs("errorRedirect")

  def saveFileUploadUrl: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(amazonUrlPattern).saveAs("fileUploadAmazonUrl")

  def saveCallBack: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(callBackUrPattern).saveAs("callBack")

  def saveReference: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(referencePattern).saveAs("reference")

  def saveFileType: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(fileTypePattern).saveAs("fileType")

  def saveAmazonDate: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(amzDatePattern).saveAs("amazonDate")

  def saveAmazonCredential: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(credentialPattern).saveAs("amazonCredential")

  def saveUpscanInitiateResponse: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(initiateResponsePattern).saveAs("upscanInitiateResponse")

  def saveUpscanInitiateReceived: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(initiateReceivedPattern).saveAs("upscanInitiateReceived")

  def saveAmazonMetaOriginalFileName: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(metaOriginalFilename).saveAs("amazonMetaOriginalFileName")

  def saveAmazonAlgorithm: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(algorithmPattern).saveAs("amazonAlgorithm")

  def saveKey: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(keyPattern).saveAs("key")

  def saveAmazonSignature: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(signaturePattern).saveAs("amazonSignature")

  def saveRequestId: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(requestIdPattern).saveAs("requestId")

  def savePolicy: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(policyPattern).saveAs("policy")

  def saveAmzSessionID: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(amazonSessionPattern).saveAs("amzSessionId")

  def saveContentType: CheckBuilder[RegexCheckType, String, String] =
    bodyCheck(contentTypePattern).saveAs("contentType")

  def fileBytes(filename: String): Array[Byte] = {
    val resource: InputStream = getClass.getResourceAsStream(filename)
    Iterator.continually(resource.read).takeWhile(_ != -1).map(_.toByte).toArray
  }
}
