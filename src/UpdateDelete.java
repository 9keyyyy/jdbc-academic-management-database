import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class UpdateDelete extends JFrame implements ActionListener {

	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
	JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16,
			btn17, btn18, btn19, btn20, btn21, btn22, btnCancel;

	// 1
	JLabel lid, lname, laddr, lphone, lemail, supdate, sdelete;
	JTextField delete_id, id, name, addr, phone, email;

	// 2
	JLabel ldelete, lupdate, lcid, lcname, lcdate, lcaddr, lcdiv, lcscore, lchour, lcperiod, lcyear, lcsem, lcdid, lpid;
	JTextField cid, cname, cdate, caddr, cdiv, cscore, chour, cperiod, cyear, csem, cdid, pid;

	// 3
	JLabel lccdelete, lccupdate, lccid, lccaddr, lccname, lccpid;
	JTextField ccdelete, ccid, ccaddr, ccname, ccpid;

	// 4
	JLabel lddelete, ldupdate, ldid, ldname, ldphone, ldaddr;
	JTextField ddelete, did, dname, dphone, daddr;

	// 5
	JLabel lbdelete, lbupdate, lbpid, lbpname, lbpaddr, lbpphone, lbemail;
	JTextField bdelete, txtpid, txtpname, txtpaddr, txtpphone, txtpemail;

	// 6
	JLabel lcand, lcand2, lcdelete, lcupdate, lcscid, lcssid, lcscpid;
	JTextField tcscid, tcssid, tcscpid, tcand, tcscid2;

//////////////

	JLabel lblpid, lbldid1, lbldpid1, l7del1, l7del2, l7up1, l7up2;
	JTextField textpid1, textdid1, textdpid, t7del1, t7del2, t7up1, t7up2;

	JLabel ld8dtxt, ld8utxt, ld8utxt1, ld8utxt2, ld8utxt3, ld8utxt4, ld8and1, ld8and2;
	JTextField d8dtxt, d8utxt, d8utxt1, d8utxt2, d8utxt3, d8utxt4, d8and1, d8and2;

	JLabel ld9dtxt, ld9utxt, ld9utxt1, ld9utxt2, ld9and1, ld9and2;
	JTextField d9dtxt, d9utxt, d9utxt1, d9utxt2, d9and1, d9and2;

	JLabel ld10dtxt, ld10utxt, ld10utxt1, ld10utxt2, ld10utxt3, ld10utxt4, ld10utxt5, ld10utxt6, ld10utxt7, ld10utxt8,
			ld10utxt9, ld10utxt10, ld10and1, ld10and2;
	JTextField d10dtxt, d10utxt, d10utxt1, d10utxt2, d10utxt3, d10utxt4, d10utxt5, d10utxt6, d10utxt7, d10utxt8,
			d10utxt9, d10utxt10, d10and1, d10and2;

	JLabel ld11dtxt, ld11utxt, ld11utxt1, ld11utxt2, ld11utxt3, ld11utxt4, ld11utxt5, ld11utxt6, ld11utxt7, ld11and1,
			ld11and2;
	JTextField d11dtxt, d11utxt, d11utxt1, d11utxt2, d11utxt3, d11utxt4, d11utxt5, d11utxt6, d11utxt7, d11and1, d11and2;

	static Connection con;
	Statement stmt, stmt1;
	ResultSet rs, rs1;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public UpdateDelete() {
		setTitle("입력");
		layInit();
		setVisible(true);
		conDB();
		setBounds(0, 0, 1900, 900); // 가로위치,세로위치,가로길이,세로길이
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

		// 학생
		btn1 = new JButton("삭제");
		btn2 = new JButton("변경");

		// 강의
		btn3 = new JButton("삭제");
		btn4 = new JButton("변경");

		// 동아리
		btn5 = new JButton("삭제");
		btn6 = new JButton("변경");

		// 학과
		btn7 = new JButton("삭제");
		btn8 = new JButton("변경");

		// 교수
		btn9 = new JButton("삭제");
		btn10 = new JButton("변경");

		// 동아리,학생
		btn11 = new JButton("삭제");
		btn12 = new JButton("변경");

		// 교수,학생
		btn13 = new JButton("삭제");
		btn14 = new JButton("변경");

		// 학과,학생
		btn15 = new JButton("삭제");
		btn16 = new JButton("변경");

		// 학과,학생
		btn17 = new JButton("삭제");
		btn18 = new JButton("변경");

		// 학생,강좌
		btn19 = new JButton("삭제");
		btn20 = new JButton("변경");

		// 계좌
		btn21 = new JButton("삭제");
		btn22 = new JButton("변경");

		btnCancel = new JButton("취소");

		// 레이블

		// 학생
		sdelete = new JLabel("Delete from student where");
		supdate = new JLabel("Update `madang`.`student` set");
		lname = new JLabel("`student_name`=");
		laddr = new JLabel(",`student_addr`=");
		lphone = new JLabel(",`student_phone`=");
		lemail = new JLabel(",`student_email`=");
		lid = new JLabel("where");

		// 강좌
		ldelete = new JLabel("Delete from course where");
		lupdate = new JLabel("Update `madang`.`course` set");
		lcname = new JLabel("`course_name`=");
		lcaddr = new JLabel(",`course_addr`=");
		lcdate = new JLabel(",`course_date`=");
		lcdiv = new JLabel(",`course_div`=");
		lcscore = new JLabel(",`course_score`=");
		lchour = new JLabel(",`course_hour`=");
		lcperiod = new JLabel(",`course_period`=");
		lcyear = new JLabel(",`course_year`=");
		lcsem = new JLabel(",`course_semester`=");
		lpid = new JLabel(",`professor_id`=");
		lcdid = new JLabel("where");

		// 동아리
		lccdelete = new JLabel("Delete from circle where");
		lccupdate = new JLabel("Update `madang`.`circle` set ");
		lccid = new JLabel("where");
		lccname = new JLabel("`circle_name`=");
		lccaddr = new JLabel(",`circle_addr`=");
		lccpid = new JLabel(",`professor_id`=");

		// 학과
		lddelete = new JLabel("Delete from department where");
		ldupdate = new JLabel("Update `madang`.`department` set ");
		ldid = new JLabel("where ");
		ldphone = new JLabel(",`department_phone`=");
		ldname = new JLabel("`department_name`=");
		ldaddr = new JLabel(",`department_addr`=");

		// 교수
		lbdelete = new JLabel("Delete from professor where");
		lbupdate = new JLabel("Update `madang`.`professor` set");
		lbpid = new JLabel("where ");
		lbpname = new JLabel("`professor_name`=");
		lbpaddr = new JLabel(",`professor_addr`=");
		lbpphone = new JLabel(",`professor_phone`=");
		lbemail = new JLabel(",`professor_email`=");

		// 6. 동아리,학생
		lcdelete = new JLabel("Delete from circle_has_student where");
		lcand = new JLabel("and");
		lcupdate = new JLabel("Update `madang`.`circle_has_student` set");
		lcssid = new JLabel("where ");
		lcand2 = new JLabel("and");
		lcscpid = new JLabel("`circle_principle`=");

		// 7. 교수,학과
		lblpid = new JLabel("Delete from professor_has_department where");
		lbldid1 = new JLabel("Update `madang`.`professor_has_department` set `professor_id` = ");
		lbldpid1 = new JLabel(", `department_id` = ");
		l7del1 = new JLabel(", `department_president` = ");
		l7del2 = new JLabel(" and ");
		l7up1 = new JLabel(" and ");
		l7up2 = new JLabel(" where ");

		// 8. 교수,학생
		ld8dtxt = new JLabel("Delete from professor_student where");
		ld8utxt = new JLabel("Update `madang`.`professor_student` set `professor_id` = ");
		ld8utxt1 = new JLabel(", `student_id` = ");
		ld8utxt2 = new JLabel(", `school_year` = ");
		ld8utxt3 = new JLabel(", `semester` = ");
		ld8utxt4 = new JLabel(" where ");
		ld8and1 = new JLabel(" and ");
		ld8and2 = new JLabel(" and ");

		// 9. 학과,학생
		ld9dtxt = new JLabel("Delete from department_has_student where");
		ld9utxt = new JLabel("Update `madang`.`department_has_student` set `student_id` = ");
		ld9utxt1 = new JLabel(", `department_id` = ");
		ld9utxt2 = new JLabel(" where ");
		ld9and1 = new JLabel(" and ");
		ld9and2 = new JLabel(" and ");

		// 10. 학생,강좌
		ld10dtxt = new JLabel("Delete from student_course where");
		ld10utxt = new JLabel("Update `madang`.`student_course` set `attendance_score` = ");
		ld10utxt1 = new JLabel(", `mid_score` = ");
		ld10utxt2 = new JLabel(", `final_score` = ");
		ld10utxt3 = new JLabel(", `etc_score` = ");
		ld10utxt4 = new JLabel(" total_score ");
		ld10utxt5 = new JLabel(", `grade` = ");
		ld10utxt6 = new JLabel(", `student_id` = ");
		ld10utxt7 = new JLabel(", `course_id` = ");
		ld10utxt8 = new JLabel(", student_course_year =");
		ld10utxt9 = new JLabel(", student_course_semester =");
		ld10utxt10 = new JLabel(" where ");
		ld10and1 = new JLabel(" and ");
		ld10and2 = new JLabel(" and ");

		// 11. 계좌
		ld11dtxt = new JLabel("Delete from tuition where");
		ld11utxt = new JLabel("Update `madang`.`tuition` set `student_id` = ");
		ld11utxt1 = new JLabel(", `tuition_year` = ");
		ld11utxt2 = new JLabel(", `tuition_semester` = ");
		ld11utxt3 = new JLabel(", `tuition_last_updated` = ");
		ld11utxt4 = new JLabel(", tuition_paid ");
		ld11utxt5 = new JLabel(", tuition_fee ");
		ld11utxt6 = new JLabel(", `tuition_num` = ");
		ld11utxt7 = new JLabel(" where ");
		ld11and1 = new JLabel(" and ");
		ld11and2 = new JLabel(" and ");

		// 텍스트

		d8and1 = new JTextField(8);
		d8and2 = new JTextField(8);
		d9and1 = new JTextField(8);
		d9and2 = new JTextField(8);
		d10and1 = new JTextField(8);
		d10and2 = new JTextField(8);
		d11and1 = new JTextField(8);
		d11and2 = new JTextField(8);

		// 학생
		delete_id = new JTextField(20);
		id = new JTextField(20);
		name = new JTextField(8);
		addr = new JTextField(8);
		phone = new JTextField(8);
		email = new JTextField(8);

		// 강좌
		cid = new JTextField(20);
		cname = new JTextField(5);
		caddr = new JTextField(5);
		cdate = new JTextField(3);
		cdiv = new JTextField(1);
		cscore = new JTextField(1);
		chour = new JTextField(1);
		cperiod = new JTextField(1);
		cyear = new JTextField(1);
		csem = new JTextField(1);
		cdid = new JTextField(20);
		pid = new JTextField(1);

		// 동아리
		ccid = new JTextField(20);// delete where
		ccaddr = new JTextField(5);
		ccname = new JTextField(5);
		ccpid = new JTextField(1);
		ccdelete = new JTextField(20);// update where

		// 학과
		did = new JTextField(20);// update where
		dphone = new JTextField(5);
		dname = new JTextField(5);
		daddr = new JTextField(2);
		ddelete = new JTextField(20);// delete where

		// 교수
		bdelete = new JTextField(20);// delete where
		txtpid = new JTextField(20);// update where
		txtpname = new JTextField(3);
		txtpaddr = new JTextField(3);
		txtpphone = new JTextField(5);
		txtpemail = new JTextField(5);

		// 동아리,학생
		tcscid = new JTextField(15);
		tcscid.setText("동아리번호 관련 조건식");
		tcscid2 = new JTextField(15);
		tcscid2.setText("학생번호 관련 조건식");
		tcssid = new JTextField(15);
		tcssid.setText("동아리번호 관련 조건식");
		tcscpid = new JTextField(2);
		tcand = new JTextField(15);
		tcand.setText("학생번호 관련 조건식");

		// 교수,학과
		textpid1 = new JTextField(8);
		textdid1 = new JTextField(2);
		textdpid = new JTextField(2);
		t7del1 = new JTextField(8);
		t7del2 = new JTextField(2);
		t7up1 = new JTextField(8);
		t7up2 = new JTextField(8);

		// 교수,학생
		d8dtxt = new JTextField(8);
		d8utxt = new JTextField(2);
		d8utxt1 = new JTextField(2);
		d8utxt2 = new JTextField(2);
		d8utxt3 = new JTextField(2);
		d8utxt4 = new JTextField(8);

		// 학과,학생

		d9dtxt = new JTextField(8); // del
		d9utxt = new JTextField(2); // sid
		d9utxt1 = new JTextField(2); // did
		d9utxt2 = new JTextField(8); // up

		// 학생,강좌

		d10dtxt = new JTextField(8); // del
		d10utxt = new JTextField(2); // attend
		d10utxt1 = new JTextField(2); // mid
		d10utxt2 = new JTextField(2); // final
		d10utxt3 = new JTextField(2); // etc
		d10utxt4 = new JTextField(2); // total
		d10utxt5 = new JTextField(2); // grade
		d10utxt6 = new JTextField(2); // sid
		d10utxt7 = new JTextField(2); // cid
		d10utxt8 = new JTextField(2); // scy
		d10utxt9 = new JTextField(2); // scs
		d10utxt10 = new JTextField(8); // up

		// 계좌
		d11dtxt = new JTextField(8);// del
		d11utxt = new JTextField(2);// sid
		d11utxt1 = new JTextField(2);// ty
		d11utxt2 = new JTextField(2);// ts
		d11utxt3 = new JTextField(5);// tlu
		d11utxt4 = new JTextField(4);// tp
		d11utxt5 = new JTextField(4);// tf
		d11utxt6 = new JTextField(2);// tn
		d11utxt7 = new JTextField(8);// up

		p1.setLayout(new FlowLayout());
		p1.setBounds(10, 10, 970, 100);
		p1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학생"));
		p1.add(sdelete);
		p1.add(delete_id);
		p1.add(btn1);
		p1.add(supdate);
		p1.add(lname);
		p1.add(name);
		p1.add(laddr);
		p1.add(addr);
		p1.add(lphone);
		p1.add(phone);
		p1.add(lemail);
		p1.add(email);
		p1.add(lid);
		p1.add(id);
		p1.add(btn2);
		add(p1);

		p2.setLayout(new FlowLayout());
		p2.setBounds(10, 120, 970, 120);
		p2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "강의"));
		p2.add(ldelete);
		p2.add(cid);
		p2.add(btn3);
		p2.add(lupdate);
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
		p2.add(lpid);
		p2.add(pid);
		p2.add(lcdid);
		p2.add(cdid);
		p2.add(btn4);
		add(p2);

		p3.setLayout(new FlowLayout());
		p3.setBounds(10, 250, 970, 100);
		p3.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리"));
		p3.add(lccdelete);
		p3.add(ccdelete);
		p3.add(btn5);
		p3.add(lccupdate);
		p3.add(lccname);
		p3.add(ccname);
		p3.add(lccaddr);
		p3.add(ccaddr);
		p3.add(lccpid);
		p3.add(ccpid);
		p3.add(lccid);
		p3.add(ccid);
		p3.add(btn6);
		add(p3);

		/*
		 * `department_id` INT NOT NULL, `department_name` VARCHAR(45) NOT NULL,
		 * `department_phone` VARCHAR(45) NOT NULL, `department_addr` VARCHAR(45) NOT
		 * NULL,
		 */

		p4.setLayout(new FlowLayout());
		p4.setBounds(10, 360, 970, 100);
		p4.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과"));
		p4.add(lddelete);
		p4.add(ddelete);
		p4.add(btn7);
		p4.add(ldupdate);
		p4.add(ldname);
		p4.add(dname);
		p4.add(ldphone);
		p4.add(dphone);
		p4.add(ldaddr);
		p4.add(daddr);
		p4.add(ldid);
		p4.add(did);
		p4.add(btn8);
		add(p4);

		p5.setLayout(new FlowLayout());
		p5.setBounds(10, 470, 970, 100);
		p5.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수"));
		p5.add(lbdelete);
		p5.add(bdelete);
		p5.add(btn9);
		p5.add(lbupdate);
		p5.add(lbpname);
		p5.add(txtpname);
		p5.add(lbpaddr);
		p5.add(txtpaddr);
		p5.add(lbpphone);
		p5.add(txtpphone);
		p5.add(lbemail);
		p5.add(txtpemail);
		p5.add(lbpid);
		p5.add(txtpid);
		p5.add(btn10);
		add(p5);

		p6.setLayout(new FlowLayout());
		p6.setBounds(10, 580, 970, 100);
		p6.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "동아리,학생"));

		p6.add(lcdelete);
		p6.add(tcscid);
		p6.add(lcand);
		p6.add(tcscid2);
		p6.add(btn11);

		p6.add(lcupdate);
		p6.add(lcscpid);
		p6.add(tcscpid);
		p6.add(lcssid);
		p6.add(tcssid);
		p6.add(lcand2);
		p6.add(tcand);
		p6.add(btn12);
		add(p6);

		////////////////

		p7.setLayout(new FlowLayout());
		p7.setBounds(980, 10, 900, 140);
		p7.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수,학과"));
		p7.add(lblpid);
		p7.add(textpid1); // del1
		p7.add(l7del2);
		p7.add(t7del1); // del2
		p7.add(btn13);
		p7.add(lbldid1);
		p7.add(textdid1); // profid
		p7.add(lbldpid1);
		p7.add(textdpid); // departid
		p7.add(l7del1);
		p7.add(t7del2); // depardpresi
		p7.add(l7up2);
		p7.add(t7up1); // up1
		p7.add(l7up1);
		p7.add(t7up2); // up2

		p7.add(btn14);
		add(p7);

		p8.setLayout(new FlowLayout());
		p8.setBounds(980, 170, 900, 140);
		p8.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "교수,학생"));
		p8.add(ld8dtxt);
		p8.add(d8dtxt);// del
		p8.add(ld8and1);
		p8.add(d8and1);
		p8.add(btn15);
		p8.add(ld8utxt);
		p8.add(d8utxt); // pid
		p8.add(ld8utxt1);
		p8.add(d8utxt1); // sid
		p8.add(ld8utxt2);
		p8.add(d8utxt2); // y
		p8.add(ld8utxt3);
		p8.add(d8utxt3); // sem
		p8.add(ld8utxt4);
		p8.add(d8utxt4); // up
		p8.add(ld8and2);
		p8.add(d8and2);

		p8.add(btn16);
		add(p8);

		p9.setLayout(new FlowLayout());
		p9.setBounds(980, 330, 900, 140);
		p9.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학과,학생"));
		p9.add(ld9dtxt);
		p9.add(d9dtxt);
		p9.add(ld9and1);
		p9.add(d9and1);
		p9.add(btn17);
		p9.add(ld9utxt);
		p9.add(d9utxt);
		p9.add(ld9utxt1);
		p9.add(d9utxt1);
		p9.add(ld9utxt2);
		p9.add(d9utxt2);
		p9.add(ld9and2);
		p9.add(d9and2);
		p9.add(btn18);
		add(p9);

		p10.setLayout(new FlowLayout());
		p10.setBounds(980, 490, 900, 140);
		p10.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "학생,강좌"));
		p10.add(ld10dtxt);
		p10.add(d10dtxt);
		p10.add(ld10and1);
		p10.add(d10and1);
		p10.add(btn19);
		p10.add(ld10utxt);
		p10.add(d10utxt);
		p10.add(ld10utxt1);
		p10.add(d10utxt1);
		p10.add(ld10utxt2);
		p10.add(d10utxt2);
		p10.add(ld10utxt3);
		p10.add(d10utxt3);
		p10.add(ld10utxt4);
		p10.add(d10utxt4);
		p10.add(ld10utxt5);
		p10.add(d10utxt5);
		p10.add(ld10utxt6);
		p10.add(d10utxt6);
		p10.add(ld10utxt7);
		p10.add(d10utxt7);
		p10.add(ld10utxt8);
		p10.add(d10utxt8);
		p10.add(ld10utxt9);
		p10.add(d10utxt9);
		p10.add(ld10utxt10);
		p10.add(d10utxt10);
		p10.add(ld10and2);
		p10.add(d10and2);
		p10.add(btn20);
		add(p10);

		p11.setLayout(new FlowLayout());
		p11.setBounds(980, 650, 900, 140);
		p11.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "계좌"));
		p11.add(ld11dtxt);
		p11.add(d11dtxt);
		p11.add(ld11and1);
		p11.add(d11and1);
		p11.add(btn21);
		p11.add(ld11utxt);
		p11.add(d11utxt);
		p11.add(ld11utxt1);
		p11.add(d11utxt1);
		p11.add(ld11utxt2);
		p11.add(d11utxt2);
		p11.add(ld11utxt3);
		p11.add(d11utxt3);
		p11.add(ld11utxt4);
		p11.add(d11utxt4);
		p11.add(ld11utxt5);
		p11.add(d11utxt5);
		p11.add(ld11utxt6);
		p11.add(d11utxt6);
		p11.add(ld11utxt7);
		p11.add(d11utxt7);
		p11.add(ld11and2);
		p11.add(d11and2);

		p11.add(btn22);
		add(p11);

		btnCancel.setBounds(500, 800, 900, 30);// 0608 수정
		add(btnCancel);

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
		btn12.addActionListener(this);

		btn13.addActionListener(this);
		btn14.addActionListener(this);

		btn15.addActionListener(this);
		btn16.addActionListener(this);

		btn17.addActionListener(this);
		btn18.addActionListener(this);

		btn19.addActionListener(this);
		btn20.addActionListener(this);

		btn21.addActionListener(this);
		btn22.addActionListener(this);

		btnCancel.addActionListener(this);

		addWindowListener(new JFrameWindowClosingEventHandler());// 0601

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Integer a;
		try {
			stmt = con.createStatement();

			// 1
			String sdelete = delete_id.getText();
			String sid = id.getText();
			String sname = name.getText();
			String saddr = addr.getText();
			String sphone = phone.getText();
			String semail = email.getText();

			// 2
			String course_id = cid.getText();
			String course_name = cname.getText();
			String course_addr = caddr.getText();
			String course_date = cdate.getText();
			String course_div = cdiv.getText();
			String course_score = cscore.getText();
			String course_hour = chour.getText();
			String course_period = cperiod.getText();
			String course_year = cyear.getText();
			String course_sem = csem.getText();
			String course_did = cdid.getText();
			String course_pid = pid.getText();

			// 3
			String circle_delete = ccdelete.getText();
			String circle_id = ccid.getText();
			String circle_addr = ccaddr.getText();
			String circle_name = ccname.getText();
			String circle_pid = ccpid.getText();

			// 4
			String department_delete = ddelete.getText();
			String department_id = did.getText();
			String department_phone = dphone.getText();
			String department_name = dname.getText();
			String department_addr = daddr.getText();

			// 5
			String professor_delete = bdelete.getText();
			String professor_id = txtpid.getText();
			String professor_name = txtpname.getText();
			String professor_addr = txtpaddr.getText();
			String professor_phone = txtpphone.getText();
			String professor_email = txtpemail.getText();

			// 6
			String csdcid = tcscid.getText();
			String csdsid = tcscid2.getText();
			String csucid = tcssid.getText();
			String csspp = tcscpid.getText();
			String csusid = tcand.getText();

			// 7
			String d7del1 = textpid1.getText();
			String d7del2 = t7del1.getText();
			String d7pid = textdid1.getText();
			String d7did = textdpid.getText();
			String d7dp = t7del2.getText();
			String d7up1 = t7up1.getText();
			String d7up2 = t7up2.getText();

//8
			String d8del = d8dtxt.getText();
			String d8pid = d8utxt.getText();
			String d8sid = d8utxt1.getText();
			String d8y = d8utxt2.getText();
			String d8sem = d8utxt3.getText();
			String d8up = d8utxt4.getText();
			String d8del2 = d8and1.getText();
			String d8up2 = d8and2.getText();

			// 9
			String d9del = d9dtxt.getText();
			String d9sid = d9utxt.getText();
			String d9did = d9utxt1.getText();
			String d9up = d9utxt2.getText();
			String d9del2 = d9and1.getText();
			String d9up2 = d9and2.getText();

			// 10
			String d10del = d10dtxt.getText();
			String d10attend = d10utxt.getText();
			String d10mid = d10utxt1.getText();
			String d10final = d10utxt2.getText();
			String d10etc = d10utxt3.getText();
			String d10total = d10utxt4.getText();
			String d10grade = d10utxt5.getText();
			String d10sid = d10utxt6.getText();
			String d10cid = d10utxt7.getText();
			String d10scy = d10utxt8.getText();
			String d10scs = d10utxt9.getText();
			String d10up = d10utxt10.getText();
			String d10del2 = d10and1.getText();
			String d10up2 = d10and2.getText();

			// 11

			String d11del = d11dtxt.getText();
			String d11sid = d11utxt.getText();
			String d11ty = d11utxt1.getText();
			String d11ts = d11utxt2.getText();
			String d11tlu = d11utxt3.getText();
			String d11tp = d11utxt4.getText();
			String d11tf = d11utxt5.getText();
			String d11tn = d11utxt6.getText();
			String d11up = d11utxt7.getText();
			String d11del2 = d11and1.getText();
			String d11up2 = d11and2.getText();

			if (e.getSource() == btn1) {// 삭제
				if (!sdelete.isEmpty()) {
					String query = "delete from student where " + sdelete;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 채워주세요\n");
					return;
				}
			} else if (e.getSource() == btn2) {// 변경
				if (sid.isEmpty() || sname.isEmpty() || saddr.isEmpty() || sphone.isEmpty() || semail.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`student` set `student_name`=" + "'" + sname + "'"
							+ ", `student_addr`=" + "'" + saddr + "'" + ", `student_phone`=" + "'" + sphone + "'"
							+ ", `student_email`=" + "'" + semail + "'" + " where " + sid;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}
			} else if (e.getSource() == btn3) {// 삭제
				if (!course_id.isEmpty()) {
					String query = "delete from course where " + course_id;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 강좌입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 채워주세요\n");
					return;
				}
			} else if (e.getSource() == btn4) {// 변경
				if (course_name.isEmpty() || course_addr.isEmpty() || course_date.isEmpty() || course_div.isEmpty()
						|| course_score.isEmpty() || course_hour.isEmpty() || course_period.isEmpty()
						|| course_year.isEmpty() || course_sem.isEmpty() || course_pid.isEmpty()
						|| course_did.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`course` set `course_name`=" + "'" + course_name + "'"
							+ " ,`course_date`=" + "'" + course_date + "'" + " ,`course_addr`=" + "'" + course_addr
							+ "'" + " ,`course_div`=" + course_div + " ,`course_score`=" + course_score
							+ " ,`course_hour`=" + course_hour + " ,`course_period`=" + course_period
							+ " ,`course_year`=" + course_year + " ,`course_semester`=" + course_sem
							+ " ,`professor_id`=" + course_pid + " where " + course_did;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 강좌입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}
			} else if (e.getSource() == btn5) {// 삭제
				if (!circle_delete.isEmpty()) {
					String query = "delete from circle where " + circle_delete;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 동아리입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 채워주세요\n");
					return;
				}
			} else if (e.getSource() == btn6) {// 변경
				if (circle_id.isEmpty() || circle_addr.isEmpty() || circle_name.isEmpty() || circle_pid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`circle` set `circle_name`=" + "'" + circle_name + "'"
							+ ",`circle_addr`=" + "'" + circle_addr + "'" + ",`professor_id`=" + circle_pid + " where "
							+ circle_id;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 동아리입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}
			} else if (e.getSource() == btn7) {// 삭제
				if (!department_delete.isEmpty()) {
					String query = "delete from department where " + department_delete;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
			} else if (e.getSource() == btn8) {// 변경
				if (department_id.isEmpty() || department_addr.isEmpty() || department_name.isEmpty()
						|| department_phone.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`department` set `department_name`=" + "'" + department_name + "'"
							+ ",`department_addr`=" + "'" + department_addr + "'" + ",`department_phone`="
							+ department_phone + " where " + department_id;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}
			} else if (e.getSource() == btn9) {// 삭제
				if (!professor_delete.isEmpty()) {
					String query = "delete from professor where " + professor_delete;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
			} else if (e.getSource() == btn10) {// 변경
				if (professor_id.isEmpty() || professor_name.isEmpty() || professor_addr.isEmpty()
						|| professor_phone.isEmpty() || professor_email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`professor` set `professor_name`=" + "'" + professor_name + "'"
							+ ",`professor_addr`=" + "'" + professor_addr + "'" + ",`professor_phone`=" + "'"
							+ professor_phone + "'" + ",`professor_email`=" + "'" + professor_email + "'" + " where "
							+ professor_id;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}
			} else if (e.getSource() == btn11) {// 삭제
				if (!csdcid.isEmpty() && !csdsid.isEmpty()) {
					String query = "delete from circle_has_student where " + csdcid + " and " + csdsid;
					System.out.println(query);
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "삭제완료\n");
						String query1 = "update `madang`.`circle` set `circle_num`=`circle_num`-1 where " + csdcid;
						stmt.executeUpdate(query1);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}

			} else if (e.getSource() == btn12) {// 변경
				if (csucid.isEmpty() || csspp.isEmpty() || csusid.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				} else {
					String query = "update `madang`.`circle_has_student` set `circle_principle`=" + csspp + " where "
							+ csucid + " and" + csusid;
					a = stmt.executeUpdate(query);
					if (a == 0) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "변경완료\n");
						return;
					}
				}

			}

			////////////

			else if (e.getSource() == btn13) {// 삭제
				if (d7del1.isEmpty() || d7del2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "delete from professor_has_department where " + d7del1 + " and " + d7del2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "삭제완료\n");
					return;
				}

			} else if (e.getSource() == btn14) {// 변경
				if (d7dp.isEmpty() || d7pid.isEmpty() || d7did.isEmpty() || d7up1.isEmpty() || d7up2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "update `madang`.`professor_has_department` set `department_president`=" + d7dp
						+ ", `professor_id`=" + d7pid + " ,`department_id`=" + d7did + " where " + d7up1 + " and "
						+ d7up2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "변경완료\n");
					return;
				}
			} else if (e.getSource() == btn15) {// 삭제
				if (d8del.isEmpty() || d8del2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "delete from professor_student where " + d8del + " and " + d8del2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "삭제완료\n");
					return;
				}

			} else if (e.getSource() == btn16) {// 변경
				if (d8pid.isEmpty() || d8sid.isEmpty() || d8y.isEmpty() || d8sem.isEmpty() || d8up.isEmpty()
						|| d8up2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "update `madang`.`professor_student` set `professor_id`=" + d8pid + ", `student_id`="
						+ d8sid + ", `school_year`=" + d8y + ", `semester`=" + d8sem + " where " + d8up + " and "
						+ d8up2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "변경완료\n");
					return;
				}
			} else if (e.getSource() == btn17) {// 삭제
				if (d9del.isEmpty() || d9del.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "delete from department_has_student where " + d9del + " and " + d9del2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "삭제완료\n");
					return;
				}
			} else if (e.getSource() == btn18) {// 변경
				if (d9sid.isEmpty() || d9did.isEmpty() || d9up.isEmpty() || d9up2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "update `madang`.`department_has_student` set `student_id`=" + d9sid
						+ ", `department_id`=" + d9did + " where " + d9up + " and " + d9up2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 관계입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "변경완료\n");
					return;
				}

			} else if (e.getSource() == btn19) {// 삭제
				if (d10del.isEmpty() || d10del2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "delete from student_course where " + d10del + " and " + d10del2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 학생강좌입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "삭제완료\n");
					return;
				}
			} else if (e.getSource() == btn20) {// 변경
				if (d10attend.isEmpty() || d10mid.isEmpty() || d10final.isEmpty() || d10etc.isEmpty()
						|| d10total.isEmpty() || d10grade.isEmpty() || d10sid.isEmpty() || d10cid.isEmpty()
						|| d10scy.isEmpty() || d10scs.isEmpty() || d10up.isEmpty() || d10up2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "update `madang`.`student_course` set `attendance_score`=" + d10attend + ", `mid_score`="
						+ d10mid + ", `final_score`=" + d10final + ", `etc_score`=" + d10etc + ", `total_score`="
						+ d10total + ", `grade`='" + d10grade + "', `student_id`=" + d10sid + ", `course_id`=" + d10cid
						+ ", `student_course_year`=" + d10scy + ", `student_course_semester`=" + d10scs + " where "
						+ d10up + " and " + d10up2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 학생강좌입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "변경완료\n");
					return;
				}
			} else if (e.getSource() == btn21) {// 삭제
				if (d11del.isEmpty() || d11del2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "delete from tuition where " + d11del + " and " + d11del2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 정보입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "삭제완료\n");
					return;
				}
			} else if (e.getSource() == btn22) {// 변경
				if (d11sid.isEmpty() || d11ty.isEmpty() || d11tp.isEmpty() || d11tf.isEmpty() || d11tn.isEmpty()
						|| d11up.isEmpty() || d11up2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸을 모두 채워주세요\n");
					return;
				}
				String query = "update `madang`.`tuition` set `student_id`=" + d11sid + ", `tuition_year`=" + d11ty
						+ ", `tuition_semester`=" + d11ts + ", `tuition_last_updated`='" + d11tlu + "', `tuition_paid`="
						+ d11tp + ", `tuition_fee`=" + d11tf + ", `tuition_num`='" + d11tn + "' where " + d11up
						+ " and " + d11up2;
				a = stmt.executeUpdate(query);
				if (a == 0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 정보입니다\n");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "변경완료\n");
					return;
				}
			} else if (e.getSource() == btnCancel) {
				dispose();
				try {
					con.close();
				} catch (Exception e4) {

				}
				System.out.println("삭제/변경 프로그램 완전 종료~!");
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1);

		}

	}

	class JFrameWindowClosingEventHandler extends WindowAdapter {// 0601

		public void windowClosing(WindowEvent we) {
			try {
				con.close();
			} catch (Exception e4) {

			}
			System.out.println("삭제/변경 프로그램 완전 종료~!");
		}
	}

}