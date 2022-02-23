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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Student extends JFrame implements ActionListener {

	JButton btnSearch;
	JTextField id, year, semester;
	JPanel pn1;
	JLabel label, label2, label3, labelGPA, labelCircle, labelp;
	JTabbedPane jtab;
	CourseInfo tab1;
	GradeInfo tab2;
	CircleInfo tab3;
	TimeTable tab4;

	static Connection con;
	Statement stmt, stmt1;
	ResultSet rs, rs1;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	DefaultTableModel model, model1, model2, model3, model4;
	String[] header = { "강의번호", "강의이름", "강의요일", "강의실", "분반번호", "취득학점", "강의시간", "강의교시", "개설학과", "강의학년", "강의학기", "강의교수" };
	String[][] data = {};

	String[] header1 = { "출석점수", "중간점수", "기말점수", "기타점수", "총점", "평점", "학번", "강의번호", "강의명", "연도", "학기" };
	String[][] data1 = {};

	String[] header2 = { "동아리번호", "동아리이름", "동아리방", "지도교수번호", "소속학생수" };
	String[][] data2 = {};

	String[] header3 = { "학생번호", "학생이름", "학생주소", "전화번호", "이메일", "지도교수번호" };
	String[][] data3 = {};

	String[] header4 = { "\t", "월요일", "화요일", "수요일", "목요일", "금요일" };
	String[][] data4 = {};

	JTable tableCourse;
	JTable tableGrade;
	JTable tableCircle;
	JTable tableCprinciple;
	JTable tableTime;

	String[] Mon = new String[20];
	String[] Tues = new String[20];
	String[] Wed = new String[20];
	String[] Thurs = new String[20];
	String[] Fri = new String[20];

	Student() {
		super("학생");
		conDB();
		Initstudent();
		setBounds(100, 100, 900, 380);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void Initstudent() {

		btnSearch = new JButton("입력");

		year = new JTextField(4);
		semester = new JTextField(2);
		id = new JTextField(2);

		label = new JLabel("년도");
		label2 = new JLabel("학기");
		label3 = new JLabel("학번");
		labelGPA = new JLabel("GPA :");
		labelCircle = new JLabel("동아리원");
		labelp = new JLabel("소속동아리");

		pn1 = new JPanel();
		pn1.add(label3);
		pn1.add(id);
		pn1.add(label);
		pn1.add(year);
		pn1.add(label2);
		pn1.add(semester);

		pn1.add(btnSearch);

		tab1 = new CourseInfo();
		tab2 = new GradeInfo();
		tab3 = new CircleInfo();
		tab4 = new TimeTable();
		JTabbedPane jtab = new JTabbedPane();
		jtab.add("강의 정보", tab1);
		jtab.add("성적", tab2);
		jtab.add("동아리", tab3);
		jtab.add("시간표", tab4);

		add("North", pn1);
		add(jtab);
		btnSearch.addActionListener(this);

		addWindowListener(new JFrameWindowClosingEventHandler());
	}

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

	@SuppressWarnings("serial")
	class TimeTable extends JPanel {
		public TimeTable() {
			setLayout(null);
			model4 = new DefaultTableModel(data4, header4) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			tableTime = new JTable(model4);
			tableTime.setFillsViewportHeight(true);
			JScrollPane js4 = new JScrollPane(tableTime);
			js4.setBounds(10, 10, 850, 250);
			add(js4);

		}

	}

	class CircleInfo extends JPanel {
		public CircleInfo() {
			setLayout(null);
			model2 = new DefaultTableModel(data2, header2) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			model3 = new DefaultTableModel(data3, header3) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableCircle = new JTable(model2);

			tableCircle.setFillsViewportHeight(true);
			tableCprinciple = new JTable(model3);
			tableCprinciple.setFillsViewportHeight(true);
			tableCprinciple.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js3 = new JScrollPane(tableCircle);
			JScrollPane js4 = new JScrollPane(tableCprinciple);

			labelp.setBounds(10, 5, 200, 20);
			labelCircle.setBounds(430, 5, 200, 20);
			js3.setBounds(10, 25, 400, 230);
			js4.setBounds(430, 25, 400, 230);
			add(js3);
			add(js4);
			add(labelp);
			add(labelCircle);
		}

	}

	@SuppressWarnings("serial")
	class GradeInfo extends JPanel {
		public GradeInfo() {
			setLayout(null);
			model1 = new DefaultTableModel(data1, header1) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableGrade = new JTable(model1);
			tableGrade.setFillsViewportHeight(true);

			JScrollPane js2 = new JScrollPane(tableGrade);
			js2.setBounds(10, 25, 850, 230);
			labelGPA.setBounds(10, 5, 200, 20);
			add(labelGPA);
			add(js2);

		}

	}

	@SuppressWarnings("serial")
	class CourseInfo extends JPanel {

		public CourseInfo() {
			setLayout(null);
			model = new DefaultTableModel(data, header) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableCourse = new JTable(model);
			tableCourse.setFillsViewportHeight(true);
			tableCourse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JScrollPane js1 = new JScrollPane(tableCourse);
			js1.setBounds(10, 10, 850, 250);
			add(js1);

		}
	}

	public double returngrade(String str) {
		if (str.equals("A+"))
			return 4.5;
		else if (str.equals("A0"))
			return 4.0;
		else if (str.equals("B+"))
			return 3.5;
		else if (str.equals("B0"))
			return 3.0;
		else if (str.equals("C+"))
			return 2.5;
		else if (str.equals("C0"))
			return 2.0;
		else if (str.equals("D+"))
			return 1.5;
		else if (str.equals("D0"))
			return 1.0;
		else
			return 0;
	}

	public void makeTable(String name, String day, int hour, int period) {
		if (day.contains("월")) {
			int duration = hour;
			while (duration != 0) {
				Mon[period + duration - 1] = name;
				duration--;
			}
		}
		if (day.contains("화")) {
			int duration = hour;
			while (duration != 0) {
				Tues[period + duration - 1] = name;
				duration--;
			}
		}
		if (day.contains("수")) {
			int duration = hour;
			while (duration != 0) {
				Wed[period + duration - 1] = name;
				duration--;
			}
		}
		if (day.contains("목")) {
			int duration = hour;
			while (duration != 0) {
				Thurs[period + duration - 1] = name;
				duration--;
			}
		}
		if (day.contains("금")) {
			int duration = hour;
			while (duration != 0) {
				Fri[period + duration - 1] = name;
				duration--;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			model.setNumRows(0);
			model1.setNumRows(0);
			model2.setNumRows(0);
			model3.setNumRows(0);
			model4.setNumRows(0);

			Mon = new String[20];
			Tues = new String[20];
			Wed = new String[20];
			Thurs = new String[20];
			Fri = new String[20];
			String y = year.getText();
			String s = semester.getText();
			String studentid = id.getText();

			try {
				// 학생 정보 불러옴
				String query = "select * from student_course" + " where student_id = " + studentid
						+ " and student_course_year = " + y + " and student_course_semester = " + s;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);

				int coursesum = 0;
				double gradesum = 0;
				while (rs.next()) {
					// 성적 정보 출력(현재 학기)
//					coursesum++;
//					Object[] data1 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//							rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10) };
//					model1.addRow(data1);
//					if (rs.getString(6) != null)
//						gradesum += returngrade(rs.getString(6));

					// 강의 정보 출력
					String query1 = "select * from course where course_id = " + rs.getString(8);
					stmt1 = con.createStatement();
					rs1 = stmt1.executeQuery(query1);
					while (rs1.next()) {
						Object[] data = new Object[] { rs1.getInt(1), rs1.getString(2), rs1.getString(3),
								rs1.getString(4), rs1.getInt(5), rs1.getInt(6), rs1.getInt(7), rs1.getInt(8),
								rs1.getInt(9), rs1.getInt(10), rs1.getInt(11), rs1.getInt(12) };
						model.addRow(data);
						makeTable(rs1.getString(2), rs1.getString(3), rs1.getInt(7), rs1.getInt(8) - 1);
					}

				}
				// 시간표 출력
				String[] period = { "1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시", "10교시", "11교시",
						"12교시", "13교시", "14교시" };
				for (int i = 0; i < 14; i++) {
					Object[] data4 = new Object[] { period[i], Mon[i], Tues[i], Wed[i], Thurs[i], Fri[i] };
					model4.addRow(data4);
				}
				
				// 성적정보 출력 (전체 학기)
				query = "select * from student_course where student_id = "+studentid;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					coursesum++;
					String query1 = "select course_name from course where course_id = "+ rs.getInt(8);
					stmt1 = con.createStatement();
					rs1 = stmt1.executeQuery(query1);
					rs1.next();
					Object[] data1 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs1.getString(1), rs.getInt(9), rs.getInt(10) };
					model1.addRow(data1);
					if (rs.getString(6) != null)
						gradesum += returngrade(rs.getString(6));
				}

				if (coursesum != 0)
					labelGPA.setText("GPA : " + Double.toString(gradesum / coursesum));
				else
					labelGPA.setText("GPA : ");

			} catch (Exception e8) {

				JOptionPane.showMessageDialog(null, "쿼리 읽기 실패1 : " + e8 + "\n");
			}
			try {
				// 동아리 정보 출력
				String query = "select circle_id from circle_has_student where student_id = " + studentid;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String query1 = "select * from circle where circle_id = " + rs.getString(1);
					stmt1 = con.createStatement();
					rs1 = stmt1.executeQuery(query1);
					while (rs1.next()) {
						Object[] data2 = new Object[] { rs1.getInt(1), rs1.getString(2), rs1.getString(3),
								rs1.getInt(4), rs1.getInt(5) };
						model2.addRow(data2);

					}
				}
				// 동아리 회장인지 확인
				query = " select circle_id from circle_has_student where student_id= " + studentid
						+ " and circle_principle = 1";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);

				if (rs.next() != false) {

					int circleid = rs.getInt(1);
					query = "select circle_name from circle where circle_id = " + circleid;
					stmt = con.createStatement();
					stmt.executeQuery(query);
					rs = stmt.executeQuery(query);
					rs.next();
					labelCircle.setText("동아리원 (" + rs.getString(1) + ")");

					query = "select student_id from circle_has_student where circle_id = " + circleid;
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						String query1 = "select * from student where student_id = " + rs.getString(1);
						stmt1 = con.createStatement();
						stmt1.executeQuery(query1);
						rs1 = stmt1.executeQuery(query1);
						while (rs1.next()) {
							Object[] data3 = new Object[] { rs1.getInt(1), rs1.getString(2), rs1.getString(3),
									rs1.getString(4), rs1.getString(5), rs1.getInt(6) };
							model3.addRow(data3);

						}
					}
				} else
					labelCircle.setText("동아리원 (조회 불가)");

			} catch (Exception e9) {
				JOptionPane.showMessageDialog(null, "쿼리 읽기 실패2 : " + e9 + "\n");
			}

		}
	}

	class JFrameWindowClosingEventHandler extends WindowAdapter {

		public void windowClosing(WindowEvent we) {
			try {
				con.close();
			} catch (Exception e4) {

			}
			System.out.println("프로그램 완전 종료~!");
		}
	}

}