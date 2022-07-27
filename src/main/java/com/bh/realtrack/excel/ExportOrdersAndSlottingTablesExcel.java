package com.bh.realtrack.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bh.realtrack.dto.OrdersAndSlottingClosedWonExcelDTO;
import com.bh.realtrack.dto.OrdersAndSlottingCommitExcelDTO;

/**
 *
 * @author Sweta Kumari
 *
 */
public class ExportOrdersAndSlottingTablesExcel {

	int lWidth = ((int) (70 * 1.14388)) * 256;
	int mWidth = ((int) (40 * 1.14388)) * 256;
	int sWidth = ((int) (22 * 1.14388)) * 256;
	int xsWidth = ((int) (18 * 1.14388)) * 256;

	public XSSFWorkbook downloadOrdersAndSlottingExcel(final XSSFWorkbook workbook,
			List<OrdersAndSlottingCommitExcelDTO> ordersAndSlottingTablesList1) {
		Sheet sheet = workbook.createSheet("Committed");
		sheet.createFreezePane(0, 1);
		CellStyle headStyle = getCellHeadStyle(workbook);
		int rowNum = 1;
		int headerColumnCount = 0;
		int dataColumnCount = 0;
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(0);
		row.setHeightInPoints(20);

		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NUMBER"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SALES ORG"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("BUSINESS TIER 3"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("REGION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SEGMENT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("NES CLASSIFICATION"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NAME"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Oppty AMOUNT MM USD"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY CM%"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY CM MM USD"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("E&P PRODUCTS [DM]"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#NOVALT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#AEROGT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#PLANTS PRODUCT"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD BASE CASE PERIOD [DM]"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD BASE CASE [DM]"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD PC STATUS"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD PC"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY SALES STAGE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("CTO VS ETO"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("DEAL BUDGETARY TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("DEAL QUOTE TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("PRIMARY INDUSTRY"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("INST COUNTRY"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ACCOUNT TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ACCOUNT NAME"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("KEY ACCOUNT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ENERGY TRANSITION SOLUTION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("REFQ RECEIVED DATE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("BID SENT DATE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SLOT INSIGHT"));
		cell.setCellStyle(headStyle);
		
		

		CellStyle bodyStyle = getCellBodyStyle(workbook);

		for (int i = 0; i <= cell.getColumnIndex(); i++) {
			sheet.setColumnWidth(i, xsWidth);
		}
		for (int i = 0; i < ordersAndSlottingTablesList1.size(); i++) {
			row = sheet.createRow(rowNum++);
			dataColumnCount = 0;
			if (ordersAndSlottingTablesList1.get(i).getQmiCategory().equals("commit at risk")) {
				cell = row.createCell(dataColumnCount);
				cell = row.createCell(0);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptyNumber());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getSalesOrg());
				cell.setCellStyle(bodyStyle);				
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getBusinessTier3());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getRegion());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getSegment());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getNes());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptyName());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptyAmtMmUsd());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptyCmPerc());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptyCmMmUsd());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEpProducts());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getNovalt());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getAerogt());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getPlantsProduct());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEodBaseCasePeriod());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEodBaseCase());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEodPcStatus());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEodPc());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getOpptySalesStage());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getCtoVsEto());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getDealBudgetaryType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getDealQuoteType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getPrimaryIndustry());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getInstCountry());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getAccountType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getAccountName());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getKeyAccount());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getEnergyTransitionSolution());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getRefqReceivedDate());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getBidSentDate());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList1.get(i).getSlotInsightTooltip());
				cell.setCellStyle(bodyStyle);

			}
		}
		Footer footer = sheet.getFooter();
		footer.setCenter("BH Confidential");
		return workbook;
	}

	public XSSFWorkbook downloadOrdersAndSlottingExcel1(final XSSFWorkbook workbook,
			List<OrdersAndSlottingCommitExcelDTO> ordersAndSlottingTablesList2) {
		Sheet sheet = workbook.createSheet("Upside");
		sheet.createFreezePane(0, 1);
		CellStyle headStyle = getCellHeadStyle(workbook);
		int rowNum = 1;
		int headerColumnCount = 0;
		int dataColumnCount = 0;
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(0);
		row.setHeightInPoints(20);

		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NUMBER"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SALES ORG"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("BUSINESS TIER 3"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("REGION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SEGMENT"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("NES CLASSIFICATION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NAME"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Oppty AMOUNT MM USD"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY CM%"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY CM MM USD"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("E&P PRODUCTS [DM]"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#NOVALT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#AEROGT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("#PLANTS PRODUCT"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD BASE CASE PERIOD [DM]"));
		cell.setCellStyle(headStyle);
		
		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("EOD BASE CASE [DM]"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OC ELIG"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY SALES STAGE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("CTO VS ETO"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("DEAL BUDGETARY TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("DEAL QUOTE TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("PRIMARY INDUSTRY"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("INST COUNTRY"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ACCOUNT TYPE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ACCOUNT NAME"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("KEY ACCOUNT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("ENERGY TRANSITION SOLUTION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("REFQ RECEIVED DATE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("BID SENT DATE"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SLOT INSIGHT"));
		cell.setCellStyle(headStyle);
		
		
		CellStyle bodyStyle = getCellBodyStyle(workbook);

		for (int i = 0; i <= cell.getColumnIndex(); i++) {
			sheet.setColumnWidth(i, xsWidth);
		}
		for (int i = 0; i < ordersAndSlottingTablesList2.size(); i++) {
			if (ordersAndSlottingTablesList2.get(i).getQmiCategory().equals("upside")) {
				row = sheet.createRow(rowNum++);
				dataColumnCount = 0;

				cell = row.createCell(dataColumnCount);
				cell = row.createCell(0);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptyNumber());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getSalesOrg());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getBusinessTier3());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getRegion());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getSegment());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getNes());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptyName());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptyAmtMmUsd());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptyCmPerc());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptyCmMmUsd());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getEpProducts());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getNovalt());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getAerogt());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getPlantsProduct());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getEodBaseCasePeriod());
				cell.setCellStyle(bodyStyle);
				
				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getEodBaseCase());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOcElig());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getOpptySalesStage());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getCtoVsEto());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getDealBudgetaryType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getDealQuoteType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getPrimaryIndustry());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getInstCountry());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getAccountType());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getAccountName());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getKeyAccount());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getEnergyTransitionSolution());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getRefqReceivedDate());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getBidSentDate());
				cell.setCellStyle(bodyStyle);

				dataColumnCount = dataColumnCount + 1;
				cell = row.createCell(dataColumnCount);
				cell.setCellValue(ordersAndSlottingTablesList2.get(i).getSlotInsightTooltip());
				cell.setCellStyle(bodyStyle);	

			}
		}
		Footer footer = sheet.getFooter();
		footer.setCenter("BH Confidential");
		return workbook;
	}

	public XSSFWorkbook downloadOrdersAndSlottingExcel2(final XSSFWorkbook workbook,
			List<OrdersAndSlottingClosedWonExcelDTO> ordersAndSlottingTablesList3) {
		Sheet sheet = workbook.createSheet("Closed Won");
		sheet.createFreezePane(0, 1);
		CellStyle headStyle = getCellHeadStyle(workbook);
		int rowNum = 1;
		int headerColumnCount = 0;
		int dataColumnCount = 0;
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(0);
		row.setHeightInPoints(20);

		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NUMBER"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("REGION"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("SEGMENT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("OPPTY NAME"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q1 AMOUNT​"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q1 CM"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q2 AMOUNT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q2 CM"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q3 AMOUNT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q3 CM"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q4 AMOUNT"));
		cell.setCellStyle(headStyle);

		headerColumnCount = headerColumnCount + 1;
		cell = row.createCell(headerColumnCount);
		cell.setCellValue(("Q4 CM"));
		cell.setCellStyle(headStyle);

		CellStyle bodyStyle = getCellBodyStyle(workbook);

		for (int i = 0; i <= cell.getColumnIndex(); i++) {
			sheet.setColumnWidth(i, xsWidth);
		}
		for (int i = 0; i < ordersAndSlottingTablesList3.size(); i++) {
			row = sheet.createRow(rowNum++);
			dataColumnCount = 0;

			cell = row.createCell(dataColumnCount);
			cell = row.createCell(0);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getOpptyNumber());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getRegion());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getSegment());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getOpptyName());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ1Amount$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ1Cm$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ2Amount$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ2Cm$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ3Amount$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ3Cm$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ4Amount$());
			cell.setCellStyle(bodyStyle);

			dataColumnCount = dataColumnCount + 1;
			cell = row.createCell(dataColumnCount);
			cell.setCellValue(ordersAndSlottingTablesList3.get(i).getQ4Cm$());
			cell.setCellStyle(bodyStyle);
		}
		Footer footer = sheet.getFooter();
		footer.setCenter("BH Confidential");
		return workbook;
	}

	private CellStyle getCellHeadStyle(final XSSFWorkbook workbook) {
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setVerticalAlignment(VerticalAlignment.TOP);
		setBorderStyle(headStyle);
		headStyle.setFont(getFontHeader(workbook));
		return headStyle;
	}

	private void setBorderStyle(final CellStyle headStyle) {
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setBorderTop(BorderStyle.THIN);
	}

	private Font getFontHeader(final XSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 10);
		font.setFontName("GE Inspira Sans");
		font.setColor(IndexedColors.WHITE.getIndex());
		return font;
	}

	private CellStyle getCellBodyStyle(final XSSFWorkbook workbook) {
		CellStyle bodyStyle = workbook.createCellStyle();
		setBorderStyle(bodyStyle);
		bodyStyle.getWrapText();
		bodyStyle.setFont(getFontContent(workbook));
		return bodyStyle;
	}

	private Font getFontContent(final XSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("GE Inspira Sans");
		font.setColor(IndexedColors.BLACK.getIndex());
		return font;
	}

}
