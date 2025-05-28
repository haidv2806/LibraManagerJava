import java.io.*;

public class FileEditorService {

    // Đọc nội dung file (chỉ áp dụng cho .txt hoặc .md)
    public static String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }

        reader.close();
        return content.toString();
    }

    // Ghi nội dung đã chỉnh sửa vào file
    public static void saveToFile(String content, File destination) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
        writer.write(content);
        writer.close();
    }

    // Trả về địa chỉ file
    public static String getFilePath(File file) {
        return file.getAbsolutePath();
    }
}