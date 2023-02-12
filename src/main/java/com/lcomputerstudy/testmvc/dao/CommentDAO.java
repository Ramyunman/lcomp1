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
	
	public ArrayList<Comment> getComments(int b_idx) {		// 댓글 리스트
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comment> commentList = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from comment where b_idx=? order by c_idx desc";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, b_idx);
			rs = pstmt.executeQuery();
			commentList = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setC_content(rs.getString("c_content"));
				comment.setC_writer(rs.getString("c_writer"));
				comment.setC_date(rs.getString("c_date"));
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
			String sql = "INSERT INTO comment(c_content, c_writer, c_date, b_idx) values (?,?,now(),?)";	//원글에 계속 다는거(사선으로)		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setString(2, comment.getC_writer());	
			pstmt.setInt(3, comment.getB_idx());
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
	
/*	public void commentIncomments(Comment comment) {	//대댓글달기
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE comment SET c_order = c_order+1 where b_idx = ? and c_order > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getB_idx());
			pstmt.setInt(2, comment.getC_order());
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "INSERT INTO comment(c_content, c_writer, c_date, b_idx, c_order, c_depth) values (?,?,now(),?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setString(2, comment.getC_writer());
			pstmt.setInt(3, comment.getB_idx());
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
*/

}
