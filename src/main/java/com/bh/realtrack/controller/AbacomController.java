package com.bh.realtrack.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bh.realtrack.dto.OrdersAndSlottingBarGraphParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCardDetailParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommonForFourTablesDTO;
import com.bh.realtrack.dto.OrdersAndSlottingPieChartParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingTablesDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardParametersDTO;
import com.bh.realtrack.dto.SaveProcessHarmonizationDashboardDTO;
import com.bh.realtrack.service.IAbacomService;

/**
 * @Author Pooja Ayre
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/v1")
public class AbacomController {
	private static final Logger log = LoggerFactory.getLogger(AbacomController.class);

	@Autowired
	IAbacomService iAbacomService;

	@GetMapping("/getUserRoleDetails")
	public Map<String, Object> getUserRoleDetails(@RequestParam("companyid") String companyId) {
		return iAbacomService.getUserRoleDetails(companyId);
	}

	@GetMapping("/getOrdersAndSlottingDropdownDetails")
	public Map<String, Object> getOrdersAndSlottingDropdownDetails() {
		return iAbacomService.getOrdersAndSlottingDropdownDetails();
	}
	
	@GetMapping("/getOrdersAndSlottingSecondDropdownDetails")
	public Map<String, Object> getOrdersAndSlottingSecondDropdownDetails() {
		return iAbacomService.getOrdersAndSlottingSecondDropdownDetails();
	}

	@RequestMapping(value = "/getProcessHarmonizationDashboard", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getProcessHarmonizationDashboard(@RequestBody ProcessHarmonizationDashboardParametersDTO phdParametersDto) {
		return iAbacomService.getProcessHarmonizationDashboard(phdParametersDto);
	}

	@RequestMapping(value = "/getLastUpdatedOn", method = RequestMethod.GET)
	public Map<String, Object> getLastUpdatedOn() {
		return iAbacomService.getLastUpdatedOn();
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingUpdatedOn", method = RequestMethod.GET)
	public Map<String, Object> getOrdersAndSlottingUpdatedOn() {
		return iAbacomService.getOrdersAndSlottingUpdatedOn();
	}

	@RequestMapping(value = "/getLastPublishedInfo", method = RequestMethod.GET)
	public Map<String, Object> getLastPublishedInfo() {
		return iAbacomService.getLastPublishedInfo();
	}
	
	@RequestMapping(value = "/getLastPublishedInfoForOrdersAndSlot", method = RequestMethod.GET)
	public Map<String, Object> getLastPublishedInfoForOrdersAndSlot() {
		return iAbacomService.getLastPublishedInfoForOrdersAndSlot();
	}

	@RequestMapping(value = "/downloadProcessHarmonizationDashboard", method = RequestMethod.GET)
	public void downloadProcessHarmonizationDashboard(@RequestParam String qmiCategory, String slotInsightProposed,
			String horizon, String slotRequestType, String budgetaryType, String forecastException,
			String disconnectionToggle, @RequestHeader final HttpHeaders headers, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");

		String fileName = "Process_Harmonization_Dashboard_" + ft.format(dNow) + ".xlsx";
		response.setContentType("text/xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		try {
			byte[] processHarmonizationDashboard = iAbacomService.downloadProcessHarmonizationDashboard(qmiCategory,
					slotInsightProposed, horizon, slotRequestType, budgetaryType, forecastException,
					disconnectionToggle);
			IOUtils.write(processHarmonizationDashboard, response.getOutputStream());
		} catch (Exception e) {
			log.error("Error occured while downloading Process Harmonization Dashboard file :: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/saveProcessHarmonizationDashboard", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> saveProcessHarmonizationDashboard(@RequestHeader HttpHeaders headers,
			@RequestBody List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList) {
		return iAbacomService.saveProcessHarmonizationDashboard(processHarmonizationDashboardList);
	}
	
	@RequestMapping(value = "/saveProcessHarmonizationDashboardTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> saveProcessHarmonizationDashboardTable(@RequestHeader HttpHeaders headers,
			@RequestBody List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList) {
		return iAbacomService.saveProcessHarmonizationDashboardTable(processHarmonizationDashboardList);
	}

	@RequestMapping(value = "/getOrdersAndSlottingCardDetails", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingCardDetails(
			@RequestBody OrdersAndSlottingCardDetailParametersDTO ordersAndSlottingCardDetailParametersList) {
		return iAbacomService.getOrdersAndSlottingCardDetails(ordersAndSlottingCardDetailParametersList);
	}

	@RequestMapping(value = "/getOrdersAndSlottingBarGraph", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingBarGraph(
			@RequestBody OrdersAndSlottingBarGraphParametersDTO ordersAndSlottingBarGraphParametersList) {
		return iAbacomService.getOrdersAndSlottingBarGraph(ordersAndSlottingBarGraphParametersList);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingPieChart", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingPieChart(
			@RequestBody OrdersAndSlottingPieChartParametersDTO ordersAndSlottingPieChartParametersList) {
		return iAbacomService.getOrdersAndSlottingPieChart(ordersAndSlottingPieChartParametersList);
	}

	@RequestMapping(value = "/getOrdersAndSlottingCommit", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingCommit(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitParametersList) {
		return iAbacomService.getOrdersAndSlottingCommit(ordersAndSlottingCommitParametersList);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingCommitCountAmt", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingCommitCountAmt(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitCountAmtParametersList) {
		return iAbacomService.getOrdersAndSlottingCommitCountAmt(ordersAndSlottingCommitCountAmtParametersList);
	}

	@RequestMapping(value = "/getOrdersAndSlottingUpside", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingUpside(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideParametersList) {
		return iAbacomService.getOrdersAndSlottingUpside(ordersAndSlottingUpsideParametersList);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingUpsideCountAmt", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingUpsideCountAmt(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideCountAmtParametersList) {
		return iAbacomService.getOrdersAndSlottingUpsideCountAmt(ordersAndSlottingUpsideCountAmtParametersList);
	}

	@RequestMapping(value = "/getOrdersAndSlottingOmit", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingOmit(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingOmitParametersList) {
		return iAbacomService.getOrdersAndSlottingOmit(ordersAndSlottingOmitParametersList);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingMoreInfoIcon", method = RequestMethod.GET)
	public Map<String, Object> getOrdersAndSlottingMoreInfoIcon(@RequestParam("opptyNumber") String opptyNumber) {
		return iAbacomService.getOrdersAndSlottingMoreInfoIcon(opptyNumber);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingClosedWonMoreInfo", method = RequestMethod.GET)
	public Map<String, Object> getOrdersAndSlottingClosedWonMoreInfo(@RequestParam("opptyNumber") String opptyNumber) {
		return iAbacomService.getOrdersAndSlottingClosedWonMoreInfo(opptyNumber);
	}
	
	@RequestMapping(value = "/saveOrdersAndSlottingTables", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> saveOrdersAndSlottingTables(@RequestHeader HttpHeaders headers,
			@RequestBody List<OrdersAndSlottingTablesDTO> ordersAndSlottingTablesList) {
		return iAbacomService.saveOrdersAndSlottingTables(ordersAndSlottingTablesList);
	}
	
	@RequestMapping(value = "/downloadOrdersAndSlottingTables", method = RequestMethod.GET)
	public void downloadOrdersAndSlottingTables(@RequestParam String segment, String keyAccount,
			String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg,
			String subOrgRegion, String viewGroupFlow, String viewConfidential,String horizon,
			@RequestHeader final HttpHeaders headers, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");

		String fileName = "Orders_Slotting_Tables_" + ft.format(dNow) + ".xlsx";
		response.setContentType("text/xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		try {
			byte[] ordersAndSlotting = iAbacomService.downloadOrdersAndSlottingTables(segment, keyAccount,
					installationCountry, plantInScope, ctoVsEto, businessTier, subOrg, subOrgRegion, viewGroupFlow,
					viewConfidential,horizon);
			IOUtils.write(ordersAndSlotting, response.getOutputStream());
		} catch (Exception e) {
			log.error("Error occured while downloading orders and slotting tables file :: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingClosedWon", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public Map<String, Object> getOrdersAndSlottingClosedWon(
			@RequestBody OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingClosedWonParametersList) {
		return iAbacomService.getOrdersAndSlottingClosedWon(ordersAndSlottingClosedWonParametersList);
	}
	
	@RequestMapping(value = "/getOrdersAndSlottingLastPublishedOn", method = RequestMethod.GET)
	public Map<String, Object> getOrdersAndSlottingLastPublishedOn() {
		return iAbacomService.getOrdersAndSlottingLastPublishedOn();
	}

	@RequestMapping(value = "/getProcessHarmonizationDashboardFilters", method = RequestMethod.GET)
	public Map<String, Object> getProcessHarmonizationDashboardFilters() {
		return iAbacomService.getProcessHarmonizationDashboardFilters();
	}
	
	@RequestMapping(value = "/getComments", method = RequestMethod.GET)
	public Map<String, Object> getComments() {
		return iAbacomService.getComments();
	}
}