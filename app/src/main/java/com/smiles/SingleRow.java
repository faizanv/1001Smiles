package com.smiles;

/**
 * Created by ffas on 6/9/15.
 */
public class SingleRow {

    private String message;
    private String detail;
    private String date;
    private boolean flag;

    public SingleRow() {
        message = "";
        detail = "";
        date = "";
        flag = false;
    }

    public SingleRow(String m, String d, String d2) {
        this.message = m;
        this.detail = d;
        this.date = d2;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public boolean getFlag() { return flag; }

    public void setMessage(String s) {
        this.message = s;
    }

    public void setDetail(String d) {
        this.detail = d;
    }

    public void setDate(String d) {
        this.date = d;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setFlag() {
        if (flag) {
            setFlag(false);
        } else {
            setFlag(true);
        }
    }
}
