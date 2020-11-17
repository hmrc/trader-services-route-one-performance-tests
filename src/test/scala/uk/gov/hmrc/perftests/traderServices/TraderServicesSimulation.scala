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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.traderServices.AgentStubRequests._
import uk.gov.hmrc.perftests.traderServices.TraderServicesRequests._
import uk.gov.hmrc.perftests.traderServices.UploadRequests._


class TraderServicesSimulation extends PerformanceTestRunner {


  setup("Trader Services Import Journey", "Import").withRequests(
    getLogin_Page,
    authenticate_User,
    update_UserRole,
    postsuccessful_Login,
    getLandingpage,
    loadLandingpage,
    getDecdetails,
    postImportDecdetails,
    postImportRequestType,
    postImportRouteType,
    postImportPriorityYN,
    postImportPriorityGoods,
    postALVS,
    postImportTransport,
    postImportVessel,
    postImportContact,
    getImportCYA,
    getFileUploadPage,
    destroy_UserPlanet

    )


  setup("Trader Services Export Journey", "Export").withRequests(

    getLogin_Page,
    authenticate_User,
    update_UserRole,
    postsuccessful_Login,
    getLandingpage,
    loadLandingpage,
    getDecdetails,
    postExportDecdetails,
    postExportRequestType,
    postExportRouteType,
    postExportPriorityYN,
    postExportPriorityGoods,
    postExportTransport,
    postExportVessel,
    postExportContact,
    getExportCYA,
    destroy_UserPlanet
  )

  runSimulation()
}

