package com ;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

public class DocumentService {

    public void saveFileContent(String path, String content) throws IOException {
        File file = new File(path);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
    public String readFileContent(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename == null) throw new IOException("Tên file không hợp lệ");
        
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        switch (extension) {
            case "txt":
            case "md":
            case "docx":
                try(InputStream is = file.getInputStream()){
                    XWPFDocument doc = new XWPFDocument(is);
                    StringBuilder content = new StringBuilder();
                    doc.getParagraphs().forEach(paragraph -> 
                        content.append(paragraph.getText()).append("\n"));
                    return content.toString();
                }
                
            default:
                throw new IOException("Định dạng file không được hỗ trợ: " + extension);
        }
        
        if (filename.endsWith(".txt")  || filename.endsWith(".md")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }else if (filename.endsWith(".docx")){
            try(InputStream is = file.getInputStream()) {
                XWPFDocument doc = new XWPFDocument(is);
                StringBuilder content = new StringBuilder();
                doc.getParagraphs().forEach(paragraph -> 
                    content.append(paragraph.getText()).append("\n"));
                return content.toString();
            }
        }else {
            throw new IOException("Lỗi khi đọc file DOCX: " + e.getMessage());
            }
        }
    }
