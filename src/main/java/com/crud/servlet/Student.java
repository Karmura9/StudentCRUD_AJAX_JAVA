package com.crud.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.connector.Response;

import com.crud.bean.StudentBean;
import com.crud.dao.StudentDao;
import com.google.gson.Gson;

@MultipartConfig
public class Student extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		
		StudentDao dao = new StudentDao();
		if(request.getParameter("id")==null) {
			List<StudentBean> students = dao.getData();

			if(students.size()==0) {
				response.setContentType("application/json");
				response.setStatus(404);
				out.println("Record Not Found");
			}else {
//				Gson gson = new Gson();
//				String json = gson.toJson(students);
//				out.println(json);
				response.setContentType("application/json");
				response.setStatus(200);
				out.println(new Gson().toJson(students));
				
			}
		}else {
			
			int std_id = Integer.parseInt(request.getParameter("id"));
			StudentBean student = dao.getStudent(std_id);
			if(student != null) {
				response.setStatus(200);
				out.println(new Gson().toJson(student));
			}else {
				response.setStatus(404);
				out.println("Student Not Found");
			}
			
		}	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		int update_id = Integer.parseInt(id);
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile"); 
		String email = request.getParameter("email"); 
		if(update_id == 0) {
			//Start Save
			StudentBean bean = new StudentBean(0, name, mobile, email);
			com.crud.dao.StudentDao dao = new com.crud.dao.StudentDao();
			boolean isSaved = dao.save(bean);
			//out.print(isSaved?"Done":"Fail");
			if(isSaved) {
				response.setStatus(200);
				out.print("Done");
			}else {
				response.setStatus(500);
				out.print("Fail");
			}
			//End Save
		}else {
			//Start Update
			StudentBean bean = new StudentBean(update_id, name, mobile, email);
			com.crud.dao.StudentDao dao = new com.crud.dao.StudentDao();
			boolean isUpdate = dao.update(bean);
			if(isUpdate) {
				response.setStatus(200);
				out.print("Updated");
			}else {
				response.setStatus(500);
				out.print("Updated Fail");
			}
			//End Update
		}
		
		
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String user_id = request.getParameter("id");
		int id = Integer.parseInt(user_id);
		StudentDao dao = new StudentDao();
		
		try {
			
			boolean isDelete;
			isDelete = dao.delete(id);
			if(isDelete) {
				response.setStatus(200);
				out.println("done");
			}else {
				response.setStatus(500);
				out.println("error");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
