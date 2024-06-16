package com.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crud.bean.StudentBean;
import com.crud.database.Database;

public class StudentDao {

	public boolean save(StudentBean bean) {
		
		try {
			Connection conn = Database.getConnection();
			PreparedStatement pst = conn.prepareStatement("INSERT INTO students (name,mobile,email) values(?,?,?)");
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getMobile());
			pst.setString(3, bean.getEmail());
			int i = pst.executeUpdate();
			if(i==1) 
				return true;
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	public List<StudentBean> getData(){
		List<StudentBean> students = new ArrayList<StudentBean>();
		
		PreparedStatement pst;
		try {
			pst = Database.getConnection().prepareStatement("SELECT * FROM students");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				
				StudentBean bean = new StudentBean(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setMobile(rs.getString("mobile"));
				bean.setEmail(rs.getString("email"));
				students.add(bean);
			}
			
			return students;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return students;
		
	}

	public boolean delete(int id) throws SQLException {
//		Thread.sleep(3000);
		Connection conn = Database.getConnection();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM students WHERE id = ?");
		pst.setInt(1, id);
		if(pst.executeUpdate()==1) 
			return true;
		else
			return false;
	}

	public StudentBean getStudent(int std_id) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn = Database.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM students WHERE id=?");
			pst.setInt(1, std_id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				StudentBean bean = new StudentBean(rs.getInt("id"), rs.getString("name"), rs.getString("mobile"), rs.getString("email"));

				return bean;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean update(StudentBean bean) {
		try {
			
			Connection conn = Database.getConnection();
			PreparedStatement pst = conn.prepareStatement("UPDATE students SET name=?, mobile=?, email=? WHERE id=?");
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getMobile());
			pst.setString(3, bean.getEmail());
			pst.setInt(4, bean.getId());
			if(pst.executeUpdate()==1)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
