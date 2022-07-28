package com.bh.realtrack.dao;

import java.util.List;
import java.util.Map;

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
import com.bh.realtrack.dto.OrdersAndSlottingUpsideDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDownloadExcelDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardParametersDTO;
import com.bh.realtrack.dto.RoleDetailsDTO;
import com.bh.realtrack.dto.SaveProcessHarmonizationDashboardDTO;

/*
 * @Author Pooja Ayre
 */
public interface IAbacomDAO {

	RoleDetailsDTO getUserRoleDetails(int companyid, String usersso);

	List<DropDownDTO> getOrdersAndAlottingSubOrganizationFilter();

	List<DropDownDTO> getordersAndSlottingSubOrgRegionFilter();

	List<DropDownDTO> getOrdersAndSlottingTpsTier3Filter();

	List<DropDownDTO> getHorizonFilter();

	List<DropDownDTO> getShowByFilter();

	List<ProcessHarmonizationDashboardDTO> getProcessHarmonizationDashboard(ProcessHarmonizationDashboardParametersDTO phdParametersDto);

	List<ProcessHarmonizationDashboardDownloadExcelDTO> getProcessHarmonizationDashboardDownloadExcel(String qmiCategory, String slotInsightProposed, String horizon, String slotRequestType, String budgetaryType, String forecastException, String disconnectionToggle);

	List<LastUpdatedOnDTO> getLastUpdatedOn();

	List<LastPublishedInfoDTO> getLastPublishedInfo();

	public boolean saveProcessHarmonizationDashboard(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList, String sso);

	List<OrdersAndSlottingCardDetailDTO> getOrdersAndSlottingCardDetails(
			OrdersAndSlottingCardDetailParametersDTO ordersAndSlottingCardDetailParametersList);

	List<OrdersAndSlottingBarGraphDTO> getOrdersAndSlottingBarGraph(
			OrdersAndSlottingBarGraphParametersDTO ordersAndSlottingBarGraphParametersList);

	List<OrdersAndSlottingCommitDTO> getOrdersAndSlottingCommit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitParametersList);

	List<OrdersAndSlottingUpsideDTO> getOrdersAndSlottingUpside(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideParametersList);

	List<OrdersAndSlottingOmitDTO> getOrdersAndSlottingOmit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingOmitParametersList);

	List<DropDownDTO> getOrdersAndSlottingSegementFilter();

	List<DropDownDTO> getOrdersAndAlottingKeyAccountFilter();

	List<DropDownDTO> getordersAndSlottingInstallationCountryFilter();

	List<DropDownDTO> getPlantInScopeFilter();

	List<DropDownDTO> getgetordersAndSlottingCtoVsEtoFilter();

	List<OrdersAndSlottingCountAmtDTO> getOrdersAndSlottingCommitCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitCountAmtParametersList);

	List<OrdersAndSlottingCountAmtDTO> getOrdersAndSlottingUpsideCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideCountAmtParametersList);

	List<OrdersAndSlottingMoreInfoIconDTO> getOrdersAndSlottingMoreInfoIcon(String opptyNumber);
	
	List<OrdersAndSlottingPieChartDTO> getOrdersAndSlottingPieChart(
			OrdersAndSlottingPieChartParametersDTO ordersAndSlottingPieChartParametersList);

	boolean saveOrdersAndSlottingTables(List<OrdersAndSlottingTablesDTO> ordersAndSlottingTablesList, String sso);

	List<OrdersAndSlottingClosedWonDTO> getOrdersAndSlottingClosedWon(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingClosedWonParametersList);
	
	List<LastPublishedInfoForOrdersAndSlotDTO> getLastPublishedInfoForOrdersAndSlot();
	
	List<OrdersAndSlottingClosedWonMoreInfoDTO> getOrdersAndSlottingClosedWonMoreInfo(String opptyNumber);


	List<OrdersAndSlottingCommitExcelDTO> getCommitTableData(String segment, String keyAccount,
			String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg,
			String subOrgRegion, String viewGroupFlow, String viewConfidential, String horizon);

	public boolean saveProcessHarmonizationDashboardTable(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList, String sso);
	
	String getOrdersAndSlottingDmUpdatedOn();

	String getOrdersAndSlottingObpUpdatedOn();

	String getOrdersAndSlottingP6UpdatedOn();

	String getOrdersAndSlottingSeapUpdatedOn();



	List<OrdersAndSlottingClosedWonExcelDTO> getClosedWonTableData(String segment, String keyAccount,
			String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg,
			String subOrgRegion, String viewGroupFlow, String viewConfidential, String horizon);

	String getOrdersAndSlottingLastPublishedOn();

	List<DropDownDTO> getProcessHarmonizationDashboardQmiFilter();

	List<DropDownDTO> getProcessHarmonizationDashboardslotRequestFilter();

	List<DropDownDTO> getProcessHarmonizationDashboardBudgetaryFilter();

	List<DropDownDTO> getProcessHarmonizationDashboardForecastFilter();

	Map<String, Object> getCommentsForDifferentIcons();

}
