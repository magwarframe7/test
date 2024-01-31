import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class test {

    public static void main(String[] args) {
        // Sample list of JSON strings
        List<String> jsonList = List.of(
                "{\"id\": 1, \"title\": \"Title 1\"}",
                "{\"id\": 2, \"title\": \"Title 2\"}",
                "{\"id\": 3, \"title\": \"Title 3\"}"
        );

        try {
            Workbook workbook = createExcelSheet(jsonList);
            FileOutputStream fileOut = new FileOutputStream("output.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Excel sheet created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Workbook createExcelSheet(List<String> jsonList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("JsonData");

        // Create header row
        Row headerRow = sheet.createRow(0);
        JSONObject firstObject = new JSONObject(jsonList.get(0));
        int colIndex = 0;
        for (String key : firstObject.keySet()) {
            Cell cell = headerRow.createCell(colIndex++);
            cell.setCellValue(key);
        }

        // Populate data rows
        int rowIndex = 1;
        for (String jsonString : jsonList) {
            JSONObject jsonObject = new JSONObject(jsonString);
            Row dataRow = sheet.createRow(rowIndex++);
            colIndex = 0;
            for (String key : firstObject.keySet()) {
                Cell cell = dataRow.createCell(colIndex++);
                cell.setCellValue(jsonObject.getString(key));
            }
        }

        // Auto-size columns for better readability
        for (int i = 0; i < firstObject.length(); i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}