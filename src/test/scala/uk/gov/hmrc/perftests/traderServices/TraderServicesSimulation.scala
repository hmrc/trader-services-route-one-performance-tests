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
import uk.gov.hmrc.perftests.traderServices.FileUploadRequests._
import uk.gov.hmrc.perftests.traderServices.JourneyNewRequests._
import uk.gov.hmrc.perftests.traderServices.JourneyUrls._


class TraderServicesSimulation extends PerformanceTestRunner {

  setup("Trader Services Import Journey", "Import").withActions(
    getLoginPage,
    loginUser,
    updateUserRole(),
    postSuccessful_Login,
    getLandingPage,
    loadLandingPage,
    pause,
    postJourney("New", traderNewUrl + entryDetailsUrl),

    getEntryDetailsPage,
    pause,
    postEntryDetails(imports, randomImportEN),

    getRequestTypePage(imports),
    pause,
    postRequestType(imports, "New"),

    getRoutePage(imports),
    pause,
    postRouteType(imports, "Route3", reasonUrl),

    getReasonPage(imports),
    pause,
    postReason(imports),

    getHasPriorityPage (imports),
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
    postTransportType(imports, contactDetailsUrl),

    getContactDetailsPage(imports),
    pause,
    postContactDetails(imports),

    getFileInfo("/new"),
    postXLSFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postDOCFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postPPTFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postDOCXFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postPPTXFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postJPEGFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postTIFFFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "no"),
    getCYAPage(imports),
    postCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )


  setup("Trader Services Export Journey", "Export").withActions(
    getLoginPage,
    loginUser,
    updateUserRole(),
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
    postRequestType(exports, "C1602"),

    getRoutePage(exports),
    pause,
    postRouteType(exports, "Hold", hasPriorityGoodsUrl),

    getHasPriorityPage(exports),
    pause,
    postPriorityYN(exports, "no", transportTypeUrl),

    getTransportTypePage(exports),
    pause,
    postTransportType(exports, transportMandatoryUrl),

    getTransportDetailsPage(exports, transportMandatoryUrl),
    pause,
    postDepartureTransportDetails(),

    getContactDetailsPage(exports),
    pause,
    postContactDetails(exports),

    getFileInfo("/new"),
    postDOCXFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postXLSXFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "yes"),
    getFileInfo("/new"),
    postPPTXFileUpload,
    uploadWait,
    getFileUploadedPage(baseNewUrl),

    postYesNoResponseMoreUpload(baseNewUrl, "no"),
    getCYAPage(exports),
    postCYA,
    getConfirmationPage,
    destroy_UserPlanet
  )

  setup("Trader Services Amend Journey", "Amend").withActions(
    getLoginPage,
    loginUser,
    updateUserRole(),
    postSuccessful_Login,

    getLandingPage,
    loadLandingPage,
    pause,
    postJourney("Existing", traderAmendUrl + caseRefUrl),

    getCaseRefPage,
    loadCaseRefPage,
    pause,
    postCaseRef,

    getTypeOfAmendmentPage,
    pause,
    postResponse(writeAndUpload, writeResponseUrl),
    getWriteResponsePage,
    pause,
    postFreeTextResponse,
    pause,

    getFileInfo("/add"),
    postODTFileUpload,
    uploadWait,
    getFileUploadedPage(baseAmendUrl),

    postYesNoResponseMoreUpload(baseAmendUrl, "yes"),
    getFileInfo("/add"),
    postODSFileUpload,
    uploadWait,
    getFileUploadedPage(baseAmendUrl),

    postYesNoResponseMoreUpload(baseAmendUrl, "yes"),
    getFileInfo("/add"),
    postODPFileUpload,
    uploadWait,
    getFileUploadedPage(baseAmendUrl),

    postYesNoResponseMoreUpload(baseAmendUrl, "no"),
    getAmendCYAPage,
    postAmendCYA,
    getConfirmationPageAmend,
    destroy_UserPlanet
  )

  runSimulation()
}

