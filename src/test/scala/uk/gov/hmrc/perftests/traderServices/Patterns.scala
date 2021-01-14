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

trait Patterns {

  val csrfPattern =
    """<input type="hidden" name="csrfToken" value="([^"]+)"""
  val userDetailsUrlPattern = s"""([^"]+)"""
  val userIdPattern = """"userId":"([^"]+)""""
  val clientUserIdPattern = """agents-external-stubs/users/([^"]+)"""
  val planetIdPattern = """"planetId":"([^"]+)""""
  val authTokenPattern = """"authToken":"([^"]+)""""
  val sessionIdPattern = """"sessionId":"([^"]+)""""

  //Upload patters
  val amazonUrlPattern = """action="(.*?)""""
  val callBackUrPattern = """name="x-amz-meta-callback-url" value="(.*?)""""
  val amzDatePattern = """name="x-amz-date" value="(.*?)""""
  val credentialPattern = """name="x-amz-credential" value="(.*?)""""
  val requestIdPattern = """name="x-amz-meta-request-id" value="(.*?)""""
  val initiateResponsePattern = """name="x-amz-meta-upscan-initiate-response" value="(.*?)""""
  val initiateReceivedPattern = """name="x-amz-meta-upscan-initiate-received" value="(.*?)""""
  val metaOriginalFilename = """name="x-amz-meta-original-filename" value="(.*?)""""
  val algorithmPattern = """name="x-amz-algorithm" value="(.*?)""""
  val keyPattern = """name="key" value="(.*?)""""
  val signaturePattern = """name="x-amz-signature" value="(.*?)""""
  val policyPattern = """name="policy" value="(.*?)""""
  val referencePattern = """data-reference="(.*?)""""
  val fileTypePattern = """data-filetype="(.*?)""""
  val successRedirectPattern = """name="success_action_redirect" value="(.*?)""""
  val errorRedirectPattern = """name="error_action_redirect" value="(.*?)""""
  val amazonSessionPattern = """x-amz-meta-session-id" value="(.*?)""""
  val contentTypePattern = """Content-Type" value="(.*?)""""

}