package com.efrobot.weixin.baseapi.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightNum implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String flightNum;//航班号

    private String compay;//航站楼
    
    private String startPlace;//出发地
    
    private String endPlace;//目的地

    private Date startTime;//开始时间
    
    private String startTimeStr;//开始时间
    
    private String startHour;//小时

    private Date endTime;//结束时间
    
    private String endTimeStr;
    
    private String endHour;//结束小时

    private String status;

    private Date cteateDate;

    private String exp1;//订单字母

    private String exp2;
    

    public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}


	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum == null ? null : flightNum.trim();
    }

    public String getCompay() {
        return compay;
    }

    public void setCompay(String compay) {
        this.compay = compay == null ? null : compay.trim();
    }

    public Date getStartTime() {
    	if (null == startTimeStr || "".equals(startTimeStr)) {
			return startTime;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			startTime = sdf.parse(startTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
    	if (null == endTimeStr || "".equals(endTimeStr)) {
			return endTime;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			endTime = sdf.parse(endTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCteateDate() {
        return cteateDate;
    }

    public void setCteateDate(Date cteateDate) {
        this.cteateDate = cteateDate;
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