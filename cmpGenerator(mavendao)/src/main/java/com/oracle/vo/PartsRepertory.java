package com.oracle.vo;

public class PartsRepertory {
    private Integer partsrepid;	//����

    private Integer partsid;	//������

    private Integer partsreqcount;	//������
    
    String partsname;	//�������
    
    

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