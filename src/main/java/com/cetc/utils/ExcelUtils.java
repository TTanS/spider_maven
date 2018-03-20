package com.cetc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	
	/**
	 * 新建一个excel表格
	 * 
	 * @param savePath
	 *            新建路径
	 * @param name
	 *            表格名字（包括格式，格式为.xlsx）
	 * @param paras
	 *            表格标题
	 * @throws Exception
	 *             异常
	 */
	public static void createExcel(String savePath, String name, String[] paras) throws Exception {
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("商品信息");
		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);
		row.setRowStyle(cellStyle);
		for (int i = 0; i < paras.length; i++) {
			row.createCell(i).setCellValue(paras[i]);
		}
		// 如果文件夹不存在 就新建
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		FileOutputStream fileOut = new FileOutputStream(savePath + name);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 是否覆盖新建表格
	 * 
	 * @param savePath
	 *            新建路径
	 * @param name
	 *            表格名字（包括格式，格式为.xlsx）
	 * @param paras
	 *            表格标题
	 * @param isOverlap
	 *            是否覆盖（true 则覆盖原文件 false则如果原文件存在，不新建）
	 * @throws Exception
	 */
	public static void createExcel(String savePath, String name, String[] paras, Boolean isOverlap) throws Exception {
		File file = new File(savePath + name);
		if (!file.exists()) {
			createExcel(savePath, name, paras);
		} else if (isOverlap.booleanValue()) {
			createExcel(savePath, name, paras);
		}
	}

	public static void createExcelOfData(String[] productData, String filePath) throws Exception {
		FileInputStream fs = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fs);
		Sheet sheet = wb.getSheetAt(0);

		FileOutputStream outFile = new FileOutputStream(filePath);

		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("宋体");
		cellStyle.setFont(font);

		Row row = sheet.createRow((short) sheet.getLastRowNum() + 1);
		row.setRowStyle(cellStyle);
		for (int i = 0; i < productData.length; i++) {
			row.createCell(i).setCellValue(productData[i]);
		}
		outFile.flush();
		wb.write(outFile);
		outFile.close();
	}
}
