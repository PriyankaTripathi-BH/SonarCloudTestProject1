package com.bh.realtrack.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bh.realtrack.dao.IAbacomDAO;
import com.bh.realtrack.dto.DropDownDTO;
import com.bh.realtrack.dto.LastPublishedInfoDTO;
import com.bh.realtrack.dto.LastPublishedInfoForOrdersAndSlotDTO;
import com.bh.realtrack.dto.LastUpdatedOnDTO;
import com.bh.realtrack.dto.OrdersAndSlottingBarGraphDTO;
import com.bh.realtrack.dto.OrdersAndSlottingBarGraphParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCardDetailDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCardDetailParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingClosedWonDTO;
import com.bh.realtrack.dto.OrdersAndSlottingClosedWonExcelDTO;
import com.bh.realtrack.dto.OrdersAndSlottingClosedWonMoreInfoDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommitDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommitExcelDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommonForFourTablesDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCountAmtDTO;
import com.bh.realtrack.dto.OrdersAndSlottingMoreInfoIconDTO;
import com.bh.realtrack.dto.OrdersAndSlottingOmitDTO;
import com.bh.realtrack.dto.OrdersAndSlottingPieChartDTO;
import com.bh.realtrack.dto.OrdersAndSlottingPieChartParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingTablesDTO;
import com.bh.realtrack.dto.OrdersAndSlottingUpdatedOnDTO;
import com.bh.realtrack.dto.OrdersAndSlottingUpsideDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDownloadExcelDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardParametersDTO;
import com.bh.realtrack.dto.RoleDetailsDTO;
import com.bh.realtrack.dto.SaveProcessHarmonizationDashboardDTO;
import com.bh.realtrack.excel.ExportOrdersAndSlottingTablesExcel;
import com.bh.realtrack.excel.ExportProcessHarmonizationDashboardExcel;
import com.bh.realtrack.model.CallerContext;
import com.bh.realtrack.service.IAbacomService;

/*
 * @Author Pooja Ayre
 */
@Service
public class AbacomServiceImpl implements IAbacomService {
	private static Logger log = LoggerFactory.getLogger(AbacomServiceImpl.class.getName());
	private CallerContext callerContext;

	IAbacomDAO iAbacomDAO;

	public AbacomServiceImpl(IAbacomDAO iAbacomDAO, CallerContext callerContext) {
		this.iAbacomDAO = iAbacomDAO;
		this.callerContext = callerContext;
	}

	@Override
	public Map<String, Object> getUserRoleDetails(String companyId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		RoleDetailsDTO roleDetails = new RoleDetailsDTO();

		try {

			int companyid = Integer.parseInt(companyId);
			String usersso = callerContext.getName();

			roleDetails = iAbacomDAO.getUserRoleDetails(companyid, usersso);

			responseMap.put("roleDetails", roleDetails);

		} catch (Exception e) {
			e.getMessage();
		}
		return responseMap;

	}

	@Override
	public Map<String, Object> getOrdersAndSlottingDropdownDetails() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<DropDownDTO> TpsTier3Filter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> SubOrganizationFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> SubOrgRegionFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> HorizonFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> ShowByFilter = new ArrayList<DropDownDTO>();

