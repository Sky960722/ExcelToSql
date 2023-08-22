package TransSQL;

import Tools.ExcelReader;
import Tools.StringUtils;
import beans.HQL;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.ExcelReader.getCellStringVal;
import static Tools.ExcelReader.getReadWorkBookType;

public class readExcel {


    public static void main(String[] args) throws Exception {

        String currentPath = System.getProperty("user.dir");


        JFrame frame = new JFrame("File Chooser Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton chooseButton = new JButton("Choose File");
        JTextField filePathField = new JTextField(30);
        filePathField.setEditable(false);

        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    filePathField.setText(filePath);
                }
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pathFile = filePathField.getText();
                if (pathFile == null || "".equals(pathFile)) {
                    return;
                }
                List<HQL> hqlList = new ArrayList<>();
                try {
                    hqlList = ExcelReader.readExcel(pathFile);
                } catch (Exception ex) {
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                HQL hql1 = hqlList.get(0);

                String filePath =
                        currentPath + "\\" + "SQL" + "\\" + hql1.getSchema() + "." + hql1.getTableName() +
                                ".sql";
                // 创建目标文件路径
                File outputFile = new File(filePath);

                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.getParentFile().mkdirs(); // 创建父文件夹

                for (HQL hql : hqlList) {
                    String toHQL = hql.toHQL();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true))){
                        writer.write(toHQL);
                        writer.newLine();
                        System.out.println("File written to: " + filePath);
                    } catch (IOException ex) {
                        // 在异常处理中显示错误消息
                        JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }


        });


        JPanel panel = new JPanel();
        panel.add(chooseButton);
        panel.add(filePathField);
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);

    }


}
