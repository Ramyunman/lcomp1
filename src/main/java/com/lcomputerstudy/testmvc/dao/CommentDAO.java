package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.Comment;

public class CommentDAO {
	
	private static CommentDAO dao = null;
	
	private CommentDAO() {
		
	}
	
	public static CommentDAO getInstance() {
		if(dao == null) {
			dao = new CommentDAO();
		}
		return dao;
	}
	
	public ArrayList<Comment> getComments() {		// 댓글 리스트
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comment> commentList = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from comment";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			commentList = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setC_content(rs.getString("c_content"));
				comment.setC_writer(rs.getString("c_writer"));
				comment.setC_date(rs.getString("c_date"));
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_order(rs.getInt("c_order"));
				comment.setC_depth(rs.getInt("c_depth"));
				comment.setB_idx(rs.getInt("b_idx"));

				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();		
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return commentList;
	}
	
	public void insertComment(Comment comment) {		// 댓글 등록
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE comment Set c_order = c_order+1 where c_group = ? and c_order > ?";	//원글에 계속 다는거 말고 새로 원글에 답글을 달때
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getC_group());
			pstmt.setInt(2, comment.getC_order()); 	
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "INSERT INTO comment(c_content, c_writer, c_date, c_group, c_order, c_depth, b_idx) values (?,?,now(),?,?,?,b_idx)";	//원글에 계속 다는거(사선으로)		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setString(2, comment.getC_writer());
			pstmt.setInt(3, comment.getC_group());
			pstmt.setInt(4, comment.getC_order() + 1);
			pstmt.setInt(5, comment.getC_depth() + 1);
			pstmt.executeUpdate();
			
				
		} catch (Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
