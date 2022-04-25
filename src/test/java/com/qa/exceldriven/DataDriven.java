package com.qa.exceldriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public ArrayList<String> getData(String testCaseName, String sheetName) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\Library.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		// Defining an ArrayList
		ArrayList<String> a = new ArrayList<String>();

		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> ce = firstRow.cellIterator();
				int k = 0; // Variable to find the column number with header 'TestCases'.
				int column = 0;
				while (ce.hasNext()) {
					Cell value = ce.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k; // Column with header TestCases
					}
					k++;
				}
				//System.out.println(column);

				// Once the required column is identified, scan the entire column to identify
				// 'testCaseName' row

				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equals(testCaseName)) {
						// Value
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							//System.out.println(cv.next().getStringCellValue());
							Cell c = cv.next();
							if (c.getCellTypeEnum() == CellType.STRING) {	// If cell value is String
								a.add(c.getStringCellValue());
							}
							else {
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));	// Converting int to String
							}
						}
					}
				}
			}
		}
		return a;
	}
}