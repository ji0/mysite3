package com.sds.icto.mysite.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.MemberVO;
import com.sds.icto.mysite.exception.MemberDAOException;

@Repository
public class MemberDAO {
	private Connection getConnection() throws ClassNotFoundException,
			SQLException, IOException {

		Connection con = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
		con = DriverManager.getConnection(dbURL, "webdb", "webdb");

		return con;
	}

	public void insert(MemberVO vo) {

		Connection con;
		try {

			con = getConnection();

			String sql = "insert into member values(member_no_seq.nextval, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new MemberDAOException(e.getMessage());
		}

	}

	public MemberVO getMember(MemberVO vo) {

		Connection con;
		MemberVO vo2 = null;

		try {

			con = getConnection();

			String sql = "select * from member where email = ? and password = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				// String password = rs.getString(4);
				String gender = rs.getString(5);

				vo2 = new MemberVO();

				vo2.setNo(no);
				vo2.setName(name);
				vo2.setEmail(email);
				vo2.setGender(gender);

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new MemberDAOException(e.getMessage());
		}
		return vo2;
	}

}
