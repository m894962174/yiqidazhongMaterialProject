package com.oracle.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PartsRepBill {
    private Integer billid;

    private String billflag;

    private String billtype;

    private Integer partsid;

    private Integer billcount;

    //@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String billtime;

    private Integer billuser;
    
    private String partsname;
    
    private String loginname;
    
    private String name;
    
    private String name1;
    

    public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartsname() {
		return partsname;
	}

	public void setPartsname(String partsname) {
		this.partsname = partsname;
	}

	public Integer getBillid() {
        return billid;
    }

    public void setBillid(Integer billid) {
        this.billid = billid;
    }

    public String getBillflag() {
        return billflag;
    }

    public void setBillflag(String billflag) {
        this.billflag = billflag == null ? null : billflag.trim();
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    public Integer getPartsid() {
        return partsid;
    }

    public void setPartsid(Integer partsid) {
        this.partsid = partsid;
    }

    public Integer getBillcount() {
        return billcount;
    }

    public void setBillcount(Integer billcount) {
        this.billcount = billcount;
    }

    

    public String getBilltime() {
		return billtime;
	}

	public void setBilltime(String billtime) {
		this.billtime = billtime;
	}

	public Integer getBilluser() {
        return billuser;
    }

    public void setBilluser(Integer billuser) {
        this.billuser = billuser;
    }
}