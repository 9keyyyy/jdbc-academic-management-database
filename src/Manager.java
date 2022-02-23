import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Manager extends JFrame implements ActionListener {
	JButton circle, circle_has_student, course, department, department_has_student, professor, professor_has_department,
			professor_student, student, student_course, tuition;
	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
	JScrollPane js, js1, js2, js3, js4, js5, js6, js7, js8, js9, js10;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	DefaultTableModel model, model1, model2, model3, model4, model5, model6, model7, model8, model9, model10;

	JTable tblcircle, tblcircle_has_student, tblcourse, tbldepartment, tbldepartment_has_student, tblprofessor,
			tblprofessor_has_department, tblprofessor_student, tblstudent, tblstudent_course, tbltuition;

	String[] header = { "동아리번호", "동아리이름", "동아리방", "지도교수", "소속학생수" };
	String[][] data = {};

	String[] header1 = { "동아리번호", "학생번호", "동아리회장" };
	String[][] data1 = {};

	String[] header2 = { "강좌번호", "강좌이름", "강의요일", "강의실", "분반번호", "취득학점", "강좌시간", "강의교시", "개설학과번호", "강좌학년", "강좌학기",
			"강의교수" };
	String[][] data2 = {};

	String[] header3 = { "학과번호", "학과이름", "학과전화번호", "학과사무실" };
	String[][] data3 = {};

	String[] header4 = { "학과번호", "학생번호" };
	String[][] data4 = {};

	String[] header5 = { "교수번호", "교수이름", "교수주소", "교수전화번호", "교수이메일" };
	String[][] data5 = {};

	String[] header6 = { "교수번호", "학과번호", "학과장" };
	String[][] data6 = {};

	String[] header7 = { "지도학기", "지도학년", "학생번호", "교수번호" };
	String[][] data7 = {};

	String[] header8 = { "학생번호", "학생이름", "학생주소", "학생전화번호", "학생이메일", "지도교수번호" };
	String[][] data8 = {};

	String[] header9 = { "출석점수", "중간고사점수", "기말고사점수", "기타점수", "총점", "평점", "학생번호", "강좌번호", "학생수강학년", "학생수강학기" };
	String[][] data9 = {};

	String[] header10 = { "학생번호", "등록금납부연도", "등록금납부학기", "마지막납부일자", "납부총액", "등록금총액", "등록금계좌번호" };
	String[][] data10 = {};

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");

			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void layInit() {
		setLayout(null);
		setTitle("전체 테이블");
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		p11 = new JPanel();

		model = new DefaultTableModel(data, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblcircle = new JTable(model);
		tblcircle.setPreferredScrollableViewportSize(new Dimension(350, 200));
		JScrollPane js = new JScrollPane(tblcircle, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);// scrollpane이렇게
		// js.setBounds(0, 0, 350, 200);
		p1.add(js);
		p1.setBounds(0, 0, 400, 280);
		p1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리 테이블"));

		model1 = new DefaultTableModel(data1, header1) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblcircle_has_student = new JTable(model1);
		tblcircle_has_student.setPreferredScrollableViewportSize(new Dimension(200, 200));
		JScrollPane js1 = new JScrollPane(tblcircle_has_student, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// js1.setBounds(0, 0, 200, 200);
		p2.add(js1);
		p2.setBounds(410, 0, 300, 280);
		p2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리,학생 테이블"));

		model2 = new DefaultTableModel(data2, header2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblcourse = new JTable(model2);
		tblcourse.setPreferredScrollableViewportSize(new Dimension(650, 200));
		JScrollPane js2 = new JScrollPane(tblcourse, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js2.setBounds(0, 0, 200, 200);
		js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p3.add(js2);

		p3.setBounds(720, 0, 700, 280);
		p3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "강좌 테이블"));

		model3 = new DefaultTableModel(data3, header3) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbldepartment = new JTable(model3);
		tbldepartment.setPreferredScrollableViewportSize(new Dimension(300, 200));
		JScrollPane js3 = new JScrollPane(tbldepartment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js3.setBounds(0, 0, 500, 400);
		js3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p4.add(js3);

		p4.setBounds(0, 290, 400, 280);
		p4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과 테이블"));

		model4 = new DefaultTableModel(data4, header4) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbldepartment_has_student = new JTable(model4);
		tbldepartment_has_student.setPreferredScrollableViewportSize(new Dimension(100, 200));
		JScrollPane js4 = new JScrollPane(tbldepartment_has_student, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js4.setBounds(0, 0, 400, 400);
		js4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p5.add(js4);

		p5.setBounds(410, 290, 200, 280);
		p5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과,학생 테이블"));

		model5 = new DefaultTableModel(data5, header5) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblprofessor = new JTable(model5);
		tblprofessor.setPreferredScrollableViewportSize(new Dimension(400, 200));
		JScrollPane js5 = new JScrollPane(tblprofessor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js5.setBounds(0, 0, 400, 400);
		js5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p6.add(js5);

		p6.setBounds(620, 290, 450, 280);
		p6.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수 테이블"));

		model6 = new DefaultTableModel(data6, header6) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblprofessor_has_department = new JTable(model6);
		tblprofessor_has_department.setPreferredScrollableViewportSize(new Dimension(200, 200));
		JScrollPane js6 = new JScrollPane(tblprofessor_has_department, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js6.setBounds(0, 0, 400, 400);
		js6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js6.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p7.add(js6);

		p7.setBounds(0, 580, 300, 280);
		p7.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과,교수 테이블"));

		model7 = new DefaultTableModel(data7, header7) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblprofessor_student = new JTable(model7);
		tblprofessor_student.setPreferredScrollableViewportSize(new Dimension(300, 200));
		JScrollPane js7 = new JScrollPane(tblprofessor_student, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js7.setBounds(0, 0, 400, 400);
		js7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js7.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p8.add(js7);
		p8.setBounds(310, 580, 400, 280);
		p8.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수,학생 테이블"));

		model8 = new DefaultTableModel(data8, header8) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblstudent = new JTable(model8);
		tblstudent.setPreferredScrollableViewportSize(new Dimension(400, 200));
		JScrollPane js8 = new JScrollPane(tblstudent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js8.setBounds(0, 0, 400, 400);
		js8.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js8.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p9.add(js8);
		p9.setBounds(720, 580, 500, 280);
		p9.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학생 테이블"));

		model9 = new DefaultTableModel(data9, header9) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblstudent_course = new JTable(model9);
		tblstudent_course.setPreferredScrollableViewportSize(new Dimension(600, 200));
		JScrollPane js9 = new JScrollPane(tblstudent_course, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js9.setBounds(1330, 620, 400, 300);
		js9.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js9.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p10.add(js9);

		p10.setBounds(1080, 290, 650, 280);
		p10.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "수강내역 테이블"));

		model10 = new DefaultTableModel(data10, header10) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbltuition = new JTable(model10);
		tbltuition.setPreferredScrollableViewportSize(new Dimension(600, 200));
		JScrollPane js10 = new JScrollPane(tbltuition, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// js10.setBounds(0, 0, 400, 400);
		js10.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		js10.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p11.add(js10);

		p11.setBounds(1220, 580, 650, 280);
		p11.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "등록금 테이블"));

		// 3

		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p6);
		add(p7);
		add(p8);
		add(p9);
		add(p10);
		add(p11);

		addWindowListener(new JFrameWindowClosingEventHandler());// 0601
	}

	public Manager() {
		layInit();
		conDB();
		setVisible(true);
		setBounds(20, 20, 1900, 900); // 가로위치,세로위치,가로길이,세로길이
		inputData();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void inputData() {

		// 1
		try {

			String query = "select * from circle";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(5) };
				model.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 2
		try {

			String query1 = "select * from circle_has_student";
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3) };
				model1.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 3

		try {

			String query2 = "select * from course";
			rs = stmt.executeQuery(query2);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
						rs.getInt(11), rs.getInt(12) };
				model2.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 4

		try {

			String query3 = "select * from department";
			rs = stmt.executeQuery(query3);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) };
				model3.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			String query4 = "select * from department_has_student";
			rs = stmt.executeQuery(query4);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2) };
				model4.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 5
		try {

			String query5 = "select * from professor";
			rs = stmt.executeQuery(query5);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) };
				model5.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 6
		try {

			String query6 = "select * from professor_has_department";
			rs = stmt.executeQuery(query6);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3) };
				model6.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 7
		try {

			String query7 = "select * from professor_student";
			rs = stmt.executeQuery(query7);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4) };
				model7.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 8
		try {
			String query8 = "select * from student";
			rs = stmt.executeQuery(query8);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6) };
				model8.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 9
		try {
			String query9 = "select * from student_course";
			rs = stmt.executeQuery(query9);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10) };
				model9.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 10
		try {
			String query10 = "select * from tuition";
			rs = stmt.executeQuery(query10);
			while (rs.next()) {
				Object[] data = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getInt(6), rs.getString(7) };
				model10.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	class JFrameWindowClosingEventHandler extends WindowAdapter {// 0601

		public void windowClosing(WindowEvent we) {
			try {
				con.close();
			} catch (Exception e4) {

			}
			System.out.println("관리자 프로그램 완전 종료~!");
		}
	}

}
