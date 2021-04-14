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
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._
import uk.gov.hmrc.perftests.traderServices.FileUploadRequests._


class TraderServicesSimulation extends PerformanceTestRunner {

  setup("Trader Services Import Journey", "Import").withActions(
    getLoginPage,
    loginUser,
    updateUserRole,
    postSuccessful_Login,
    getPreLandingPage,
    loadPreLandingPage,
    loadLandingPage,
    pause,
    postJourney("New", traderNewUrl + entryDetailsUrl),

    getEntryDetailsPage,
    pause,
    postEntryDetails(imports, randomImportEN),

    getRequestTypePage(imports),
    pause,
    postRequestType(imports, randomImportRqType),

    getRoutePage(imports),
    pause,
    postRouteType(imports),

    getHasPriorityPage(imports),
    pause,
    postPriorityYN(imports, "yes", whichPriorityGoodsUrl),

    getPriorityGoodsPage(imports),
    pause,
    postPriorityGoods(imports, hasALVSUrl),

    getALVSPage,
    pause,
    postALVS,

    getTransportTypePage(imports),
    pause,
    postTransportType(imports, transportOptionalUrl),

    getTransportDetailsPage(imports, transportOptionalUrl),
    pause,
    postImportTransportDetails(transportOptionalUrl),

    getContactDetailsPage(imports),
    pause,
    postContactDetails(imports),

    getFileInfo(newUrl),
    postXLSFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "yes"),
    getFileInfo(newUrl),
    postDOCFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "yes"),
    getFileInfo(newUrl),
    postPPTFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "no"),
    getCYAPage(imports),
    postCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )


  setup("Trader Services Export Journey", "Export").withActions(
    getLoginPage,
    loginUser,
    updateUserRole,
    postSuccessful_Login,

    getLandingPage,
    loadLandingPage,
    pause,
    postJourney("New", traderNewUrl + entryDetailsUrl),

    getEntryDetailsPage,
    pause,
    postEntryDetails(exports, randomExportEN),

    getRequestTypePage(exports),
    pause,
    postRequestType(exports, randomExportRqMandatoryType),

    getRoutePage(exports),
    pause,
    postRouteType(exports),

    getHasPriorityPage(exports),
    pause,
    postPriorityYN(exports, "no", transportTypeUrl),

    getTransportTypePage(exports),
    pause,
    postTransportType(exports, transportMandatoryUrl),

    getTransportDetailsPage(exports, transportMandatoryUrl),
    pause,
    postExportTransportDetails(transportMandatoryUrl),

    getContactDetailsPage(exports),
    pause,
    postContactDetails(exports),

    getFileInfo(newUrl),
    postDOCXFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "yes"),
    getFileInfo(newUrl),
    postXLSXFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "yes"),
    getFileInfo(newUrl),
    postPPTXFileUpload,
    uploadWait,
    getFileUploadedPage(newUrl),

    postYesNoResponseMoreUpload(newUrl, "no"),
    getCYAPage(exports),
    postCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )

  setup("Trader Services Amend Journey", "Amend").withActions(
    getLoginPage,
    loginUser,
    updateUserRole,
    postSuccessful_Login,

    getLandingPage,
    loadLandingPage,
    pause,
    postJourney("Existing", traderAmendUrl + caseRefUrl),

    getCaseRefPage,
    loadCaseRefPage,
    pause,
    postCaseref,

    getTypeOfAmendmentPage,
    pause,
    postResponse(writeAndUpload, writeResponseUrl),
    getWriteResponsePage,
    pause,
    postFreeTextResponse,
    pause,

    getFileInfo(amendUrl),
    postODTFileUpload,
    uploadWait,
    getFileUploadedPage(amendUrl),

    postYesNoResponseMoreUpload(amendUrl, "yes"),
    getFileInfo(amendUrl),
    postODSFileUpload,
    uploadWait,
    getFileUploadedPage(amendUrl),

    postYesNoResponseMoreUpload(amendUrl, "yes"),
    getFileInfo(amendUrl),
    postODPFileUpload,
    uploadWait,
    getFileUploadedPage(amendUrl),

    postYesNoResponseMoreUpload(amendUrl, "no"),
    getAmendCYAPage,
    postAmendCYA,
    getConfirmationPageAmend,
    destroy_UserPlanet
  )

  runSimulation()
}

