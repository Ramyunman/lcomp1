package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;


public class BoardDAO {
	
	private static BoardDAO dao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public ArrayList<Board> getBoards(Pagination pagination) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = pagination.getPageNum();
		
		try {
			conn = DBConnection.getConnection();
			// String query = "select * from user limit ?,3";
			String query = new StringBuilder()
					.append("SELECT         @ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("               t_board.*\n")
					.append("FROM           board t_board\n")
					.append("INNER JOIN      (SELECT @rownum := (SELECT COUNT(*)-?+1 FROM board t_board)) tc ON 1=1 \n")
					.append("ORDER BY b_idx DESC\n")
					.append("LIMIT          ?, ?\n")
					.toString();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, Pagination.perPage);
			rs = pstmt.executeQuery();
			list = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setRownum(rs.getInt("ROWNUM"));
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_views(rs.getString("b_views"));
				board.setB_writer(rs.getString("b_writer"));
				board.setB_date(rs.getString("b_date"));
				//board.setB_dateArr(board.getB_date().split("-"));		//전화번호 3개로 쪼개기
				
				list.add(board);
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
		
		return list;
	}
	
	public void insertBoard(Board board) {		// 등록
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title, b_content, b_views, b_writer, b_date, b_group, b_order, b_depth) values (?,?,?,?,now(),0,1,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_views());
			pstmt.setString(4, board.getB_writer());
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement("UPDATE board SET b_group = last_insert_id() WHERE b_idx = last_insert_id()");
			pstmt.executeUpdate();
		
		} catch (Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public Board detailBoard(Board board) {		// 상세보기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board resultBoard = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from board where b_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());	//첫번째 물음표에 board.getB_idx()가 들어간다.
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				resultBoard = new Board();
				resultBoard.setB_idx(Integer.parseInt(rs.getString("b_idx")));
				resultBoard.setB_title(rs.getString("b_title"));
				resultBoard.setB_content(rs.getString("b_content"));
				resultBoard.setB_views(rs.getString("b_views"));
				resultBoard.setB_writer(rs.getString("b_writer"));
				resultBoard.setB_date(rs.getString("b_date"));
				//resultBoard.setB_dateArr(resultBoard.getB_date().split("-"));		
				resultBoard.setB_group(Integer.parseInt(rs.getString("b_group")));		
				resultBoard.setB_order(Integer.parseInt(rs.getString("b_order")));		
				resultBoard.setB_depth(Integer.parseInt(rs.getString("b_depth")));		
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultBoard;
	}
	
	public void updateUser(Board board) {		// 수정
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE board SET b_title = ?, b_content = ?, b_views = ?, b_writer = ?, b_date = ? where b_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_views());
			pstmt.setString(4, board.getB_writer());
			pstmt.setString(5, board.getB_date());
			pstmt.setString(6, String.valueOf(board.getB_idx()));
			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public Board deleteBoard(Board board) {		// 삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board resultBoard = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "delete from board where b_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultBoard;
	}
	
	public int getBoardsCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) count FROM board ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
		
	}
	
	public void replyInsert(Board board) {		// 답글 
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title, b_content, b_views, b_writer, b_date, b_group, b_order, b_depth) values(?,?,?,?,now(),?,?,?)";		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_views());
			pstmt.setString(4, board.getB_writer());
			pstmt.setInt(5, board.getB_group());
			pstmt.setInt(6, board.getB_order());
			pstmt.setInt(7, board.getB_depth());
			pstmt.executeUpdate();
			pstmt.close();
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
