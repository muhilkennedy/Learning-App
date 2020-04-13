package com.miniproject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.miniproject.model.User;

/**
 * @author Muhil Kennedy
 * Invoice creation in docx and converted to PDF.
 *
 */
public class InvoiceUtil {
	
	private static final String Key_CustomerName = "#CustomerName";
	private static final String Key_CustomerStreet = "#CustomerStreet";
	private static final String Key_CustomerCity = "#CustomerCity";
	private static final String Key_CustomerPin = "#CustomerPin";
	private static final String Key_CustomerMobile = "#CustomerMobile";
	private static final String Key_CustomerEmail = "#CustomerEmail";
	private static final String Key_OrderId = "#InvoiceNum";
	private static final String Key_OrderDate = "#InvoiceDate";

	public static File generateInvoice(User user) throws Exception {
		File file = new File(
				InvoiceUtil.class.getClassLoader().getResource("template/Invoice-Template.docx").getFile());
		InputStream is = new FileInputStream(file);
		XWPFDocument poiDocx = new XWPFDocument(is);
		// generate map for all prop values.
		// Adress needs to be passed explicitly in future to handle multiple address.
		Map map = generateInvoiceFieldsMap(user);
		// replace props in table.
		XWPFTable productTable = null;
		List<XWPFTable> tables = poiDocx.getTables();
		for (XWPFTable table : tables) {
			scanAndReplaceValueInTable(table, map);
			if(table.getText().contains("QTY")) {
				productTable = table;
			}
		}
		if(productTable == null) {
			throw new Exception("Cannot able to find Product table!");
		}
		//based on ordered items we need to add them
		for(int i=0; i<5 ; i++) {
			XWPFTableRow newRow = productTable.createRow();
			newRow.getCell(0).setText("itemName");
			newRow.getCell(1).setText("itemQty");
			newRow.getCell(2).setText("perCost");
			newRow.getCell(3).setText("total");
		}
		//inserting dummy row for clarity.
		productTable.createRow();
		//calculate sub-total and manipulate taxes and balance due.
		XWPFTableRow subTotalRow = productTable.createRow();
		subTotalRow.getCell(2).setText("SUB-TOTAL");
		subTotalRow.getCell(3).setText("0.0");
		
		XWPFTableRow sgstRow = productTable.createRow();
		sgstRow.getCell(2).setText("SGST");
		sgstRow.getCell(3).setText("5%");
		
		XWPFTableRow cgstRow = productTable.createRow();
		cgstRow.getCell(2).setText("CGST");
		cgstRow.getCell(3).setText("5%");
		
		XWPFTableRow dueBalanceRow = productTable.createRow();
		dueBalanceRow.getCell(2).setText("BALANCE DUE");
		dueBalanceRow.getCell(3).setText("0.0");
		
		File tempFile = new File("/Users/i339628/Desktop/testDoc.docx");
		poiDocx.write(new FileOutputStream(tempFile));
		
		convertDocToPDF(tempFile, new File("/Users/i339628/Desktop/testDoc.pdf"));
		return null;
	}
	
	public static Map<String, String> generateInvoiceFieldsMap(User user) {
		Map<String, String> map = new HashMap<>();
		map.put(Key_CustomerName, user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase());
		map.put(Key_CustomerStreet, user.getAddress().get(0).getStreet());
		map.put(Key_CustomerCity, user.getAddress().get(0).getCity());
		map.put(Key_CustomerPin, user.getAddress().get(0).getPin());
		map.put(Key_CustomerMobile, "Contact : " + user.getMobile());
		map.put(Key_CustomerEmail, "Email-Id : " + user.getEmailId());
		map.put(Key_OrderId, " ");
		String pattern = "MM-dd-yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		map.put(Key_OrderDate, date);
		return map;
	}
	
	public static void scanAndReplaceValueInTable(XWPFTable table, Map<String, String> map) {
		for (XWPFTableRow row : table.getRows()) {
			for (XWPFTableCell cell : row.getTableCells()) {
				String cellContent = cell.getText();
				if (map.containsKey(cellContent)) {
					cell.removeParagraph(0);
					cell.setText(map.get(cellContent));
				}
			}
		}
	}
	
	/**
	 * @param docFile
	 * @param pdfFile
	 * @throws Exception - Docx4jException
	 */
	public static void convertDocToPDF(File docFile, File pdfFile) throws Exception {
		WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
		OutputStream os = new FileOutputStream(pdfFile);
		Docx4J.toPDF(word, os);
	}
}
