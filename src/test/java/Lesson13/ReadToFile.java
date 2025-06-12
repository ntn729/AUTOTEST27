package Lesson13;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;


public class ReadToFile {
    public static void main(String[] args) {
        try (FileInputStream file = new FileInputStream("dataLogin.xlsx");
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheetDataLogin = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            // Đọc dữ liệu từ file Excel
            for (Row row : sheetDataLogin) {
                if (row.getRowNum() == 0) continue; // Bỏ qua hàng tiêu đề

                // Lấy giá trị từ cột "user" (cột 0) và "pass" (cột 1)
                String user = dataFormatter.formatCellValue(row.getCell(0)).trim();
                String pass = dataFormatter.formatCellValue(row.getCell(1)).trim();

                // In ra chỉ khi cả "user" và "pass" không rỗng
                if (!user.isEmpty() || !pass.isEmpty()) {
                    System.out.println("User: " + user);
                    System.out.println("Pass: " + pass);
                }
            }
        } catch (IOException e) {
            System.err.println("Đã xảy ra lỗi khi đọc file Excel: " + e.getMessage());
        }
    }
}
