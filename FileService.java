import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileService {

    // Mở file chooser và chỉ chấp nhận .docx, .md, .txt
    public static File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Tệp hợp lệ (.docx, .md, .txt)", "docx", "md", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = getFileExtension(selectedFile);
            if (extension.matches("docx|md|txt")) {
                return selectedFile;
            } else {
                JOptionPane.showMessageDialog(null, "Chỉ chấp nhận file .docx, .md hoặc .txt");
            }
        }
        return null;
    }

    // Lấy đuôi file
    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0 && lastDot < name.length() - 1) {
            return name.substring(lastDot + 1).toLowerCase();
        }
        return "";
    }
}