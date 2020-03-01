package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	public LoginServlet(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		// 接收客户端信息
		//User user = new User(request.getParameter("username"),request.getParameter("password"));
		// 验证处理
		//System.out.println("Android端接收数据成功!"+user);
		PrintWriter out = response.getWriter();
		Connection con;
		Statement sql;
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String uri="jdbc:mysql://127.0.0.1/servicedata";//localhost
		try{
			con = DriverManager.getConnection(uri,"root","123456");
			String condition = "select * from userInfo where username = '"+username+"' and password = '"+password+"'";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			if(rSet.next()) {
				System.out.println("Success!");
				//out.println("login successfully!");	
				out.write("success");
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("password", password);
			}
			else {
				System.out.print("Failed!");
				out.write("failed");
			}
			  // 返回信息到客户端
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("text/html");
	       // out.print("用户名：" + username);
	       // out.print("密码：" + password);
	        out.flush();
	        out.close();
			con.close();		
		} catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
