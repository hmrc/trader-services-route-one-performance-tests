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

import java.io.InputStream

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import io.gatling.http.response.Response

trait SaveToGatlingSessions extends Patterns {

  def saveCsrfToken: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    regex(_ => csrfPattern).saveAs("csrfToken")
  }

  def bodyCheck(body: String) = regex(body)

  def saveBearerToken = {
    bodyCheck(authTokenPattern).saveAs("bearerToken")
  }

  def saveBearerTokenHeader = {
    headerRegex("Authorization","""Bearer\s([^"]+)""").saveAs("bearerToken")
  }

  def saveSessionId = {
    bodyCheck(sessionIdPattern).saveAs("sessionId")
  }

  def saveSessionIdHeader = {
    header("X-Session-ID").saveAs("sessionId")
  }

  def savePlanetIdHeader = {
    header("X-Planet-ID").saveAs("planetId")
  }

  def saveUserIdHeader = {
    header("X-User-ID").saveAs("userId")
  }

  def saveClientUserId = {
    headerRegex("Location", clientUserIdPattern).saveAs("clientUserId")
  }

  def saveUserDetailsUrl = {
    headerRegex("Location", userDetailsUrlPattern).saveAs("userDetailsUrl")
  }


  //Uploads
  def saveSuccessRedirect: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(successRedirectPattern).saveAs("successRedirect")
  }

  def saveErrorRedirect: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(errorRedirectPattern).saveAs("errorRedirect")
  }

  def saveFileUploadurl: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(amazonUrlPattern).saveAs("fileUploadAmazonUrl")
  }

  def saveCallBack: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(callBackUrPattern).saveAs("callBack")
  }

  def saveReference: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(referencePattern).saveAs("reference")
  }

  def saveFileType: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(fileTypePattern).saveAs("fileType")
  }

  def saveAmazonDate: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(amzDatePattern).saveAs("amazonDate")
  }

  def saveAmazonCredential: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(credentialPattern).saveAs("amazonCredential")
  }

  def saveUpscanIniateResponse: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(initiateResponsePattern).saveAs("upscanInitiateResponse")
  }

  def saveUpscanInitiateRecieved: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(initiateReceivedPattern).saveAs("upscanInitiateReceived")
  }

  def saveAmazonMetaOriginalFileName: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(metaOriginalFilename).saveAs("amazonMetaOriginalFileName")
  }

  def saveAmazonAlgorithm: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(algorithmPattern).saveAs("amazonAlgorithm")
  }

  def saveKey: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(keyPattern).saveAs("key")
  }

  def saveAmazonSignature: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(signaturePattern).saveAs("amazonSignature")
  }

  def savePolicy: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(policyPattern).saveAs("policy")
  }

  def fileBytes(filename: String): Array[Byte] = {
    val resource: InputStream = getClass.getResourceAsStream(filename)
    Iterator.continually(resource.read).takeWhile(_ != -1).map(_.toByte).toArray
  }
}