/*
 * Copyright 2024 HM Revenue & Customs
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
    pauseTest,
    postJourney("New", traderNewUrl + entryDetailsUrl),
    getEntryDetailsPage,
    pauseTest,
    postEntryDetails(imports, randomImportEN),
    getRequestTypePage(imports),
    pauseTest,
    postRequestType(imports, "New"),
    getRoutePage(imports),
    pauseTest,
    postRouteType(imports, "Route3", reasonUrl),
    getReasonPage(imports),
    pauseTest,
    postReason(imports),
    getHasPriorityPage(imports),
    pauseTest,
    postPriorityYN(imports, "yes", whichPriorityGoodsUrl),
    getPriorityGoodsPage(imports),
    pauseTest,
    postPriorityGoods(imports, hasALVSUrl),
    getALVSPage,
    pauseTest,
    postALVS,
    getTransportTypePage(imports),
    pauseTest,
    postTransportType(imports, contactDetailsUrl),
    getContactDetailsPage(imports),
    pauseTest,
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
    pauseTest,
    postJourney("New", traderNewUrl + entryDetailsUrl),
    getEntryDetailsPage,
    pauseTest,
    postEntryDetails(exports, randomExportEN),
    getRequestTypePage(exports),
    pauseTest,
    postRequestType(exports, "C1602"),
    getRoutePage(exports),
    pauseTest,
    postRouteType(exports, "Hold", hasPriorityGoodsUrl),
    getHasPriorityPage(exports),
    pauseTest,
    postPriorityYN(exports, "no", transportTypeUrl),
    getTransportTypePage(exports),
    pauseTest,
    postTransportType(exports, transportMandatoryUrl),
    getTransportDetailsPage(exports, transportMandatoryUrl),
    pauseTest,
    postDepartureTransportDetails(),
    getContactDetailsPage(exports),
    pauseTest,
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
    pauseTest,
    postJourney("Existing", traderAmendUrl + caseRefUrl),
    loadCaseRefPage,
    pauseTest,
    postCaseRef,
    getTypeOfAmendmentPage,
    pauseTest,
    postResponse(writeAndUpload, writeResponseUrl),
    getWriteResponsePage,
    pauseTest,
    postFreeTextResponse,
    pauseTest,
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
