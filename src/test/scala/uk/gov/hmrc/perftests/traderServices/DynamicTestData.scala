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

import java.time.{LocalDate, LocalTime}
import java.util.UUID

import scala.util.Random

trait DynamicTestData {

  lazy val nowDay: LocalDate = LocalDate.now
  lazy val (d, m, y) = (nowDay.getDayOfMonth, nowDay.getMonthValue, nowDay.getYear)
  lazy val nowTime: LocalTime = LocalTime.now()
  lazy val (hr, min) = (nowTime.getHour, nowTime.getMinute)

  val randomImportRqType: String = Random.shuffle(List("New", "Cancellation")).head

  val randomExportRqOptionalType: String = Random.shuffle(List("New", "Cancellation", "Withdrawal", "C1603")).head
  val randomExportRqMandatoryType: String = Random.shuffle(List("C1601", "C1602")).head

  val randomRouteType: String = Random.shuffle(List("Route1", "Route1Cap", "Route2", "Route3", "Route6")).head
  //Hold absent => Mandatory vessel page, impacts later flow...

  val randomPriorityGoods: String = Random.shuffle(List("ExplosivesOrFireworks", "HumanRemains", "LiveAnimals")).head
  val randomTransport: String = Random.shuffle(List("Air", "Maritime", "RORO")).head

  val randomString: String = s"${UUID.randomUUID().toString}"
  val randomEmail: String = s"$randomString@test.com"

  //omitting 666 & 667 => set up as error pages in stub data
  lazy val randomEPU: String = (100 + Random.nextInt(565)).toString
  lazy val randomAlpha: String = Random.alphanumeric.filter(_.isLetter).head.toString

  lazy val randomImportEN: String = (100000 + Random.nextInt(899999)).toString + randomAlpha
  lazy val randomExportEN: String = randomAlpha + (10000 + Random.nextInt(89999)).toString + randomAlpha


  //randomize...
  val message = "ABCDEFHIJKLMNOPQRSTUVWXYZ abcdefhijklmnopqrstuvxyz 1234567890 §-=[];',./!@£$%^&*()±_+{}:|<>?¡€#¢∞§¶•ªº–≠“‘…æ«≤≥æ"

}