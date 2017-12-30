package commonLibs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDriver {

	private InputStream fileReader;
	private OutputStream fileWriter;
	private Workbook excelWorkbook;
	private String excelFileName;

	public ExcelDriver() {
		this.setNull();
	}

	public void createNewExcelWorkbook(String fileName) {
		try {

			fileName = fileName.trim();
			if (fileName.isEmpty()) {
				throw new Exception("No file name specified...");
			}

			if ((new File(fileName)).exists()) {
				throw new Exception("File already Exists");
			}

			if (fileName.toLowerCase().endsWith("xlsx")) {
				excelWorkbook = new XSSFWorkbook();

			} else if (fileName.toLowerCase().endsWith("xls")) {
				excelWorkbook = new HSSFWorkbook();

			} else {
				throw new Exception("Invalid File Extension...");
			}

			fileWriter = new FileOutputStream(fileName);
			excelWorkbook.write(fileWriter);
			fileWriter.close();
//			((FileInputStream) oExcelWorkbook).close();
			this.setNull();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setNull() {
		fileReader = null;
		fileWriter = null;
		excelWorkbook = null;
		excelFileName = "";
	}

	// ---------------------------------------------------------

	public void openExcelWorkbook(String fileName) {
		try {

			fileName = fileName.trim();
			if (fileName.isEmpty()) {
				throw new Exception("No file name specified...");
			}

			if (!(new File(fileName)).exists()) {
				throw new Exception("File does not Exists");
			}

			fileReader = new FileInputStream(fileName);
			excelFileName = fileName;
			excelWorkbook = WorkbookFactory.create(fileReader);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------

	public void save() {
		try {
			fileWriter = new FileOutputStream(excelFileName);
			excelWorkbook.write(fileWriter);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------

	public void saveAs(String fileName) {
		try {
			if (fileName.isEmpty()) {
				throw new Exception("No file name specified...");
			}

			if ((new File(fileName)).exists()) {
				throw new Exception("File already Exists");
			}

			fileWriter = new FileOutputStream(fileName);
			excelWorkbook.write(fileWriter);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------

	public void close() {
		try {
//			oExcelWorkbook.close();
			fileReader.close();
			setNull();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// --------------------------------------------------------------------

	public void createSheet(String sheetName, String sWorkbook) {
		try {
			sheetName = sheetName.trim();
			if (sheetName.isEmpty()) {
				throw new Exception("Sheet name not specified");

			}

			Sheet sheet;
			openExcelWorkbook(sWorkbook);
			sheet = excelWorkbook.getSheet(sheetName);
			if (sheet != null) {
				throw new Exception("Sheet already Exist");
			}
			excelWorkbook.createSheet();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// --------------------------------------------------------------------

	public int getRowCountOfSheet(String sheetName) {
		try {
			sheetName = sheetName.trim();
			if (sheetName.isEmpty()) {
				throw new Exception("Sheet name not specified");

			}
			Sheet sheet;
			sheet = excelWorkbook.getSheet(sheetName);
			System.out.println("Sheet Name: " + sheetName);
			if (sheet == null) {
				throw new Exception("Sheet does not Exist");
			}

			return sheet.getLastRowNum();

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	// ------------------------------------------------------------------------

	public int getCellCount(String sheetName, int rowIndex) {
		try {
			sheetName = sheetName.trim();
			if (sheetName.isEmpty()) {
				throw new Exception("Sheet name not specified");

			}

			Sheet sheet;

			sheet = excelWorkbook.getSheet(sheetName);
			if (sheet == null) {
				throw new Exception("Sheet doesnot Exist");
			}

			if (rowIndex < 1) {
				throw new Exception("ROw Index start from 1");
			}

			Row row;

			row = sheet.getRow(rowIndex - 1);

			if (row == null) {
				return 0;
			} else {
				return row.getLastCellNum() + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	// ---------------------------------------------------------------------

	public String getCellCData(String sheetName, int rowIndex, int cellIndex) { //irow=2, icell=2
																			
		try {
			sheetName = sheetName.trim();
			if (sheetName.isEmpty()) {
				throw new Exception("Sheet name not specified");

			}
			
			Sheet sheet;
			sheet = excelWorkbook.getSheet(sheetName);
			
			if(sheet == null){ throw new Exception("Sheet does not Exist"); }
			 

			if (rowIndex < 1 || cellIndex < 1) {
				throw new Exception("Row & Cell Index start from 1");
			}

			Row row;
			row = sheet.getRow(rowIndex - 1);

			if (row == null) {
				return "";
			}

			Cell cell;
			cell = row.getCell(cellIndex - 1);
			if (cell == null) {
				// fill the return string as per your requirement
				return "";
			} else {

				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					System.out
							.println("Cell Value:"
									+ String.valueOf((long) cell
											.getNumericCellValue()));
					return String.valueOf((long) cell.getNumericCellValue());
				} else {
					System.out.println("String cell value: "
							+ cell.getStringCellValue());
					return cell.getStringCellValue();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	// ----------------------------------------------------------------

	public void setCellCData(String sheetName, int rowIndex, int cellIndex,
			String valueToSet) {
		try {
			sheetName = sheetName.trim();
			if (sheetName.isEmpty()) {
				throw new Exception("Sheet name not specified");

			}

			Sheet sheet;

			sheet = excelWorkbook.getSheet(sheetName);
			if (sheet == null) {
				throw new Exception("Sheet doesnot  Exist");
			}

			if (rowIndex < 1 || cellIndex < 1) {
				throw new Exception("Row & Cell Index start from 1");
			}

			Row row;

			row = sheet.getRow(rowIndex - 1);

			if (row == null) {
				sheet.createRow(rowIndex - 1);
				row = sheet.getRow(rowIndex - 1);
			}

			Cell cell;
			cell = row.getCell(cellIndex - 1);
			if (cell == null) {
				row.createCell(cellIndex - 1);
				cell = row.getCell(cellIndex - 1);
			}

			cell.setCellValue(valueToSet);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
