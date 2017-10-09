package com.assignment.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name="reg")
public class Registration {
	
private String fname;
private String lname;
private String email;
private String userName;
private String password;

	public Registration() {
	
		// TODO Auto-generated constructor stub
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public void validateFname(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    String fname=value.toString();
	   
	    if(!(fname.matches("[a-zA-Z_]+"))) {
	    	throw new ValidatorException(new FacesMessage(
	    			"Name should contain only characters"));
	    }
	    	
	}
	public void validateLname(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    String lname=value.toString();
	   
	    if(!(lname.matches("[a-zA-Z_]+"))) {
	    	throw new ValidatorException(new FacesMessage(
	    			"Name should contain only characters"));
	    }
	    	
	}

	public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    String username=value.toString();
	   
	    if(!(username.matches("[a-zA-Z_]+"))) {
	    	throw new ValidatorException(new FacesMessage(
	    			"Username should contain only characters"));
	    }
	    	
	}
	
	public String testName() {
		String check = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
			ds.setDatabaseName(System.getenv("ICSI518_DB"));
			ds.setUser(System.getenv("ICSI518_USER"));
			ds.setPassword(System.getenv("ICSI518_PASSWORD"));
			if(ds!=null){
				con = ds.getConnection();
				String sql = "INSERT INTO registration(fname, lname, email,username, password) values (?,?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, fname);
				pstmt.setString(2 ,lname);
				pstmt.setString(3, email);
				pstmt.setString(4, userName);
				pstmt.setString(5, password);
				pstmt.executeUpdate();
				FacesContext.getCurrentInstance().addMessage("LoginForm:login",new FacesMessage(FacesMessage.SEVERITY_WARN, "Registered Successfully. Please Login.",
					"Registered Successfully. Please login."));
				check = "yes";
			}	
		}
		catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {

			}
			
	}
		if (check.equals("yes")) 
			return "success";
		else
			return "failure";
	}
}
