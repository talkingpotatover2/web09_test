package com.magic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.magic.dto.EmployeesVO;


public class EmployeesDAO {
	private EmployeesDAO() {
		
	}
	
	private static EmployeesDAO instance = new EmployeesDAO();
	
	public static EmployeesDAO getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws Exception {
		Connection conn = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		conn = ds.getConnection();
		return conn;
	}
	
	public int userCheck(String userid, String pwd, String lev) {
		int result = 1;
		Connection conn = null;
		String sql = "select * from employees where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(pwd.equals(rs.getString("pass"))) {
					if(lev.equals(rs.getString("lev"))) {
						result = 2;
						if(lev.equals("B")) {
							result = 3;
						}
					} else {
						result = 1;  //레벨 불일치
					}
				}else {
					result = 0;  //비밀번호 불일치
				}
			}else {
				result = -1;  //아이디 불일치
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null) 
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null) 
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public EmployeesVO getMember(String userid) {
		EmployeesVO member = null;
		String sql = "select * from employees where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new EmployeesVO();
				member.setId(rs.getString("id"));
				member.setPass(rs.getString("pass"));
				member.setName(rs.getString("name"));
				member.setLev(rs.getString("lev"));
				member.setEnter(rs.getDate("enter"));
				member.setGender(rs.getInt("gender"));
				member.setPhone(rs.getString("phone"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null) 
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null) 
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return member;
	}
}
