package provider.common;

import provider.response.BaseResponse;

public enum CycleErrorCode {
    NO_AUTHORITY(40011, "no permission"),
    NO_AUTHORITY_OTHER(40012, "no permission: %s"),

    NO_LOGIN(40014, "您还没有登录"),

    IDENTIFY_CODE_ERROE(40013, "验证码错误"),

    FAIL_ACTIVATION(40031, "激活失败"),

    NO_ACTIVATION(40033, "未激活"),

    REPEAT_ACTIVATION(4032,"重复激活"),
    REQUEST_ERROR(400014, "错误的请求参数: %s"),
    REQUEST_MISSING(400015, "缺少请求参数: %s"),
    NOT_EXIST(400012, "%s不存在"),

    INSERT_ERROR(500014, "插入失败"),
    UPDATE_ERROR(500015, "更新失败"),
    QUERY_ERROR(500018, "查询失败"),
    NAME_EXIT(500019, "名称已存在，请重新命名"),
    UNKNOWN_ERROR(501010, "unknown error");


    private int code;
    private String msg;

    CycleErrorCode (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static BaseResponse getParamMissRes(String param) {
        return CycleErrorCode.REQUEST_MISSING.getResponse(param);
    }

    public static BaseResponse getParamErrorRes(String param) {
        return CycleErrorCode.REQUEST_ERROR.getResponse(param);
    }

    public BaseResponse getResponse() {
        return BaseResponse.builder().success(false).errorCode(code).errorMsg(msg).build();
    }

    public BaseResponse getResponse(Object... params) {
        return BaseResponse.builder().success(false).errorCode(code).errorMsg(String.format(msg, params)).build();
    }
}
