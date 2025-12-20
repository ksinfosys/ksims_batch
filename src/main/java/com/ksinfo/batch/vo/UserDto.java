package com.ksinfo.batch.vo;

public class UserDto {

	private String empId;
	private String empName;
	private String empCompMail;
	private String stayExpirationDate;
	private String hiredDate;

	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpCompMail() {
		return empCompMail;
	}
	public void setEmpCompMail(String empCompMail) {
		this.empCompMail = empCompMail;
	}
	public String getStayExpirationDate() {
		return stayExpirationDate;
	}
	public void setStayExpirationDate(String stayExpirationDate) {
		this.stayExpirationDate = stayExpirationDate;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
	}
}
