package com.efrobot.weixin.baseapi.pojo;

import java.io.Serializable;

public class OrderStatus implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer status;

    private String describe;

    private String button;

    private String exp1;

    private String exp2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button == null ? null : button.trim();
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1 == null ? null : exp1.trim();
    }

    public String getExp2() {
        return exp2;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2 == null ? null : exp2.trim();
    }
}