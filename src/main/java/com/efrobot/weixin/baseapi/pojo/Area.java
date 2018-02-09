package com.efrobot.weixin.baseapi.pojo;

import java.io.Serializable;

public class Area implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String area;

    private Integer price;

    private Integer discount;

    private String exp1;

    private String exp2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }



    public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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