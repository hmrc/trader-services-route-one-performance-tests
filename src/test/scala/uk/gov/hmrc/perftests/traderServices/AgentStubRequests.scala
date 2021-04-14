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
import uk.gov.hmrc.perftests.traderServices.JourneyUrls.redirectUrl

object AgentStubRequests extends ServicesConfiguration with SaveToGatlingSessions {

  val loginUrl: String = readProperty("loginUrl")
  val loginSubmitUrl: String = readProperty("loginSubmitUrl")
  val baseUrlExternalStubs: String = baseUrlFor("agents-external-stubs")

  val postSignInUrl = s"$baseUrlExternalStubs/agents-external-stubs/sign-in"
  val updateUserUrl = s"$baseUrlExternalStubs/agents-external-stubs/users"
  val updateSpecificUserUrl = s"$baseUrlExternalStubs/agents-external-stubs/users/$${userId}"

  def getLoginPage: HttpRequestBuilder = {
    http("Get login stub page")
      .get(s"$loginUrl")
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def loginUser: HttpRequestBuilder =
    http("Authenticate a user")
      .post(s"$postSignInUrl")
      .check(status.is(201))
      .check(saveUserDetailsUrl)
      .check(saveBearerTokenHeader)
      .check(saveSessionIdHeader)
      .check(savePlanetIdHeader)
      .check(saveUserIdHeader)

  def updateUserRole: HttpRequestBuilder =
    http("Update current user to have HMRC-CUS-ORG")
      .put(updateUserUrl)
      .body(StringBody(stubUserAsAgentWithEnrolment))
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer ${bearerToken}")
      .check(status.is(202))
      .check(header("Location").is("/agents-external-stubs/users/${userId}"))

  private val stubUserAsAgentWithEnrolment =
    """{
      |    "affinityGroup" : "Individual",
      |    "principalEnrolments" : [
      |              {
      |                  "key" : "HMRC-CUS-ORG",
      |                  "identifiers" : [
      |                    {
      |                      "key" : "EORINumber",
      |                      "value" : "GB671687041489123"
      |                    }
      |                  ]
      |              }
      |          ]
      |}
    """.stripMargin


  def postSuccessful_Login: HttpRequestBuilder = {
    http("Login with user credentials")
      .post(loginSubmitUrl)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("userId", "${userId}")
      .formParam("planetId", "${planetId}")
      .check(status.is(303))
      .check(header("Location").is(redirectUrl))
  }

  def destroyPlanetUrl(planetId: String) = s"$baseUrlExternalStubs/agents-external-stubs/planets/$planetId"

  def destroy_UserPlanet: HttpRequestBuilder =
    http("Destroy planet")
      .delete(destroyPlanetUrl("${planetId}"))
      .header("Authorization", "Bearer ${bearerToken}")
      .check(status.is(204))
}
