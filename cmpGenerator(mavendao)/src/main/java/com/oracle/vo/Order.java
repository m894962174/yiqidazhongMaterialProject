package com.oracle.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Order {
    private Integer orderid;

    private String ordercode;	//订单编码

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//	@JsonFormat(pattern="yyyy-MM-dd")
    private String orderdate;	//订单日期

    private String orderflag;	//订单状态
    
    private String orderflagname;
    
    private Integer orderpartscount;	//订单进货配件数
    
    private String partsname;	//配件名
    
    private Integer partsrepcount;	//库存配件数量
    
    private Integer partsid;
    
    
    
	public Integer getPartsid() {
		return partsid;
	}

	public void setPartsid(Integer partsid) {
		this.partsid = partsid;
	}

	public Integer getOrderpartscount() {
		return orderpartscount;
	}

	public void setOrderpartscount(Integer orderpartscount) {
		this.orderpartscount = orderpartscount;
	}

	public String getPartsname() {
		return partsname;
	}

	public void setPartsname(String partsname) {
		this.partsname = partsname;
	}

	public Integer getPartsrepcount() {
		return partsrepcount;
	}

	public void setPartsrepcount(Integer partsrepcount) {
		this.partsrepcount = partsrepcount;
	}

	public String getOrderflagname() {
		return orderflagname;
	}

	public void setOrderflagname(String orderflagname) {
		this.orderflagname = orderflagname;
	}

	public Integer getOrderid() {
        return orderid;
    }
    
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode == null ? null : ordercode.trim();
    }

    

    public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(String orderflag) {
        this.orderflag = orderflag == null ? null : orderflag.trim();
    }
}