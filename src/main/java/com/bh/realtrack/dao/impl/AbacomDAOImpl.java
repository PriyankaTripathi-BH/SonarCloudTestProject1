package com.bh.realtrack.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.ws.rs.ServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

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
import com.bh.realtrack.dto.OrdersAndSlottingUpsideDTO;
import com.bh.realtrack.dto.OrdersAndSlottingUpsideExcelDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardDownloadExcelDTO;
import com.bh.realtrack.dto.ProcessHarmonizationDashboardParametersDTO;
import com.bh.realtrack.dto.RoleDetailsDTO;
import com.bh.realtrack.dto.SaveProcessHarmonizationDashboardDTO;
import com.bh.realtrack.enumerators.ErrorCode;
import com.bh.realtrack.util.AbacomConstants;
import com.bh.realtrack.util.AssertUtils;

/*
 * @Author Pooja Ayre
 */
@Repository
public class AbacomDAOImpl implements IAbacomDAO {

	private static Logger log = LoggerFactory.getLogger(AbacomDAOImpl.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	private String getFormattedDateType1(String dateValue) {
		String newFormattedDate = null;
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd-MMM-yyyy";
		String oldDateString = dateValue;
		String newDateString;
		if (oldDateString != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date d;
			try {
				d = sdf.parse(oldDateString);
				sdf.applyPattern(NEW_FORMAT);
				newDateString = sdf.format(d);
				newFormattedDate = newDateString.toUpperCase();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			newFormattedDate = dateValue;
		}
		return newFormattedDate;
	}

	private java.sql.Date getFormattedDateType2(String dateValue) throws ParseException {
		final String OLD_FORMAT = "dd-MMM-yyy";
		final String NEW_FORMAT = "yyyy-MM-dd";
		String lastUpdatedDate = dateValue;
		String oldDateString = lastUpdatedDate;
		String newDateString;
		SimpleDateFormat date_e = new SimpleDateFormat(OLD_FORMAT);
		Date d;
		d = date_e.parse(oldDateString);
		date_e.applyPattern(NEW_FORMAT);

		newDateString = date_e.format(d);
		Date date_converted = new SimpleDateFormat("yyyy-MM-dd").parse(newDateString);
		return new java.sql.Date(date_converted.getTime());
	}

	private java.sql.Date getFormattedDateType3(String dateValue) throws ParseException {
		final String OLD_FORMAT = "dd-MMM-yyyy";
		final String NEW_FORMAT = "yyyy-MM-dd";
		String lastUpdatedDate = dateValue;
		String oldDateString = lastUpdatedDate;
		String newDateString;
		SimpleDateFormat date_e = new SimpleDateFormat(OLD_FORMAT);
		Date d;
		d = date_e.parse(oldDateString);
		date_e.applyPattern(NEW_FORMAT);

		newDateString = date_e.format(d);
		Date date_converted = new SimpleDateFormat("yyyy-MM-dd").parse(newDateString);
		return new java.sql.Date(date_converted.getTime());
	}

	private java.sql.Timestamp getCurrentTimeStamp() throws ParseException {
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		java.util.Date today = new java.util.Date();

		String newPublishedDateString = dateFormatGmt.format(today);
		Date date_converted_publishedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newPublishedDateString);
		return new java.sql.Timestamp(date_converted_publishedDate.getTime());
	}

	private java.sql.Date getCurrentDate() {
		Date date = new Date();
		return new java.sql.Date(date.getTime());
	}

	@Override
	public RoleDetailsDTO getUserRoleDetails(int companyId, String usersso) {
		return jdbcTemplate.query(AbacomConstants.GET_ROLE_DETAILS, new Object[] { usersso, companyId },
				new ResultSetExtractor<RoleDetailsDTO>() {
					public RoleDetailsDTO extractData(ResultSet rs) throws SQLException {
						RoleDetailsDTO dto = new RoleDetailsDTO();
						while (rs.next()) {
							dto.setRoleId(rs.getInt("role_id"));
							dto.setRoleName(null != rs.getString("role_name") ? rs.getString("role_name") : "");
						}
						return dto;
					}
				});
	}

	@Override
	public List<DropDownDTO> getOrdersAndAlottingSubOrganizationFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_SUB_ORGANIZATION, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> subOrgFilterList = new ArrayList<DropDownDTO>();
						try {
							String subOrg = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								subOrg = rs.getString("sub_org");
								if (null != subOrg && !subOrg.equalsIgnoreCase("")) {
									dropDownDTO.setKey(subOrg);
									dropDownDTO.setVal(subOrg);
									subOrgFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Orders&Slotting : Receiving -  Sub Organization FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return subOrgFilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getordersAndSlottingSubOrgRegionFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_SUB_ORG_REGION, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> subOrgRegionFilterList = new ArrayList<DropDownDTO>();
						try {
							String subOrgRegion = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								subOrgRegion = rs.getString("region");
								if (null != subOrgRegion && !subOrgRegion.equalsIgnoreCase("")) {
									dropDownDTO.setKey(subOrgRegion);
									dropDownDTO.setVal(subOrgRegion);
									subOrgRegionFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Orders&Slotting : Receiving -  SubOrg Region FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return subOrgRegionFilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getOrdersAndSlottingTpsTier3Filter() {
		return jdbcTemplate.query(AbacomConstants.GET_TPS_TIERS3, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> tpsTier3FilterList = new ArrayList<DropDownDTO>();
						try {
							String tpsTier3 = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								tpsTier3 = rs.getString("tps_tier_3");
								if (null != tpsTier3 && !tpsTier3.equalsIgnoreCase("")) {
									dropDownDTO.setKey(tpsTier3);
									dropDownDTO.setVal(tpsTier3);
									tpsTier3FilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Orders&Slotting : Receiving -  TPS TIER3 FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return tpsTier3FilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getHorizonFilter() {
		String[] horizon = { "Current Quarter", "Current Year", "Next Year", "6Q Rolling", "3Years" };
		String[] horizonVal = { "Current Quarter", "Current Year", "Next Year", "6Q Rolling", "3 Years" };
		List<DropDownDTO> horizonList = new ArrayList<DropDownDTO>();
		int i = 0;
		while (i < horizon.length) {
			DropDownDTO dropDownDTO = new DropDownDTO();
			dropDownDTO.setKey(horizon[i]);
			dropDownDTO.setVal(horizonVal[i]);
			horizonList.add(dropDownDTO);
			i++;
		}
		return horizonList;
	}

	@Override
	public List<DropDownDTO> getShowByFilter() {
		String[] showby = { "Orders [$]", "CM As Sold [$]", "NovaLT [#]" };
		List<DropDownDTO> showByList = new ArrayList<DropDownDTO>();
		for (String obj : showby) {
			DropDownDTO dropDownDTO = new DropDownDTO();
			dropDownDTO.setKey(obj);
			dropDownDTO.setVal(obj);
			showByList.add(dropDownDTO);
		}
		return showByList;
	}

	@Override
	public List<ProcessHarmonizationDashboardDTO> getProcessHarmonizationDashboard(
			ProcessHarmonizationDashboardParametersDTO phdParametersDto) {
		String query = null;
		if (phdParametersDto.getHorizon().equals("0")) {
			if (phdParametersDto.getDisconnectionToggle().equalsIgnoreCase("Y")) {
				query = AbacomConstants.GET_PHD_WITH_TOGGLE_OVERALL_HORIZON;
			} else {
				query = AbacomConstants.GET_PHD_WITHOUT_TOGGLE_OVERALL_HORIZON;
			}
			return jdbcTemplate.query(query,
					new Object[] { phdParametersDto.getQmiCategory(), phdParametersDto.getQmiCategory(),
							phdParametersDto.getSlotInsightProposed(), phdParametersDto.getSlotInsightProposed(),
							phdParametersDto.getSlotRequestType(), phdParametersDto.getSlotRequestType(),
							phdParametersDto.getBudgetaryType(), phdParametersDto.getBudgetaryType(),
							phdParametersDto.getForecastException(), phdParametersDto.getForecastException() },
					new ResultSetExtractor<List<ProcessHarmonizationDashboardDTO>>() {
						public List<ProcessHarmonizationDashboardDTO> extractData(ResultSet rs) throws SQLException {
							List<ProcessHarmonizationDashboardDTO> processHarmonizationDashboardList = new ArrayList<ProcessHarmonizationDashboardDTO>();
							while (rs.next()) {
								ProcessHarmonizationDashboardDTO processHarmonizationDashboardDTO = new ProcessHarmonizationDashboardDTO();
								processHarmonizationDashboardDTO.setOpptyNumber(
										null != rs.getString("oppty_num") ? rs.getString("oppty_num") : "");
								processHarmonizationDashboardDTO.setOpptyName(
										null != rs.getString("oppty_name_dm") ? rs.getString("oppty_name_dm") : "");
								processHarmonizationDashboardDTO.setQmiCatgeory(
										null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
								processHarmonizationDashboardDTO.setSlotRequestType(
										null != rs.getString("slot_request_type") ? rs.getString("slot_request_type")
												: "");
								processHarmonizationDashboardDTO.setBusinessRelease(
										null != rs.getString("business_release") ? rs.getString("business_release")
												: "");
								processHarmonizationDashboardDTO.setSlotConfirmation(
										null != rs.getString("slot_confirmation") ? rs.getString("slot_confirmation")
												: "");
								processHarmonizationDashboardDTO
										.setEod(getFormattedDateType1(rs.getString("eod_date")));
								processHarmonizationDashboardDTO
										.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
								processHarmonizationDashboardDTO
										.setColorf0(null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
								processHarmonizationDashboardDTO.setTooltipf0(
										null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf1(null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
								processHarmonizationDashboardDTO.setTooltipf1(
										null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf2(null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
								processHarmonizationDashboardDTO.setTooltipf2(
										null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf3(null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
								processHarmonizationDashboardDTO.setTooltipf3(
										null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");
								processHarmonizationDashboardDTO
										.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
												? rs.getString("slot_insight_last_updated")
												: "");
								processHarmonizationDashboardDTO
										.setSlotInsightToBePublished(null != rs.getString("slot_insight_last_published")
												? rs.getString("slot_insight_last_published")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastUpdatedTooltip(
										null != rs.getString("slot_insight_last_updated_tooltip")
												? rs.getString("slot_insight_last_updated_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedColor(
										null != rs.getString("slot_insight_last_saved_color")
												? rs.getString("slot_insight_last_saved_color")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedTooltip(
										null != rs.getString("slot_insight_last_saved_tooltip")
												? rs.getString("slot_insight_last_saved_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastPublishedTooltip(
										null != rs.getString("slot_insight_last_published_tooltip")
												? rs.getString("slot_insight_last_published_tooltip")
												: "");

								processHarmonizationDashboardDTO.setOpptyAmountUsd(
										null != rs.getString("line_amount_usd") ? rs.getString("line_amount_usd") : "");
								processHarmonizationDashboardDTO
										.setNote(null != rs.getString("note") ? rs.getString("note") : "");
								processHarmonizationDashboardDTO
										.setComments(null != rs.getString("coments") ? rs.getString("coments") : "");

								processHarmonizationDashboardList.add(processHarmonizationDashboardDTO);
							}
							return processHarmonizationDashboardList;
						}
					});
		} else {
			if (phdParametersDto.getDisconnectionToggle().equalsIgnoreCase("Y")) {
				query = AbacomConstants.GET_PROCESS_HARMONIZATION_DASHBOARD_WITH_TOGGLE;
			} else {
				query = AbacomConstants.GET_PROCESS_HARMONIZATION_DASHBOARD;
			}
			return jdbcTemplate.query(query,
					new Object[] { phdParametersDto.getHorizon(), phdParametersDto.getHorizon(),
							phdParametersDto.getHorizon(), phdParametersDto.getHorizon(), phdParametersDto.getHorizon(),
							phdParametersDto.getQmiCategory(), phdParametersDto.getQmiCategory(),
							phdParametersDto.getSlotInsightProposed(), phdParametersDto.getSlotInsightProposed(),
							phdParametersDto.getSlotRequestType(), phdParametersDto.getSlotRequestType(),
							phdParametersDto.getBudgetaryType(), phdParametersDto.getBudgetaryType(),
							phdParametersDto.getForecastException(), phdParametersDto.getForecastException() },
					new ResultSetExtractor<List<ProcessHarmonizationDashboardDTO>>() {
						public List<ProcessHarmonizationDashboardDTO> extractData(ResultSet rs) throws SQLException {
							List<ProcessHarmonizationDashboardDTO> processHarmonizationDashboardList = new ArrayList<ProcessHarmonizationDashboardDTO>();
							while (rs.next()) {
								ProcessHarmonizationDashboardDTO processHarmonizationDashboardDTO = new ProcessHarmonizationDashboardDTO();
								processHarmonizationDashboardDTO.setOpptyNumber(
										null != rs.getString("oppty_num") ? rs.getString("oppty_num") : "");
								processHarmonizationDashboardDTO.setOpptyName(
										null != rs.getString("oppty_name_dm") ? rs.getString("oppty_name_dm") : "");
								processHarmonizationDashboardDTO.setQmiCatgeory(
										null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
								processHarmonizationDashboardDTO.setSlotRequestType(
										null != rs.getString("slot_request_type") ? rs.getString("slot_request_type")
												: "");
								processHarmonizationDashboardDTO.setBusinessRelease(
										null != rs.getString("business_release") ? rs.getString("business_release")
												: "");
								processHarmonizationDashboardDTO.setSlotConfirmation(
										null != rs.getString("slot_confirmation") ? rs.getString("slot_confirmation")
												: "");
								processHarmonizationDashboardDTO
										.setEod(getFormattedDateType1(rs.getString("eod_date")));
								processHarmonizationDashboardDTO
										.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
								processHarmonizationDashboardDTO
										.setColorf0(null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
								processHarmonizationDashboardDTO.setTooltipf0(
										null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf1(null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
								processHarmonizationDashboardDTO.setTooltipf1(
										null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf2(null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
								processHarmonizationDashboardDTO.setTooltipf2(
										null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf3(null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
								processHarmonizationDashboardDTO.setTooltipf3(
										null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");
								processHarmonizationDashboardDTO
										.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
												? rs.getString("slot_insight_last_updated")
												: "");
								processHarmonizationDashboardDTO
										.setSlotInsightToBePublished(null != rs.getString("slot_insight_last_published")
												? rs.getString("slot_insight_last_published")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastUpdatedTooltip(
										null != rs.getString("slot_insight_last_updated_tooltip")
												? rs.getString("slot_insight_last_updated_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedColor(
										null != rs.getString("slot_insight_last_saved_color")
												? rs.getString("slot_insight_last_saved_color")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedTooltip(
										null != rs.getString("slot_insight_last_saved_tooltip")
												? rs.getString("slot_insight_last_saved_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastPublishedTooltip(
										null != rs.getString("slot_insight_last_published_tooltip")
												? rs.getString("slot_insight_last_published_tooltip")
												: "");

								processHarmonizationDashboardDTO.setOpptyAmountUsd(
										null != rs.getString("line_amount_usd") ? rs.getString("line_amount_usd") : "");
								processHarmonizationDashboardDTO
										.setNote(null != rs.getString("note") ? rs.getString("note") : "");
								processHarmonizationDashboardDTO
										.setComments(null != rs.getString("coments") ? rs.getString("coments") : "");

								processHarmonizationDashboardList.add(processHarmonizationDashboardDTO);
							}
							return processHarmonizationDashboardList;
						}
					});
		}

	}

	@Override
	public List<ProcessHarmonizationDashboardDownloadExcelDTO> getProcessHarmonizationDashboardDownloadExcel(
			String qmiCategory, String slotInsightProposed, String horizon, String slotRequestType,
			String budgetaryType, String forecastException, String disconnectionToggle) {
		
		String excelQuery = null;
		if (horizon.equals("0")) {
			if (disconnectionToggle.equalsIgnoreCase("Y")) {
				excelQuery = AbacomConstants.GET_PHD_DOWNLOAD_EXCEL_WITH_TOGGLE_OVERALL_HORIZON;
			} else {
				excelQuery = AbacomConstants.GET_PHD_DOWNLOAD_EXCEL_WITHOUT_TOGGLE_OVERALL_HORIZON;
			}
			return jdbcTemplate.query(excelQuery,
					new Object[] { qmiCategory, qmiCategory,
							slotInsightProposed, slotInsightProposed, slotRequestType, slotRequestType, budgetaryType,
							budgetaryType, forecastException, forecastException },
					new ResultSetExtractor<List<ProcessHarmonizationDashboardDownloadExcelDTO>>() {
						public List<ProcessHarmonizationDashboardDownloadExcelDTO> extractData(ResultSet rs)
								throws SQLException {
							List<ProcessHarmonizationDashboardDownloadExcelDTO> phdDownloadExcelList = new ArrayList<>();
							try {
								while (rs.next()) {
									ProcessHarmonizationDashboardDownloadExcelDTO phdexcelDTO = new ProcessHarmonizationDashboardDownloadExcelDTO();
									phdexcelDTO.setColorf0(
											null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
									phdexcelDTO.setTooltipf0(
											null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
									phdexcelDTO.setColorf1(
											null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
									phdexcelDTO.setTooltipf1(
											null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
									phdexcelDTO.setColorf2(
											null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
									phdexcelDTO.setTooltipf2(
											null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
									phdexcelDTO.setColorf3(
											null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
									phdexcelDTO.setTooltipf3(
											null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");
									phdexcelDTO
											.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
													? rs.getString("slot_insight_last_updated")
													: "");
									phdexcelDTO.setSlotInsightToBePublished(
											null != rs.getString("slot_insight_last_published")
													? rs.getString("slot_insight_last_published")
													: "");
									phdexcelDTO.setSlotInsightLastUpdatedTooltip(
											null != rs.getString("slot_insight_last_updated_tooltip")
													? rs.getString("slot_insight_last_updated_tooltip")
													: "");
									phdexcelDTO.setOpptyNumber(
											null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
									phdexcelDTO.setOpptyName(
											null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
									phdexcelDTO.setQmiCatgeory(
											null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
									phdexcelDTO.setSlotRequestType(null != rs.getString("slot_request_type")
											? rs.getString("slot_request_type")
											: "");
									phdexcelDTO.setBusinessRelease(
											null != rs.getString("business_release") ? rs.getString("business_release")
													: "");
									phdexcelDTO.setSlotConfirmation(null != rs.getString("slot_confirmation")
											? rs.getString("slot_confirmation")
											: "");
									phdexcelDTO.setEod(getFormattedDateType1(rs.getString("eod")));
									phdexcelDTO
											.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
									phdexcelDTO.setAlert(null != rs.getString("alert") ? rs.getString("alert") : "");
									phdexcelDTO.setDealBudgetaryType(null != rs.getString("deal_budgetary_type")
											? rs.getString("deal_budgetary_type")
											: "");
									phdexcelDTO.setForecastException(null != rs.getString("forecast_exception")
											? rs.getString("forecast_exception")
											: "");
									phdexcelDTO.setClosed(null != rs.getString("closed") ? rs.getString("closed") : "");
									phdexcelDTO.setRecordTypeName(
											null != rs.getString("record_type_name") ? rs.getString("record_type_name")
													: "");
									phdexcelDTO.setSeapEodAtSubmission(null != rs.getString("seap_eod_at_submission")
											? rs.getString("seap_eod_at_submission")
											: "");
									phdexcelDTO.setSeapOpptyAmountAtSubMission(
											null != rs.getString("seap_oppty_amount_at_submission")
													? rs.getString("seap_oppty_amount_at_submission")
													: "");
									phdexcelDTO.setSeapSosAtSubmission(null != rs.getString("seap_sos_at_submission")
											? rs.getString("seap_sos_at_submission")
											: "");
									phdexcelDTO.setSeapMainReason(
											null != rs.getString("seap_main_reason") ? rs.getString("seap_main_reason")
													: "");
									phdexcelDTO.setSeapSosCurrentStatus(null != rs.getString("seap_sop_current_status")
											? rs.getString("seap_sop_current_status")
											: "");
									phdexcelDTO.setSeapLoggedDate(
											null != rs.getString("seap_logged_date") ? rs.getString("seap_logged_date")
													: "");
									phdexcelDTO.setSeapWfNumber(
											null != rs.getString("seap_wf_number") ? rs.getString("seap_wf_number")
													: "");

									phdexcelDTO.setNote(null != rs.getString("note") ? rs.getString("note") : "");
									phdexcelDTO.setComments(
											null != rs.getString("coments") ? rs.getString("coments") : "");
									phdexcelDTO.setOpptyAmountUsd(
											null != rs.getString("oppty_amount_usd") ? rs.getString("oppty_amount_usd")
													: "");

									phdDownloadExcelList.add(phdexcelDTO);
								}
							} catch (SQLException e) {
								log.error("Error in getting Process Harmonization Dashboard - Excel Details :: "
										+ e.getMessage());
								throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
							}
							return phdDownloadExcelList;
						}
					});
		} else {
			if (disconnectionToggle.equalsIgnoreCase("Y")) {
				excelQuery = AbacomConstants.GET_PHD_DOWNLOAD_EXCEL_WITH_TOGGLE;
			} else {
				excelQuery = AbacomConstants.GET_PHD_DOWNLOAD_EXCEL_WITHOUT_TOGGLE;
			}
			return jdbcTemplate.query(excelQuery,
					new Object[] { horizon, horizon, horizon, horizon, horizon, qmiCategory, qmiCategory,
							slotInsightProposed, slotInsightProposed, slotRequestType, slotRequestType, budgetaryType,
							budgetaryType, forecastException, forecastException },
					new ResultSetExtractor<List<ProcessHarmonizationDashboardDownloadExcelDTO>>() {
						public List<ProcessHarmonizationDashboardDownloadExcelDTO> extractData(ResultSet rs)
								throws SQLException {
							List<ProcessHarmonizationDashboardDownloadExcelDTO> phdDownloadExcelList = new ArrayList<>();
							try {
								while (rs.next()) {
									ProcessHarmonizationDashboardDownloadExcelDTO phdexcelDTO = new ProcessHarmonizationDashboardDownloadExcelDTO();
									phdexcelDTO.setColorf0(
											null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
									phdexcelDTO.setTooltipf0(
											null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
									phdexcelDTO.setColorf1(
											null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
									phdexcelDTO.setTooltipf1(
											null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
									phdexcelDTO.setColorf2(
											null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
									phdexcelDTO.setTooltipf2(
											null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
									phdexcelDTO.setColorf3(
											null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
									phdexcelDTO.setTooltipf3(
											null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");
									phdexcelDTO
											.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
													? rs.getString("slot_insight_last_updated")
													: "");
									phdexcelDTO.setSlotInsightToBePublished(
											null != rs.getString("slot_insight_last_published")
													? rs.getString("slot_insight_last_published")
													: "");
									phdexcelDTO.setSlotInsightLastUpdatedTooltip(
											null != rs.getString("slot_insight_last_updated_tooltip")
													? rs.getString("slot_insight_last_updated_tooltip")
													: "");
									phdexcelDTO.setOpptyNumber(
											null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
									phdexcelDTO.setOpptyName(
											null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
									phdexcelDTO.setQmiCatgeory(
											null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
									phdexcelDTO.setSlotRequestType(null != rs.getString("slot_request_type")
											? rs.getString("slot_request_type")
											: "");
									phdexcelDTO.setBusinessRelease(
											null != rs.getString("business_release") ? rs.getString("business_release")
													: "");
									phdexcelDTO.setSlotConfirmation(null != rs.getString("slot_confirmation")
											? rs.getString("slot_confirmation")
											: "");
									phdexcelDTO.setEod(getFormattedDateType1(rs.getString("eod")));
									phdexcelDTO
											.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
									phdexcelDTO.setAlert(null != rs.getString("alert") ? rs.getString("alert") : "");
									phdexcelDTO.setDealBudgetaryType(null != rs.getString("deal_budgetary_type")
											? rs.getString("deal_budgetary_type")
											: "");
									phdexcelDTO.setForecastException(null != rs.getString("forecast_exception")
											? rs.getString("forecast_exception")
											: "");
									phdexcelDTO.setClosed(null != rs.getString("closed") ? rs.getString("closed") : "");
									phdexcelDTO.setRecordTypeName(
											null != rs.getString("record_type_name") ? rs.getString("record_type_name")
													: "");
									phdexcelDTO.setSeapEodAtSubmission(null != rs.getString("seap_eod_at_submission")
											? rs.getString("seap_eod_at_submission")
											: "");
									phdexcelDTO.setSeapOpptyAmountAtSubMission(
											null != rs.getString("seap_oppty_amount_at_submission")
													? rs.getString("seap_oppty_amount_at_submission")
													: "");
									phdexcelDTO.setSeapSosAtSubmission(null != rs.getString("seap_sos_at_submission")
											? rs.getString("seap_sos_at_submission")
											: "");
									phdexcelDTO.setSeapMainReason(
											null != rs.getString("seap_main_reason") ? rs.getString("seap_main_reason")
													: "");
									phdexcelDTO.setSeapSosCurrentStatus(null != rs.getString("seap_sop_current_status")
											? rs.getString("seap_sop_current_status")
											: "");
									phdexcelDTO.setSeapLoggedDate(
											null != rs.getString("seap_logged_date") ? rs.getString("seap_logged_date")
													: "");
									phdexcelDTO.setSeapWfNumber(
											null != rs.getString("seap_wf_number") ? rs.getString("seap_wf_number")
													: "");

									phdexcelDTO.setNote(null != rs.getString("note") ? rs.getString("note") : "");
									phdexcelDTO.setComments(
											null != rs.getString("coments") ? rs.getString("coments") : "");
									phdexcelDTO.setOpptyAmountUsd(
											null != rs.getString("oppty_amount_usd") ? rs.getString("oppty_amount_usd")
													: "");

									phdDownloadExcelList.add(phdexcelDTO);
								}
							} catch (SQLException e) {
								log.error("Error in getting Process Harmonization Dashboard - Excel Details :: "
										+ e.getMessage());
								throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
							}
							return phdDownloadExcelList;
						}
					});
		}

	}

	@Override
	public List<LastUpdatedOnDTO> getLastUpdatedOn() {
		return jdbcTemplate.query(AbacomConstants.GET_LAST_UPDATED_ON, new Object[] {},
				new ResultSetExtractor<List<LastUpdatedOnDTO>>() {
					public List<LastUpdatedOnDTO> extractData(ResultSet rs) throws SQLException {
						List<LastUpdatedOnDTO> lastUpdatedOnList = new ArrayList<LastUpdatedOnDTO>();
						while (rs.next()) {
							LastUpdatedOnDTO lastUpdatedOnDTO = new LastUpdatedOnDTO();
							lastUpdatedOnDTO.setLastUpdatedOn(getFormattedDateType1(rs.getString("last_updated_on")));
							lastUpdatedOnList.add(lastUpdatedOnDTO);
						}
						return lastUpdatedOnList;
					}
				});
	}

	@Override
	public List<LastPublishedInfoDTO> getLastPublishedInfo() {
		return jdbcTemplate.query(AbacomConstants.GET_LAST_PUBLISHED_DATE, new Object[] {},
				new ResultSetExtractor<List<LastPublishedInfoDTO>>() {
					public List<LastPublishedInfoDTO> extractData(ResultSet rs) throws SQLException {
						List<LastPublishedInfoDTO> lastPublishedInfoList = new ArrayList<LastPublishedInfoDTO>();
						while (rs.next()) {
							LastPublishedInfoDTO lastPublishedInfoDTO = new LastPublishedInfoDTO();
							lastPublishedInfoDTO.setLastPublishedBy(
									null != rs.getString("last_published_by") ? rs.getString("last_published_by") : "");
							lastPublishedInfoDTO.setLastPublishedDate(
									null != rs.getString("last_published_Date") ? rs.getString("last_published_Date")
											: "");
							lastPublishedInfoList.add(lastPublishedInfoDTO);
						}
						return lastPublishedInfoList;
					}
				});
	}

	@Override
	public boolean saveProcessHarmonizationDashboard(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList, String sso) {
		boolean resultFlag = false, resultFlag1 = false, isSaved = false;
		int count = 0;
		Connection con = null;
		try {
			if (AssertUtils.isListNotEmpty(processHarmonizationDashboardList)) {

				resultFlag1 = saveProcessHarmonizationDashboardTable(processHarmonizationDashboardList, sso);
				if (resultFlag1 == true) {
					log.info("Data saved successfully in save table");
				} else {
					log.info("Error in saving data in save table");
				}

				con = jdbcTemplate.getDataSource().getConnection();
				List<SaveProcessHarmonizationDashboardDTO> allSavedDataList = deleteProcessHarmonizationDashboard();

				for (SaveProcessHarmonizationDashboardDTO dto : allSavedDataList) {
					PreparedStatement pstm1 = con
							.prepareStatement(AbacomConstants.BEFORE_INSERT_SELECT_PROCESS_HARMONIZATION_DASHBOARD);
					pstm1.setDate(1, getCurrentDate());
					pstm1.setString(2, dto.getOpptyNumber());
					ResultSet result = pstm1.executeQuery();
					while (result.next()) {
						count = result.getInt(1);
					}
					try {
						if (count == 0) {
							java.sql.Date date_eod;
							java.sql.Date date_s;
							if (dto.getEod() != null) {
								date_eod = getFormattedDateType2(dto.getEod());
							} else {
								date_eod = null;
							}
							if (dto.getSlotStartDate() != null) {
								date_s = getFormattedDateType2(dto.getSlotStartDate());
							} else {
								date_s = null;
							}
							PreparedStatement pstm = con
									.prepareStatement(AbacomConstants.INSERT_PROCESS_HARMONIZATION_DASHBOARD);
							pstm.setString(1, dto.getOpptyNumber());
							pstm.setString(2, dto.getOpptyName());
							pstm.setString(3, dto.getQmiCatgeory());
							pstm.setDate(4, date_eod);
							pstm.setString(5, dto.getSlotRequestType());
							pstm.setString(6, dto.getBusinessRelease());
							pstm.setString(7, dto.getSlotConfirmation());
							pstm.setDate(8, date_s);
							pstm.setString(9, dto.getSlotInsightLastUpdated());
							pstm.setString(10, dto.getSlotInsightToBePublished());
							pstm.setString(11, sso);
							pstm.setTimestamp(12, getCurrentTimeStamp());
							pstm.setString(13, dto.getColorf0());
							pstm.setString(14, dto.getTooltipf0());
							pstm.setString(15, dto.getColorf1());
							pstm.setString(16, dto.getTooltipf1());
							pstm.setString(17, dto.getColorf2());
							pstm.setString(18, dto.getTooltipf2());
							pstm.setString(19, dto.getColorf3());
							pstm.setString(20, dto.getTooltipf3());
							pstm.setString(21, dto.getSlotInsightLastUpdatedTooltip());
							pstm.setString(22, dto.getSlotInsightLastSavedColor());

							if ((dto.getSlotInsightLastSavedTooltip().equals("")
									|| dto.getSlotInsightLastPublishedTooltip().equals(""))
									&& !dto.getComments().equals("")) {
								pstm.setString(23, dto.getComments());
								pstm.setString(24, dto.getComments());
								pstm.setString(25, dto.getNote());
								pstm.setString(26, dto.getComments());
								pstm.setString(27, dto.getOpptyAmountUsd());

							} else {
								pstm.setString(23, dto.getSlotInsightLastSavedTooltip());
								pstm.setString(24, dto.getSlotInsightLastPublishedTooltip());
								pstm.setString(25, dto.getNote());
								pstm.setString(26, dto.getComments());
								pstm.setString(27, dto.getOpptyAmountUsd());
							}

							if (pstm.executeUpdate() > 0) {
								resultFlag = true;
							}

							if (resultFlag == true && resultFlag1 == true) {
								isSaved = true;
							}
						}
					} catch (SQLException e) {
						log.error("sql exception while inserting in process harmonization dashboard:" + e.getMessage());
					}
				}
			}
		} catch (SQLException e) {
			log.error("sql exception while inserting in process harmonization dashboard:" + e.getMessage());
		} catch (Exception e) {
			log.error("something went wrong while saving process harmonization dashboard:" + e.getMessage());
			throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("something went wrong while saving process harmonization dashboard:" + e.getMessage());
				}
			}
		}
		return isSaved;
	}
		

	private List<SaveProcessHarmonizationDashboardDTO> deleteProcessHarmonizationDashboard() {
		java.sql.Date date1 = getCurrentDate();
		boolean resultFlag = false;
		int count = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<SaveProcessHarmonizationDashboardDTO> publishPresentList = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(AbacomConstants.SELECT_PROCESS_HARMONIZATION_DASHBOARD);
			pstm.setDate(1, date1);
			rs = pstm.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
				log.info("Selected " + count + " rows for date :: " + date1);
			}
			resultFlag = true;
	
				 publishPresentList = getAllPresentData();
			
			pstm = con.prepareStatement(AbacomConstants.DELETE_PROCESS_HARMONIZATION_DASHBOARD);
			pstm.setDate(1, date1);
			int result = pstm.executeUpdate();
			if (result > 0) {
				log.info("Deleted " + count + " rows for date :: " + date1);
				resultFlag = true;
			}
		} catch (Exception e) {
			log.error("something went wrong while deleting process harmonization dashboard:" + e.getMessage());
			throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("something went wrong while deleting process harmonization dashboard:" + e.getMessage());
				}
			}
		}
		return publishPresentList;
	}

	private List<SaveProcessHarmonizationDashboardDTO> getPublishTableData() {
		java.sql.Date date1 = getCurrentDate();
			return jdbcTemplate.query(AbacomConstants.SELECT_PHD_PUBLISH_PRESENT_DATA,
					new Object[] {date1},
					new ResultSetExtractor<List<SaveProcessHarmonizationDashboardDTO>>() {
						public List<SaveProcessHarmonizationDashboardDTO> extractData(ResultSet rs) throws SQLException {
							List<SaveProcessHarmonizationDashboardDTO> presentSavedList = new ArrayList<SaveProcessHarmonizationDashboardDTO>();
							while (rs.next()) {
								SaveProcessHarmonizationDashboardDTO processHarmonizationDashboardDTO = new SaveProcessHarmonizationDashboardDTO();
								processHarmonizationDashboardDTO.setOpptyNumber(
										null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
								processHarmonizationDashboardDTO.setOpptyName(
										null != rs.getString("oppty_name_dm") ? rs.getString("oppty_name_dm") : "");
								processHarmonizationDashboardDTO.setQmiCatgeory(
										null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
								processHarmonizationDashboardDTO
								.setEod(getFormattedDateType1(rs.getString("eod")));
								processHarmonizationDashboardDTO.setSlotRequestType(
										null != rs.getString("slot_request_type") ? rs.getString("slot_request_type")
												: "");
								processHarmonizationDashboardDTO.setBusinessRelease(
										null != rs.getString("business_release") ? rs.getString("business_release")
												: "");
								processHarmonizationDashboardDTO.setSlotConfirmation(
										null != rs.getString("slot_confirmation") ? rs.getString("slot_confirmation")
												: "");
								
								processHarmonizationDashboardDTO
										.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
								processHarmonizationDashboardDTO
								.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
										? rs.getString("slot_insight_last_updated")
										: "");
						processHarmonizationDashboardDTO
								.setSlotInsightToBePublished(null != rs.getString("slot_insight_last_published")
										? rs.getString("slot_insight_last_published")
										: "");
								processHarmonizationDashboardDTO
										.setColorf0(null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
								processHarmonizationDashboardDTO.setTooltipf0(
										null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf1(null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
								processHarmonizationDashboardDTO.setTooltipf1(
										null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf2(null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
								processHarmonizationDashboardDTO.setTooltipf2(
										null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
								processHarmonizationDashboardDTO
										.setColorf3(null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
								processHarmonizationDashboardDTO.setTooltipf3(
										null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");
								
								processHarmonizationDashboardDTO.setSlotInsightLastUpdatedTooltip(
										null != rs.getString("proposed_slot_insight_tooltip")
												? rs.getString("proposed_slot_insight_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedColor(
										null != rs.getString("slot_insight_last_saved_color")
												? rs.getString("slot_insight_last_saved_color")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastSavedTooltip(
										null != rs.getString("slot_insight_last_saved_tooltip")
												? rs.getString("slot_insight_last_saved_tooltip")
												: "");
								processHarmonizationDashboardDTO.setSlotInsightLastPublishedTooltip(
										null != rs.getString("slot_insight_to_be_published_tooltip")
												? rs.getString("slot_insight_to_be_published_tooltip")
												: "");
								processHarmonizationDashboardDTO
								.setNote(null != rs.getString("note") ? rs.getString("note") : "");
								
								processHarmonizationDashboardDTO
								.setComments(null != rs.getString("comments") ? rs.getString("comments") : "");

								processHarmonizationDashboardDTO.setOpptyAmountUsd(
										null != rs.getString("oppty_amount_usd") ? rs.getString("oppty_amount_usd") : "");
								

								presentSavedList.add(processHarmonizationDashboardDTO);
							}
							return presentSavedList;
						}
					});
		}

	@Override
	public List<OrdersAndSlottingCardDetailDTO> getOrdersAndSlottingCardDetails(
			OrdersAndSlottingCardDetailParametersDTO ordersAndSlottingCardDetailParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_CARD_DETAIL,
				new Object[] { ordersAndSlottingCardDetailParametersList.getTier(),
						ordersAndSlottingCardDetailParametersList.getHorizon(),
						ordersAndSlottingCardDetailParametersList.getRegion(),
						ordersAndSlottingCardDetailParametersList.getSubOrg(),
						ordersAndSlottingCardDetailParametersList.getToggleIn(),
						ordersAndSlottingCardDetailParametersList.getConfidentailIn() },
				new ResultSetExtractor<List<OrdersAndSlottingCardDetailDTO>>() {
					public List<OrdersAndSlottingCardDetailDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingCardDetailDTO> ordersAndSlottingCardDetailList = new ArrayList<OrdersAndSlottingCardDetailDTO>();
						while (rs.next()) {
							OrdersAndSlottingCardDetailDTO cardDetailsDto = new OrdersAndSlottingCardDetailDTO();
							cardDetailsDto.setOrdersAmountOut(rs.getDouble("orders_amount_out"));
							cardDetailsDto.setOrdersPercentageOut(rs.getDouble("orders_percentage_out"));
							cardDetailsDto.setCmAmountOut(rs.getDouble("cm_amount_out"));
							cardDetailsDto.setCmPercentageOut(rs.getDouble("cm_percentage_out"));
							cardDetailsDto.setNovaItOut(rs.getInt("nova_lt_out"));
							cardDetailsDto.setAerogtOut(rs.getInt("aerogt_out"));
							cardDetailsDto.setvPeriod(
									null != rs.getString("v_period_out") ? rs.getString("v_period_out") : "");
							cardDetailsDto.setCmAsPerc(rs.getDouble("cm_as_perc"));
							
							ordersAndSlottingCardDetailList.add(cardDetailsDto);
						}
						return ordersAndSlottingCardDetailList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingBarGraphDTO> getOrdersAndSlottingBarGraph(
			OrdersAndSlottingBarGraphParametersDTO ordersAndSlottingBarGraphParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_BAR_GRAPH,
				new Object[] { ordersAndSlottingBarGraphParametersList.getpHorizon(),
						ordersAndSlottingBarGraphParametersList.getShowByIn(),
						ordersAndSlottingBarGraphParametersList.getSubOrg(),
						ordersAndSlottingBarGraphParametersList.getSubOrgRegionIn(),
						ordersAndSlottingBarGraphParametersList.getBussinessTierIn(),
						ordersAndSlottingBarGraphParametersList.getToggleIn(),
						ordersAndSlottingBarGraphParametersList.getConfidentialIn() },
				new ResultSetExtractor<List<OrdersAndSlottingBarGraphDTO>>() {
					public List<OrdersAndSlottingBarGraphDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingBarGraphDTO> ordersAndSlottingBarGraphList = new ArrayList<OrdersAndSlottingBarGraphDTO>();
						while (rs.next()) {
							OrdersAndSlottingBarGraphDTO barGraphDto = new OrdersAndSlottingBarGraphDTO();
							barGraphDto
									.setMonthYear(null != rs.getString("month_year") ? rs.getString("month_year") : "");
							barGraphDto.setClosedWonOut(rs.getDouble("closed_won_out"));
							barGraphDto.setUpsideOut(rs.getDouble("upside_out"));
							barGraphDto.setOmitOut(rs.getDouble("omit_out"));
							barGraphDto.setComitAndRiskOut(rs.getDouble("comit_and_risk_out"));
							ordersAndSlottingBarGraphList.add(barGraphDto);
						}
						return ordersAndSlottingBarGraphList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingCommitDTO> getOrdersAndSlottingCommit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_COMMIT,
				new Object[] { ordersAndSlottingCommitParametersList.getSegment(),
						ordersAndSlottingCommitParametersList.getKeyAccount(),
						ordersAndSlottingCommitParametersList.getInstallationCountry(),
						ordersAndSlottingCommitParametersList.getPlantInScope(),
						ordersAndSlottingCommitParametersList.getCtoVsEto(),
						ordersAndSlottingCommitParametersList.getBusinessTier(),
						ordersAndSlottingCommitParametersList.getSubOrg(),
						ordersAndSlottingCommitParametersList.getSubOrgRegion(),
						ordersAndSlottingCommitParametersList.getViewGroupFlow(),
						ordersAndSlottingCommitParametersList.getViewConfidential(),
						ordersAndSlottingCommitParametersList.getHorizon() },
				new ResultSetExtractor<List<OrdersAndSlottingCommitDTO>>() {
					public List<OrdersAndSlottingCommitDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingCommitDTO> ordersAndSlottingCommitList = new ArrayList<OrdersAndSlottingCommitDTO>();
						while (rs.next()) {
							OrdersAndSlottingCommitDTO commitDto = new OrdersAndSlottingCommitDTO();
							commitDto.setSlotInsightLastPublishedOut(
									null != rs.getString("slot_insight_last_published_out")
											? rs.getString("slot_insight_last_published_out")
											: "");
							commitDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							commitDto
									.setRegionOut(null != rs.getString("region_out") ? rs.getString("region_out") : "");
							commitDto.setTpsSegment(
									null != rs.getString("tps_segment") ? rs.getString("tps_segment") : "");
							commitDto
									.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							commitDto.setLineAmountUsd(rs.getDouble("line_amount_usd"));
							commitDto.setOpptyCmPerc(rs.getDouble("oppty_cm_perc"));
							commitDto.setEodBc(null != rs.getString("eod_bc") ? rs.getString("eod_bc") : "");
							commitDto.setNovaLt(rs.getDouble("nova_lt"));
							commitDto.setAeroGt(rs.getDouble("aero_gt"));
							commitDto.setSlotRequestType(
									null != rs.getString("slot_request_type") ? rs.getString("slot_request_type") : "");
							commitDto.setEodPcStatus(
									null != rs.getString("eod_pc_status") ? rs.getString("eod_pc_status") : "");
							commitDto.setEodPc(null != rs.getString("eod_pc") ? rs.getString("eod_pc") : "");
							commitDto
									.setEodPeriod(null != rs.getString("eod_period") ? rs.getString("eod_period") : "");
							commitDto
									.setEpProduct(null != rs.getString("ep_product") ? rs.getString("ep_product") : "");

							commitDto.setSlotInsightTooltip(null != rs.getString("slot_insight_published_tooltip")
									? rs.getString("slot_insight_published_tooltip")
									: "");

							commitDto.setIsConfidential(null != rs.getString("isconfidential")? rs.getString("isconfidential"):"");
							
							ordersAndSlottingCommitList.add(commitDto);
						}
						return ordersAndSlottingCommitList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingUpsideDTO> getOrdersAndSlottingUpside(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_UPSIDE,
				new Object[] { ordersAndSlottingUpsideParametersList.getSegment(),
						ordersAndSlottingUpsideParametersList.getKeyAccount(),
						ordersAndSlottingUpsideParametersList.getInstallationCountry(),
						ordersAndSlottingUpsideParametersList.getPlantInScope(),
						ordersAndSlottingUpsideParametersList.getCtoVsEto(),
						ordersAndSlottingUpsideParametersList.getBusinessTier(),
						ordersAndSlottingUpsideParametersList.getSubOrg(),
						ordersAndSlottingUpsideParametersList.getSubOrgRegion(),
						ordersAndSlottingUpsideParametersList.getViewGroupFlow(),
						ordersAndSlottingUpsideParametersList.getViewConfidential(),
						ordersAndSlottingUpsideParametersList.getHorizon() },
				new ResultSetExtractor<List<OrdersAndSlottingUpsideDTO>>() {
					public List<OrdersAndSlottingUpsideDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingUpsideDTO> ordersAndSlottingUpsideList = new ArrayList<OrdersAndSlottingUpsideDTO>();
						while (rs.next()) {
							OrdersAndSlottingUpsideDTO upsideDto = new OrdersAndSlottingUpsideDTO();
							upsideDto.setSlotInsightLastPublishedOut(
									null != rs.getString("slot_insight_last_published_out")
											? rs.getString("slot_insight_last_published_out")
											: "");
							upsideDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							upsideDto
									.setRegionOut(null != rs.getString("region_out") ? rs.getString("region_out") : "");
							upsideDto.setTpsSegment(
									null != rs.getString("tps_segment") ? rs.getString("tps_segment") : "");
							upsideDto
									.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							upsideDto.setLineAmountUsd(rs.getDouble("line_amount_usd"));
							upsideDto.setOpptyCmPerc(rs.getDouble("oppty_cm_perc"));
							upsideDto.setEodBc(null != rs.getString("eod_bc") ? rs.getString("eod_bc") : "");
							upsideDto.setNovaLt(rs.getDouble("nova_lt"));
							upsideDto.setAeroGt(rs.getDouble("aero_gt"));
							upsideDto.setSlotRequestType(
									null != rs.getString("slot_request_type") ? rs.getString("slot_request_type") : "");
							upsideDto.setEodOc(null != rs.getString("eod_oc") ? rs.getString("eod_oc") : "");
							upsideDto
									.setEpProduct(null != rs.getString("ep_product") ? rs.getString("ep_product") : "");
							upsideDto
									.setEodPeriod(null != rs.getString("eod_period") ? rs.getString("eod_period") : "");
							
							upsideDto.setSlotInsightTooltip(null != rs.getString("slot_insight_published_tooltip")
									? rs.getString("slot_insight_published_tooltip")
									: "");
							upsideDto.setIsConfidential(null != rs.getString("isconfidential")? rs.getString("isconfidential"):"");
							
							ordersAndSlottingUpsideList.add(upsideDto);
						}
						return ordersAndSlottingUpsideList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingOmitDTO> getOrdersAndSlottingOmit(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingOmitParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_OMIT, new Object[] {
				ordersAndSlottingOmitParametersList.getSegment(), ordersAndSlottingOmitParametersList.getKeyAccount(),
				ordersAndSlottingOmitParametersList.getInstallationCountry(),
				ordersAndSlottingOmitParametersList.getPlantInScope(),
				ordersAndSlottingOmitParametersList.getCtoVsEto(),
				ordersAndSlottingOmitParametersList.getBusinessTier(), ordersAndSlottingOmitParametersList.getSubOrg(),
				ordersAndSlottingOmitParametersList.getSubOrgRegion(),
				ordersAndSlottingOmitParametersList.getViewGroupFlow(),
				ordersAndSlottingOmitParametersList.getViewConfidential() },
				new ResultSetExtractor<List<OrdersAndSlottingOmitDTO>>() {
					public List<OrdersAndSlottingOmitDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingOmitDTO> ordersAndSlottingOmitList = new ArrayList<OrdersAndSlottingOmitDTO>();
						while (rs.next()) {
							OrdersAndSlottingOmitDTO omitDto = new OrdersAndSlottingOmitDTO();
							
							omitDto.setSlotInsight(null != rs.getString("slot_insight") ? rs.getString("slot_insight") : "");
							omitDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							omitDto.setRegion(null != rs.getString("region") ? rs.getString("region") : "");
							omitDto.setSegment(null != rs.getString("segment") ? rs.getString("segment") : "");
							omitDto.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							omitDto.setOpptyAmountUsd(
									null != rs.getString("oppty_amount_usd") ? rs.getString("oppty_amount_usd") : "");
							omitDto.setOpptyCmPerc(null != rs.getString("oppty_cm") ? rs.getString("oppty_cm") : "");
							omitDto.setEod(null != rs.getString("eod") ? rs.getString("eod") : "");
							omitDto.setNovaLt(null != rs.getString("nova_lt") ? rs.getString("nova_lt") : "");
							omitDto.setAeroGt(null != rs.getString("aero_gt") ? rs.getString("aero_gt") : "");
							
							ordersAndSlottingOmitList.add(omitDto);
						}
						return ordersAndSlottingOmitList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getOrdersAndSlottingSegementFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_SEGMENT, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> segementList = new ArrayList<DropDownDTO>();
						try {
							String segment = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								segment = rs.getString("segment");
								if (null != segment && !segment.equalsIgnoreCase("")) {
									dropDownDTO.setKey(segment);
									dropDownDTO.setVal(segment);
									segementList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Orders&Slotting : Receiving -  SEGMENT :: " + e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return segementList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getOrdersAndAlottingKeyAccountFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_KEY_ACCOUNT, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> keyAccountList = new ArrayList<DropDownDTO>();
						try {
							String keyAccount = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								keyAccount = rs.getString("key_account");
								if (null != keyAccount && !keyAccount.equalsIgnoreCase("")) {
									dropDownDTO.setKey(keyAccount);
									dropDownDTO.setVal(keyAccount);
									keyAccountList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error(
									"Error in getting Orders&Slotting : Receiving -  KEY ACCOUNT :: " + e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return keyAccountList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getordersAndSlottingInstallationCountryFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_INSTALLATION_COUNTRY, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> installCountryList = new ArrayList<DropDownDTO>();
						try {
							String installCountry = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								installCountry = rs.getString("installation_country");
								if (null != installCountry && !installCountry.equalsIgnoreCase("")) {
									dropDownDTO.setKey(installCountry);
									dropDownDTO.setVal(installCountry);
									installCountryList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Orders&Slotting : Receiving -  INSTALLATION COUNTRY :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return installCountryList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getgetordersAndSlottingCtoVsEtoFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_CTO_VS_ETO, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					@Override
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> cToVsEtoList = new ArrayList<DropDownDTO>();
						try {
							String cToVsEto = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								cToVsEto = rs.getString("ctovseto");
								if (null != cToVsEto && !cToVsEto.equalsIgnoreCase("")) {
									dropDownDTO.setKey(cToVsEto);
									dropDownDTO.setVal(cToVsEto);
									cToVsEtoList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error(
									"Error in getting Orders&Slotting : Receiving -  CTO VS ETO :: " + e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return cToVsEtoList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getPlantInScopeFilter() {
		String[] plantInScope = { "Y", "N" };
		List<DropDownDTO> plantInScopeList = new ArrayList<DropDownDTO>();
		for (String obj : plantInScope) {
			DropDownDTO dropDownDTO = new DropDownDTO();
			dropDownDTO.setKey(obj);
			dropDownDTO.setVal(obj);
			plantInScopeList.add(dropDownDTO);
		}
		return plantInScopeList;
	}

	@Override
	public List<OrdersAndSlottingCountAmtDTO> getOrdersAndSlottingCommitCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingCommitCountAmtParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_COMMIT_COUNT_AMT,
				new Object[] { ordersAndSlottingCommitCountAmtParametersList.getSegment(),
						ordersAndSlottingCommitCountAmtParametersList.getKeyAccount(),
						ordersAndSlottingCommitCountAmtParametersList.getInstallationCountry(),
						ordersAndSlottingCommitCountAmtParametersList.getPlantInScope(),
						ordersAndSlottingCommitCountAmtParametersList.getCtoVsEto(),
						ordersAndSlottingCommitCountAmtParametersList.getBusinessTier(),
						ordersAndSlottingCommitCountAmtParametersList.getSubOrg(),
						ordersAndSlottingCommitCountAmtParametersList.getSubOrgRegion(),
						ordersAndSlottingCommitCountAmtParametersList.getViewGroupFlow(),
						ordersAndSlottingCommitCountAmtParametersList.getViewConfidential(),
						ordersAndSlottingCommitCountAmtParametersList.getHorizon() },
				new ResultSetExtractor<List<OrdersAndSlottingCountAmtDTO>>() {
					public List<OrdersAndSlottingCountAmtDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingCountAmtDTO> ordersAndSlottingCommitCountAmtList = new ArrayList<OrdersAndSlottingCountAmtDTO>();
						while (rs.next()) {
							OrdersAndSlottingCountAmtDTO commitCountAmtDto = new OrdersAndSlottingCountAmtDTO();
							commitCountAmtDto.setTotalAmount(rs.getDouble("total_amount"));
							commitCountAmtDto.setTotalOppty(rs.getInt("total_oppty"));

							ordersAndSlottingCommitCountAmtList.add(commitCountAmtDto);
						}
						return ordersAndSlottingCommitCountAmtList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingCountAmtDTO> getOrdersAndSlottingUpsideCountAmt(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingUpsideCountAmtParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_UPSIDE_COUNT_AMT,
				new Object[] { ordersAndSlottingUpsideCountAmtParametersList.getSegment(),
						ordersAndSlottingUpsideCountAmtParametersList.getKeyAccount(),
						ordersAndSlottingUpsideCountAmtParametersList.getInstallationCountry(),
						ordersAndSlottingUpsideCountAmtParametersList.getPlantInScope(),
						ordersAndSlottingUpsideCountAmtParametersList.getCtoVsEto(),
						ordersAndSlottingUpsideCountAmtParametersList.getBusinessTier(),
						ordersAndSlottingUpsideCountAmtParametersList.getSubOrg(),
						ordersAndSlottingUpsideCountAmtParametersList.getSubOrgRegion(),
						ordersAndSlottingUpsideCountAmtParametersList.getViewGroupFlow(),
						ordersAndSlottingUpsideCountAmtParametersList.getViewConfidential(),
						ordersAndSlottingUpsideCountAmtParametersList.getHorizon() },
				new ResultSetExtractor<List<OrdersAndSlottingCountAmtDTO>>() {
					public List<OrdersAndSlottingCountAmtDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingCountAmtDTO> ordersAndSlottingUpsideCountAmtList = new ArrayList<OrdersAndSlottingCountAmtDTO>();
						while (rs.next()) {
							OrdersAndSlottingCountAmtDTO upsideCountAmtDto = new OrdersAndSlottingCountAmtDTO();
							upsideCountAmtDto.setTotalAmount(rs.getDouble("total_amount"));
							upsideCountAmtDto.setTotalOppty(rs.getInt("total_oppty"));

							ordersAndSlottingUpsideCountAmtList.add(upsideCountAmtDto);
						}
						return ordersAndSlottingUpsideCountAmtList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingMoreInfoIconDTO> getOrdersAndSlottingMoreInfoIcon(String opptyNumber) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_MORE_INFO_ICON,
				new Object[] { opptyNumber }, new ResultSetExtractor<List<OrdersAndSlottingMoreInfoIconDTO>>() {
					public List<OrdersAndSlottingMoreInfoIconDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingMoreInfoIconDTO> ordersAndSlottingUpsideMoreInfoIconList = new ArrayList<OrdersAndSlottingMoreInfoIconDTO>();
						while (rs.next()) {
							OrdersAndSlottingMoreInfoIconDTO moreInfoIconDto = new OrdersAndSlottingMoreInfoIconDTO();
							moreInfoIconDto.setOpptyNumber(
									null != rs.getString("oppty_number_out") ? rs.getString("oppty_number_out") : "");
							moreInfoIconDto.setOpptyName(
									null != rs.getString("oppty_name_out") ? rs.getString("oppty_name_out") : "");
							moreInfoIconDto.setQmiCategory(
									null != rs.getString("qmi_category_out") ? rs.getString("qmi_category_out") : "");
							moreInfoIconDto.setOpptyCmUsd(rs.getDouble("oppty_amount_usd_out"));
							moreInfoIconDto.setEod(null != rs.getString("eod_out") ? rs.getString("eod_out") : "");
							moreInfoIconDto
									.setSalesName(null != rs.getString("psnd_out") ? rs.getString("psnd_out") : "");
							moreInfoIconDto
									.setComopsName(null != rs.getString("pcnd_out") ? rs.getString("pcnd_out") : "");
							moreInfoIconDto
									.setOpptySalesStage(null != rs.getString("oss_out") ? rs.getString("oss_out") : "");
							moreInfoIconDto.setCtoVsEto(
									null != rs.getString("ctovseto_out") ? rs.getString("ctovseto_out") : "");
							moreInfoIconDto
									.setDealBudgedary(null != rs.getString("dbt_out") ? rs.getString("dbt_out") : "");
							moreInfoIconDto
									.setPrimaryIndustry(null != rs.getString("pi_out") ? rs.getString("pi_out") : "");
							moreInfoIconDto
									.setInstCountry(null != rs.getString("ic_out") ? rs.getString("ic_out") : "");
							moreInfoIconDto.setRegion(null != rs.getString("prc_out") ? rs.getString("prc_out") : "");
							moreInfoIconDto
									.setCommAccountType(null != rs.getString("at_out") ? rs.getString("at_out") : "");
							moreInfoIconDto
									.setCommAccountName(null != rs.getString("an_out") ? rs.getString("an_out") : "");
							moreInfoIconDto
									.setKeyAccountName(null != rs.getString("ka_out") ? rs.getString("ka_out") : "");
							moreInfoIconDto
									.setDealQuoteType(null != rs.getString("dqt_out") ? rs.getString("dqt_out") : "");
							moreInfoIconDto.setEpProduct(null != rs.getString("ep_out") ? rs.getString("ep_out") : "");
							moreInfoIconDto
									.setPlantsProduct(null != rs.getString("pp_out") ? rs.getString("pp_out") : "");
							moreInfoIconDto
									.setOpptyCreationDt(null != rs.getString("cdt_out") ? rs.getString("cdt_out") : "");
							moreInfoIconDto.setRfqReceivedDate(
									null != rs.getString("rrdt_out") ? rs.getString("rrdt_out") : "");
							moreInfoIconDto
									.setBidSentDate(null != rs.getString("bsdt_out") ? rs.getString("bsdt_out") : "");
							moreInfoIconDto
									.setBidValidityDays(null != rs.getString("bvd_out") ? rs.getString("bvd_out") : "");
							moreInfoIconDto
									.setDispositionDate(null != rs.getString("dd_out") ? rs.getString("dd_out") : "");
							moreInfoIconDto
									.setSlotStartDate(null != rs.getString("ssd_out") ? rs.getString("ssd_out") : "");
							moreInfoIconDto
									.setP6PrjProducts(null != rs.getString("ps_out") ? rs.getString("ps_out") : "");
							moreInfoIconDto.setPp(null != rs.getString("ppr_out") ? rs.getString("ppr_out") : "");

							moreInfoIconDto
									.setBusinessRelease(null != rs.getString("br_out") ? rs.getString("br_out") : "");
							moreInfoIconDto
									.setSlotRequestType(null != rs.getString("srt_out") ? rs.getString("srt_out") : "");
							moreInfoIconDto.setEnergyTransitionSolution(
									null != rs.getString("ets_out") ? rs.getString("ets_out") : "");
							
							moreInfoIconDto.setBusinessTier3(null != rs.getString("business_tier_3") ? rs.getString("business_tier_3") : "");
							moreInfoIconDto.setSlotConfirmationP6(null != rs.getString("slot_confirmation") ? rs.getString("slot_confirmation") : "");
							moreInfoIconDto.setOpptyId(null != rs.getString("oppty_id") ? rs.getString("oppty_id") : "");
							moreInfoIconDto.setNes(null != rs.getString("nes_classification__c") ? rs.getString("nes_classification__c") : "");
							
							ordersAndSlottingUpsideMoreInfoIconList.add(moreInfoIconDto);
						}
						return ordersAndSlottingUpsideMoreInfoIconList;
					}
				});
	}

	@Override
	public boolean saveOrdersAndSlottingTables(List<OrdersAndSlottingTablesDTO> ordersAndSlottingTablesList,
			String sso) {
		boolean resultFlag = false, deleteFlag = false;
		int count = 0;
		Connection con = null;
		try {
			if (AssertUtils.isListNotEmpty(ordersAndSlottingTablesList)) {
				con = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement pstm1 = con
						.prepareStatement(AbacomConstants.DELETE_ORDERS_AND_SLOTTING_DETAILS);
				if (pstm1.executeUpdate() > 0) {
					count = 1;
				}
				for (OrdersAndSlottingTablesDTO dto : ordersAndSlottingTablesList) {
					try {
						if (count == 0 || count == 1) {
							java.sql.Date date_eod_bc;
							java.sql.Date date_eod_pc;
							if (dto.getEodBc() != null && !dto.getEodBc().equals("")) {
								date_eod_bc = getFormattedDateType3(dto.getEodBc());
							} else {
								date_eod_bc = null;
							}
							if (dto.getEodPc() != null && !dto.getEodPc().equals("")) {
								date_eod_pc = getFormattedDateType3(dto.getEodPc());
							} else {
								date_eod_pc = null;
							}
							PreparedStatement pstm = con
									.prepareStatement(AbacomConstants.INSERT_INTO_ORDERS_AND_SLOTTING_TABLES);
							pstm.setString(1, dto.getSlotInsightLastPublishedOut());
							pstm.setString(2, dto.getOpptyNumber());
							pstm.setString(3, dto.getRegionOut());
							pstm.setString(4, dto.getTpsSegment());
							pstm.setString(5, dto.getOpptyName());
							pstm.setDouble(6, dto.getLineAmountUsd());
							pstm.setDouble(7, dto.getOpptyCmPerc());
							pstm.setDate(8, date_eod_bc);
							pstm.setDouble(9, dto.getNovaLt());
							pstm.setDouble(10, dto.getAeroGt());
							pstm.setString(11, dto.getSlotRequestType());
							pstm.setString(12, dto.getEodPcStatus());
							pstm.setDate(13, date_eod_pc);
							pstm.setString(14, dto.getEodOc());
							pstm.setString(15, sso);
							pstm.setTimestamp(16, getCurrentTimeStamp());
							pstm.setString(17, dto.getEodPeriod());
							pstm.setString(18, dto.getQmiCategory());
							pstm.setString(19, dto.getEpProduct());
							pstm.setString(20, dto.getSlotInsightTooltip());
							if (pstm.executeUpdate() > 0) {
								resultFlag = true;
							}
						}
					} catch (Exception e) {
						log.error("something went wrong while inserting orders and slotting table:" + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			log.error("something went wrong while saving orders and slotting tables" + e.getMessage());
			throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("something went wrong while closing conection:" + e.getMessage());
				}
			}
		}
		return resultFlag;
	}


	@Override
	public List<OrdersAndSlottingPieChartDTO> getOrdersAndSlottingPieChart(
			OrdersAndSlottingPieChartParametersDTO ordersAndSlottingPieChartParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_PIE_CHART,
				new Object[] { ordersAndSlottingPieChartParametersList.getpHorizon(),
						ordersAndSlottingPieChartParametersList.getShowByIn(),
						ordersAndSlottingPieChartParametersList.getSubOrg(),
						ordersAndSlottingPieChartParametersList.getSubOrgRegionIn(),
						ordersAndSlottingPieChartParametersList.getBussinessTierIn(),
						ordersAndSlottingPieChartParametersList.getToggleIn(),
						ordersAndSlottingPieChartParametersList.getConfidentialIn() },
				new ResultSetExtractor<List<OrdersAndSlottingPieChartDTO>>() {
					public List<OrdersAndSlottingPieChartDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingPieChartDTO> ordersAndSlottingPieChartList = new ArrayList<OrdersAndSlottingPieChartDTO>();
						while (rs.next()) {
							OrdersAndSlottingPieChartDTO pieChartDto = new OrdersAndSlottingPieChartDTO();
							pieChartDto.setCommitAtRiskOut(rs.getDouble("commit_at_risk_out"));
							pieChartDto.setUpsideOut(rs.getDouble("upside_out"));
							pieChartDto.setActivePipelinePercOut(rs.getDouble("active_pipeline_perc_out"));
							ordersAndSlottingPieChartList.add(pieChartDto);
						}
						return ordersAndSlottingPieChartList;
					}
				});
	}
	
	@Override
	public List<OrdersAndSlottingCommitExcelDTO> getCommitTableData(String segment, String keyAccount,
			String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg,
			String subOrgRegion, String viewGroupFlow, String viewConfidential, String horizon) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_COMMIT_EXCEL_DATA,
				new Object[] { segment, keyAccount, installationCountry, plantInScope, ctoVsEto, businessTier, subOrg,
						subOrgRegion, viewGroupFlow, viewConfidential, horizon },
				new ResultSetExtractor<List<OrdersAndSlottingCommitExcelDTO>>() {
					public List<OrdersAndSlottingCommitExcelDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingCommitExcelDTO> ordersAndSlottingCommitList = new ArrayList<OrdersAndSlottingCommitExcelDTO>();
						while (rs.next()) {
							OrdersAndSlottingCommitExcelDTO commitDto = new OrdersAndSlottingCommitExcelDTO();

							commitDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							commitDto.setBusinessTier3(null != rs.getString("business_tier_3") ? rs.getString("business_tier_3") : "");
							commitDto
									.setRegion(null != rs.getString("region_out") ? rs.getString("region_out") : "");
							commitDto.setSegment(
									null != rs.getString("tps_segment") ? rs.getString("tps_segment") : "");
							commitDto
									.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							commitDto.setOpptyAmtMmUsd(rs.getDouble("line_amount_usd"));
							commitDto.setOpptyCmPerc(rs.getDouble("oppty_cm_perc"));
							commitDto.setOpptyCmMmUsd(rs.getDouble("line_cm_usd"));
							commitDto
							.setEpProducts(null != rs.getString("ep_product") ? rs.getString("ep_product") : "");
							commitDto.setNovalt(rs.getInt("nova_lt"));
							commitDto.setAerogt(rs.getInt("aero_gt"));
							commitDto.setEodPcStatus(
									null != rs.getString("eod_pc_status") ? rs.getString("eod_pc_status") : "");
							commitDto
							.setEodBaseCasePeriod(null != rs.getString("eod_period") ? rs.getString("eod_period") : "");
							commitDto.setEodBaseCase(null != rs.getString("eod_bc") ? rs.getString("eod_bc") : "");
							commitDto.setOcElig(null != rs.getString("eod_oc") ? rs.getString("eod_oc") : "");
							commitDto.setEodPc(null != rs.getString("eod_pc") ? rs.getString("eod_pc") : "");
							commitDto.setOpptySalesStage(null != rs.getString("oss_out") ? rs.getString("oss_out") : "");
							commitDto.setCtoVsEto(null != rs.getString("ctovseto_out") ? rs.getString("ctovseto_out") : "");
							commitDto.setDealBudgetaryType(null != rs.getString("dbt_out") ? rs.getString("dbt_out") : "");
							commitDto.setDealQuoteType(null != rs.getString("dqt_out") ? rs.getString("dqt_out") : "");
							commitDto.setPrimaryIndustry(null != rs.getString("pi_out") ? rs.getString("pi_out") : "");
							commitDto.setInstCountry(null != rs.getString("ic_out") ? rs.getString("ic_out") : "");
							commitDto.setAccountType(null != rs.getString("at_out") ? rs.getString("at_out") : "");
							commitDto.setAccountName(null != rs.getString("an_out") ? rs.getString("an_out") : "");
							commitDto.setKeyAccount(null != rs.getString("ka_out") ? rs.getString("ka_out") : "");
							commitDto.setEnergyTransitionSolution(null != rs.getString("ets_out") ? rs.getString("ets_out") : "");
							commitDto.setPlantsProduct(null != rs.getString("pp_out") ? rs.getString("pp_out") : "");
							commitDto.setRefqReceivedDate(null != rs.getString("rrdt_out") ? rs.getString("rrdt_out") : "");
							commitDto.setBidSentDate(null != rs.getString("bsdt_out") ? rs.getString("bsdt_out") : "");
							
							String orig = null != rs.getString("slot_insight_published_tooltip")
									? rs.getString("slot_insight_published_tooltip")
									: "";
							if (!orig.equals("")) {
								boolean check = false;
								for (int i = 0; i < orig.length(); i++) {
									if (orig.charAt(i) == ':') {
										check = true;
									}
								}
								String reqTooltip = "";
								if (check) {
									String str1[] = orig.split(" ");
									for (int i = 1; i < str1.length; i++) {
										if (str1[i].equals("Action:")) {
											break;
										} else {
											reqTooltip += str1[i] + " ";
										}
									}
									commitDto.setSlotInsightTooltip(reqTooltip);
								} else {
									commitDto.setSlotInsightTooltip(orig);
								}
							} else {
								commitDto.setSlotInsightTooltip(orig);
							}
							
							commitDto.setNes(null != rs.getString("nes_classification_c") ? rs.getString("nes_classification_c") : "");
							commitDto.setQmiCategory(null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");					
							commitDto.setSalesOrg(null != rs.getString("sub_org_map") ? rs.getString("sub_org_map") : "");
							
							ordersAndSlottingCommitList.add(commitDto);
						}
						return ordersAndSlottingCommitList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingClosedWonDTO> getOrdersAndSlottingClosedWon(
			OrdersAndSlottingCommonForFourTablesDTO ordersAndSlottingClosedWonParametersList) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_CLOSED_WON, new Object[] {
				ordersAndSlottingClosedWonParametersList.getSegment(),
				ordersAndSlottingClosedWonParametersList.getKeyAccount(),
				ordersAndSlottingClosedWonParametersList.getInstallationCountry(),
				ordersAndSlottingClosedWonParametersList.getPlantInScope(),
				ordersAndSlottingClosedWonParametersList.getCtoVsEto(),
				ordersAndSlottingClosedWonParametersList.getBusinessTier(),
						ordersAndSlottingClosedWonParametersList.getSubOrg(),
						ordersAndSlottingClosedWonParametersList.getSubOrgRegion(),
						ordersAndSlottingClosedWonParametersList.getViewGroupFlow(),
						ordersAndSlottingClosedWonParametersList.getViewConfidential(),
						ordersAndSlottingClosedWonParametersList.getHorizon() },
				new ResultSetExtractor<List<OrdersAndSlottingClosedWonDTO>>() {
					public List<OrdersAndSlottingClosedWonDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingClosedWonDTO> ordersAndSlottingClosedWonList = new ArrayList<OrdersAndSlottingClosedWonDTO>();
						while (rs.next()) {
							OrdersAndSlottingClosedWonDTO closedWonDto = new OrdersAndSlottingClosedWonDTO();

							closedWonDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							
							String regionValue = rs.getString("region_out");
							if (regionValue != null) {
								if (containsComma(regionValue)) {
									closedWonDto.setRegion("");
								} else {
									closedWonDto.setRegion(regionValue);
								}
							} else {
								closedWonDto.setRegion("");
							}

							closedWonDto
									.setSegment(null != rs.getString("tps_segment") ? rs.getString("tps_segment") : "");
							closedWonDto
									.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							closedWonDto.setQ1Amount$(rs.getDouble("q1_orders_out"));
							closedWonDto.setQ2Amount$(rs.getDouble("q2_orders_out"));
							closedWonDto.setQ3Amount$(rs.getDouble("q3_orders_out"));
							closedWonDto.setQ4Amount$(rs.getDouble("q4_orders_out"));
							closedWonDto.setQ1Cm$(rs.getDouble("q1_cm_out"));
							closedWonDto.setQ2Cm$(rs.getDouble("q2_cm_out"));
							closedWonDto.setQ3Cm$(rs.getDouble("q3_cm_out"));
							closedWonDto.setQ4Cm$(rs.getDouble("q4_cm_out"));

							ordersAndSlottingClosedWonList.add(closedWonDto);
						}
						return ordersAndSlottingClosedWonList;
					}
				});
	}

	private static boolean containsComma(String stringValue) {
		boolean result = false;
		int len = stringValue.length();
		for (int i = 0; i < len; i++) {
			char c = stringValue.charAt(i);
			if (c == ',') {
				result = true;
				break;
			}
		}
		return result;
	}
	
	@Override
	public List<LastPublishedInfoForOrdersAndSlotDTO> getLastPublishedInfoForOrdersAndSlot() {
		return jdbcTemplate.query(AbacomConstants.GET_LAST_PUBLISHED_DATE_FOR_ORDERS_AND_SLOT, new Object[] {},
				new ResultSetExtractor<List<LastPublishedInfoForOrdersAndSlotDTO>>() {
					public List<LastPublishedInfoForOrdersAndSlotDTO> extractData(ResultSet rs) throws SQLException {
						List<LastPublishedInfoForOrdersAndSlotDTO> lastPublishedInfoList = new ArrayList<LastPublishedInfoForOrdersAndSlotDTO>();
						while (rs.next()) {
							LastPublishedInfoForOrdersAndSlotDTO lastPublishedInfoDTO = new LastPublishedInfoForOrdersAndSlotDTO();
							lastPublishedInfoDTO.setLastPublishedDate(
									null != rs.getString("last_published_date") ? rs.getString("last_published_date")
											: "");
							lastPublishedInfoDTO.setLastPublishedBy(
									null != rs.getString("last_published_by") ? rs.getString("last_published_by") : "");
							lastPublishedInfoList.add(lastPublishedInfoDTO);
						}
						return lastPublishedInfoList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingClosedWonMoreInfoDTO> getOrdersAndSlottingClosedWonMoreInfo(String opptyNumber) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_CLOSED_WON_MORE_INFO,
				new Object[] { opptyNumber }, new ResultSetExtractor<List<OrdersAndSlottingClosedWonMoreInfoDTO>>() {
					public List<OrdersAndSlottingClosedWonMoreInfoDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingClosedWonMoreInfoDTO> moreInfoList = new ArrayList<OrdersAndSlottingClosedWonMoreInfoDTO>();
						while (rs.next()) {
							OrdersAndSlottingClosedWonMoreInfoDTO moreInfoDto = new OrdersAndSlottingClosedWonMoreInfoDTO();
							moreInfoDto.setOpptyNumberOut(
									null != rs.getString("oppty_number_out") ? rs.getString("oppty_number_out") : "");
							moreInfoDto.setOpptyNameOut(
									null != rs.getString("oppty_name_out") ? rs.getString("oppty_name_out") : "");
							moreInfoDto.setQmiCategoryOut(
									null != rs.getString("qmi_category_out") ? rs.getString("qmi_category_out") : "");
							if (rs.getString("booked_amount_usd_out").equals("0") || rs.getString("booked_amount_usd_out").equals("0.00")) {
								moreInfoDto.setBookedAmountUsdOut("");
							} else {
								moreInfoDto.setBookedAmountUsdOut(null != rs.getString("booked_amount_usd_out")
										? rs.getString("booked_amount_usd_out")
										: "");
							}
							if (rs.getString("booked_cm_usd_out").equals("0") || rs.getString("booked_cm_usd_out").equals("0.00")) {
								moreInfoDto.setBookedCmUsdOut("");
							} else {
								moreInfoDto.setBookedCmUsdOut(
										null != rs.getString("booked_cm_usd_out") ? rs.getString("booked_cm_usd_out")
												: "");
							}

							if (rs.getString("booked_cm_perc_out").equals("0") || rs.getString("booked_cm_perc_out").equals("0.00")) {
								moreInfoDto.setBookedCmPercOut("");
							} else {
								moreInfoDto.setBookedCmPercOut(
										null != rs.getString("booked_cm_perc_out") ? rs.getString("booked_cm_perc_out")
												: "");
							}

							moreInfoDto.setBookYyQqOut(
									null != rs.getString("book_yy_qq_out") ? rs.getString("book_yy_qq_out") : "");

							if (rs.getString("oppty_amount_usd_out").equals("0.00") || rs.getString("oppty_amount_usd_out").equals("0")) {
								moreInfoDto.setOpptyAmountUsdOut("");
							} else {
								moreInfoDto.setOpptyAmountUsdOut(null != rs.getString("oppty_amount_usd_out")
										? rs.getString("oppty_amount_usd_out")
										: "");
							}
							if (rs.getString("oppty_cm_usd_out").equals("0.00") || rs.getString("oppty_cm_usd_out").equals("0")) {
								moreInfoDto.setOpptyCmUsdOut("");
							} else {
								moreInfoDto.setOpptyCmUsdOut(
										null != rs.getString("oppty_cm_usd_out") ? rs.getString("oppty_cm_usd_out")
												: "");
							}
							if (rs.getString("oppty_cm_perc_out").equals("0.00") || rs.getString("oppty_cm_perc_out").equals("0")) {
								moreInfoDto.setOpptyCmPercOut("");
							} else {
								moreInfoDto.setOpptyCmPercOut(
										null != rs.getString("oppty_cm_perc_out") ? rs.getString("oppty_cm_perc_out")
												: "");
							}

							moreInfoDto.setEodOut(null != rs.getString("eod_out") ? rs.getString("eod_out") : "");
							moreInfoDto.setChangeOrderOut(
									null != rs.getString("change_order_out") ? rs.getString("change_order_out") : "");
							moreInfoDto.setBookedMonth(
									null != rs.getString("bookedmonth") ? rs.getString("bookedmonth") : "");
							moreInfoDto.setTpsTier3(null != rs.getString("tier_3") ? rs.getString("tier_3") : "");
							moreInfoDto.setOpptyId(null != rs.getString("oppty_id_out") ? rs.getString("oppty_id_out") : "");
							
							if (moreInfoDto.getBookedAmountUsdOut().equals("")
									&& moreInfoDto.getBookedCmUsdOut().equals("")
									&& moreInfoDto.getBookedCmPercOut().equals("")
									&& moreInfoDto.getOpptyAmountUsdOut().equals("")
									&& moreInfoDto.getOpptyCmPercOut().equals("")
									&& moreInfoDto.getOpptyCmUsdOut().equals("")) {
								continue;
							} else {
								moreInfoList.add(moreInfoDto);
							}
							
						}
						return moreInfoList;
					}
				});
	}

	@Override
	public boolean saveProcessHarmonizationDashboardTable(
			List<SaveProcessHarmonizationDashboardDTO> processHarmonizationDashboardList, String sso) {
		boolean resultFlag = false, deleteFlag = false;
		int count = 0;
        List<SaveProcessHarmonizationDashboardDTO> newList = new ArrayList<SaveProcessHarmonizationDashboardDTO>();
		Connection con = null;
		try {
			if (AssertUtils.isListNotEmpty(processHarmonizationDashboardList)) {
				con = jdbcTemplate.getDataSource().getConnection();

				List<SaveProcessHarmonizationDashboardDTO> listOfPresentData = getAllPresentData();
				List<String> opptyNumList = listOfPresentData.stream()
						.map(SaveProcessHarmonizationDashboardDTO::getOpptyNumber).collect(Collectors.toList());

				for (SaveProcessHarmonizationDashboardDTO saveDto : processHarmonizationDashboardList) {
					newList.add(saveDto);
					for(int i = 0; i<listOfPresentData.size();i++) {
						if(saveDto.getOpptyNumber().equals(listOfPresentData.get(i).getOpptyNumber())) {
							listOfPresentData.remove(i);
							i--;
						}
					}
				}
				
					if(!listOfPresentData.isEmpty()) {
						newList.addAll(listOfPresentData);
					}

				PreparedStatement pstm1 = con
						.prepareStatement(AbacomConstants.DELETE_PROCESS_HARMONIZATION_DASHBOARD_TABLE);
				if (pstm1.executeUpdate() > 0) {
					count = 1;
				}
				for (SaveProcessHarmonizationDashboardDTO dto : newList) {
					try {
						if (count == 1 || count == 0) {
							java.sql.Date date_eod;
							java.sql.Date date_s;
							if (dto.getEod() != null) {
								date_eod = getFormattedDateType2(dto.getEod());
							} else {
								date_eod = null;
							}
							if (dto.getSlotStartDate() != null) {
								date_s = getFormattedDateType2(dto.getSlotStartDate());
							} else {
								date_s = null;
							}
							PreparedStatement pstm = con
									.prepareStatement(AbacomConstants.INSERT_PROCESS_HARMONIZATION_DASHBOARD_TABLE);
							
							pstm.setString(1, dto.getOpptyNumber());
							pstm.setString(2, dto.getOpptyName());
							pstm.setString(3, dto.getQmiCatgeory());
							pstm.setDate(4, date_eod);
							pstm.setString(5, dto.getSlotRequestType());
							pstm.setString(6, dto.getBusinessRelease());
							pstm.setString(7, dto.getSlotConfirmation());
							pstm.setDate(8, date_s);
							pstm.setString(9, dto.getSlotInsightLastUpdated());
							pstm.setString(10, dto.getSlotInsightToBePublished());
							pstm.setString(11, sso);
							pstm.setTimestamp(12, getCurrentTimeStamp());
							pstm.setString(13, dto.getColorf0());
							pstm.setString(14, dto.getTooltipf0());
							pstm.setString(15, dto.getColorf1());
							pstm.setString(16, dto.getTooltipf1());
							pstm.setString(17, dto.getColorf2());
							pstm.setString(18, dto.getTooltipf2());
							pstm.setString(19, dto.getColorf3());
							pstm.setString(20, dto.getTooltipf3());
							pstm.setString(21, dto.getSlotInsightLastUpdatedTooltip());
							pstm.setString(22, dto.getSlotInsightLastSavedColor());

							if ((dto.getSlotInsightLastSavedTooltip().equals("")
									|| dto.getSlotInsightLastPublishedTooltip().equals(""))
									&& !dto.getComments().equals("")) {
								pstm.setString(23, dto.getComments());
								pstm.setString(24, dto.getComments());
								pstm.setString(25, dto.getNote());
								pstm.setString(26, dto.getComments());
								pstm.setString(27, dto.getOpptyAmountUsd());

							} else {
								pstm.setString(23, dto.getSlotInsightLastSavedTooltip());
								pstm.setString(24, dto.getSlotInsightLastPublishedTooltip());
								pstm.setString(25, dto.getNote());
								pstm.setString(26, dto.getComments());
								pstm.setString(27, dto.getOpptyAmountUsd());
							}

							if (pstm.executeUpdate() > 0) {
								resultFlag = true;
							}
						}
					} catch (SQLException e) {
						log.error("sql exception while inserting in process harmonization dashboard table:"
								+ e.getMessage());
					}
				}
			}
		} catch (SQLException e) {
			log.error("sql exception while inserting in process harmonization dashboard table:" + e.getMessage());
		} catch (Exception e) {
			log.error("something went wrong while saving process harmonization dashboard table:" + e.getMessage());
			throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("something went wrong while saving process harmonization dashboard table:"
							+ e.getMessage());
				}
			}
		}
		return resultFlag;
	}

	private List<SaveProcessHarmonizationDashboardDTO> getAllPresentData() {
		return jdbcTemplate.query(AbacomConstants.SELECT_PHD_PRESENT_DATA, new Object[] {},
				new ResultSetExtractor<List<SaveProcessHarmonizationDashboardDTO>>() {
					public List<SaveProcessHarmonizationDashboardDTO> extractData(ResultSet rs) throws SQLException {
						List<SaveProcessHarmonizationDashboardDTO> presentSavedList = new ArrayList<SaveProcessHarmonizationDashboardDTO>();
						while (rs.next()) {
							SaveProcessHarmonizationDashboardDTO processHarmonizationDashboardDTO = new SaveProcessHarmonizationDashboardDTO();
							processHarmonizationDashboardDTO.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							processHarmonizationDashboardDTO.setOpptyName(
									null != rs.getString("oppty_name_dm") ? rs.getString("oppty_name_dm") : "");
							processHarmonizationDashboardDTO.setQmiCatgeory(
									null != rs.getString("qmi_category") ? rs.getString("qmi_category") : "");
							processHarmonizationDashboardDTO.setEod(getFormattedDateType1(rs.getString("eod")));
							processHarmonizationDashboardDTO.setSlotRequestType(
									null != rs.getString("slot_request_type") ? rs.getString("slot_request_type") : "");
							processHarmonizationDashboardDTO.setBusinessRelease(
									null != rs.getString("business_release") ? rs.getString("business_release") : "");
							processHarmonizationDashboardDTO.setSlotConfirmation(
									null != rs.getString("slot_confirmation") ? rs.getString("slot_confirmation") : "");

							processHarmonizationDashboardDTO
									.setSlotStartDate(getFormattedDateType1(rs.getString("slot_start_date")));
							processHarmonizationDashboardDTO
									.setSlotInsightLastUpdated(null != rs.getString("slot_insight_last_updated")
											? rs.getString("slot_insight_last_updated")
											: "");
							processHarmonizationDashboardDTO
									.setSlotInsightToBePublished(null != rs.getString("slot_insight_last_published")
											? rs.getString("slot_insight_last_published")
											: "");
							processHarmonizationDashboardDTO
									.setColorf0(null != rs.getString("f0_color") ? rs.getString("f0_color") : "");
							processHarmonizationDashboardDTO
									.setTooltipf0(null != rs.getString("f0_tooltip") ? rs.getString("f0_tooltip") : "");
							processHarmonizationDashboardDTO
									.setColorf1(null != rs.getString("f1_color") ? rs.getString("f1_color") : "");
							processHarmonizationDashboardDTO
									.setTooltipf1(null != rs.getString("f1_tooltip") ? rs.getString("f1_tooltip") : "");
							processHarmonizationDashboardDTO
									.setColorf2(null != rs.getString("f2_color") ? rs.getString("f2_color") : "");
							processHarmonizationDashboardDTO
									.setTooltipf2(null != rs.getString("f2_tooltip") ? rs.getString("f2_tooltip") : "");
							processHarmonizationDashboardDTO
									.setColorf3(null != rs.getString("f3_color") ? rs.getString("f3_color") : "");
							processHarmonizationDashboardDTO
									.setTooltipf3(null != rs.getString("f3_tooltip") ? rs.getString("f3_tooltip") : "");

							processHarmonizationDashboardDTO.setSlotInsightLastUpdatedTooltip(
									null != rs.getString("proposed_slot_insight_tooltip")
											? rs.getString("proposed_slot_insight_tooltip")
											: "");
							processHarmonizationDashboardDTO
									.setSlotInsightLastSavedColor(null != rs.getString("slot_insight_last_saved_color")
											? rs.getString("slot_insight_last_saved_color")
											: "");
							processHarmonizationDashboardDTO.setSlotInsightLastSavedTooltip(
									null != rs.getString("slot_insight_last_saved_tooltip")
											? rs.getString("slot_insight_last_saved_tooltip")
											: "");
							processHarmonizationDashboardDTO.setSlotInsightLastPublishedTooltip(
									null != rs.getString("slot_insight_to_be_published_tooltip")
											? rs.getString("slot_insight_to_be_published_tooltip")
											: "");
							processHarmonizationDashboardDTO
									.setNote(null != rs.getString("note") ? rs.getString("note") : "");

							processHarmonizationDashboardDTO
									.setComments(null != rs.getString("comments") ? rs.getString("comments") : "");

							processHarmonizationDashboardDTO.setOpptyAmountUsd(
									null != rs.getString("oppty_amount_usd") ? rs.getString("oppty_amount_usd") : "");

							presentSavedList.add(processHarmonizationDashboardDTO);
						}
						return presentSavedList;
					}
				});
	}

	@Override
	public List<OrdersAndSlottingClosedWonExcelDTO> getClosedWonTableData(String segment, String keyAccount,
			String installationCountry, String plantInScope, String ctoVsEto, String businessTier, String subOrg,
			String subOrgRegion, String viewGroupFlow, String viewConfidential, String horizon) {
		return jdbcTemplate.query(AbacomConstants.SELECT_ORDERS_AND_SLOTTING_CLOSED_WON,
				new Object[] { segment, keyAccount, installationCountry, plantInScope, ctoVsEto, businessTier, subOrg,
						subOrgRegion, viewGroupFlow, viewConfidential, horizon },
				new ResultSetExtractor<List<OrdersAndSlottingClosedWonExcelDTO>>() {
					public List<OrdersAndSlottingClosedWonExcelDTO> extractData(ResultSet rs) throws SQLException {
						List<OrdersAndSlottingClosedWonExcelDTO> ordersAndSlottingCommitList = new ArrayList<OrdersAndSlottingClosedWonExcelDTO>();
						while (rs.next()) {
							OrdersAndSlottingClosedWonExcelDTO closedWonDto = new OrdersAndSlottingClosedWonExcelDTO();
							closedWonDto.setOpptyNumber(
									null != rs.getString("oppty_number") ? rs.getString("oppty_number") : "");
							closedWonDto
									.setRegion(null != rs.getString("region_out") ? rs.getString("region_out") : "");
							closedWonDto
									.setSegment(null != rs.getString("tps_segment") ? rs.getString("tps_segment") : "");
							closedWonDto
									.setOpptyName(null != rs.getString("oppty_name") ? rs.getString("oppty_name") : "");
							closedWonDto.setQ1Amount$(rs.getDouble("q1_orders_out"));
							closedWonDto.setQ2Amount$(rs.getDouble("q2_orders_out"));
							closedWonDto.setQ3Amount$(rs.getDouble("q3_orders_out"));
							closedWonDto.setQ4Amount$(rs.getDouble("q4_orders_out"));
							closedWonDto.setQ1Cm$(rs.getDouble("q1_cm_out"));
							closedWonDto.setQ2Cm$(rs.getDouble("q2_cm_out"));
							closedWonDto.setQ3Cm$(rs.getDouble("q3_cm_out"));
							closedWonDto.setQ4Cm$(rs.getDouble("q4_cm_out"));

							ordersAndSlottingCommitList.add(closedWonDto);
						}
						return ordersAndSlottingCommitList;
					}
				});
	}

	public String getOrdersAndSlottingDmUpdatedOn() {
		return jdbcTemplate.query(AbacomConstants.DM_UPDATED_ON, new Object[] {}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException {
				String dmUpdatedOn = null;
				while (rs.next()) {
					dmUpdatedOn = null != rs.getString("dm_updated_on") ? rs.getString("dm_updated_on") : "";
				}
				return dmUpdatedOn;
			}
		});
	}

	@Override
	public String getOrdersAndSlottingObpUpdatedOn() {
		return jdbcTemplate.query(AbacomConstants.OBP_UPDATED_ON, new Object[] {}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException {
				String obpUpdatedOn = null;
				while (rs.next()) {
					obpUpdatedOn = null != rs.getString("obp_updated_on") ? rs.getString("obp_updated_on") : "";
				}
				return obpUpdatedOn;
			}
		});
	}

	@Override
	public String getOrdersAndSlottingP6UpdatedOn() {
		return jdbcTemplate.query(AbacomConstants.P6_UPDATED_ON, new Object[] {}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException {
				String p6UpdatedOn = null;
				while (rs.next()) {
					p6UpdatedOn = null != rs.getString("p6_updated_on") ? rs.getString("p6_updated_on") : "";
				}
				return p6UpdatedOn;
			}
		});
	}

	@Override
	public String getOrdersAndSlottingSeapUpdatedOn() {
		return jdbcTemplate.query(AbacomConstants.SEAP_UPDATED_ON, new Object[] {}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException {
				String seapUpdatedOn = null;
				while (rs.next()) {
					seapUpdatedOn = null != rs.getString("seap_updated_on") ? rs.getString("seap_updated_on") : "";
				}
				return seapUpdatedOn;
			}
		});
	}

	@Override
	public String getOrdersAndSlottingLastPublishedOn() {
		return jdbcTemplate.query(AbacomConstants.GET_ORDERS_SLOTTING_LAST_PUBLISHED_ON, new Object[] {},
				new ResultSetExtractor<String>() {
					public String extractData(ResultSet rs) throws SQLException {
						String lastPublishedOn=  null;
						while (rs.next()) {
							lastPublishedOn = 
									(null != rs.getString("published_on") ? rs.getString("published_on") : "");
						}
						return lastPublishedOn;
					}
				});
	}

	@Override
	public List<DropDownDTO> getProcessHarmonizationDashboardQmiFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_QMI_CATEGORY, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> QmiCategoryFilterList = new ArrayList<DropDownDTO>();
						try {
							String qmiCategory = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								qmiCategory = rs.getString("qmi_category");
								if (null != qmiCategory && !qmiCategory.equalsIgnoreCase("")) {
									dropDownDTO.setKey(qmiCategory);
									dropDownDTO.setVal(qmiCategory);
									QmiCategoryFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Process Harmonization Dashboard : Receiving - QMI CATEGORY FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return QmiCategoryFilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getProcessHarmonizationDashboardslotRequestFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_SLOT_REQUEST_TYPE, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> slotRequestTypeFilterList = new ArrayList<DropDownDTO>();
						try {
							String slotRequestType = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								slotRequestType = rs.getString("slot_request_type");
								if (null != slotRequestType && !slotRequestType.equalsIgnoreCase("")) {
									dropDownDTO.setKey(slotRequestType);
									dropDownDTO.setVal(slotRequestType);
									slotRequestTypeFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Process Harmonization Dashboard : Receiving - SLOT REQUEST TYPE FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return slotRequestTypeFilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getProcessHarmonizationDashboardBudgetaryFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_BUDGETARY_TYPE, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> budgetaryTypeFilterList = new ArrayList<DropDownDTO>();
						try {
							String budgetaryType = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								budgetaryType = rs.getString("deal_budgetory_type");
								if (null != budgetaryType && !budgetaryType.equalsIgnoreCase("")) {
									dropDownDTO.setKey(budgetaryType);
									dropDownDTO.setVal(budgetaryType);
									budgetaryTypeFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error("Error in getting Process Harmonization Dashboard : Receiving - BUDGETARY TYPE FILTER :: "
									+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return budgetaryTypeFilterList;
					}
				});
	}

	@Override
	public List<DropDownDTO> getProcessHarmonizationDashboardForecastFilter() {
		return jdbcTemplate.query(AbacomConstants.GET_FORECAST_EXCEPTION, new Object[] {},
				new ResultSetExtractor<List<DropDownDTO>>() {
					public List<DropDownDTO> extractData(ResultSet rs) throws SQLException {
						List<DropDownDTO> forecastExceptionFilterList = new ArrayList<DropDownDTO>();
						try {
							String forecastException = null;
							while (rs.next()) {
								DropDownDTO dropDownDTO = new DropDownDTO();
								forecastException = rs.getString("forecast_exception");
								if (null != forecastException && !forecastException.equalsIgnoreCase("")) {
									dropDownDTO.setKey(forecastException);
									dropDownDTO.setVal(forecastException);
									forecastExceptionFilterList.add(dropDownDTO);
								}
							}
						} catch (Exception e) {
							log.error(
									"Error in getting Process Harmonization Dashboard : Receiving - FORECAST EXCEPTION FILTER :: "
											+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return forecastExceptionFilterList;
					}
				});
	}

	@Override
	public Map<String, Object> getCommentsForDifferentIcons() {
		return jdbcTemplate.query(AbacomConstants.GET_ALL_ICONS_COMMENTS, new Object[] {},
				new ResultSetExtractor<Map<String, Object>>() {
					public Map<String, Object> extractData(ResultSet rs) throws SQLException {
						Map<String, Object> iconCommentsMap = new HashMap<String, Object>();

						try {
							while (rs.next()) {

								if (iconCommentsMap.containsKey(rs.getString("attribute_nm"))) {
									DropDownDTO dropDownDTO = new DropDownDTO();
									String iconKey = rs.getString("attribute_nm");
									List<DropDownDTO> iconCommentsList = (List<DropDownDTO>) iconCommentsMap.get(iconKey);
									String iconComments = rs.getString("attribute_value");
									dropDownDTO.setKey(iconComments);
									dropDownDTO.setVal(iconComments);
									iconCommentsList.add(dropDownDTO);
									iconCommentsMap.put(iconKey, iconCommentsList);
								} else {
									DropDownDTO dropDownDTO = new DropDownDTO();
									String iconColor = rs.getString("attribute_nm");
									List<DropDownDTO> iconCommentsList = new ArrayList<DropDownDTO>();
									String iconComments = rs.getString("attribute_value");
									dropDownDTO.setKey(iconComments);
									dropDownDTO.setVal(iconComments);
									iconCommentsList.add(dropDownDTO);
									iconCommentsMap.put(iconColor, iconCommentsList);
								}
							}
						} catch (Exception e) {
							log.error(
									"Error in getting Process Harmonization Dashboard : Receiving - ICONS COMMENTS :: "
											+ e.getMessage());
							throw new ServerErrorException(ErrorCode.INTERNAL.getResponseStatus(), e);
						}
						return iconCommentsMap;
					}
				});
	}
	
}

