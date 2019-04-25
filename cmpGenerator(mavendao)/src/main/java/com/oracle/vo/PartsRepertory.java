package com.oracle.vo;

public class PartsRepertory {
    private Integer partsrepid;	//主键

    private Integer partsid;	//配件外键

    private Integer partsreqcount;	//配件库存
    
    String partsname;	//配件名称
    
    

    public String getPartsname() {
		return partsname;
	}

	public void setPartsname(String partsname) {
		this.partsname = partsname;
	}

	public Integer getPartsrepid() {
        return partsrepid;
    }

    public void setPartsrepid(Integer partsrepid) {
        this.partsrepid = partsrepid;
    }

    public Integer getPartsid() {
        return partsid;
    }

    public void setPartsid(Integer partsid) {
        this.partsid = partsid;
    }

    public Integer getPartsreqcount() {
        return partsreqcount;
    }

    public void setPartsreqcount(Integer partsreqcount) {
        this.partsreqcount = partsreqcount;
    }

	@Override
	public String toString() {
		return "PartsRepertory [partsrepid=" + partsrepid + ", partsid=" + partsid + ", partsreqcount=" + partsreqcount
				+ ", partsname=" + partsname + "]";
	}
    
    
}