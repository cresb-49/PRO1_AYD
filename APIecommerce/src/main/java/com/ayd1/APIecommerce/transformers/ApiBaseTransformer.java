package com.ayd1.APIecommerce.transformers;

import java.util.List;

public class ApiBaseTransformer {
    private int code;
    private String message;
    private Object data;
    private String warning;
    private String error;
    private List<String> errors;
    private List<String> warnings;

    public ApiBaseTransformer() {

    }

    public ApiBaseTransformer(int code, String message, Object data, String warning, String error, List<String> errors,
            List<String> warnings) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.warning = warning;
        this.error = error;
        this.errors = errors;
        this.warnings = warnings;
    }

    public ApiBaseTransformer(int code, String message, Object data, String warning, String error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.warning = warning;
        this.error = error;
        this.errors = null;
        this.warnings = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
