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

import java.time.{LocalDate, LocalTime}

import scala.util.Random

trait TestData {

  lazy val nowDay: LocalDate  = LocalDate.now
  lazy val (d, m, y)          = (nowDay.getDayOfMonth, nowDay.getMonthValue, nowDay.getYear)
  lazy val nowTime: LocalTime = LocalTime.now()
  lazy val (hr, min)          = (nowTime.getHour, nowTime.getMinute)

  lazy val randomPriorityGoods: String =
    Random.shuffle(List("ExplosivesOrFireworks", "HumanRemains", "LiveAnimals")).head
  lazy val randomTransport: String     = Random.shuffle(List("Air", "Maritime", "RORO")).head

  def randomString(length: Int): String = Random.alphanumeric.take(length).mkString

  lazy val shortString: String = randomString(20)
  lazy val longString: String  = randomString(1000)

  lazy val randomEmail: String = s"$shortString@test.com"

  lazy val randomEpuDigits: Int = Random.nextInt(666)
  lazy val randomEPU: String    = f"$randomEpuDigits%03d"

  lazy val randomAlpha: String = Random.alphanumeric.filter(_.isLetter).head.toString

  lazy val randomImportDigits: Int = Random.nextInt(999999)
  lazy val randomImportEN: String  = f"$randomImportDigits%06d" + randomAlpha

  lazy val randomExportDigits: Int = Random.nextInt(99999)
  lazy val randomExportEN: String  = randomAlpha + f"$randomExportDigits%05d" + randomAlpha

  // Journey choices for amend
  lazy val writeResponseOnly: String = "WriteResponse"
  lazy val UploadOnly: String        = "UploadDocuments"
  lazy val writeAndUpload: String    = "WriteResponseAndUploadDocuments"
}
