package com.example.test.bean;

import lombok.Data;

/**
 * @author mpf
 * @date 2019/12/9
 */
@Data
public class RespBean {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String VALIDATE_FAILURE = "validateFailure";
    public static final String VALIDATE_SUCCESS = "validateSuccess";
    public static final String PARAM_NULL = "paramNull";
    public static final String PERMISSION_FAILURE = "permissionFailure";
    public static final String PERMISSION_SUCCESS = "permissionSuccess";
    public static final String TIME_FAILURE = "timeFailure";
    public static final String TIME_SUCCESS = "timeSuccess";

    private String status;
    private String msg;
    private Object data;
    private String code;

    public RespBean() {
    }

    public RespBean(String status, String msg) {
        this(status, msg, null);
    }

    public RespBean(String status, Object object) {
        this(status, null, object);
    }

    public RespBean(String status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.data = object;
    }

    public static RespBean resp(String status) {
        return resp(status, null, null);
    }

    public static RespBean resp(String status, Object data) {
        return resp(status, null, data);
    }

    public static RespBean resp(String status, String msg) {
        return resp(status, msg, null);
    }

    public static RespBean resp(String status, String msg, Object data) {
        return resp(status, null, msg, data);
    }

    public static RespBean resp(String status, String code, String msg, Object data) {
        RespBean respBean = new RespBean();
        respBean.setStatus(status);
        respBean.setMsg(msg);
        respBean.setData(data);
        respBean.setCode(code);
        return respBean;
    }

    /**
     * 参数为null or empty时的返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendParamMessage() {
        return sendParamMessage(null);
    }
    public static RespBean sendParamMessage(Object object) {
        return sendParamMessage(PARAM_NULL, object);
    }

    public static RespBean sendParamMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, PARAM_NULL, message, object);
    }

    /**
     * 时间失败时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendTimeErrorMessage() {
        return sendTimeErrorMessage(null);
    }

    public static RespBean sendTimeErrorMessage(Object object) {
        return sendTimeErrorMessage(TIME_FAILURE, object);
    }

    public static RespBean sendTimeErrorMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, TIME_FAILURE, message, object);
    }

    /**
     * 时间成功时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendTimeSuccessMessage() {
        return sendTimeSuccessMessage(null);
    }

    public static RespBean sendTimeSuccessMessage(Object object) {
        return sendTimeSuccessMessage(TIME_SUCCESS, object);
    }

    public static RespBean sendTimeSuccessMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, TIME_SUCCESS, message, object);
    }

    /**
     * 权限失败时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendPermissionErrorMessage() {
        return sendPermissionErrorMessage(null);
    }

    public static RespBean sendPermissionErrorMessage(Object object) {
        return sendPermissionErrorMessage(PERMISSION_FAILURE, object);
    }

    public static RespBean sendPermissionErrorMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, PERMISSION_FAILURE, message, object);
    }

    /**
     * 权限成功时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendPermissionSuccessMessage() {
        return sendPermissionSuccessMessage(null);
    }

    public static RespBean sendPermissionSuccessMessage(Object object) {
        return sendPermissionSuccessMessage(PERMISSION_SUCCESS, object);
    }

    public static RespBean sendPermissionSuccessMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, PERMISSION_SUCCESS, message, object);
    }

    /**
     * 验证失败时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendValidateErrorMessage() {
        return sendValidateErrorMessage(null);
    }

    public static RespBean sendValidateErrorMessage(Object object) {
        return sendValidateErrorMessage(VALIDATE_FAILURE, object);
    }

    public static RespBean sendValidateErrorMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, VALIDATE_FAILURE, message, object);
    }

    /**
     * 验证成功时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendValidateSuccessMessage() {
        return sendValidateSuccessMessage(null);
    }

    public static RespBean sendValidateSuccessMessage(Object object) {
        return sendValidateSuccessMessage(VALIDATE_SUCCESS, object);
    }

    public static RespBean sendValidateSuccessMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, VALIDATE_SUCCESS, message, object);
    }

    /**
     * 失败时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendErrorMessage() {
        return sendErrorMessage(null);
    }
    public static RespBean sendErrorMessage(Object object) {
        return sendErrorMessage(ERROR, object);
    }

    public static RespBean sendErrorMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, ERROR, message, object);
    }

    /**
     * 成功时返回方法
     *
     * @return
     * @author ff
     */
    public static RespBean sendSuccessMessage() {
        return sendSuccessMessage(null);
    }
    public static RespBean sendSuccessMessage(Object object) {
        return sendSuccessMessage(SUCCESS, object);
    }

    public static RespBean sendSuccessMessage(String message, Object object) {
        return RespBean.resp(SUCCESS, SUCCESS, message, object);
    }
}
