package com.bh.realtrack.service;

import java.util.List;
import java.util.Map;

import com.bh.realtrack.dto.OrdersAndSlottingBarGraphParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCardDetailParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommonForFourTablesDTO;
import com.bh.realtrack.dto.OrdersAndSlottingPieChartParametersDTO;
import com.bh.realtrack.dto.OrdersAndSlottingTablesDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardParametersDTO;
import com.bh.realtrack.dto.SaveProcessHarmonizationDashboardDTO;

/*
 * @Author Pooja Ayre
 */
public interface IAbacomService {

	Map<String, Object> getUserRoleDetails(String companyId);

	Map<String, Object> getOrdersAndSlottingDropdownDetails();

	Map<String, Object> getProcessHarmonizationDashboard(ProcessHarmonizationDashboardParametersDTO phdParametersDto);

	public byte[] downloadProcessHarmonizationDashboard(String qmiCategory, String slotInsightProposed, String horizon, String slotRequestType, String budgetaryType, String forecastException, String disconnectionToggle);

	Map<String, Object> getLastUpdatedOn();

	Map<String, Object> getLastPublishedInfo();

	Map<String, Object> saveProcessHarmonizationDashboard(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList);

	Map<String, Object> getOrdersAndSlottingCardDetails(
			OrdersAndSlottingCardDetailParametersDTO ordersAndSlottingCardDetailParametersList);

	Map<String, Object> getOrdersAndSlottingBarGraph(
			OrdersAndSlottingBarGraphParametersDTO ordersAndSlottingBarGraphParametersList);

	Map<String, Object> getOrdersAndSlottingCommit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitParametersList);

	Map<String, Object> getOrdersAndSlottingUpside(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideParametersList);

	Map<String, Object> getOrdersAndSlottingOmit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingOmitParametersList);

	Map<String, Object> getOrdersAndSlottingSecondDropdownDetails();
	
	Map<String, Object> getOrdersAndSlottingCommitCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitCountAmtParametersList);

	Map<String, Object> getOrdersAndSlottingUpsideCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideCountAmtParametersList);

	Map<String, Object> getOrdersAndSlottingMoreInfoIcon(String opptyNumber);

	Map<String, Object> saveOrdersAndSlottingTables(List<OrdersAndSlottingTablesDTO> ordersAndSlottingTablesList);

	Map<String, Object> getOrdersAndSlottingPieChart(
			OrdersAndSlottingPieChartParametersDTO ordersAndSlottingPieChartParametersList);

	byte[] downloadOrdersAndSlottingTables(String segment, String keyAccount, String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg, String subOrgRegion, String viewGroupFlow, String viewConfidential, String horizon);

	Map<String, Object> getOrdersAndSlottingClosedWon(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingClosedWonParametersList);

	Map<String, Object> getLastPublishedInfoForOrdersAndSlot();

	Map<String, Object> getOrdersAndSlottingClosedWonMoreInfo(String opptyNumber);

	Map<String, Object> saveProcessHarmonizationDashboardTable(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList);

	Map<String, Object> getOrdersAndSlottingUpdatedOn();

	Map<String, Object> getOrdersAndSlottingLastPublishedOn();

	Map<String, Object> getProcessHarmonizationDashboardFilters();

	Map<String, Object> getComments();

}
