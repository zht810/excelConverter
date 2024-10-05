package com.zht.exceldownloadplugin.window;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.zht.exceldownloadplugin.logic.Generate;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class GenerateWindow {
    private JButton selectButton;
    private JTextArea templateTa;
    private JButton generateButton;
    private JPanel mainPanel;
    private JTextField selectFilePath;
    private JTextField savePathTf;
    private JButton selectSavePathBt;

    private static final String templateDefault = "请输入模板,eg:update table set kpi='{kpi}' where name='${name}' and age>${age};";

    public GenerateWindow(Project project) {
        mainPanel.setPreferredSize(new Dimension(600, 400));
        templateTa.setText(templateDefault);

        // 输入模板时清空值
        templateTa.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (templateTa.getText().equals(templateDefault)) {
                    templateTa.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (StringUtils.isBlank(templateTa.getText())) {
                    templateTa.setText(templateDefault);
                }
            }
        });

        // 添加普通文件选择
        selectButton.addActionListener(event ->
                FileChooser.chooseFiles(FileChooserDescriptorFactory.createSingleFileDescriptor("xlsx"),
                        project,
                        null,
                        (List<VirtualFile> files) -> {
                            String filePath = files.stream().map(VirtualFile::getCanonicalPath).filter(StringUtils::isNotBlank).findFirst().orElse(null);
                            selectFilePath.setText(filePath);
                        })

        );

        // 选择保存的路径
        selectSavePathBt.addActionListener(event ->
                FileChooser.chooseFiles(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        project,
                        null,
                        (List<VirtualFile> files) -> {
                            String filePath = files.stream().map(VirtualFile::getCanonicalPath).filter(StringUtils::isNotBlank).findFirst().orElse(null);
                            savePathTf.setText(filePath);
                        })

        );

        // 生成结果
        generateButton.addActionListener(event ->
                {
                    String selectPath = selectFilePath.getText();
                    String template = templateTa.getText();
                    String savaPath = savePathTf.getText();

                    String result = Generate.doReadAndWrite(selectPath, template, savaPath);

                    // 弹窗提示
                    Messages.showMessageDialog(result, "提示", Messages.getInformationIcon());
                }
        );
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
