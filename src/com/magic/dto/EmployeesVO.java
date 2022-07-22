package com.magic.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeesVO {
	//ValueObject는 DB정보를 묶어줌
	private String id, pass, name, lev, phone;
	private Date enter;
	private Integer gender;
}
