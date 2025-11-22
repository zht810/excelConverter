package com.zht.exceldownloadplugin.entity;

public class GenerateResult {
    private String msg;
    private boolean isSuccess;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public static GenerateResult success(String msg) {
        GenerateResult generateResult = new GenerateResult();
        generateResult.setMsg(msg);
        generateResult.setSuccess(true);
        return generateResult;
    }

    public static GenerateResult fail(String msg) {
        GenerateResult generateResult = new GenerateResult();
        generateResult.setMsg(msg);
        generateResult.setSuccess(false);
        return generateResult;
    }
}
