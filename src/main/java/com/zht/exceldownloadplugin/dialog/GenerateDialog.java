package com.zht.exceldownloadplugin.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.zht.exceldownloadplugin.window.GenerateWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenerateDialog extends DialogWrapper {

    public GenerateDialog() {
        super(true);
        init();
    }

    @Override
    public @Nullable JComponent createCenterPanel() {
        GenerateWindow generateWindow = new GenerateWindow(null);
        return generateWindow.getPanel();
    }

    @Override
    protected Action @NotNull [] createActions() {
        // 返回一个空数组，这样就不会显示任何按钮
        return new Action[0];
    }


}
