package ChainAbuse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static void exportToExcel(List<BitcoinAddress> addresses, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Scan Results");

        createHeaderRow(sheet);

        // Fill in data rows
        int rowNum = 1;
        for (BitcoinAddress addr : addresses) {
            createDataRow(sheet, addr, rowNum++);
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to an Excel file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            LogManager.getInstance().addLog("Exported scan results to " + fileName);
        } catch (IOException e) {
            LogManager.getInstance().addLog("Error exporting to Excel: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                LogManager.getInstance().addLog("Error closing workbook: " + e.getMessage());
            }
        }
    }

    private static void createHeaderRow(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] headers = {"Address", "Status", "Details", "# of Abuses", "Link"};
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }
    }

    private static void createDataRow(Sheet sheet, BitcoinAddress addr, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(addr.getAddress());
        row.createCell(1).setCellValue(addr.getStatus());
        row.createCell(2).setCellValue(addr.getDetails());
        row.createCell(3).setCellValue(addr.getAbuseCount());
        row.createCell(4).setCellValue(addr.getReportLink());
    }
}
