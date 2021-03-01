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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.traderServices.AgentStubRequests._
import uk.gov.hmrc.perftests.traderServices.AmendRequests._
import uk.gov.hmrc.perftests.traderServices.JourneyNewRequests._
import uk.gov.hmrc.perftests.traderServices.UploadRequests._
import uk.gov.hmrc.perftests.traderServices.UploadDocRequests._


class TraderServicesSimulation extends PerformanceTestRunner {


  setup("Trader Services Import Journey", "Import").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getPreLandingpage,
    loadPreLandingpage,

    getLandingpage,
    loadLandingpage,
    pause,
    postjourneyNew,

    getDecdetails,
    pause,
    postImportDecdetails,

    getImportRequestPage,
    pause,
    postImportRequestType,

    getImportRoute,
    pause,
    postImportRouteType,

    getImportPriorityYN,
    pause,
    postImportPriorityYN,

    getImportPriority,
    pause,
    postImportPriorityGoods,

    getALVS,
    pause,
    postALVS,

    getImportTransport,
    pause,
    postImportTransport,

    getImportVesselOptional,
    pause,
    postImportVesselOptional,

    getImportContactDetails,
    pause,
    postImportContact,

    getFileUploadInfo,
    postPDFFileUpload,
    uploadWait,
    getFileUploadedPage,

    postYesMoreUpload,
    getFileUploadInfo,
    postJPEGFileUpload,
    uploadWait,
    getFileUploadedPage,

    postNoMoreUpload,
    getImportCYA,
    postImportCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )


  setup("Trader Services Export Journey", "Export").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getPreLandingpage,
    loadPreLandingpage,

    getLandingpage,
    loadLandingpage,
    pause,
    postjourneyNew,

    getDecdetails,
    pause,
    postExportDecdetails,

    getExportRequestPage,
    pause,
    postExportRequestType,

    getExportRoute,
    pause,
    postExportRouteType,

    getExportPriorityYN,
    pause,
    postExportPriorityYN,

    getExportPriority,
    pause,
    postExportPriorityGoods,

    getExportTransport,
    pause,
    postExportTransport,

    getExportVesselMandatory,
    pause,
    postExportVesselMandatory,

    getExportContactDetails,
    pause,
    postExportContact,

    getFileUploadInfo,
    postPDFFileUpload,
    uploadWait,

    postNoMoreUpload,
    getExportCYA,
    postExportCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )

  setup("Trader Services Amend Journey", "Amend").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getPreLandingpage,
    loadPreLandingpage,

    getLandingpage,
    loadLandingpage,
    pause,
    postJourneyAmend,

    getCaseRefPage,
    loadCaseRefPage,
    pause,
    postCaseref,

    getWhichAmend,
    pause,
    postWriteAndUpload,
    getWriteResponsePage,
    pause,
    postFreeTextResponse,
    pause,

    getAmendFileUploadInfo,
    postPDFFileUpload,
    uploadWait,
    getAmendFileUploadedPage,
    postAmendYesMoreUpload,

    getAmendFileUploadInfo,
    postJPEGFileUpload,
    uploadWait,
    getAmendFileUploadedPage,

    postAmendNoMoreUpload,
    getAmendCYA,
    postAmendCYA,
    getConfirmationPageAmend
  )

  runSimulation()
}

