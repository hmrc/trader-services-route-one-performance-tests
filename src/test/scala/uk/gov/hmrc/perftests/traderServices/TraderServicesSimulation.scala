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
import uk.gov.hmrc.perftests.traderServices.UploadFileRequests._


class TraderServicesSimulation extends PerformanceTestRunner {


  setup("Trader Services Import Journey", "Import").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getPreLandingPage,
    loadPreLandingPage,
    loadLandingPage,

    pause,
    postJourneyNew,

    getEntryDetailsPage,
    pause,
    postImportEntryDetails,

    getImportRequestPage,
    pause,
    postImportRequestType,

    getImportRoutePage,
    pause,
    postImportRouteType,

    getImportHasPriorityPage,
    pause,
    postImportPriorityYN,

    getImportPriorityGoodsPage,
    pause,
    postImportPriorityGoods,

    getALVSPage,
    pause,
    postALVS,

    getImportTransportPage,
    pause,
    postImportTransport,

    getImportVesselOptionalPage,
    pause,
    postImportVesselOptional,

    getImportContactDetailsPage,
    pause,
    postImportContact,

    getFileInfoNew,
    postXLSFileUpload,
    uploadWait,
    getFileUploadedPage,

    postYesMoreUpload,
    getFileInfoNew,
    postDOCFileUpload,
    uploadWait,
    getFileUploadedPage,

    postYesMoreUpload,
    getFileInfoNew,
    postPPTFileUpload,
    uploadWait,
    getFileUploadedPage,

    postNoMoreUpload,
    getImportCYAPage,
    postImportCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )


  setup("Trader Services Export Journey", "Export").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getLandingPage,
    loadLandingPage,
    pause,
    postJourneyNew,

    getEntryDetailsPage,
    pause,
    postExportEntryDetails,

    getExportRequestPage,
    pause,
    postExportRequestType,

    getExportRoutePage,
    pause,
    postExportRouteType,

    getExportHasPriorityPage,
    pause,
    postExportPriorityYN,

    getExportTransportPage,
    pause,
    postExportTransport,

    getExportVesselMandatoryPage,
    pause,
    postExportVesselMandatory,

    getExportContactDetailsPage,
    pause,
    postExportContact,

    getFileInfoNew,
    postDOCXFileUpload,
    uploadWait,
    getFileUploadedPage,

    postYesMoreUpload,
    getFileInfoNew,
    postXLSXFileUpload,
    uploadWait,
    getFileUploadedPage,

    postYesMoreUpload,
    getFileInfoNew,
    postPPTXFileUpload,
    uploadWait,
    getFileUploadedPage,

    postNoMoreUpload,
    getExportCYAPage,
    postExportCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )

  setup("Trader Services Amend Journey", "Amend").withActions(
    getLogin_Page,
    login_User,
    update_UserRole,
    postsuccessful_Login,

    getLandingPage,
    loadLandingPage,
    pause,
    postJourneyAmend,

    getCaseRefPage,
    loadCaseRefPage,
    pause,
    postCaseref,

    getTypeOfAmendmentPage,
    pause,
    postWriteAndUpload,
    getWriteResponsePage,
    pause,
    postFreeTextResponse,
    pause,

    getFileInfoAmend,
    postODTFileUpload,
    uploadWait,
    getAmendFileUploadedPage,

    postAmendYesMoreUpload,
    getFileInfoAmend,
    postODSFileUpload,
    uploadWait,
    getAmendFileUploadedPage,

    postAmendYesMoreUpload,
    getFileInfoAmend,
    postODPFileUpload,
    uploadWait,
    getAmendFileUploadedPage,

    postAmendNoMoreUpload,
    getAmendCYAPage,
    postAmendCYA,
    getConfirmationPageAmend,
    destroy_UserPlanet
  )

  runSimulation()
}

