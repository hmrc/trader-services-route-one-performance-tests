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
}

//  def saveUserId = {
//    bodyCheck(userIdPattern).saveAs("userId")
//  }
//  def savePlanetId = {
//    bodyCheck(planetIdPattern).saveAs("planetId")
//  }
