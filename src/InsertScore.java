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
import javax.swing.JTextArea;

public class InsertScore extends JFrame implements ActionListener {

	JTextArea attendance, mid, final_score, etc, total, grade;
	JLabel lblAtt, lblMid, lblFinal, lblEtc, lblTotal, lblGrade;
	JPanel p1;
	JButton btnEnter1, btnCancel;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	Integer iatt, imid, ifinal, iect, itotal;
	String cgrade;

	Integer course, student, year, semester;

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� �ε� ����");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* �����ͺ��̽��� �����ϴ� ���� */
			System.out.println("�����ͺ��̽� ���� �غ�...");

			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("�����ͺ��̽� ���� ����");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void layInit() {

		p1 = new JPanel();

		attendance = new JTextArea(1, 10);
		mid = new JTextArea(1, 10);
		final_score = new JTextArea(1, 10);
		etc = new JTextArea(1, 10);
		total = new JTextArea(1, 10);
		grade = new JTextArea(1, 10);

		lblAtt = new JLabel("�⼮����");
		lblMid = new JLabel("�߰��������");
		lblFinal = new JLabel("�⸻�������");
		lblEtc = new JLabel("��Ÿ����");
		lblTotal = new JLabel("����");
		lblGrade = new JLabel("����");

		JButton btnEnter1 = new JButton("�Է�");
		JButton btnCancel = new JButton("���");

		p1.setLayout(null);

		p1.add(attendance);
		attendance.setBounds(200, 40, 100, 20);

		p1.add(mid);
		mid.setBounds(200, 80, 100, 20);

		p1.add(final_score);
		final_score.setBounds(200, 120, 100, 20);

		p1.add(etc);
		etc.setBounds(200, 160, 100, 20);

		p1.add(total);
		total.setBounds(200, 200, 100, 20);

		p1.add(grade);
		grade.setBounds(200, 240, 100, 20);

		p1.add(lblAtt);
		lblAtt.setBounds(100, 40, 100, 20);

		p1.add(lblMid);
		lblMid.setBounds(100, 80, 100, 20);

		p1.add(lblFinal);
		lblFinal.setBounds(100, 120, 100, 20);

		p1.add(lblEtc);
		lblEtc.setBounds(100, 160, 100, 20);

		p1.add(lblTotal);
		lblTotal.setBounds(100, 200, 100, 20);

		p1.add(lblGrade);
		lblGrade.setBounds(100, 240, 100, 20);

		btnEnter1.addActionListener(new ActionListener() {// 0603
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				if (b.getText().equals("Action"))
					b.setText("�׼�");
				else {
					
					try {
						if (attendance.getText().isEmpty() || mid.getText().isEmpty() || final_score.getText().isEmpty()|| etc.getText().isEmpty() || total.getText().isEmpty() || grade.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "��ĭ�� ��� ä���ּ���\n");
							return;
						} else {
							System.out.println("��[��");
							stmt = con.createStatement();
							iatt = Integer.parseInt(attendance.getText());
							imid = Integer.parseInt(mid.getText());
							ifinal = Integer.parseInt(final_score.getText());
							iect = Integer.parseInt(etc.getText());
							itotal = Integer.parseInt(total.getText());
							cgrade = grade.getText();

							System.out.println(iatt);

							String query = "update `madang`.`student_course` set `attendance_score`=" + iatt
									+ ", `mid_score`=" + imid + ", `final_score`=" + ifinal + ", `etc_score`=" + iect
									+ ", `total_score`=" + itotal + ", `grade`='" + cgrade + "' where `student_id`="
									+ student + " and `course_id`=" + course;
							System.out.println(query);
							stmt.executeUpdate(query);
							JOptionPane.showMessageDialog(null,"�ԷµǾ����ϴ�!");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,e1);
						e1.printStackTrace();
					}

				}
			}
		});

		p1.add(btnEnter1);
		btnEnter1.setBounds(80, 280, 100, 20);

		p1.add(btnCancel);
		btnCancel.setBounds(230, 280, 100, 20);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				if (b == btnCancel) {
					dispose();
					try {
						con.close();
					} catch (Exception e4) {

					}
					System.out.println("�Է� ���α׷� ���� ����~!");
				}
			}
			// setTitle(b.getText());
		});// 0603

		add(p1);

		addWindowListener(new JFrameWindowClosingEventHandler());// 0601
	}

	public InsertScore(int course, int student, int year, int semester) {

		this.course = course;
		this.student = student;
		this.year = year;
		this.semester = semester;

		conDB();
		layInit();
		setTitle("�����Է�");
		setVisible(true);
		setSize(400, 400); // ������ġ,������ġ,���α���,���α���

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
			System.out.println("�Է� ���α׷� ���� ����~!");
		}
	}

}
