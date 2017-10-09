package com.assignment.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name="login1")
@SessionScoped
public class Login {

	public Login() {
		// TODO Auto-generated constructor stub
		
	}
	private String fName;
	private String userName;
	private String password;
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
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    String username=value.toString();
	    if (username == null|| username.isEmpty()) {
	    			return;
	    }
    	
	}
	public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    String pwd=value.toString();
	    if (pwd == null|| pwd.isEmpty()) {
	    			return;
	    }
    	
	}
	public String loginUser()
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(System.getenv("ICSI518_SERVER"));
			ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
			ds.setDatabaseName(System.getenv("ICSI518_DB"));
			ds.setUser(System.getenv("ICSI518_USER"));
			ds.setPassword(System.getenv("ICSI518_PASSWORD"));
			if (ds != null) {
	        
				con = ds.getConnection();
				String sql = "select * from registration where username = '"+ userName + "'AND password='" + password + "'";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(!rs.next())
				{
					FacesContext.getCurrentInstance().addMessage("LoginForm:login",new FacesMessage(FacesMessage.SEVERITY_WARN, "Username and Passowrd does not match",
									"Please enter correct username and Password"));
					return "Login";
					
				}
				else
				{
					fName = rs.getString("fname");
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.getExternalContext().getSessionMap().put("username", userName);
					rs.close();
					return "success";
				}	
	        }	
		}
		catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} 
		finally {
			try {
				if(con!=null)
					con.close();
			} 
			catch (SQLException e) {

			}		
		}
		return null;		
	}
	public String redirectRegister() {
		return "registration";
	}
	
	public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml");
		return "success";
    }
	
}
