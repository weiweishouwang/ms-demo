package com.zhw.ms.common.contract.consts;

import java.util.ArrayList;
import java.util.List;

public class CommonConst {


    public final static String DEFAULT_DOC_ID_FIELD = "docid";

    public final static String CONTENTS_FIELD = "contents";

    public final static String CHARSET_JP = "Windows-31J";

    public final static String CHARSET_UTF8 = "UTF-8";

    public final static String CHARSET_ISO8859_1 = "iso8859-1";

    public final static String PDF_TYPE = "pdf";
    public final static String XLS_TYPE = "xls";
    public final static String XLSX_TYPE = "xlsx";
    public final static String XLSM_TYPE = "xlsm";
    public final static String DOC_TYPE = "doc";
    public final static String DOCX_TYPE = "docx";
    public final static String DOCM_TYPE = "docm";
    public final static String PPT_TYPE = "ppt";
    public final static String PPTX_TYPE = "pptx";
    public final static String PPTM_TYPE = "pptm";
    public final static String VSD_TYPE = "vsd";
    public final static String TXT_TYPE = "txt";
    public final static String CSV_TYPE = "csv";
    public final static String RTF_TYPE = "rtf";
    public final static String BMP_TYPE = "bmp";
    public final static String JPG_TYPE = "jpg";
    public final static String GIF_TYPE = "gif";
    public final static String PNG_TYPE = "png";

    public final static String WEB_EXCEPTION_KEY = "exception";

    public final static int DEFAULT_PAGE_SIZE = 10;

    public final static String BROWSE_IE = "IE";
    public final static String BROWSE_FILEFOX = "FILEFOX";
    public final static String BROWSE_CHEOME = "CHEOME";
    public final static String BROWSE_SAFARI = "SAFARI";

    public final static String SECURITY_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

    public final static String EMPTY = "";

    public final static String SPLIT_SEMICOLON = ";";

    public final static String SPLIT_COMMA = ",";

    public final static String SPLIT_UNDERLINE = "_";

    public final static String SPLIT_LEFT_BRACKETS = "(";

    public final static String SPLIT_RIGHT_BRACKETS = ")";

    public final static String JS_UNDEFINED = "undefined";

    public final static String SPLIT_EQUAL = "=";
    public final static String SPLIT_AND = "&";
    public final static String SPLIT_QUESTION = "?";
    public final static String METHOD_GET = "GET";

    public final static String ICON_TREE_FOLDER_OPEN = "tree-folder-open";
    public final static String ICON_USER = "icon-user";
    public final static String ICON_BLANK = "icon-blank";
    public final static String ICON_ORG = "icon-org";
    public final static String ICON_AREA = "icon-area";
    public final static String MENU_PRE = "MENU_";
    public final static String FUNC_PRE = "FUNC_";
    public final static String FTYPE_EXCEL = ".spreadsheetml.sheet";
    public final static String FTYPE_PICTURE = "image";
    public static final String USERNAME = "j_username";
    public static final String PASSWORD = "j_password";
    public static final String VALIDATE_CODE = "validateCode";
    public static final String STR_TRUE = "true";
    public static final int COOKIE_AGE = 14 * 24 * 60 * 60;
    public static final String USER_LOCALE = "userLocale";
    public static final String CELL_FORMAT_TEXT = "@";

    public static final String WEB_SEPARATOR = "/";

    public static final String WIN_SEPARATOR = "\\\\";

    public static final char SEP_COMMA = ',';

    public static final char SEP_DASH = '-';

    public static final char SEP_UNDERLINE = '_';

    public static final char SEP_POINT = '.';

    public static final String IMG_PNG = "png";

    public static final String LANG_ZH = "zh";

    public static final String LANG_EN = "en";

    public static final String LANG_JA = "ja";

    public static final char SEP_COLON = ':';

    /**
     * S_LOGIN_USER
     */
    public final static String S_LOGIN_USER = "S_LOGIN_USER";

    /**
     * 邮件预览名称
     */
    public final static String MAIL_PREVIEW = "_preview";

    /**
     * HTML空格
     */
    public final static String HTML_SPACE = "&nbsp;";
    public final static String HTML_SPACE3 = "&nbsp;&nbsp;&nbsp;";

    /**
     * HTML文件类型
     */
    public final static String HTML_TYPE = ".html";
    /**
     * 可上传的文件类型
     */
    public final static List<String> acceptUploadType = new ArrayList<String>();

    static {
        acceptUploadType.add(PDF_TYPE);
        acceptUploadType.add(XLS_TYPE);
        acceptUploadType.add(XLSX_TYPE);
        acceptUploadType.add(XLSM_TYPE);
        acceptUploadType.add(DOC_TYPE);
        acceptUploadType.add(DOCX_TYPE);
        acceptUploadType.add(DOCM_TYPE);
        acceptUploadType.add(PPT_TYPE);
        acceptUploadType.add(PPTX_TYPE);
        acceptUploadType.add(PPTM_TYPE);
        acceptUploadType.add(VSD_TYPE);
        acceptUploadType.add(TXT_TYPE);
        acceptUploadType.add(CSV_TYPE);
        acceptUploadType.add(RTF_TYPE);
        acceptUploadType.add(JPG_TYPE);
        acceptUploadType.add(GIF_TYPE);
        acceptUploadType.add(PNG_TYPE);
        acceptUploadType.add(BMP_TYPE);
    }

    public static String YES = "YES";
    public static String NO = "NO";

    public static final String REQUEST_ID = "requestID";

    public static final String PLATFORM_IOS = "IOS";
    public static final String PLATFORM_ANDROID = "ANDROID";
    public static final int HOUR_SECOND = 60 * 60;
    public static final int DAY_SECOND = HOUR_SECOND * 24;
}
