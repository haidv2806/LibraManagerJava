package com;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.javateam.libramanagerjava.DocumentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentController {
    private DocumentService documentViewer;

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            Syting content = DocumentService.readFileContent(file);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    public ResponseEntity<String> saveFile(MultipartFile file) {
        try {
            String content = DocumentService.readFileContent(file);
            // Giả sử bạn có một phương thức để lưu nội dung vào cơ sở dữ liệu
            saveToFile(content);
            return ResponseEntity.ok("File đã được lưu thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}
