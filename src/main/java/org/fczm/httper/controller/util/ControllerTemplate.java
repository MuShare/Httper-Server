package org.fczm.httper.controller.util;

import org.fczm.httper.service.DeviceManager;
import org.fczm.httper.service.RequestManager;
import org.fczm.httper.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ControllerTemplate {

    @Autowired
    protected DeviceManager deviceManager;

    @Autowired
    protected RequestManager requestManager;

    @Autowired
    protected UserManager userManager;

    public ResponseEntity generateOK(Map<String, Object> result) {
        return generateResponseEntity(result, HttpStatus.OK, null, null);
    }

    public ResponseEntity generateBadRequest(int errorCode, String errorMessage) {
        return generateResponseEntity(null, HttpStatus.BAD_REQUEST, errorCode, errorMessage);
    }

    public ResponseEntity generateBadRequest(ErrorCode errorCode) {
        return generateBadRequest(errorCode.code, errorCode.message);
    }

    public ResponseEntity generateResponseEntity(Map<String, Object> result, HttpStatus status, Integer errCode, String errMsg) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (result != null) {
            data.put("result", result);
        }
        data.put("status", status.value());
        if (errCode != null) {
            data.put("errorCode", errCode);
        }
        if (errMsg != null) {
            data.put("errorMessage", errMsg);
        }
        return new ResponseEntity(data, status);
    }

}
