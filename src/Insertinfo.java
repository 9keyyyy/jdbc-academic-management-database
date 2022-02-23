import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Insertinfo extends JFrame implements ActionListener {

	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
	JButton btn1, btn2, btn3, btn4, btn5, btncancel, btn6, btn7, btn8, btn9, btn10, btn11;

	JLabel lid, lname, laddr, lphone, lemail;
	JTextField id, name, addr, phone, email;

	JLabel lcid, lcname, lcdate, lcaddr, lcdiv, lcscore, lchour, lcperiod, lcyear, lcsem;
	JTextField cid, cname, cdate, caddr, cdiv, cscore, chour, cperiod, cyear, csem;
	JLabel lccid, lccaddr, lccname;
	JTextField ccid, ccaddr, ccname;
	JLabel ldid, ldname, ldphone, ldaddr;
	JTextField did, dname, dphone, daddr;
	JLabel lpid, lpname, lpaddr, lpphone, lpemail;
	JTextField pid, pname, paddr, pphone, pemail;
	JLabel ltid, ltyear, ltsem, ltupdate, ltpaid, ltfee, ltnum;
	JTextField tid, tyear, tsem, tupdate, tpaid, tfee, tnum;
	JLabel lcscid, lcssid;
	JTextField cscid, cssid;
	JLabel lblAtt, lblMid, lblFinal, lblEtc, lblTotal, lblGrade, lscyear, lscsem, lscsid, lsccid;
	JTextField attendance, mid, final_score, etc, total, grade, scyear, scsem, scsid, sccid;
	JLabel lpssem, lpsyear, lpssid, lpspid;
	JTextField pssem, psyear, pssid, pspid;
	JLabel lpdpid, lpddid;
	JTextField pdpid, pddid;
	JLabel ldsdid, ldssid;
	JTextField dsdid, dssid;

	static Connection con;
	Statement stmt, stmt1;
	ResultSet rs, rs1;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public Insertinfo() {
		setTitle("입력");
		layInit();
		setVisible(true);
		conDB();
		setBounds(200, 50, 1000, 970); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void layInit() {
		setLayout(null);

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

		btn1 = new JButton("저장");
		btn2 = new JButton("저장");
		btn3 = new JButton("저장");
		btn4 = new JButton("저장");
		btn5 = new JButton("저장");
		btn6 = new JButton("저장");
		btncancel = new JButton("취소");
		btn7 = new JButton("저장");
		btn8 = new JButton("저장");
		btn9 = new JButton("저장");
		btn10 = new JButton("저장");
		btn11 = new JButton("저장");

		lid = new JLabel("학번");
		lname = new JLabel("이름");
		laddr = new JLabel("주소");
		lphone = new JLabel("전화번호");
		lemail = new JLabel("이메일");

		lcid = new JLabel("강의번호");
		lcname = new JLabel("강의명");
		lcaddr = new JLabel("강의실");
		lcdate = new JLabel("요일");
		lcdiv = new JLabel("분반");
		lcscore = new JLabel("학점");
		lchour = new JLabel("시간");
		lcperiod = new JLabel("교시");
		lcyear = new JLabel("연도");
		lcsem = new JLabel("학기");
		ldid = new JLabel("학과번호");
		lpid = new JLabel("교수번호");

		lccid = new JLabel("동아리번호");
		lccname = new JLabel("동아리명");
		lccaddr = new JLabel("동아리방");

		ldphone = new JLabel("전화번호");
		ldname = new JLabel("학과명");
		ldaddr = new JLabel("학과사무실");

		lpname = new JLabel("교수명");
		lpaddr = new JLabel("교수주소");
		lpphone = new JLabel("전화번호");
		lpemail = new JLabel("이메일");

		lblAtt = new JLabel("출석점수");
		lblMid = new JLabel("중간점수");
		lblFinal = new JLabel("기말점수");
		lblEtc = new JLabel("기타점수");
		lblTotal = new JLabel("총점");
		lblGrade = new JLabel("평점");
		lscyear = new JLabel("학기");
		lscsem = new JLabel("연도");
		lscsid = new JLabel("학생번호");
		lsccid = new JLabel("강의번호");

		lcscid = new JLabel("동아리번호");
		lcssid = new JLabel("학생번호");

		ltid = new JLabel("학생번호");
		ltyear = new JLabel("납부년도");
		ltsem = new JLabel("납부학기");
		ltupdate = new JLabel("마지막납부일자");
		ltpaid = new JLabel("납부총액");
		ltfee = new JLabel("등록금총액");
		ltnum = new JLabel("계좌번호");

		lpsyear = new JLabel("연도");
		lpssem = new JLabel("학기");
		lpssid = new JLabel("학생번호");
		lpspid = new JLabel("교수번호");

		lpdpid = new JLabel("교수번호");
		lpddid = new JLabel("학과번호");

		ldsdid = new JLabel("학과번호");
		ldssid = new JLabel("학생번호");

		dsdid = new JTextField(2);
		dssid = new JTextField(2);

		pdpid = new JTextField(2);
		pddid = new JTextField(2);

		psyear = new JTextField(3);
		pssem = new JTextField(2);
		pssid = new JTextField(2);
		pspid = new JTextField(2);

		tid = new JTextField(2);
		tyear = new JTextField(2);
		tsem = new JTextField(2);
		tupdate = new JTextField(5);
		tpaid = new JTextField(3);
		tfee = new JTextField(3);
		tnum = new JTextField(5);

		cscid = new JTextField(2);
		cssid = new JTextField(2);

		attendance = new JTextField(2);
		mid = new JTextField(2);
		final_score = new JTextField(2);
		etc = new JTextField(2);
		total = new JTextField(2);
		grade = new JTextField(2);
		scyear = new JTextField(2);
		scsem = new JTextField(2);
		scsid = new JTextField(2);
		sccid = new JTextField(2);

		pname = new JTextField(3);
		paddr = new JTextField(5);
		pphone = new JTextField(5);
		pemail = new JTextField(5);

		dphone = new JTextField(5);
		dname = new JTextField(5);
		daddr = new JTextField(2);

		ccid = new JTextField(2);
		ccaddr = new JTextField(3);
		ccname = new JTextField(3);

		id = new JTextField(2);
		name = new JTextField(5);
		addr = new JTextField(7);
		phone = new JTextField(7);
		email = new JTextField(7);

		cid = new JTextField(2);
		cname = new JTextField(5);
		caddr = new JTextField(3);
		cdate = new JTextField(3);
		cdiv = new JTextField(2);
		cscore = new JTextField(2);
		chour = new JTextField(2);
		cperiod = new JTextField(2);
		cyear = new JTextField(2);
		csem = new JTextField(2);
		did = new JTextField(5);
		pid = new JTextField(2);

		p1.setLayout(new FlowLayout());
		p1.setBounds(10, 10, 970, 70);
		p1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학생"));

		p1.add(lid);
		p1.add(id);
		p1.add(lname);
		p1.add(name);
		p1.add(laddr);
		p1.add(addr);
		p1.add(lphone);
		p1.add(phone);
		p1.add(lemail);
		p1.add(email);
		p1.add(btn1);
		add(p1);

		p2.setLayout(new FlowLayout());
		p2.setBounds(10, 90, 970, 70);
		p2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "강의"));

		p2.add(lcid);
		p2.add(cid);
		p2.add(lcname);
		p2.add(cname);
		p2.add(lcaddr);
		p2.add(caddr);
		p2.add(lcdate);
		p2.add(cdate);
		p2.add(lcdiv);
		p2.add(cdiv);
		p2.add(lcscore);
		p2.add(cscore);
		p2.add(lchour);
		p2.add(chour);
		p2.add(lcperiod);
		p2.add(cperiod);
		p2.add(lcyear);
		p2.add(cyear);
		p2.add(lcsem);
		p2.add(csem);
		p2.add(ldid);
		p2.add(did);
		p2.add(lpid);
		p2.add(pid);
		p2.add(btn2);
		add(p2);

		p3.setLayout(new FlowLayout());
		p3.setBounds(10, 170, 970, 70);
		p3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리"));

		p3.add(lccid);
		p3.add(ccid);
		p3.add(lccname);
		p3.add(ccname);
		p3.add(lccaddr);
		p3.add(ccaddr);
		p3.add(btn3);
		add(p3);

		p4.setLayout(new FlowLayout());
		p4.setBounds(10, 250, 970, 70);
		p4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과"));
		p4.add(ldid);
		p4.add(did);
		p4.add(ldname);
		p4.add(dname);
		p4.add(ldphone);
		p4.add(dphone);
		p4.add(ldaddr);
		p4.add(daddr);
		p4.add(btn4);
		add(p4);

		p5.setLayout(new FlowLayout());
		p5.setBounds(10, 330, 970, 70);
		p5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수"));
		p5.add(lpid);
		p5.add(pid);
		p5.add(lpname);
		p5.add(pname);
		p5.add(lpaddr);
		p5.add(paddr);
		p5.add(lpphone);
		p5.add(pphone);
		p5.add(lpemail);
		p5.add(pemail);
		p5.add(btn5);
		add(p5);

		p6.setLayout(new FlowLayout());
		p6.setBounds(10, 410, 970, 70);
		p6.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "수강내역"));
		p6.add(lblAtt);
		p6.add(attendance);
		p6.add(lblMid);
		p6.add(mid);
		p6.add(lblFinal);
		p6.add(final_score);
		p6.add(lblEtc);
		p6.add(etc);
		p6.add(lblTotal);
		p6.add(total);
		p6.add(lblGrade);
		p6.add(grade);
		p6.add(lscyear);
		p6.add(scyear);
		p6.add(lscsem);
		p6.add(scsem);
		p6.add(lscsid);
		p6.add(scsid);
		p6.add(lsccid);
		p6.add(sccid);
		p6.add(btn6);
		add(p6);

		p7.add(lcscid);
		p7.add(cscid);
		p7.add(lcssid);
		p7.add(cssid);
		p7.add(btn7);
		p7.setLayout(new FlowLayout());
		p7.setBounds(10, 490, 970, 70);
		p7.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리-학생"));
		add(p7);

		p8.add(ltid);
		p8.add(tid);
		p8.add(ltyear);
		p8.add(tyear);
		p8.add(ltsem);
		p8.add(tsem);
		p8.add(ltupdate);
		p8.add(tupdate);
		p8.add(ltpaid);
		p8.add(tpaid);
		p8.add(ltfee);
		p8.add(tfee);
		p8.add(ltnum);
		p8.add(tnum);
		p8.add(btn8);
		p8.setLayout(new FlowLayout());
		p8.setBounds(10, 570, 970, 70);
		p8.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "등록금"));
		add(p8);

		p9.add(lpsyear);
		p9.add(psyear);
		p9.add(lpssem);
		p9.add(pssem);
		p9.add(lpssid);
		p9.add(pssid);
		p9.add(lpspid);
		p9.add(pspid);
		p9.add(btn9);
		p9.setLayout(new FlowLayout());
		p9.setBounds(10, 650, 970, 70);
		p9.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "지도관계"));
		add(p9);

		p10.add(lpdpid);
		p10.add(pdpid);
		p10.add(lpddid);
		p10.add(pddid);
		p10.add(btn10);
		p10.setLayout(new FlowLayout());
		p10.setBounds(10, 730, 970, 70);
		p10.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수-학과"));
		add(p10);

		p11.add(ldsdid);
		p11.add(dsdid);
		p11.add(ldssid);
		p11.add(dssid);
		p11.add(btn11);
		p11.setLayout(new FlowLayout());
		p11.setBounds(10, 810, 970, 70);
		p11.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학생-학과"));
		add(p11);

		btncancel.setBounds(460, 890, 70, 30);
		add(btncancel);

		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		btn10.addActionListener(this);
		btn11.addActionListener(this);
		btncancel.addActionListener(this);
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

	public class insertDCourse extends JFrame implements ActionListener {

		JLabel tlcid, tlcname, tlcdate, tlcaddr, tlcdiv, tlcscore, tlchour, tlcperiod, tlcyear, tlcsem;
		JTextField tcid, tcname, tcdate, tcaddr, tcdiv, tcscore, tchour, tcperiod, tcyear, tcsem;
		JButton tbtn1;
		JPanel tp2;
		String Id;
		int flag;

		public insertDCourse(String tdid, int f) {
			setLayout(null);
			setTitle("강의 입력");
			setVisible(true);
			setBounds(200, 100, 1000, 150); // 가로위치,세로위치,가로길이,세로길이
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Id = tdid;
			flag = f;
			tbtn1 = new JButton("저장");

			tlcid = new JLabel("강의번호");
			tlcname = new JLabel("강의명");
			tlcaddr = new JLabel("강의실");
			tlcdate = new JLabel("요일");
			tlcdiv = new JLabel("분반");
			tlcscore = new JLabel("학점");
			tlchour = new JLabel("시간");
			tlcperiod = new JLabel("교시");
			tlcyear = new JLabel("학년");
			tlcsem = new JLabel("학기");

			tcid = new JTextField(2);
			tcname = new JTextField(5);
			tcaddr = new JTextField(3);
			tcdate = new JTextField(3);
			tcdiv = new JTextField(2);
			tcscore = new JTextField(2);
			tchour = new JTextField(2);
			tcperiod = new JTextField(2);
			tcyear = new JTextField(2);
			tcsem = new JTextField(2);

			tp2 = new JPanel();
			tp2.setLayout(new FlowLayout());
			tp2.setBounds(10, 10, 970, 70);
			tp2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "강의"));

			tp2.add(tlcid);
			tp2.add(tcid);
			tp2.add(tlcname);
			tp2.add(tcname);
			tp2.add(tlcaddr);
			tp2.add(tcaddr);
			tp2.add(tlcdate);
			tp2.add(tcdate);
			tp2.add(tlcdiv);
			tp2.add(tcdiv);
			tp2.add(tlcscore);
			tp2.add(tcscore);
			tp2.add(tlchour);
			tp2.add(tchour);
			tp2.add(tlcperiod);
			tp2.add(tcperiod);
			tp2.add(tlcyear);
			tp2.add(tcyear);
			tp2.add(tlcsem);
			tp2.add(tcsem);

			tp2.add(tbtn1);
			add(tp2);

			tbtn1.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == tbtn1) {
				String sid = tcid.getText();
				String sname = tcname.getText();
				String sdate = tcdate.getText();
				String saddr = tcaddr.getText();
				String sdiv = tcdiv.getText();
				String sscore = tcscore.getText();
				String shour = tchour.getText();
				String speriod = tcperiod.getText();
				String syear = tcyear.getText();
				String ssem = tcsem.getText();
				if (flag == 0) {
					try {
						String query = "INSERT INTO course VALUES (" + sid + ",'" + sname + "','" + sdate + "','"
								+ saddr + "', " + sdiv + "," + sscore + ", " + shour + ", " + speriod + "," + Id + " , "
								+ syear + ", " + ssem + ", 1)";
						stmt = con.createStatement();
						stmt.executeUpdate(query);

						JOptionPane.showMessageDialog(null, "입력되었습니다!\n(Default) 학과장 번호 : 1, 강의교수 번호 : 1");
						dispose();
					} catch (Exception e8) {
						JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
					}
				} else {
					try {
						String query = "INSERT INTO course VALUES (" + sid + ",'" + sname + "','" + sdate + "','"
								+ saddr + "', " + sdiv + "," + sscore + ", " + shour + ", " + speriod + ",1 , " + syear
								+ ", " + ssem + ", " + Id + ")";
						stmt = con.createStatement();
						stmt.executeUpdate(query);

						JOptionPane.showMessageDialog(null, "입력되었습니다!\n(Default) 학과 번호 : 1, 담당학생 번호 : 1");
						dispose();
					} catch (Exception e8) {
						JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
					}
				}
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btn1) {
			String sid = id.getText();
			String sname = name.getText();
			String saddr = addr.getText();
			String sphone = phone.getText();
			String semail = email.getText();
			if (sid.isEmpty() || sname.isEmpty() || saddr.isEmpty() || sphone.isEmpty() || semail.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {

				String query = "INSERT INTO student VALUES (" + sid + ",'" + sname + "','" + saddr + "','" + sphone
						+ "', '" + semail + "', 1)";
				String query1 = "INSERT INTO professor_student VALUES (1, 2021, " + sid + " , 1)";
				String query2 = "INSERT INTO tuition VALUES (" + sid
						+ ", 1, 1,'2021-02-18', 0, 4000000 , '11-111-111')";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);
				stmt.executeUpdate(query2);
				JOptionPane.showMessageDialog(null, "입력되었습니다!\n(Default) 지도교수 번호 : 1");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn2) {
			String sid = cid.getText();
			String sname = cname.getText();
			String sdate = cdate.getText();
			String saddr = caddr.getText();
			String sdiv = cdiv.getText();
			String sscore = cscore.getText();
			String shour = chour.getText();
			String speriod = cperiod.getText();
			String syear = cyear.getText();
			String ssem = csem.getText();
			if (sscore.isEmpty() || shour.isEmpty() || speriod.isEmpty() || syear.isEmpty() || ssem.isEmpty()
					|| sid.isEmpty() || sname.isEmpty() || saddr.isEmpty() || sdate.isEmpty() || sdiv.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO course VALUES (" + sid + ",'" + sname + "','" + sdate + "','" + saddr
						+ "', " + sdiv + "," + sscore + ", " + shour + ", " + speriod + ", 1, " + syear + ", " + ssem
						+ ", 1)";

				stmt = con.createStatement();
				stmt.executeUpdate(query);

				JOptionPane.showMessageDialog(null, "입력되었습니다!\n(Default) 강의교수 번호 : 1");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn3) {
			String sid = ccid.getText();
			String sname = ccname.getText();
			String saddr = ccaddr.getText();
			if (sid.isEmpty() || sname.isEmpty() || saddr.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO circle VALUES (" + sid + ",'" + sname + "','" + saddr + "', 1, 1)";
				String query1 = "INSERT INTO circle_has_student VALUES (" + sid + ",1,1)";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);
				JOptionPane.showMessageDialog(null, "입력되었습니다!\n(Default) 동아리회장 번호 : 1");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn4) {
			String sid = did.getText();
			String sname = dname.getText();
			String sphone = dphone.getText();
			String saddr = daddr.getText();
			if (sid.isEmpty() || sname.isEmpty() || sphone.isEmpty() || saddr.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO department VALUES (" + sid + ",'" + sname + "','" + sphone + "','" + saddr
						+ "')";
				String query1 = "INSERT INTO professor_has_department VALUES (1," + sid + ",1)";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);
				new insertDCourse(sid, 0);
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn5) {
			String sid = pid.getText();
			String sname = pname.getText();
			String saddr = paddr.getText();
			String sphone = pphone.getText();
			String semail = pemail.getText();
			if (sid.isEmpty() || sname.isEmpty() || sphone.isEmpty() || saddr.isEmpty() || semail.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO professor VALUES (" + sid + ",'" + sname + "','" + saddr + "','" + sphone
						+ "','" + semail + "')";
				String query1 = "update professor_student set professor_id = " + sid
						+ " where student_id = 1 and semester = 1 and school_year = 2";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);
				new insertDCourse(sid, 1);
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn6) {
			String attend = attendance.getText();
			String mids = mid.getText();
			String finals = final_score.getText();
			String etcs = etc.getText();
			String totals = total.getText();
			String grades = grade.getText();
			String scyears = scyear.getText();
			String scsems = scsem.getText();
			String scsids = scsid.getText();
			String sccids = sccid.getText();
			if (grades.isEmpty() || scyears.isEmpty() || scsems.isEmpty() || scsids.isEmpty() || sccids.isEmpty()
					|| attend.isEmpty() || mids.isEmpty() || finals.isEmpty() || etcs.isEmpty() || totals.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "select student_id from tuition where student_id = " + scsids
						+ " and tuition_paid < tuition_fee";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next() != false) {
					JOptionPane.showMessageDialog(null, "등록금 미납!");
					return;
				}

				query = "select course_score from course where course_id = " + sccids;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next() == false) {
					JOptionPane.showMessageDialog(null, "없는 강의입니다!");
					return;
				}
				int coursescore = rs.getInt(1);

				int coursid[] = new int[10];
				query = "select course_id from student_course where student_id = " + scsids;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				int len = 0;
				while (rs.next()) {
					coursid[len++] = rs.getInt(1);
				}
				int sum = 0;
				for (int i = 0; i < len; i++) {
					query = "select course_score from course where course_id = " + coursid[i];
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					rs.next();
					sum += rs.getInt(1);
				}

				if (sum + coursescore > 10) {
					JOptionPane.showMessageDialog(null, "수강학점 초과!");
					return;
				}

				query = " INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES ("
						+ attend + "," + mids + "," + finals + "," + etcs + "," + totals + "," + grades + "," + scsids
						+ "," + sccids + "," + scyears + "," + scsems + ")";

				stmt = con.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");

			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn7) {
			String sid = cssid.getText();
			String cid = cscid.getText();
			if (sid.isEmpty() || cid.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO circle_has_student VALUES (" + cid + "," + sid + ",0)";
				String query2 = "select circle_num from circle where circle_id = " + cid;
				stmt = con.createStatement();
				rs = stmt.executeQuery(query2);
				rs.next();
				int num = rs.getInt(1);
				num++;
				String query1 = "update circle set circle_num = " + num + " where circle_id = " + cid;
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn8) {
			String sid = tid.getText();
			String syear = tyear.getText();
			String ssem = tsem.getText();
			String supdate = tupdate.getText();
			String spaid = tpaid.getText();
			String sfee = tfee.getText();
			String snum = tnum.getText();
			if (sfee.isEmpty() || snum.isEmpty() || sid.isEmpty() || syear.isEmpty() || ssem.isEmpty()
					|| supdate.isEmpty() || spaid.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			if (Integer.parseInt(sfee) < Integer.parseInt(spaid)) {
				JOptionPane.showMessageDialog(null, "등록금 금액 초과 납부!");
				return;
			}
			try {
				String query = "INSERT INTO tuition VALUES (" + sid + "," + syear + "," + ssem + ", '" + supdate + "', "
						+ spaid + "," + sfee + ", '" + snum + "')";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn9) {
			String sssem = pssem.getText();
			String ssyear = psyear.getText();
			String sssid = pssid.getText();
			String sspid = pspid.getText();
			if (sssem.isEmpty() || ssyear.isEmpty() || sssid.isEmpty() || sspid.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO professor_student VALUES (" + sssem + "," + ssyear + "," + sssid + ", "
						+ sspid + ")";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btn10) {
			String spdpid = pdpid.getText();
			String spddid = pddid.getText();
			if (spdpid.isEmpty() || spddid.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO professor_has_department VALUES (" + spdpid + "," + spddid + ", 0)";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		}

		else if (e.getSource() == btn11) {
			String sdsdid = dsdid.getText();
			String sdssid = dssid.getText();
			if (sdsdid.isEmpty() || sdssid.isEmpty()) {
				JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요!");
				return;
			}
			try {
				String query = "INSERT INTO department_has_student VALUES (" + sdsdid + "," + sdssid + ")";
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력되었습니다!");
			} catch (Exception e8) {
				JOptionPane.showMessageDialog(null, "입력 실패 :" + e8);
			}
		} else if (e.getSource() == btncancel) {
			dispose();
		}
	}

}