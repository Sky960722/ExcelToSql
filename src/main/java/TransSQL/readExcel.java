package TransSQL;

import Beans.DW.DWTB;
import Beans.ODS.ODSTB;
import Tools.DWDDLReader;
import Tools.DWHQLReader;
import Tools.ExcelReader;
import Beans.DW.HQL;
import Tools.ODSReader;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readExcel {

    private static Logger logger = LoggerFactory.getLogger(readExcel.class);

    public static void main(String[] args) throws Exception {

        String currentPath = System.getProperty("user.dir");
        System.setProperty("logDirectory", currentPath);

        JFrame frame = new JFrame("File Chooser Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton chooseButton = new JButton("Choose File");
        JTextField filePathField = new JTextField(30);
        filePathField.setEditable(false);

        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile(frame, filePathField);
            }
        });

        JButton submitButton1 = new JButton("生成dw层ddl");
        submitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateDWDDL(filePathField, frame, currentPath);
            }
        });


        JButton submitButton2 = new JButton("生成ods层ddl");
        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateODSDDL(filePathField, currentPath, frame);

            }
        });

        JButton submitButton3 = new JButton("生成dw层sql");
        submitButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateDWSQL(filePathField, currentPath, frame);

            }
        });

        JButton submitButton4 = new JButton("生成ods层sql");
        submitButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateODSSQL(filePathField, currentPath, frame);

            }
        });


        JPanel panel = new JPanel();
        panel.add(chooseButton);
        panel.add(filePathField);
        panel.add(submitButton1);
        panel.add(submitButton2);
        panel.add(submitButton3);
        panel.add(submitButton4);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setSize(400, 300);
        frame.setVisible(true);

    }

    private static void generateDWSQL(JTextField filePathField, String currentPath, JFrame frame) {
        String pathFile = filePathField.getText();
        if (pathFile == null || "".equals(pathFile)) {
            return;
        }

        try {
            List<HQL> hqlList = DWHQLReader.excelToHalList(pathFile);

            String sqlFilePath =
                    currentPath + "\\" + "DW" + "\\" + hqlList.get(0).getTableName().substring(0, 3) + "\\" + "SQL" + "\\" + hqlList.get(0).getSchema() + "." + hqlList.get(0).getTableName() + ".sql";
// 创建目标文件路径

            File sqlOutputFile = new File(sqlFilePath);


            if (sqlOutputFile.exists()) {
                sqlOutputFile.delete();
            }
            sqlOutputFile.getParentFile().mkdirs(); // 创建父文件夹
            for (HQL hql : hqlList) {


                try (BufferedWriter sqlWriter = new BufferedWriter(new FileWriter(sqlOutputFile, true))) {
                    sqlWriter.write(hql.toHQL());
                } catch (IOException ex) {
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            // 在异常处理中显示错误消息
            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void generateODSDDL(JTextField filePathField, String currentPath, JFrame frame) {
        String pathFile = filePathField.getText();
        if (pathFile == null || "".equals(pathFile)) {
            return;
        }

        try {
            Workbook workbook = ExcelReader.getReadWorkBookType(pathFile);
            //获取当前第一页中所有表的跑批配置和频率
            List<ODSTB> odstbs = ODSReader.readTbConfg(workbook);
            //根据List里面的ODSTB 循环遍历 2页，获取所有表的字段，主键
            //分区键默认用的是etl_date
            Sheet sheet = workbook.getSheetAt(2);
            ODSReader.fillTbFields(odstbs, sheet);

            for (ODSTB odstb : odstbs) {
                String odmDDLPath =
                        currentPath + "\\" + "ODS" + "\\" + "DDL" + "\\" + "ODM" + "\\" + odstb.getSchema() + "\\" +
                                "odm." + odstb.getSchema() + "." + odstb.getTbName() + ".ddl";

                String sdmDDLPath =
                        currentPath + "\\" + "ODS" + "\\" + "DDL" + "\\" + "SDM" + "\\" + odstb.getSchema() + "\\" +
                                "sdm." + odstb.getSchema() + "." + odstb.getTbName() + ".ddl";

                // 创建目标文件路径
                File odmDDLPathFile = new File(odmDDLPath);

                File sdmDDLPathFile = new File(sdmDDLPath);

                if (odmDDLPathFile.exists()) {
                    odmDDLPathFile.delete();
                }
                odmDDLPathFile.getParentFile().mkdirs(); // 创建父文件夹

                if (sdmDDLPathFile.exists()) {
                    sdmDDLPathFile.delete();
                }
                sdmDDLPathFile.getParentFile().mkdirs(); // 创建父文件夹

                try (BufferedWriter odmWriter = new BufferedWriter(new FileWriter(odmDDLPathFile, true)); BufferedWriter sdmWriter = new BufferedWriter(new FileWriter(sdmDDLPathFile, true))) {
                    odmWriter.write(odstb.generateODMddl());
                    odmWriter.newLine();
                    sdmWriter.write(odstb.generateSDMddl());
                    sdmWriter.newLine();
                } catch (IOException ex) {
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        } catch (Exception ex) {
            // 在异常处理中显示错误消息
            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void generateODSSQL(JTextField filePathField, String currentPath, JFrame frame) {
        String pathFile = filePathField.getText();
        if (pathFile == null || "".equals(pathFile)) {
            return;
        }

        try {
            Workbook workbook = ExcelReader.getReadWorkBookType(pathFile);
            //获取当前第一页中所有表的跑批配置和频率
            List<ODSTB> odstbs = ODSReader.readTbConfg(workbook);
            //根据List里面的ODSTB 循环遍历 2页，获取所有表的字段，主键
            //分区键默认用的是etl_date
            Sheet sheet = workbook.getSheetAt(2);
            ODSReader.fillTbFields(odstbs, sheet);

            for (ODSTB odstb : odstbs) {

                String sqlFilePath =
                        currentPath + "\\" + "ODS" + "\\" + "SQL" + "\\" + odstb.getSchema() + "\\" + odstb.getSchema() + "." + odstb.getTbName() + ".sql";

                // 创建目标文件路径

                File sqlOutputFile = new File(sqlFilePath);


                if (sqlOutputFile.exists()) {
                    sqlOutputFile.delete();
                }
                sqlOutputFile.getParentFile().mkdirs(); // 创建父文件夹

                try (BufferedWriter sqlWriter = new BufferedWriter(new FileWriter(sqlOutputFile, true))) {
                    sqlWriter.write(odstb.generateSQL());
                } catch (IOException ex) {
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        } catch (Exception ex) {
            // 在异常处理中显示错误消息
            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }


    private static void generateDWDDL(JTextField filePathField, JFrame frame, String currentPath) {
        String pathFile = filePathField.getText();
        if (pathFile == null || "".equals(pathFile)) {
            return;
        }



        try (Workbook workbook = ExcelReader.getReadWorkBookType(pathFile)) {
            //获取当前第一页中所有表的跑批配置和频率
            try {

            }catch (Exception e){
                JOptionPane.showMessageDialog(frame, "An error occurred: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            List<DWTB> dwtbs = DWDDLReader.readTbConfg(workbook);
            //根据List里面的ODSTB 循环遍历 2页，获取所有表的字段，主键
            //分区键默认用的是etl_date
            Sheet sheet = workbook.getSheetAt(2);
            DWDDLReader.fillTbFields(dwtbs, sheet);
            for (DWTB dwtb : dwtbs) {
                logger.info(dwtb.toString());


                if(dwtb == null || dwtb.getTbName() == ""){
                    logger.error(dwtb.toString());
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + "dw生成配置有问题", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String dwDDLPath =
                        currentPath + "\\" + "DW" + "\\" + dwtb.getTbName().substring(0, 3) + "\\" + "DDL" + "\\" + dwtb.getSchema() + "." + dwtb.getTbName() + ".ddl";

                // 创建目标文件路径
                File dwDDLPathFile = new File(dwDDLPath);


                if (dwDDLPathFile.exists()) {
                    dwDDLPathFile.delete();
                }
                dwDDLPathFile.getParentFile().mkdirs(); // 创建父文件夹

                try (BufferedWriter dwDdlWriter = new BufferedWriter(new FileWriter(dwDDLPathFile, true))) {
                    logger.info(dwtb.toString());
                    dwDdlWriter.write(dwtb.generateDDL());
                    dwDdlWriter.newLine();
                } catch (IOException ex) {
                    logger.error("出错的dwtb：" + dwtb.toString());
                    // 在异常处理中显示错误消息
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            // 在异常处理中显示错误消息
            JOptionPane.showMessageDialog(frame, "An error occurred: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }


    private static void chooseFile(JFrame frame, JTextField filePathField) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            filePathField.setText(filePath);
        }
    }


}
