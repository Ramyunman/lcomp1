package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.Search;


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
		Search search = null;
		
		String where = "";
		
		if(search == null) {
			search = new Search();
			search.setTcw("none");
		}
		
		switch (search.getTcw()) {
			case "title":
				where = "WHERE b_title like ? \n";
				break;
			case "content":
				where = "WHERE b_content like ? \n";
				break;
			case "writer":
				where = "WHERE b_writer like ? \n";
				break;
			case "none":
				where = "";
				break;
		}
						
		try {
			conn = DBConnection.getConnection();
			// String query = "select * from user limit ?,3";
			String query = new StringBuilder()
					.append("SELECT         @ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("               t_board.*\n")
					.append("FROM           board t_board\n")
					.append("INNER JOIN      (SELECT @rownum := (SELECT COUNT(*)-?+1 FROM board t_board)) tc ON 1=1 \n")
					.append(where)
					.append("ORDER BY b_group DESC, b_order ASC\n")
					.append("LIMIT          ?, ?\n")
					.toString();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum);
			pstmt.setString(2, "%"+search.getSearchbox()+"%");		//추가
			pstmt.setInt(3, pageNum);
			pstmt.setInt(4, Pagination.perPage);
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
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
				
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
			String sql = "insert into board(b_title, b_content, b_views, b_writer, b_date, b_group, b_order, b_depth) values (?,?,0,?,now(),0,1,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_writer());
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
	
	public void updateBoard(Board board) {		// 수정
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
			String sql = "update board set b_order = b_order+1 where b_group = ? and b_order > ?";	//원글에 계속 다는거 말고 새로 원글에 답글을 달때
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_group());
			pstmt.setInt(2, board.getB_order()); 	
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "insert into board(b_title, b_content, b_views, b_writer, b_date, b_group, b_order, b_depth) values (?,?,0,?,now(),?,?,?)";	//원글에 계속 다는거(사선으로)		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_writer());
			pstmt.setInt(4, board.getB_group());
			pstmt.setInt(5, board.getB_order() + 1);
			pstmt.setInt(6, board.getB_depth() + 1);
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
