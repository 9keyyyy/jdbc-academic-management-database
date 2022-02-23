import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Admin extends JFrame implements ActionListener {
	JButton btnAll, btnInsert, btnDU, btnReset;
	JPanel pn1, p1, p2;

	static Connection con;
	Statement stmt, stmt1;
	ResultSet rs, rs1;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

// 0605
	UpdateDelete updatedelete;
	Insertinfo insertinfo;
	Manager manager;

	public Admin() {
		setTitle("������");
		layInit();
		setVisible(true);
		conDB();
		setBounds(200, 200, 570, 170); // ������ġ,������ġ,���α���,���α���
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void layInit() {
		setLayout(null);

		p1 = new JPanel();
		p2 = new JPanel();

		btnAll = new JButton("��ü ���̺� �����ֱ�");
		btnReset = new JButton("���̺� �ʱ�ȭ");
		btnDU = new JButton("����/����");
		btnInsert = new JButton("�Է�");

		btnAll.setBounds(100, 10, 150, 35);
		add(btnAll);
		btnReset.setBounds(300, 10, 150, 35);
		add(btnReset);
		btnDU.setBounds(100, 70, 150, 35);
		add(btnDU);
		btnInsert.setBounds(300, 70, 150, 35);
		add(btnInsert);

		btnAll.addActionListener(this);
		btnReset.addActionListener(this);
		btnDU.addActionListener(this);
		btnInsert.addActionListener(this);

	}

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

	@Override // 0605
	public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
		if (e.getSource() == btnAll) {
			if (manager != null)
				manager.dispose();
			manager = new Manager();
		} else if (e.getSource() == btnReset) {
			inittable();
		} else if (e.getSource() == btnInsert) {
			if (insertinfo != null)
				insertinfo.dispose();// ���� frame ���ֱ�
			insertinfo = new Insertinfo();//
		} else if (e.getSource() == btnDU) {
			if (updatedelete != null)
				updatedelete.dispose();
			updatedelete = new UpdateDelete();//
		}
	}

	public void inittable() {
		try {

			stmt = con.createStatement();
			String deltable[] = { "drop table if exists student_course", "drop table if exists course",
					"drop table if exists professor_has_department", "drop table if exists circle_has_student",
					"drop table if exists professor_student", "drop table if exists department_has_student",
					"drop table if exists circle", "drop table if exists tuition", "drop table if exists student",
					"drop table if exists professor", "drop table if exists department" };
			String createtable[] = { "CREATE TABLE IF NOT EXISTS `madang`.`professor` (\r\n"
					+ "  `professor_id` INT NOT NULL,\r\n" + "  `professor_name` VARCHAR(45) NOT NULL,\r\n"
					+ "  `professor_addr` VARCHAR(45) NOT NULL,\r\n" + "  `professor_phone` VARCHAR(45) NOT NULL,\r\n"
					+ "  `professor_email` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`professor_id`))\r\n"
					+ "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`circle` (\r\n" + "  `circle_id` INT NOT NULL,\r\n"
							+ "  `circle_name` VARCHAR(45) NOT NULL,\r\n" + "  `circle_addr` VARCHAR(45) NOT NULL,\r\n"
							+ "  `professor_id` INT NOT NULL,\r\n" + "  `circle_num` INT NOT NULL,\r\n"
							+ "  PRIMARY KEY (`circle_id`),\r\n"
							+ "  INDEX `fk_circle_professor1_idx` (`professor_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_circle_professor1`\r\n" + "    FOREIGN KEY (`professor_id`)\r\n"
							+ "    REFERENCES `madang`.`professor` (`professor_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`student` (\r\n" + "  `student_id` INT NOT NULL,\r\n"
							+ "  `student_name` VARCHAR(45) NOT NULL,\r\n"
							+ "  `student_addr` VARCHAR(45) NOT NULL,\r\n"
							+ "  `student_phone` VARCHAR(45) NOT NULL,\r\n"
							+ "  `student_email` VARCHAR(45) NOT NULL,\r\n" + "  `professor_id` INT NOT NULL,\r\n"
							+ "  PRIMARY KEY (`student_id`),\r\n"
							+ "  INDEX `fk_student_professor1_idx` (`professor_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_student_professor1`\r\n" + "    FOREIGN KEY (`professor_id`)\r\n"
							+ "    REFERENCES `madang`.`professor` (`professor_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`circle_has_student` (\r\n" + "  `circle_id` INT NOT NULL,\r\n"
							+ "  `student_id` INT NOT NULL,\r\n" + "  `circle_principle` INT NOT NULL,\r\n"
							+ "  INDEX `fk_circle_has_student_student1_idx` (`student_id` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_circle_has_student_circle1_idx` (`circle_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_circle_has_student_circle1`\r\n" + "    FOREIGN KEY (`circle_id`)\r\n"
							+ "    REFERENCES `madang`.`circle` (`circle_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n" + "  CONSTRAINT `fk_circle_has_student_student1`\r\n"
							+ "    FOREIGN KEY (`student_id`)\r\n"
							+ "    REFERENCES `madang`.`student` (`student_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`tuition` (\r\n" + "  `student_id` INT NOT NULL,\r\n"
							+ "  `tuition_year` INT NOT NULL,\r\n" + "  `tuition_semester` INT NOT NULL,\r\n"
							+ "  `tuition_last_updated` DATETIME NOT NULL,\r\n" + "  `tuition_paid` INT NOT NULL,\r\n"
							+ "  `tuition_fee` INT NOT NULL,\r\n" + "  `tuition_num` VARCHAR(45) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`student_id`, `tuition_num`),\r\n"
							+ "  INDEX `fk_tuition_student1_idx` (`student_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_tuition_student1`\r\n" + "    FOREIGN KEY (`student_id`)\r\n"
							+ "    REFERENCES `madang`.`student` (`student_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`department` (\r\n" + "  `department_id` INT NOT NULL,\r\n"
							+ "  `department_name` VARCHAR(45) NOT NULL,\r\n"
							+ "  `department_phone` VARCHAR(45) NOT NULL,\r\n"
							+ "  `department_addr` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`department_id`))\r\n"
							+ "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`course` (\r\n" + "  `course_id` INT NOT NULL,\r\n"
							+ "  `course_name` VARCHAR(45) NOT NULL,\r\n" + "  `course_date` VARCHAR(45) NOT NULL,\r\n"
							+ "  `course_addr` VARCHAR(45) NOT NULL,\r\n" + "  `course_div` INT NOT NULL,\r\n"
							+ "  `course_score` INT NOT NULL,\r\n" + "  `course_hour` INT NOT NULL,\r\n"
							+ "  `course_period` INT NOT NULL,\r\n" + "  `department_id` INT NOT NULL,\r\n"
							+ "  `course_year` INT NOT NULL,\r\n" + "  `course_semester` INT NOT NULL,\r\n"
							+ "  `professor_id` INT NOT NULL,\r\n" + "  PRIMARY KEY (`course_id`),\r\n"
							+ "  INDEX `fk_course_department1_idx` (`department_id` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_course_professor1_idx` (`professor_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_course_department1`\r\n" + "    FOREIGN KEY (`department_id`)\r\n"
							+ "    REFERENCES `madang`.`department` (`department_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n" + "  CONSTRAINT `fk_course_professor1`\r\n"
							+ "    FOREIGN KEY (`professor_id`)\r\n"
							+ "    REFERENCES `madang`.`professor` (`professor_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`department_has_student` (\r\n"
							+ "  `department_id` INT NOT NULL,\r\n" + "  `student_id` INT NOT NULL,\r\n"
							+ "  INDEX `fk_department_has_student_student1_idx` (`student_id` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_department_has_student_department1_idx` (`department_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_department_has_student_department1`\r\n"
							+ "    FOREIGN KEY (`department_id`)\r\n"
							+ "    REFERENCES `madang`.`department` (`department_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n" + "  CONSTRAINT `fk_department_has_student_student1`\r\n"
							+ "    FOREIGN KEY (`student_id`)\r\n"
							+ "    REFERENCES `madang`.`student` (`student_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`professor_has_department` (\r\n"
							+ "  `professor_id` INT NOT NULL,\r\n" + "  `department_id` INT NOT NULL,\r\n"
							+ "  `department_president` INT NOT NULL,\r\n"
							+ "  INDEX `fk_professor_has_department_department1_idx` (`department_id` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_professor_has_department_professor1_idx` (`professor_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_professor_has_department_professor1`\r\n"
							+ "    FOREIGN KEY (`professor_id`)\r\n"
							+ "    REFERENCES `madang`.`professor` (`professor_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n"
							+ "  CONSTRAINT `fk_professor_has_department_department1`\r\n"
							+ "    FOREIGN KEY (`department_id`)\r\n"
							+ "    REFERENCES `madang`.`department` (`department_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`professor_student` (\r\n" + "  `semester` INT NOT NULL,\r\n"
							+ "  `school_year` INT NOT NULL,\r\n" + "  `student_id` INT NOT NULL,\r\n"
							+ "  `professor_id` INT NOT NULL,\r\n"
							+ "  INDEX `fk_professor_student_professor1_idx` (`professor_id` ASC) VISIBLE,\r\n"
							+ "  PRIMARY KEY (`student_id`, `semester`, `school_year`),\r\n"
							+ "  CONSTRAINT `fk_professor_student_student1`\r\n" + "    FOREIGN KEY (`student_id`)\r\n"
							+ "    REFERENCES `madang`.`student` (`student_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n" + "  CONSTRAINT `fk_professor_student_professor1`\r\n"
							+ "    FOREIGN KEY (`professor_id`)\r\n"
							+ "    REFERENCES `madang`.`professor` (`professor_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;",
					"CREATE TABLE IF NOT EXISTS `madang`.`student_course` (\r\n" + "  `attendance_score` INT NULL,\r\n"
							+ "  `mid_score` INT NULL,\r\n" + "  `final_score` INT NULL,\r\n"
							+ "  `etc_score` INT NULL,\r\n" + "  `total_score` INT NULL,\r\n"
							+ "  `grade` VARCHAR(45) NULL,\r\n" + "  `student_id` INT NOT NULL,\r\n"
							+ "  `course_id` INT NOT NULL,\r\n" + "  `student_course_year` INT NOT NULL,\r\n"
							+ "  `student_course_semester` INT NOT NULL,\r\n"
							+ "  INDEX `fk_student_course_course1_idx` (`course_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_student_course_student1`\r\n" + "    FOREIGN KEY (`student_id`)\r\n"
							+ "    REFERENCES `madang`.`student` (`student_id`)\r\n" + "    ON DELETE cascade\r\n"
							+ "    ON UPDATE cascade,\r\n" + "  CONSTRAINT `fk_student_course_course1`\r\n"
							+ "    FOREIGN KEY (`course_id`)\r\n" + "    REFERENCES `madang`.`course` (`course_id`)\r\n"
							+ "    ON DELETE cascade\r\n" + "    ON UPDATE cascade)\r\n" + "ENGINE = InnoDB;" };
			String insertQuery[] = {
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (1, '�ŵ���', 'AI825', '0234083241', 'dshin@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (2, '���¼�', 'AI823', '0234083240', 'tspark@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (3 , '��뱹', 'AI722', '0234083759', 'ykim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (4, '�����', 'AI625', '0234083795', 'wikim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (5, '�鼺��', 'AI622', '0234083797', 'sbaik@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (6, '�ۿ���', 'AI623', '0234083830', 'oysong@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (7, '������', '����502', '0234083747', 'jano@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (8, '����', '�������1012B', '0234083756', 'jangy@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (9, '������', '���ǰ�317', '0269352492', 'jkwak@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (10, '���ø�', 'AI413', '0234083148', 'simons@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (11, '������', 'AI605', '0269352484', 'useung@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (12, '������', 'AI607', '0234083784', 'jinu@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (13, '��ö', 'AI609', '0269352670', 'cjeong@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (14, '������', 'AI819', '0234083474', 'hmoon@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (15, '������', 'AI601', '0269352671', 'ykchoi@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (16, '�̼���', 'AI455', '0234081867', 'lsj@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (17, '�赿��', '�湫��714', '0234083820', 'djkim75@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (18, '��¾�', '�湫��713', '0234083291', 'sekim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (19, '���ȿ', '�湫��717', '0234083814', 'dhbae@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (20, '����', '���ǰ�513', '0234083826', 'jinhur@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (21, '������', '���ǰ�518', '0234083970', 'jinwoocho@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (22, '������', '������909', '0269352563', 'jyshim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (23, '����ö', '������917', '0234083134', 'jyc@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (24, '���μ�', '������711', '0234083130', 'inlee@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (25, '�ս���', '������512', '0234083706', 'shsogon@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (26, '�̼�ȣ', '���ǰ�306', '0234083205', 'leesh@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (27, '�̽¿�', '���ǰ�312', '0234083161', 'leesy@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (28, '�̿���', '���ǰ� 311', '0234083979', 'eonkyung@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (1, '���ڷ���', '�л�ȸ��510', 1, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (2, '����', '�л�ȸ��620', 2, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (3, '����', '�л�ȸ��516', 3, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (4, 'SELS', '�л�ȸ��504', 4, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (5, '������', '�л�ȸ��603', 5, 5);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (6, '������', '�л�ȸ��524', 6, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (7, '��', '�л�ȸ��509', 7, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (8, '��������ȸ', '�л�ȸ��607', 8, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (9, '������ȸ', '�л�ȸ��512', 9, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (10, '�������̽�', '�л�ȸ��518', 10, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (11, 'UNSA', '�л�ȸ��521', 11, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (12, '������', '�л�ȸ��520', 12, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (13, '�ҿ�Ʈ����', '�л�ȸ��B201', 13, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (14, '������ȸ', '�л�ȸ��526', 14, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (15, '��Ʈ��', '�л�ȸ��B202', 15, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (16, '�͹���', '�л�ȸ��621', 16, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (17, '������7', '�л�ȸ��605', 17, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (18, '�ѿ︲', '�л�ȸ��514', 18, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (19, 'STC', '�л�ȸ��508', 19, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (20, '��Ʈ��', '�л�ȸ��528', 20, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (21, '�׼�ȸ', '�л�ȸ��532', 21, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (22, '����ȣ����', '�л�ȸ��509', 22, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (23, '�ι���', '�л�ȸ��624', 23, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (24, 'BAMBOO', '�л�ȸ��507', 24, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (25, '��������', '�л�ȸ��606', 25, 1);\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (1, '��ǻ�Ͱ��а�', '0234083321', 'AI442');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (2, '����Ʈ�����а�', '0234083667', 'AI402');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (3, '������ȣ�а�', '0234084181', 'AI404');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (4, '�����ͻ��̾��а�', '0269352544', 'AI403');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (5, '���ɱ������к�', '0234083900', 'AI405');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (6, '�������̳뺣�̼�', '0234083323', '����514');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (7, '��ȭ�ִϸ��̼���', '0234083328', '���ڰ�415');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (8, '�ΰ������а�', '0269352483', 'AI409');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (9, '������к�', '0234083331', '�湫��719');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (10, '�Ǽ�ȯ�����', '0234083332', '�湫��718');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (11, 'ȯ�濡�������������а�', '0234083320', '���ǰ�516');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (12, '�����ڿ��ý��۰��а�', '0234083671', '�湫��1102A');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (13, '�����а�', '0234083663', '�湫��1009A');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (14, '�װ������а�', '0234083333', '�湫��1009B');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (15, '����ż�����а�', '0234083664', '�湫��816');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (16, '���ڿ��ڷ°��а�', '0234083491', '�����307');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (17, '����ý��۰��а�', '0234083674', '�������1009C');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (18, '�����а�', '0234083306', '�������704B');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (19, '�����а�', '0234083308', '������604');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (20, '�̵��Ŀ�´����̼��а�', '0234083307', '������506');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (21, '������а�', '0234084301', '������908');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (22, '������а�', '0234083302', '������805');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (23, '�����а�', '0234083305', '������915');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (24, '�����а�', '0234083304', '������706');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (25, '�۷ι������а�', '0234081831', '�������817');\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (1, 14, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (2, 15, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (3, 16, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (4, 17, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (5, 18, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (6, 19, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (7, 20, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (8, 21, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (9, 22, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (10, 23, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (11, 24, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (12, 25, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (13, 1, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (14, 2, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (15, 3, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (16, 4, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (17, 5, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (18, 6, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (19, 7, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (20, 8, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (21, 9, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (22, 10, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (23, 11, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (24, 12, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (25, 13, 1);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (26, 3, 0);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (27, 2, 0);\r\n",
					"INSERT INTO `madang`.`professor_has_department` (`professor_id`, `department_id`, `department_president`) VALUES (28, 1, 0);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (1,'���°�','����� ������','010-5285-3994','wkdska957@naver.com',12);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (2,'������','����� ������','010-7467-2111','whitecapella@naver.com',15);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (3,'����','����� ������','010-6339-7148','kanggakky1214@gmail.com',20);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (4,'������','����� ����','010-4912-4701','bond9986@naver.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (5,'��ٽ�','����� ���۱�','010-5443-5830','dbsdbsgks4@naver.com',23);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (6,'�����','����� ���α�','010-1932-1332','pppclove29@naver.com',2);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (7,'�迬��','����� �߶���','010-2438-8474','inki3003@gmail.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (8,'�迵��','����� �����','010-4630-7751','rbqls1057@naver.com',3);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (9,'�迹��','����� ��õ��','010-9374-5864','yedol1@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (10,'������','����� ���ʱ�','010-9705-7507','sjleo1@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (11,'����ȣ','����� ���ĺ�','010-5748-8896','duckddud213@naver.com',5);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (12,'�ڹμ�','����� ������','010-4314-4595','tlawogh45@naver.com',6);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (13,'�ڹ���','����� ��õ��','010-9199-9770','a41937164@gmail.com',7);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (14,'�ڼ���','������ ��ȱ�','010-1669-6751','bleach1021@naver.com',8);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (15,'����ȯ','������ �Ǽ���','010-7558-8648','godtjrdl98@gmail.com',9);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (16,'������','������ �ȴޱ�','010-9227-6911','jods9758@naver.com',10);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (17,'���ֿ�','������ ���뱸','010-1573-5417','minia777654@naver.com',11);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (18,'������','������ ������','010-1335-5393','cg456456@naver.com',12);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (19,'������','������ �߿���','010-4930-8289','chlqudco2@naver.com',13);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (20,'������','������ �д籸','010-8730-9533','yrc9229@gmail.com',14);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (21,'���ؿ�','���� ���籸','010-9420-1023','happy981024@naver.com',15);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (22,'�ſ켷','���� �ϻ굿��','010-1544-4096','hep1224@naver.com',16);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (23,'����ȣ','���� �ϻ꼭��','010-5275-2813','eunseo9808@naver.com',17);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (24,'��â��','���ν� ó�α�','010-3325-1846','ymkim97@gmail.com',18);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (25,'������','���ν� ���ﱸ','010-3365-2020','kimjiho246@naver.com',19);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (26,'������','���ν� ������','010-9091-9517','saengseun276@naver.com',20);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (27,'��â��','�λ걤���� ������','010-4303-4885','sky10240107@naver.com',21);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (28,'�̰���','�λ걤���� ������ ','010-2245-6212','jyp9810@naver.com',22);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (29,'�̱���','�λ걤���� ���屺','010-9091-8540','kyl9164@naver.com',23);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (30,'�̼���','�λ걤���� ����','010-8302-1012','whdlsgml1599@naver.com',24);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (31,'�̼���','�λ걤���� ����','010-3168-7685','wlsdn1004100@naver.com',25);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (32,'������','�λ걤���� ������','010-4469-5548','anekd1917@naver.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (33,'������','�뱸������ ����','010-1041-2930','sdw504512@naver.com',2);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (34,'���̱�','�뱸������ �޼���','010-8462-4555','lin980818@naver.com',3);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (35,'������','�뱸������ ����','010-3475-8917','dydwo706@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (36,'������','�뱸������ �ϱ�','010-9852-7728','codns1119@naver.com',5);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (37,'������','�뱸������ ����','010-4505-6397','ochh508@naver.com',6);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (38,'������','���ֱ����� ���걸 ','010-4043-4007','0802skdus@naver.com',7);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (39,'������','���ֱ����� ����','010-3078-9755','veritas4317@naver.com',8);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (40,'�㼭��','���ֱ����� ����','010-5751-6670','imk1017@naver.com',9);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (1, '�����ͺ��̽�', 'ȭ��', 'AIB206', 1, 2, 2, 1, 1, 2019, 1, 1);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (2, 'C#���α׷���', 'ȭ��', 'AIB108', 2, 2, 2, 3, 2, 2019, 1, 2);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (3, '�ü��', 'ȭ��', 'AIB112', 1, 2, 2, 5, 3, 2019, 1, 3);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (4, '�ڷᱸ���׽ǽ�', '����', 'AIB205', 3, 2, 2, 7, 4, 2019, 1, 4);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (5, '����н�', '��', 'AIB105', 1, 2, 2, 9, 5, 2019, 2, 5);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (6, '����ȸ��', '����', '�湫��115', 2, 2, 2, 1, 6, 2019, 2, 6);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (7, '���л�', '��', '�湫��205', 5, 2, 2, 3, 7, 2019, 2, 7);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (8, '��â�������μ���', 'ȭ��', '�湫��415', 1, 2, 2, 5, 8, 2019, 2, 8);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (9, '�����������', '��', '�湫��221', 2, 3, 3, 1, 9, 2019, 2, 9);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (10, '�̹�������ŷ', '��', '�������201', 5, 3, 3, 4, 10, 2019, 1, 10);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (11, '�Ϻ�����������', 'ȭ��', 'AIB205', 1, 3, 3, 7, 11, 2019, 1, 11);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (12, '����ö��', '��', '�������702', 2, 3, 3, 1, 12, 2019, 1, 12);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (13, '��������', '��', 'AIB110', 2, 3, 3, 4, 13, 2, 2021, 13);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (14, '��ǻ�ͳ�Ʈ��ũ', 'ȭ��', 'AIB201', 2, 3, 3, 7, 14, 2020, 1, 14);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (15, '���μ������ڱ���', '����', '�������102', 1, 3, 3, 1, 15, 2020, 1, 15);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (16, '������', 'ȭ��', '�����102', 2, 3, 3, 4, 16, 2020, 1, 16);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (17, '��ǻ�ͻ���ݱ����ڵ�', '��', '�м�������401', 1, 3, 3, 7, 17, 2020, 1, 17);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (18, '�ΰ�Ŀ�´����̼�', 'ȭ��', '�������215', 2, 3, 3, 1, 18, 2020, 2, 18);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (19, '���ʹ�������', 'ȭ��', 'AIB201', 1, 1, 1, 1, 19, 2020, 2, 19);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (20, '�濵����', '����', '�������305', 2, 1, 1, 2, 20, 2020, 2, 20);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (21, 'ä���ѷ�', '����', '�������405', 1, 1, 1, 3, 21, 2020, 2, 21);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (22, '������ȸ�ǻ��̹�����', '��', '����Ȧ504', 2, 1, 1, 4, 22, 2020, 1, 22);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (23, '��ü����', 'ȭ��', '�����201', 1, 1, 1, 5, 23, 2020, 1, 23);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (24, 'Ǫ���ٴٻ����̾߱�', '��', '�����302', 2, 1, 1, 6, 24, 2020, 1, 24);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (25, '���ʹ���ȭ��', 'ȭ��', '�����110', 1, 1, 1, 7, 25, 2020, 1, 25);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 50, 100, 0, 150, 'B+', 1, 1, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 100, 100, 0, 200, 'A+', 1, 2, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 80, 80, 0, 160, 'A0', 1, 3, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (80, 80, 70, 2, 152, 'A0', 1, 4, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (92, 85, 40, 0, 125, 'C+', 1, 5, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (96, 45, 70, 0, 115, 'D+', 1, 6, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 59, 90, 0, 149, 'B+', 1, 7, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 61, NULL, NULL, NULL, NULL, 2, 8, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 11, 60, 0, 71, 'F', 2, 9, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 5, 40, NULL, NULL, NULL, 2, 13, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 0, NULL, NULL, NULL, NULL, 2, 14, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 22, NULL, NULL, NULL, NULL, 2, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (94, 35, NULL, NULL, NULL, NULL, 3, 13, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 48, NULL, NULL, NULL, NULL, 3, 6, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (90, 48, NULL, NULL, NULL, NULL, 3, 7, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (15, 0, NULL, NULL, NULL, NULL, 3, 8, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 22, NULL, NULL, NULL, NULL, 3, 12, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, NULL, NULL, NULL, NULL, NULL, 3, 18, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 52, NULL, NULL, NULL, NULL, 5, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (96, 7, NULL, NULL, NULL, NULL, 5, 2, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 56, NULL, NULL, NULL, NULL, 5, 4, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 35, NULL, NULL, NULL, NULL, 5, 25, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (90, 21, NULL, NULL, NULL, NULL, 7, 20, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 25, NULL, NULL, NULL, NULL, 7, 14, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (94, 25, NULL, NULL, NULL, NULL, 7, 1, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (92, 59, NULL, NULL, NULL, NULL, 7, 13, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 27, NULL, NULL, NULL, NULL, 7, 3, 2019, 2);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 58, NULL, NULL, NULL, NULL, 7, 4, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 52, NULL, NULL, NULL, NULL, 10, 1, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 76, NULL, NULL, NULL, NULL, 10, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (97, 26, NULL, NULL, NULL, NULL, 10, 6, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (95, 48, NULL, NULL, NULL, NULL, 10, 16, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (44, 8, NULL, NULL, NULL, NULL, 10, 18, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (46, 16, NULL, NULL, NULL, NULL, 4, 20, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (16, 28, NULL, NULL, NULL, NULL, 6, 24, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 93, NULL, NULL, NULL, NULL, 8, 7, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 24, NULL, NULL, NULL, NULL, 9, 14, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (97, 68, NULL, NULL, NULL, NULL, 11, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (72, 15, NULL, NULL, NULL, NULL, 11, 23, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (82, 18, NULL, NULL, NULL, NULL, 12, 21, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 23, NULL, NULL, NULL, NULL, 13, 22, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (92, 35, NULL, NULL, NULL, NULL, 13, 3, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 95, NULL, NULL, NULL, NULL, 14, 4, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 94, NULL, NULL, NULL, NULL, 15, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 26, NULL, NULL, NULL, NULL, 15, 23, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 67, NULL, NULL, NULL, NULL, 15, 20, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (92, 88, NULL, NULL, NULL, NULL, 16, 14, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (88, 92, NULL, NULL, NULL, NULL, 17, 3, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (86, NULL, NULL, NULL, NULL, NULL, 18, 1, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (56, 8, NULL, NULL, NULL, NULL, 19, 8, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 88, NULL, NULL, NULL, NULL, 20, 9, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 56, NULL, NULL, NULL, NULL, 21, 11, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (82, 67, NULL, NULL, NULL, NULL, 22, 13, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (98, 82, NULL, NULL, NULL, NULL, 23, 18, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 61, NULL, NULL, NULL, NULL, 24, 19, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (100, 58, NULL, NULL, NULL, NULL, 25, 12, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 25, 1, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 26, 2, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 27, 3, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 28, 4, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 29, 5, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 30, 6, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 31, 7, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 32, 8, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 33, 9, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 34, 10, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 35, 11, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 36, 12, 2019, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 37, 13, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 38, 14, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 39, 15, 2020, 1);\r\n",
					"INSERT INTO `madang`.`student_course` (`attendance_score`, `mid_score`, `final_score`, `etc_score`, `total_score`, `grade`, `student_id`, `course_id`, `student_course_year`, `student_course_semester`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, 40, 16, 2020, 1);\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (1, 2021, 1, '2021-02-18', 4000000, 4000000, '110-102-452');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (2, 2021, 1, '2021-02-19', 3000000, 4000000, '112-587-633');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (3, 2021, 1, '2021-02-18', 4000000, 4000000, '145-852-462');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (4, 2021, 1, '2021-02-22', 4000000, 4000000, '123-563-523');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (5, 2020, 2, '2020-06-18', 4000000, 4000000, '153-521-460');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (6, 2021, 1, '2021-02-18', 4000000, 4000000, '512-523-812');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (7, 2021, 1, '2021-02-18', 4000000, 4000000, '541-995-512');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (8, 2021, 1, '2021-02-25', 4000000, 4000000, '637-541-962');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (9, 2021, 1, '2020-05-31', 3500000, 4000000, '315-541-456');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (10, 2020, 2, '2020-05-19', 4000000, 4000000, '631-541-562');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (11, 2021, 1, '2021-02-18', 4000000, 4000000, '236-842-562');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (12, 2021, 1, '2021-02-25', 4000000, 4000000, '126-423-952');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (13, 2021, 1, '2021-02-21', 4000000, 4000000, '236-541-854');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (14, 2021, 1, '2021-02-18', 0, 4000000, '665-541-541');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (15, 2019, 2, '2019-05-18', 4000000, 4000000, '412-531-425');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (16, 2021, 1, '2021-02-15', 4000000, 4000000, '213-851-561');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (17, 2021, 1, '2021-02-28', 4000000, 4000000, '265-854-564');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (18, 2021, 1, '2021-02-19', 4000000, 4000000, '236-741-756');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (19, 2021, 1, '2021-02-18', 4000000, 4000000, '315-548-965');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (20, 2020, 1, '2020-02-18', 4000000, 4000000, '310-524-531');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (21, 2021, 1, '2021-02-22', 4000000, 4000000, '412-531-985');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (22, 2021, 1, '2021-02-18', 4000000, 4000000, '714-531-250');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (23, 2021, 1, '2021-02-19', 4000000, 4000000, '721-568-412');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (24, 2021, 1, '2021-02-23', 4000000, 4000000, '248-561-825');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (25, 2019, 2, '2019-05-25', 4000000, 4000000, '123-568-412');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (26, 2021, 1, '2021-02-18', 4000000, 4000000, '523-564-512');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (27, 2021, 1, '2021-02-26', 4000000, 4000000, '745-560-563');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (28, 2021, 1, '2021-02-19', 4000000, 4000000, '259-862-631');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (29, 2021, 1, '2021-02-18', 4000000, 4000000, '123-533-412');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (30, 2019, 2, '2019-05-28', 4000000, 4000000, '157-523-561');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (31, 2021, 1, '2021-02-28', 4000000, 4000000, '128-563-426');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (32, 2021, 1, '2021-02-26', 4000000, 4000000, '412-893-562');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (33, 2021, 1, '2021-02-18', 4000000, 4000000, '742-563-522');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (34, 2021, 1, '2021-02-27', 4000000, 4000000, '547-862-552');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (35, 2020, 2, '2021-05-18', 4000000, 4000000, '514-756-523');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (36, 2021, 1, '2021-02-19', 1000000, 4000000, '654-852-452');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (37, 2021, 1, '2021-02-23', 4000000, 4000000, '452-865-541');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (38, 2021, 1, '2021-02-27', 4000000, 4000000, '452-845-789');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (39, 2021, 1, '2021-02-23', 4000000, 4000000, '652-568-412');\r\n",
					"INSERT INTO `madang`.`tuition` (`student_id`, `tuition_year`, `tuition_semester`, `tuition_last_updated`, `tuition_paid`, `tuition_fee`, `tuition_num`) VALUES (40, 2020, 1, '2020-03-03', 4000000, 4000000, '412-568-456');\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (2,2);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (3,3);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (4,4);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (5,5);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (6,6);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (7,7);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (8,8);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (9,9);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (10,10);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (11,11);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (12,12);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (13,13);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (14,14);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (15,15);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (16,16);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (17,17);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (18,18);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (19,19);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (20,20);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (21,21);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (22,22);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (23,23);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (24,24);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (25,25);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (1,26);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (2,27);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (3,28);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (4,29);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (5,30);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (6,31);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (7,32);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (8,33);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (9,34);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (10,35);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (11,36);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (12,37);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (13,38);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (14,38);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (15,39);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (16,40);\r\n",
					"INSERT INTO `madang`.`department_has_student` (`department_id`,`student_id`) VALUES (1,1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (1, 1, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (1, 2, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (1, 3, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (2, 4, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (2, 5, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (2, 6, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (3, 7, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (4, 8, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (4, 9, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (5, 10, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (5, 11, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (5, 12, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (5, 13, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (5, 14, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (6, 15, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (6, 16, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (7, 17, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (7, 18, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (7, 19, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (8, 20, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (8, 21, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (8, 22, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (9, 23, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (9, 24, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (9, 25, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (10, 26, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (10, 27, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (11, 28, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (12, 29, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (13, 30, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (14, 31, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (14, 32, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (14, 33, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (15, 34, 1);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (15, 35, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (16, 36, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (17, 37, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (17, 38, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (17, 39, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (18, 40, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (18, 1, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (19, 2, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (19, 3, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (20, 4, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (21, 5, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (22, 6, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (22, 7, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (23, 8, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (23, 9, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (24, 10, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (24, 11, 0);\r\n",
					"INSERT INTO `madang`.`circle_has_student` (`circle_id`, `student_id`, `circle_principle`) VALUES (25, 12, 0);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,4,1);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,6,2);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,8,3);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,9,4);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,11,5);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,12,6);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,13,7);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,14,8);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,15,9);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,16,10);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,17,11);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,18,12);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,19,13);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,20,14);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,21,15);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,22,16);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,23,17);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,24,18);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,25,19);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,26,20);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,27,21);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,28,22);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,29,23);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,30,24);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,31,25);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,32,1);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,33,2);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,34,3);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,35,4);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,36,5);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,37,6);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,38,7);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,39,8);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,40,9);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,1,10);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,1,11);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,1,12);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,2,13);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,2,14);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,2,15);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,3,16);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,3,17);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,3,18);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2020,3,19);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2019,3,20);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,5,21);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,5,22);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,5,23);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,7,24);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,7,25);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,7,1);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2021,10,2);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (2,2021,10,3);\r\n",
					"INSERT INTO `madang`.`professor_student` (`semester`,`school_year`,`student_id`,`professor_id`) VALUES (1,2020,10,4);\r\n" };

			for (int i = 0; i < deltable.length; i++)
				stmt.executeUpdate(deltable[i]);
			for (int i = 0; i < createtable.length; i++)
				stmt.executeUpdate(createtable[i]);
			for (int i = 0; i < insertQuery.length; i++)
				stmt.executeUpdate(insertQuery[i]);
			JOptionPane.showMessageDialog(null, "�ʱ�ȭ ����!\n");
		} catch (Exception e5) {
			JOptionPane.showMessageDialog(null, "�ʱ�ȭ ���� :" + e5);
		}
	}

}