		try {
			TpsTier3Filter = iAbacomDAO.getOrdersAndSlottingTpsTier3Filter();
			SubOrganizationFilter = iAbacomDAO.getOrdersAndAlottingSubOrganizationFilter();
			SubOrgRegionFilter = iAbacomDAO.getordersAndSlottingSubOrgRegionFilter();
			HorizonFilter = iAbacomDAO.getHorizonFilter();
			ShowByFilter = iAbacomDAO.getShowByFilter();

			responseMap.put("tpsTier3", TpsTier3Filter);
			responseMap.put("subOrganization", SubOrganizationFilter);
			responseMap.put("subOrgRegion", SubOrgRegionFilter);
			responseMap.put("horizonFilter", HorizonFilter);
			responseMap.put("showByFilter", ShowByFilter);

		} catch (Exception e) {
			log.error("getordersandslottingDropdownDetails(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getProcessHarmonizationDashboard(ProcessHarmonizationDashboardParametersDTO phdParametersDto) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<ProcessHarmonizationDashboardDTO> processHarmonizationDashboard = new ArrayList<ProcessHarmonizationDashboardDTO>();
		try {

			processHarmonizationDashboard = iAbacomDAO.getProcessHarmonizationDashboard(phdParametersDto);

			responseMap.put("process_harmonization_dashboard_key", processHarmonizationDashboard);

		} catch (Exception e) {
			log.error("getProcessHarmonizationDashboard(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@SuppressWarnings("resource")
	@Override
	public byte[] downloadProcessHarmonizationDashboard(String qmiCategory, String slotInsightProposed, String horizon,
			String slotRequestType, String budgetaryType, String forecastException, String disconnectionToggle) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = null;
		ExportProcessHarmonizationDashboardExcel exportProcessHarmonizationDashboardExcel = new ExportProcessHarmonizationDashboardExcel();
		List<ProcessHarmonizationDashboardDownloadExcelDTO> processHarmonizationDashboardExcel = new ArrayList<ProcessHarmonizationDashboardDownloadExcelDTO>();

		byte[] excelData = null;
		try {
			workbook = new XSSFWorkbook();
			if (qmiCategory != null && qmiCategory.isEmpty() == false && slotInsightProposed != null
					&& slotInsightProposed.isEmpty() == false && horizon != null && horizon.isEmpty() == false
					&& slotRequestType != null && slotRequestType.isEmpty() == false && budgetaryType != null
					&& budgetaryType.isEmpty() == false && forecastException != null
					&& forecastException.isEmpty() == false && disconnectionToggle != null
					&& disconnectionToggle.isEmpty() == false) {

				processHarmonizationDashboardExcel = iAbacomDAO.getProcessHarmonizationDashboardDownloadExcel(
						qmiCategory, slotInsightProposed, horizon, slotRequestType, budgetaryType, forecastException,
						disconnectionToggle);

				workbook = exportProcessHarmonizationDashboardExcel.downloadProcessHarmonizationDashboard(workbook,
						processHarmonizationDashboardExcel);
			}
			workbook.write(bos);
			excelData = bos.toByteArray();
		} catch (Exception e) {
			log.error(
					"Error occured while downloading Process Harmonization Dashboard Excel file :: " + e.getMessage());
		} finally {
			try {
				bos.close();
				workbook.close();
			} catch (IOException e) {
				log.error("Error occured while downloading Process Harmonization Dashboard Excel file :: "
						+ e.getMessage());
			}
		}
		return excelData;
	}

	@Override
	public Map<String, Object> getLastUpdatedOn() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<LastUpdatedOnDTO> lastUpdatedOn = new ArrayList<LastUpdatedOnDTO>();
		try {

			lastUpdatedOn = iAbacomDAO.getLastUpdatedOn();

			responseMap.put("last_updated_on_key", lastUpdatedOn);

		} catch (Exception e) {
			log.error("getLastUpdatedOn(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getLastPublishedInfo() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<LastPublishedInfoDTO> lastPublishedInfo = new ArrayList<LastPublishedInfoDTO>();
		try {

			lastPublishedInfo = iAbacomDAO.getLastPublishedInfo();

			responseMap.put("last_publish_info_key", lastPublishedInfo);

		} catch (Exception e) {
			log.error("getLastPublishedInfo(): Exception occurred :  " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> saveProcessHarmonizationDashboard(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList) {
		boolean resultFlag = false;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String sso = callerContext.getName();
		try {
			resultFlag = iAbacomDAO.saveProcessHarmonizationDashboard(processHarmonizationDashboardList, sso);
			if (resultFlag) {
				responseMap.put("status", "success");
				responseMap.put("message", "Data saved successfully.");
			} else {
				responseMap.put("status", "Error");
				responseMap.put("message", "Error in saving data.");
			}
		} catch (Exception e) {
			responseMap.put("status", "Error");
			responseMap.put("message", "Error in saving data.");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingCardDetails(
			OrdersAndSlottingCardDetailParametersDTO ordersAndSlottingCardDetailParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingCardDetailDTO> ordersAndSlottingCardDetail = new ArrayList<OrdersAndSlottingCardDetailDTO>();
		try {

			ordersAndSlottingCardDetail = iAbacomDAO
					.getOrdersAndSlottingCardDetails(ordersAndSlottingCardDetailParametersList);

			responseMap.put("orders_and_slotting_card_details_key", ordersAndSlottingCardDetail);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingCardDetails: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingBarGraph(
			OrdersAndSlottingBarGraphParametersDTO ordersAndSlottingBarGraphParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingBarGraphDTO> ordersAndSlottingBarGraph = new ArrayList<OrdersAndSlottingBarGraphDTO>();
		try {

			ordersAndSlottingBarGraph = iAbacomDAO
					.getOrdersAndSlottingBarGraph(ordersAndSlottingBarGraphParametersList);

			responseMap.put("orders_and_slotting_bar_graph_key", ordersAndSlottingBarGraph);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingBarGraph: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingCommit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingCommitDTO> ordersAndSlottingCommit = new ArrayList<OrdersAndSlottingCommitDTO>();
		try {

			ordersAndSlottingCommit = iAbacomDAO.getOrdersAndSlottingCommit(ordersAndSlottingCommitParametersList);

			responseMap.put("orders_and_slotting_commit_key", ordersAndSlottingCommit);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingCommit: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingUpside(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingUpsideDTO> ordersAndSlottingUpside = new ArrayList<OrdersAndSlottingUpsideDTO>();
		try {

			ordersAndSlottingUpside = iAbacomDAO.getOrdersAndSlottingUpside(ordersAndSlottingUpsideParametersList);

			responseMap.put("orders_and_slotting_upside_key", ordersAndSlottingUpside);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingUpside: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingOmit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingOmitParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingOmitDTO> ordersAndSlottingOmit = new ArrayList<OrdersAndSlottingOmitDTO>();
		try {

			ordersAndSlottingOmit = iAbacomDAO.getOrdersAndSlottingOmit(ordersAndSlottingOmitParametersList);

			responseMap.put("orders_and_slotting_omit_key", ordersAndSlottingOmit);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingOmit: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingSecondDropdownDetails() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<DropDownDTO> SegmentFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> KeyAccountFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> InstallationCountryFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> PlantInScopeFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> CtoVsEtoFilter = new ArrayList<DropDownDTO>();
		try {
			SegmentFilter = iAbacomDAO.getOrdersAndSlottingSegementFilter();
			KeyAccountFilter = iAbacomDAO.getOrdersAndAlottingKeyAccountFilter();
			InstallationCountryFilter = iAbacomDAO.getordersAndSlottingInstallationCountryFilter();
			PlantInScopeFilter = iAbacomDAO.getPlantInScopeFilter();
			CtoVsEtoFilter = iAbacomDAO.getgetordersAndSlottingCtoVsEtoFilter();

			responseMap.put("segment", SegmentFilter);
			responseMap.put("keyAccount", KeyAccountFilter);
			responseMap.put("installationCountry", InstallationCountryFilter);
			responseMap.put("plantInScope", PlantInScopeFilter);
			responseMap.put("ctoVsEto", CtoVsEtoFilter);

		} catch (Exception e) {
			log.error("getordersandslottingSecondDropdownDetails(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingCommitCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitCountAmtParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingCountAmtDTO> ordersAndSlottingCommitCountAmt = new ArrayList<OrdersAndSlottingCountAmtDTO>();
		try {
			ordersAndSlottingCommitCountAmt = iAbacomDAO
					.getOrdersAndSlottingCommitCountAmt(ordersAndSlottingCommitCountAmtParametersList);
			responseMap.put("orders_and_slotting_commit_count_amt_key", ordersAndSlottingCommitCountAmt);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingCommitCountAmt: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingUpsideCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideCountAmtParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingCountAmtDTO> ordersAndSlottingUpsideCountAmt = new ArrayList<OrdersAndSlottingCountAmtDTO>();
		try {
			ordersAndSlottingUpsideCountAmt = iAbacomDAO
					.getOrdersAndSlottingUpsideCountAmt(ordersAndSlottingUpsideCountAmtParametersList);
			responseMap.put("orders_and_slotting_upside_count_amt_key", ordersAndSlottingUpsideCountAmt);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingUpsideCountAmt: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingMoreInfoIcon(String opptyNumber) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingMoreInfoIconDTO> ordersAndSlottingMoreInfoIcon = new ArrayList<OrdersAndSlottingMoreInfoIconDTO>();
		try {
			if(opptyNumber!=null && opptyNumber.isEmpty() == false) {
			ordersAndSlottingMoreInfoIcon = iAbacomDAO.getOrdersAndSlottingMoreInfoIcon(opptyNumber);
			responseMap.put("more_info_icon_key", ordersAndSlottingMoreInfoIcon);
			}
		} catch (Exception e) {
			log.error("getOrdersAndSlottingMoreInfoIcon: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingPieChart(
			OrdersAndSlottingPieChartParametersDTO ordersAndSlottingPieChartParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingPieChartDTO> ordersAndSlottingPieChart = new ArrayList<OrdersAndSlottingPieChartDTO>();
		try {
			ordersAndSlottingPieChart = iAbacomDAO
					.getOrdersAndSlottingPieChart(ordersAndSlottingPieChartParametersList);
			responseMap.put("orders_and_slotting_pie_chart_key", ordersAndSlottingPieChart);
		} catch (Exception e) {
			log.error("getOrdersAndSlottingPieChart: Exception occurred : " + e.getMessage());

		}
		return responseMap;
	}

	@Override
	public Map<String, Object> saveOrdersAndSlottingTables(
			List<OrdersAndSlottingTablesDTO> ordersAndSlottingTablesList) {
		boolean resultFlag = false;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String sso = callerContext.getName();
		try {
			resultFlag = iAbacomDAO.saveOrdersAndSlottingTables(ordersAndSlottingTablesList, sso);
			if (resultFlag) {
				responseMap.put("status", "success");
				responseMap.put("message", "Data saved successfully.");
			} else {
				responseMap.put("status", "Error");
				responseMap.put("message", "Error in saving data.");
			}
		} catch (Exception e) {
			log.error("saveOrdersAndSlottingTables: Exception occurred : " + e.getMessage());
			responseMap.put("status", "Error");
			responseMap.put("message", "Error in saving data.");
		}
		return responseMap;
	}

	@SuppressWarnings("resource")
	@Override
	public byte[] downloadOrdersAndSlottingTables(String segment, String keyAccount, String installationCountry,
			String plantInScope, String ctoVsEto, String businessTier, String subOrg, String subOrgRegion,
			String viewGroupFlow, String viewConfidential, String horizon) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = null;
		ExportOrdersAndSlottingTablesExcel exportOrdersAndSlottingExcel = new ExportOrdersAndSlottingTablesExcel();
		List<OrdersAndSlottingCommitExcelDTO> ordersAndSlottingTablesList1 = new ArrayList<OrdersAndSlottingCommitExcelDTO>();
		List<OrdersAndSlottingClosedWonExcelDTO> ordersAndSlottingTablesList3 = new ArrayList<OrdersAndSlottingClosedWonExcelDTO>();
		byte[] excelData = null;
		try {
			workbook = new XSSFWorkbook();
			if (segment != null && segment.isEmpty() == false && keyAccount != null && keyAccount.isEmpty() == false
					&& installationCountry != null && installationCountry.isEmpty() == false && plantInScope != null
					&& plantInScope.isEmpty() == false && ctoVsEto != null && ctoVsEto.isEmpty() == false
					&& businessTier != null && businessTier.isEmpty() == false && subOrg != null
					&& subOrg.isEmpty() == false && subOrgRegion != null && subOrgRegion.isEmpty() == false
					&& viewGroupFlow != null && viewGroupFlow.isEmpty() == false && viewConfidential != null
					&& viewConfidential.isEmpty() == false && horizon != null && horizon.isEmpty() == false) {
				
				ordersAndSlottingTablesList1 = iAbacomDAO.getCommitTableData(segment, keyAccount, installationCountry,
						plantInScope, ctoVsEto, businessTier, subOrg, subOrgRegion, viewGroupFlow, viewConfidential,
						horizon);

				ordersAndSlottingTablesList3 = iAbacomDAO.getClosedWonTableData(segment, keyAccount,
						installationCountry, plantInScope, ctoVsEto, businessTier, subOrg, subOrgRegion, viewGroupFlow,
						viewConfidential, horizon);
				if (horizon.equals("current quarter") || horizon.equals("current year")) {
					workbook = exportOrdersAndSlottingExcel.downloadOrdersAndSlottingExcel(workbook,
							ordersAndSlottingTablesList1);
					workbook = exportOrdersAndSlottingExcel.downloadOrdersAndSlottingExcel1(workbook,
							ordersAndSlottingTablesList1);
					workbook = exportOrdersAndSlottingExcel.downloadOrdersAndSlottingExcel2(workbook,
							ordersAndSlottingTablesList3);
				} else {
					workbook = exportOrdersAndSlottingExcel.downloadOrdersAndSlottingExcel(workbook,
							ordersAndSlottingTablesList1);
					workbook = exportOrdersAndSlottingExcel.downloadOrdersAndSlottingExcel1(workbook,
							ordersAndSlottingTablesList1);
				}
			}
			workbook.write(bos);
			excelData = bos.toByteArray();
		} catch (Exception e) {
			log.error("Error occured while downloading orders and slotting Excel file :: " + e.getMessage());
		} finally {
			try {
				bos.close();
				workbook.close();
			} catch (IOException e) {
				log.error("Error occured while closing connection :: " + e.getMessage());
			}
		}
		return excelData;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingClosedWon(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingClosedWonParametersList) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingClosedWonDTO> ordersAndSlottingClosedWon = new ArrayList<OrdersAndSlottingClosedWonDTO>();
		try {

			ordersAndSlottingClosedWon = iAbacomDAO
					.getOrdersAndSlottingClosedWon(ordersAndSlottingClosedWonParametersList);

			responseMap.put("orders_and_slotting_Closed_won", ordersAndSlottingClosedWon);

		} catch (Exception e) {
			log.error("ordersAndSlottingClosedWon: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getLastPublishedInfoForOrdersAndSlot() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<LastPublishedInfoForOrdersAndSlotDTO> lastPublishedInfo = new ArrayList<LastPublishedInfoForOrdersAndSlotDTO>();
		try {

			lastPublishedInfo = iAbacomDAO.getLastPublishedInfoForOrdersAndSlot();

			responseMap.put("orders_and_slot_last_published_info_key", lastPublishedInfo);

		} catch (Exception e) {
			log.error("getLastPublishedInfoForOrdersAndSlot(): Exception occurred :  " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingClosedWonMoreInfo(String opptyNumber) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<OrdersAndSlottingClosedWonMoreInfoDTO> ordersAndSlottingMoreInfo = new ArrayList<OrdersAndSlottingClosedWonMoreInfoDTO>();
		try {
			if(opptyNumber!=null && opptyNumber.isEmpty() == false) {
			ordersAndSlottingMoreInfo = iAbacomDAO.getOrdersAndSlottingClosedWonMoreInfo(opptyNumber);
			responseMap.put("closed_won_more_info_key", ordersAndSlottingMoreInfo);
			}
		} catch (Exception e) {
			log.error("getOrdersAndSlottingClosedWonMoreInfo: Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> saveProcessHarmonizationDashboardTable(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList) {
		boolean resultFlag = false;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String sso = callerContext.getName();
		try {
			resultFlag = iAbacomDAO.saveProcessHarmonizationDashboardTable(processHarmonizationDashboardList, sso);
			if (resultFlag) {
				responseMap.put("status", "success");
				responseMap.put("message", "Data saved successfully.");
			} else {
				responseMap.put("status", "Error");
				responseMap.put("message", "Error in saving data.");
			}
		} catch (Exception e) {
			responseMap.put("status", "Error");
			responseMap.put("message", "Exception in saving data.");
			log.error("saveProcessHarmonizationDashboardTable : Exception occurred : " + e.getMessage());

		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingUpdatedOn() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		OrdersAndSlottingUpdatedOnDTO updatedOn = new OrdersAndSlottingUpdatedOnDTO();
		try {

			updatedOn.setDmUpdatedOn(iAbacomDAO.getOrdersAndSlottingDmUpdatedOn());
			updatedOn.setObpUpdatedOn(iAbacomDAO.getOrdersAndSlottingObpUpdatedOn());
			updatedOn.setP6UpdatedOn(iAbacomDAO.getOrdersAndSlottingP6UpdatedOn());
			updatedOn.setSeapUpdatedOn(iAbacomDAO.getOrdersAndSlottingSeapUpdatedOn());

			responseMap.put("updated_on_key", updatedOn);
			
			
		} catch (Exception e) {
			log.error("getOrdersAndSlottingUpdatedOn(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getOrdersAndSlottingLastPublishedOn() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String lastPublishedOn = null;
		try {
			lastPublishedOn = iAbacomDAO.getOrdersAndSlottingLastPublishedOn();

			responseMap.put("last_published_on_key", lastPublishedOn);

		} catch (Exception e) {
			log.error("getOrdersAndSlottingLastPublishedOn(): Exception occurred :  " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getProcessHarmonizationDashboardFilters() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<DropDownDTO> qmiCategoryFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> horizonFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> slotRequestTypeFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> budgetaryTypeFilter = new ArrayList<DropDownDTO>();
		List<DropDownDTO> forecastExceptionFilter = new ArrayList<DropDownDTO>();

		try {
			qmiCategoryFilter = iAbacomDAO.getProcessHarmonizationDashboardQmiFilter();
			horizonFilter = iAbacomDAO.getHorizonFilter();
			slotRequestTypeFilter = iAbacomDAO.getProcessHarmonizationDashboardslotRequestFilter();
			budgetaryTypeFilter = iAbacomDAO.getProcessHarmonizationDashboardBudgetaryFilter();
			forecastExceptionFilter = iAbacomDAO.getProcessHarmonizationDashboardForecastFilter();

			responseMap.put("qmiCategoryFilter", qmiCategoryFilter);
			responseMap.put("horizonFilter", horizonFilter);
			responseMap.put("slotRequestTypeFilter", slotRequestTypeFilter);
			responseMap.put("budgetaryTypeFilter", budgetaryTypeFilter);
			responseMap.put("forecastExceptionFilter", forecastExceptionFilter);

		} catch (Exception e) {
			log.error("getProcessHarmonizationDashboardFilters(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getComments() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> commentMap = new HashMap<String, Object>();

		try {
			commentMap = iAbacomDAO.getCommentsForDifferentIcons();
			responseMap.put("allIconsComments", commentMap);

		} catch (Exception e) {
			log.error("getComments(): Exception occurred : " + e.getMessage());
		}
		return responseMap;
	}

}