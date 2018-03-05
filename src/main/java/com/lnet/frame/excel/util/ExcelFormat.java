package com.lnet.frame.excel.util;

public enum ExcelFormat {

    /**
     * OFFICE 2003格式(.XLS .XLT)
     */
    OFFICE2003,

    /**
     * OFFICE 2007格式(.XLSX .XLTX)
     */
    OFFICE2007;


    public static ExcelFormat from(String filename){
        if (filename.toUpperCase().endsWith(XSLX_EXT) || filename.toUpperCase().endsWith(XLTX_EXT)) {
            return  ExcelFormat.OFFICE2007;
        } else if (filename.toUpperCase().endsWith(XLS_EXT) || filename.toUpperCase().endsWith(XLT_EXT)) {
            return ExcelFormat.OFFICE2003;
        }
        throw new IllegalArgumentException("错误的文件类型");
    }

    private static final String XSLX_EXT = "XLSX";
    private static final String XLTX_EXT = "XLTX";
    private static final String XLS_EXT = "XLS";
    private static final String XLT_EXT = "XLT";
}
