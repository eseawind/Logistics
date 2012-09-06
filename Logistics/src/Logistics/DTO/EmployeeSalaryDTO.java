package Logistics.DTO;

import java.sql.Date;

public class EmployeeSalaryDTO {
	private Integer ESID;//工资记录编号
	private Integer EmployeeID;//工号
	private double PostSalary;//岗位工资
	private double PerformanceSalary;//绩效工资
	private double OvertimeSalary;//加班工资
	private double PostAllowance;//岗位津贴
	private double BasicBonus;//日常奖金
	private double AnnualBonus;//年终奖金
	private double ExtraMoney;//补款
	private double DeductionMoney;//扣款
	private double TransportAllowance;//交通补助
	private double CommunicationAllowance;//通讯补助
	private double MealAllowance;//餐费补助
	private double OtherPayment;//其他款项
	private Date PayDay;//工资日期
	private String Remarks;
	private EmployeeProfileDTO EPDTO=null;
	
	public double countTotalSalry(){
		double totalSalry=0;
		totalSalry+=this.PostSalary;
		totalSalry+=this.PerformanceSalary;
		totalSalry+=this.OvertimeSalary;
		totalSalry+=this.PostAllowance;
		totalSalry+=this.BasicBonus;
		totalSalry+=this.AnnualBonus;
		totalSalry+=this.ExtraMoney;
		totalSalry+=this.DeductionMoney;
		totalSalry+=this.TransportAllowance;
		totalSalry+=this.CommunicationAllowance;
		totalSalry+=this.MealAllowance;
		totalSalry+=this.OtherPayment;
		return totalSalry;
	}
	
	
	public String getRemarks() {
		return Remarks;
	}


	public void setRemarks(String remarks) {
		Remarks = remarks;
	}


	public EmployeeProfileDTO getEPDTO() {
		return EPDTO;
	}
	public void setEPDTO(EmployeeProfileDTO epdto) {
		EPDTO = epdto;
	}
	public Integer getESID() {
		return ESID;
	}
	public void setESID(Integer esid) {
		ESID = esid;
	}
	public Integer getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(Integer employeeID) {
		EmployeeID = employeeID;
	}
	public double getPostSalary() {
		return PostSalary;
	}
	public void setPostSalary(double postSalary) {
		PostSalary = postSalary;
	}
	public double getPerformanceSalary() {
		return PerformanceSalary;
	}
	public void setPerformanceSalary(double performanceSalary) {
		PerformanceSalary = performanceSalary;
	}
	public double getOvertimeSalary() {
		return OvertimeSalary;
	}
	public void setOvertimeSalary(double overtimeSalary) {
		OvertimeSalary = overtimeSalary;
	}
	public double getPostAllowance() {
		return PostAllowance;
	}
	public void setPostAllowance(double postAllowance) {
		PostAllowance = postAllowance;
	}
	public double getBasicBonus() {
		return BasicBonus;
	}
	public void setBasicBonus(double basicBonus) {
		BasicBonus = basicBonus;
	}
	public double getAnnualBonus() {
		return AnnualBonus;
	}
	public void setAnnualBonus(double annualBonus) {
		AnnualBonus = annualBonus;
	}
	public double getExtraMoney() {
		return ExtraMoney;
	}
	public void setExtraMoney(double extraMoney) {
		ExtraMoney = extraMoney;
	}
	public double getDeductionMoney() {
		return DeductionMoney;
	}
	public void setDeductionMoney(double deductionMoney) {
		DeductionMoney = deductionMoney;
	}
	public double getTransportAllowance() {
		return TransportAllowance;
	}
	public void setTransportAllowance(double transportAllowance) {
		TransportAllowance = transportAllowance;
	}
	public double getCommunicationAllowance() {
		return CommunicationAllowance;
	}
	public void setCommunicationAllowance(double communicationAllowance) {
		CommunicationAllowance = communicationAllowance;
	}
	public double getMealAllowance() {
		return MealAllowance;
	}
	public void setMealAllowance(double mealAllowance) {
		MealAllowance = mealAllowance;
	}
	public double getOtherPayment() {
		return OtherPayment;
	}
	public void setOtherPayment(double otherPayment) {
		OtherPayment = otherPayment;
	}
	public Date getPayDay() {
		return PayDay;
	}
	public void setPayDay(Date payDay) {
		PayDay = payDay;
	}
	
	public void setPayDay(String s){
		PayDay=Date.valueOf(s);
	}
	

}
