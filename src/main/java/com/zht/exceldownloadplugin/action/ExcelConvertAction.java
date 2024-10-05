package com.zht.exceldownloadplugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.zht.exceldownloadplugin.dialog.GenerateDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ExcelConvertAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        GenerateDialog generateDialog = new GenerateDialog();
        generateDialog.show();
    }
}
