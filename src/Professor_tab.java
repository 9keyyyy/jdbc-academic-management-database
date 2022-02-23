import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Professor_tab extends JFrame {

	public Panel1 panel1;
	public Panel2 panel2;
	public Panel3 panel3;
	public Panel4 panel4;

	static Connection con;
	Statement stmt, stmt1;
	ResultSet rs, rs1;
	String Driver = "";
	static String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	static String userid = "madang";
	static String pwd = "madang";

	String[] Mon = new String[20];
	String[] Tues = new String[20];
	String[] Wed = new String[20];
	String[] Thurs = new String[20];
	String[] Fri = new String[20];

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

	private class Panel1 extends JPanel {

		JTextArea txtYear, txtProfessor;
		JComboBox<Integer> comboSemester;
		JLabel lblYear, lblSemester, lblProfessor;

		JTable tableResult;
		JTable tableCourseStudent;
		DefaultTableModel model, model4;
		JButton btnEnter;

		String[] header = { "개설학과", "강좌번호", "분반번호", "년도", "학기", "교과목명", "강의요일", "강의실", "강좌교시", "학점", "강좌시간" };
		String[][] data = {};

		String[] header4 = { "학생번호", "이름", "주소", "전화번호", "이메일", "담당교수" };
		String[][] data4 = {};

		Integer year, professor, semester;

		public Panel1() {
			setLayout(null);
			init();
		}

		public Integer txtYear() {
			year = Integer.parseInt(txtYear.getText());
			return year;
		}

		public Integer txtProfessor() {

			professor = Integer.parseInt(txtProfessor.getText());

			return professor;
		}

		public Integer txtSemester() {
			semester = (Integer) comboSemester.getSelectedItem();
			return semester;
		}

		public void init() {

			txtYear = new JTextArea(1, 15);
			comboSemester = new JComboBox<>();
			comboSemester.addItem(1);
			comboSemester.addItem(2);
			lblYear = new JLabel("년도");
			lblSemester = new JLabel("학기");
			lblProfessor = new JLabel("교수 아이디");
			txtProfessor = new JTextArea(1, 10);
			btnEnter = new JButton("검색");

			lblProfessor.setBounds(150, 30, 80, 20);
			txtProfessor.setBounds(230, 30, 100, 20);
			lblYear.setBounds(350, 30, 80, 20);
			txtYear.setBounds(400, 30, 100, 20);
			lblSemester.setBounds(530, 30, 30, 20);
			comboSemester.setBounds(560, 30, 50, 20);
			btnEnter.setBounds(620, 30, 70, 20);

			add(lblProfessor);
			add(txtProfessor);

			add(lblYear);
			add(txtYear);

			add(lblSemester);
			add(comboSemester);

			add(btnEnter);

			// 강의 강좌
			model = new DefaultTableModel(data, header) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableResult = new JTable(model);
			tableResult.setFillsViewportHeight(true);
			tableResult.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JScrollPane js1 = new JScrollPane(tableResult);
			js1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			js1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			tableResult.addMouseListener(new MouseEventListener());
			js1.setBounds(10, 70, 400, 300);
			add(js1);

			// 수강 학생 정보
			model4 = new DefaultTableModel(data4, header4) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableCourseStudent = new JTable(model4);
			tableCourseStudent.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableCourseStudent.setFillsViewportHeight(true);
			tableCourseStudent.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js4 = new JScrollPane(tableCourseStudent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			tableCourseStudent.addMouseListener(new MouseEventListener());
			js4.setBounds(420, 70, 400, 300);
			add(js4);

			btnEnter.addActionListener(new EventHandlerSave());
		}

		class EventHandlerSave implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					stmt = con.createStatement();
					if (e.getSource() == btnEnter) {
						model.setNumRows(0);

						if (txtYear.getText().isEmpty() || txtProfessor.getText().isEmpty())
							JOptionPane.showMessageDialog(null, "아이디와 년도을 모두 작성해주세요");
						else {
							year = Integer.parseInt(txtYear.getText());
							professor = Integer.parseInt(txtProfessor.getText());
							semester = (Integer) comboSemester.getSelectedItem();

							// 강좌
							String query = "select * from course where professor_id=" + professor + " AND course_year="
									+ year + " AND course_semester=" + semester;
							System.out.println(query);

							rs = stmt.executeQuery(query);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
										+ rs.getString(4) + "\t" + rs.getInt(5) + "\t" + rs.getInt(6) + "\t"
										+ rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t"
										+ rs.getInt(10) + "\t" + rs.getInt(11) + "\t" + rs.getInt(12) + "\t" + "\n";
								System.out.println(str);
								Object[] data = new Object[] { rs.getInt(9), rs.getInt(1), rs.getInt(5), rs.getInt(10),
										rs.getInt(11), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(8),
										rs.getInt(6), rs.getInt(7) };
								model.addRow(data);
							}
						}

					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		private class MouseEventListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("성공");

					try {
						stmt = con.createStatement();

						// 수강학생
						if (e.getSource() == tableResult) {
							model4.setNumRows(0);

							int row = tableResult.getSelectedRow();
							int col = tableResult.getSelectedColumn();

							System.out.println(tableResult.getModel().getValueAt(row, 1));

							int id = (int) tableResult.getModel().getValueAt(row, 1);
							String query1 = "select * from student where student_id in (select student_id from student_course where course_id="
									+ id + ")";
							System.out.println(query1);

							rs = stmt.executeQuery(query1);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
										+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + "\n";
								System.out.println(str);
								Object[] data4 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getInt(6) };
								model4.addRow(data4);
							}
						} else if (e.getSource() == tableCourseStudent) {

							year = Integer.parseInt(txtYear.getText());
							professor = Integer.parseInt(txtProfessor.getText());
							semester = (Integer) comboSemester.getSelectedItem();

							int row2 = tableCourseStudent.getSelectedRow();
							int col2 = tableCourseStudent.getSelectedColumn();
							int id2 = (int) tableCourseStudent.getModel().getValueAt(row2, 0);// 수강학생아이디

							int row1 = tableResult.getSelectedRow();
							int col1 = tableResult.getSelectedColumn();

							int id1 = (int) tableResult.getModel().getValueAt(row1, 1);// 강좌 아이디

							InsertScore f = new InsertScore(id1, id2, year, semester);

						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		}

	}
	// panel1

	public class Panel2 extends JPanel {

		JTextArea txtStatus, txtYear, txtProfessor;
		JComboBox<Integer> comboSemester;
		JLabel lblYear, lblSemester, lblProfessor;

		JTable tableStudent;

		JTable tableStudentGrade;
		DefaultTableModel model2, model5;
		JButton btnEnter;

		String[] header2 = { "학생번호", "이름", "주소", "전화번호", "이메일", "담당교수" };
		String[][] data2 = {};

		String[] header5 = { "출결점수", "중간고사", "기말고사", "기타점수", "총점", "성적", "학생번호", "강좌번호", "수강년도", "수강학기" };
		String[][] data5 = {};

		public Panel2() {

			setLayout(null);
			init();

		}

		public void init() {

			txtYear = new JTextArea(1, 15);
			comboSemester = new JComboBox<>();
			comboSemester.addItem(1);
			comboSemester.addItem(2);
			lblYear = new JLabel("년도");
			lblSemester = new JLabel("학기");
			lblProfessor = new JLabel("교수 아이디");
			txtProfessor = new JTextArea(1, 10);
			btnEnter = new JButton("검색");

			lblProfessor.setBounds(150, 30, 80, 20);
			txtProfessor.setBounds(230, 30, 100, 20);
			lblYear.setBounds(350, 30, 80, 20);
			txtYear.setBounds(400, 30, 100, 20);
			lblSemester.setBounds(530, 30, 30, 20);
			comboSemester.setBounds(560, 30, 50, 20);
			btnEnter.setBounds(620, 30, 70, 20);

			add(lblProfessor);
			add(txtProfessor);

			add(lblYear);
			add(txtYear);

			add(lblSemester);
			add(comboSemester);

			add(btnEnter);

			// 학생
			model2 = new DefaultTableModel(data2, header2) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableStudent = new JTable(model2);
			tableStudent.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableStudent.setFillsViewportHeight(true);
			tableStudent.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js2 = new JScrollPane(tableStudent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			tableStudent.addMouseListener(new MouseEventListener());
			js2.setBounds(10, 70, 400, 300);
			add(js2);

			// 학생 성적

			model5 = new DefaultTableModel(data5, header5) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableStudentGrade = new JTable(model5);
			tableStudentGrade.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableStudentGrade.setFillsViewportHeight(true);
			tableStudentGrade.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js5 = new JScrollPane(tableStudentGrade, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			tableStudentGrade.addMouseListener(new MouseEventListener());
			js5.setBounds(420, 70, 400, 300);
			add(js5);

			btnEnter.addActionListener(new EventHandlerSave());
		}

		class EventHandlerSave implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					stmt = con.createStatement();

					if (e.getSource() == btnEnter) {
						model2.setNumRows(0);

						if (txtYear.getText().isEmpty() || txtProfessor.getText().isEmpty())
							JOptionPane.showMessageDialog(null, "아이디와 년도을 모두 작성해주세요");
						else {
							Integer year = Integer.parseInt(txtYear.getText());
							Integer professor = Integer.parseInt(txtProfessor.getText());
							Integer semester = (Integer) comboSemester.getSelectedItem();

							// 학생
							String query1 = "select * from student where student_id in (select student_id from professor_student where professor_id="
									+ professor + " AND semester=" + semester + " AND school_year=" + year + ")";
							System.out.println(query1);

							rs = stmt.executeQuery(query1);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
										+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + "\n";
								System.out.println(str);
								Object[] data2 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5), rs.getInt(6) };
								model2.addRow(data2);
							}
						}
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		private class MouseEventListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("성공");

					try {
						stmt = con.createStatement();
						model5.setNumRows(0);
						if (e.getSource() == tableStudent) {
							int row = tableStudent.getSelectedRow();
							int col = tableStudent.getSelectedColumn();

							System.out.println(tableStudent.getModel().getValueAt(row, 0));

							int id = (int) tableStudent.getModel().getValueAt(row, 0);
							String query1 = "select * from student_course where student_id=" + id;
							System.out.println(query1);

							rs = stmt.executeQuery(query1);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t"
										+ rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getString(6) + "\t"
										+ rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t"
										+ rs.getInt(10) + "\t" + "\n";
								System.out.println(str);
								Object[] data4 = new Object[] { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
										rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
										rs.getInt(10) };
								model5.addRow(data4);
							}

						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		}

	}
	// 2

	public class Panel3 extends JPanel {

		JTextArea txtStatus, txtYear, txtProfessor;
		JComboBox<Integer> comboSemester;
		JLabel lblYear, lblSemester, lblProfessor;

		JTable tableDepartment;
		JTable tableProfessor;
		DefaultTableModel model3, model4;
		JButton btnEnter;

		// 소속학과
		String[] header3 = { "학과번호", "학과이름", "전화번호", "위치" };
		String[][] data3 = {};

		// 교수 정보
		String[] header4 = { "교수번호", "이름", "주소", "전화번호", "이메일" };
		String[][] data4 = {};

		public Panel3() {

			setLayout(null);
			init();

		}

		public void init() {

			txtYear = new JTextArea(1, 15);
			comboSemester = new JComboBox<>();
			comboSemester.addItem(1);
			comboSemester.addItem(2);
			lblYear = new JLabel("년도");
			lblSemester = new JLabel("학기");
			lblProfessor = new JLabel("교수 아이디");
			txtProfessor = new JTextArea(1, 10);
			btnEnter = new JButton("검색");

			lblProfessor.setBounds(150, 30, 80, 20);
			txtProfessor.setBounds(230, 30, 100, 20);
			lblYear.setBounds(350, 30, 80, 20);
			txtYear.setBounds(400, 30, 100, 20);
			lblSemester.setBounds(530, 30, 30, 20);
			comboSemester.setBounds(560, 30, 50, 20);
			btnEnter.setBounds(620, 30, 70, 20);

			add(lblProfessor);
			add(txtProfessor);

			// add(lblYear);
			// add(txtYear);

			// add(lblSemester);
			// add(comboSemester);

			add(btnEnter);

			// 학과
			model3 = new DefaultTableModel(data3, header3) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableDepartment = new JTable(model3);
			tableDepartment.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableDepartment.setFillsViewportHeight(true);
			tableDepartment.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js3 = new JScrollPane(tableDepartment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			tableDepartment.addMouseListener(new MouseEventListener());
			js3.setBounds(10, 70, 310, 300);
			add(js3);

			// 교수 정보
			model4 = new DefaultTableModel(data4, header4) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableProfessor = new JTable(model4);
			tableProfessor.setPreferredScrollableViewportSize(new Dimension(400, 1));
			tableProfessor.setFillsViewportHeight(true);
			tableProfessor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane js4 = new JScrollPane(tableProfessor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			tableProfessor.addMouseListener(new MouseEventListener());
			js4.setBounds(420, 70, 400, 300);
			add(js4);

			btnEnter.addActionListener(new EventHandlerSave());
		}

		class EventHandlerSave implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					stmt = con.createStatement();

					if (e.getSource() == btnEnter) {
						model3.setNumRows(0);

						if (txtProfessor.getText().isEmpty())
							JOptionPane.showMessageDialog(null, "아이디를 작성해주세요");
						else {
							// Integer year = Integer.parseInt(txtYear.getText());
							Integer professor = Integer.parseInt(txtProfessor.getText());
							// Integer semester = (Integer) comboSemester.getSelectedItem();

							// 학과
							String query2 = "select * from department where department_id in(select department_id"
									+ " from professor_has_department" + " where professor_id=" + professor + ")";
							System.out.println(query2);

							rs = stmt.executeQuery(query2);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
										+ rs.getString(4) + "\n";

								System.out.println(str);

								Object[] data3 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4) };
								model3.addRow(data3);
							}
						}
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		private class MouseEventListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("성공");

					try {
						stmt = con.createStatement();

						if (e.getSource() == tableDepartment) {
							model4.setNumRows(0);

							int row = tableDepartment.getSelectedRow();
							int col = tableDepartment.getSelectedColumn();

							System.out.println(tableDepartment.getModel().getValueAt(row, 0));

							int id = (int) tableDepartment.getModel().getValueAt(row, 0);// 학과번호
							String query1 = "select * from professor where professor_id = (select professor_id from professor_has_department where department_id = "
									+ id + " and department_president=1)";
							System.out.println(query1);

							rs = stmt.executeQuery(query1);

							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
										+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + "\n";
								System.out.println(str);
								Object[] data4 = new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3),
										rs.getString(4), rs.getString(5) };
								model4.addRow(data4);
							}
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		}
	}
//3

	public class Panel4 extends JPanel {

		DefaultTableModel model4;
		JTable tableTime;

		String[] header4 = { "\t", "월요일", "화요일", "수요일", "목요일", "금요일" };
		String[][] data4 = {};

		JTextArea txtYear, txtProfessor;
		JComboBox<Integer> comboSemester;
		JLabel lblYear, lblSemester, lblProfessor;
		JButton btnEnter;

		public void init() {

			setLayout(null);

			txtYear = new JTextArea(1, 15);
			comboSemester = new JComboBox<>();
			comboSemester.addItem(1);
			comboSemester.addItem(2);
			lblYear = new JLabel("년도");
			lblSemester = new JLabel("학기");
			lblProfessor = new JLabel("교수 아이디");
			txtProfessor = new JTextArea(1, 10);
			btnEnter = new JButton("검색");

			lblProfessor.setBounds(150, 30, 80, 20);
			txtProfessor.setBounds(230, 30, 100, 20);
			lblYear.setBounds(350, 30, 80, 20);
			txtYear.setBounds(400, 30, 100, 20);
			lblSemester.setBounds(530, 30, 30, 20);
			comboSemester.setBounds(560, 30, 50, 20);
			btnEnter.setBounds(620, 30, 70, 20);

			add(lblProfessor);
			add(txtProfessor);

			add(lblYear);
			add(txtYear);

			add(lblSemester);
			add(comboSemester);

			add(btnEnter);

			model4 = new DefaultTableModel(data4, header4) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			tableTime = new JTable(model4);

			tableTime.setFillsViewportHeight(true);

			JScrollPane js4 = new JScrollPane(tableTime);

			js4.setBounds(10, 70, 780, 250);
			add(js4);

			btnEnter.addActionListener(new EventHandlerSave());
		}

		public Panel4() {
			init();
		}

		class EventHandlerSave implements ActionListener {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == btnEnter) {
					model4.setNumRows(0);

					if (txtYear.getText().isEmpty() || txtProfessor.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "아이디와 년도을 모두 작성해주세요");
						return;
					}

					Mon = new String[20];
					Tues = new String[20];
					Wed = new String[20];
					Thurs = new String[20];
					Fri = new String[20];

					Integer year = Integer.parseInt(txtYear.getText());
					Integer professor = Integer.parseInt(txtProfessor.getText());
					Integer semester = (Integer) comboSemester.getSelectedItem();

					try {
						// 학생 정보 불러옴
						String query = "select course_id from course where professor_id=" + professor
								+ " and course_year=" + year + " and course_semester=" + semester;
						stmt = con.createStatement();
						rs = stmt.executeQuery(query);

						int coursesum = 0;
						double gradesum = 0;
						while (rs.next()) {

							// 강의 정보 출력
							String query1 = "select * from course where course_id = " + rs.getString(1);
							stmt1 = con.createStatement();
							rs1 = stmt1.executeQuery(query1);
							while (rs1.next()) {
								Object[] data = new Object[] { rs1.getInt(1), rs1.getString(2), rs1.getString(3),
										rs1.getString(4), rs1.getInt(5), rs1.getInt(6), rs1.getInt(7), rs1.getInt(8),
										rs1.getInt(9), rs1.getInt(10), rs1.getInt(11), rs1.getInt(12) };
								makeTable(rs1.getString(2), rs1.getString(3), rs1.getInt(7), rs1.getInt(8) - 1);
							}

						}
						// 시간표 출력
						String[] period = { "1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시", "10교시",
								"11교시", "12교시", "13교시", "14교시" };
						for (int i = 0; i < 14; i++) {
							Object[] data4 = new Object[] { period[i], Mon[i], Tues[i], Wed[i], Thurs[i], Fri[i] };
							model4.addRow(data4);
						}
					} catch (Exception e8) {

						JOptionPane.showMessageDialog(null, "쿼리 읽기 실패1 : " + e8 + "\n");
					}

				}
			}
		}
	}
// 4

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

	public void init() {

		panel1 = new Panel1();
		panel2 = new Panel2();
		panel3 = new Panel3();
		panel4 = new Panel4();
		setTitle("교수");
		JTabbedPane jtab = new JTabbedPane();

		jtab.addTab("강의과목", panel1);
		jtab.addTab("지도학생", panel2);
		jtab.addTab("학과정보", panel3);
		jtab.addTab("시간표", panel4);

		add(jtab);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(850, 500);
		setVisible(true);
	}

	public Professor_tab() {
		init();
		conDB();
	}
}
