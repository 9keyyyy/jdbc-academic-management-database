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
		setTitle("관리자");
		layInit();
		setVisible(true);
		conDB();
		setBounds(200, 200, 570, 170); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void layInit() {
		setLayout(null);

		p1 = new JPanel();
		p2 = new JPanel();

		btnAll = new JButton("전체 테이블 보여주기");
		btnReset = new JButton("테이블 초기화");
		btnDU = new JButton("삭제/변경");
		btnInsert = new JButton("입력");

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
				insertinfo.dispose();// 기존 frame 없애기
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
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (1, '신동일', 'AI825', '0234083241', 'dshin@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (2, '박태순', 'AI823', '0234083240', 'tspark@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (3 , '김용국', 'AI722', '0234083759', 'ykim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (4, '김원일', 'AI625', '0234083795', 'wikim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (5, '백성욱', 'AI622', '0234083797', 'sbaik@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (6, '송오영', 'AI623', '0234083830', 'oysong@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (7, '노재춘', '율곡502', '0234083747', 'jano@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (8, '장윤', '광개토관1012B', '0234083756', 'jangy@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (9, '곽진태', '영실관317', '0269352492', 'jkwak@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (10, '오시몬', 'AI413', '0234083148', 'simons@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (11, '임유승', 'AI605', '0269352484', 'useung@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (12, '송진우', 'AI607', '0234083784', 'jinu@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (13, '정철', 'AI609', '0269352670', 'cjeong@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (14, '문현준', 'AI819', '0234083474', 'hmoon@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (15, '최유경', 'AI601', '0269352671', 'ykchoi@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (16, '이수진', 'AI455', '0234081867', 'lsj@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (17, '김동주', '충무관714', '0234083820', 'djkim75@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (18, '김승억', '충무관713', '0234083291', 'sekim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (19, '배덕효', '충무관717', '0234083814', 'dhbae@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (20, '허진', '영실관513', '0234083826', 'jinhur@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (21, '조진우', '영실관518', '0234083970', 'jinwoocho@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (22, '심지영', '집현관909', '0269352563', 'jyshim@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (23, '정연철', '집현관917', '0234083134', 'jyc@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (24, '이인숙', '집현관711', '0234083130', 'inlee@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (25, '손승혜', '집현관512', '0234083706', 'shsogon@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (26, '이선호', '영실관306', '0234083205', 'leesh@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (27, '이승연', '영실관312', '0234083161', 'leesy@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`professor` (`professor_id`, `professor_name`, `professor_addr`, `professor_phone`, `professor_email`) VALUES (28, '이연경', '영실관 311', '0234083979', 'eonkyung@sejong.ac.kr');\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (1, '늘혬코러스', '학생회관510', 1, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (2, '더블랙', '학생회관620', 2, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (3, '러쉬', '학생회관516', 3, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (4, 'SELS', '학생회관504', 4, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (5, '마스터', '학생회관603', 5, 5);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (6, '별무리', '학생회관524', 6, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (7, '율', '학생회관509', 7, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (8, '세종문학회', '학생회관607', 8, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (9, '세종서회', '학생회관512', 9, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (10, '인터페이스', '학생회관518', 10, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (11, 'UNSA', '학생회관521', 11, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (12, '레지나', '학생회관520', 12, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (13, '소울트레인', '학생회관B201', 13, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (14, '세종극회', '학생회관526', 14, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (15, '인트로', '학생회관B202', 15, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (16, '터벌림', '학생회관621', 16, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (17, '페이지7', '학생회관605', 17, 3);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (18, '한울림', '학생회관514', 18, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (19, 'STC', '학생회관508', 19, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (20, '요트부', '학생회관528', 20, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (21, '죽순회', '학생회관532', 21, 1);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (22, '유스호스텔', '학생회관509', 22, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (23, '두바퀴', '학생회관624', 23, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (24, 'BAMBOO', '학생회관507', 24, 2);\r\n",
					"INSERT INTO `madang`.`circle` (`circle_id`, `circle_name`, `circle_addr`, `professor_id`, `circle_num`) VALUES (25, '유마프랜', '학생회관606', 25, 1);\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (1, '컴퓨터공학과', '0234083321', 'AI442');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (2, '소프트웨어학과', '0234083667', 'AI402');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (3, '정보보호학과', '0234084181', 'AI404');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (4, '데이터사이언스학과', '0269352544', 'AI403');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (5, '지능기전공학부', '0234083900', 'AI405');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (6, '디자인이노베이션', '0234083323', '진관514');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (7, '만화애니메이션텍', '0234083328', '군자관415');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (8, '인공지능학과', '0269352483', 'AI409');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (9, '건축공학부', '0234083331', '충무관719');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (10, '건설환경공학', '0234083332', '충무관718');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (11, '환경에너지공간융합학과', '0234083320', '영실관516');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (12, '지구자원시스템공학과', '0234083671', '충무관1102A');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (13, '기계공학과', '0234083663', '충무관1009A');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (14, '항공우주학과', '0234083333', '충무관1009B');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (15, '나노신소재공학과', '0234083664', '충무관816');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (16, '양자원자력공학과', '0234083491', '율곡관307');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (17, '국방시스템공학과', '0234083674', '광개토관1009C');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (18, '경제학과', '0234083306', '광개토관704B');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (19, '행정학과', '0234083308', '집현관604');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (20, '미디어커뮤니케이션학과', '0234083307', '집현관506');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (21, '국어국문학과', '0234084301', '집현관908');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (22, '영어영문학과', '0234083302', '집현관805');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (23, '역사학과', '0234083305', '집현관915');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (24, '교육학과', '0234083304', '집현관706');\r\n",
					"INSERT INTO `madang`.`department` (`department_id`, `department_name`, `department_phone`, `department_addr`) VALUES (25, '글로벌조리학과', '0234081831', '광개토관817');\r\n",
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
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (1,'강승곤','서울시 강남구','010-5285-3994','wkdska957@naver.com',12);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (2,'강예림','서울시 강동구','010-7467-2111','whitecapella@naver.com',15);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (3,'고석준','서울시 강서구','010-6339-7148','kanggakky1214@gmail.com',20);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (4,'곽나연','서울시 은평구','010-4912-4701','bond9986@naver.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (5,'김다슬','서울시 동작구','010-5443-5830','dbsdbsgks4@naver.com',23);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (6,'김민정','서울시 구로구','010-1932-1332','pppclove29@naver.com',2);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (7,'김연신','서울시 중락구','010-2438-8474','inki3003@gmail.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (8,'김영명','서울시 노원구','010-4630-7751','rbqls1057@naver.com',3);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (9,'김예린','서울시 양천구','010-9374-5864','yedol1@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (10,'김지수','서울시 서초구','010-9705-7507','sjleo1@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (11,'김지호','서울시 송파부','010-5748-8896','duckddud213@naver.com',5);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (12,'박민성','서울시 광진구','010-4314-4595','tlawogh45@naver.com',6);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (13,'박민지','서울시 금천구','010-9199-9770','a41937164@gmail.com',7);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (14,'박세준','수원시 장안구','010-1669-6751','bleach1021@naver.com',8);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (15,'박윤환','수원시 권선구','010-7558-8648','godtjrdl98@gmail.com',9);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (16,'박정은','수원시 팔달구','010-9227-6911','jods9758@naver.com',10);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (17,'박주원','수원시 영통구','010-1573-5417','minia777654@naver.com',11);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (18,'박주현','성남시 수정구','010-1335-5393','cg456456@naver.com',12);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (19,'박지영','성남시 중원구','010-4930-8289','chlqudco2@naver.com',13);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (20,'박형준','성남시 분당구','010-8730-9533','yrc9229@gmail.com',14);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (21,'서해영','고양시 덕양구','010-9420-1023','happy981024@naver.com',15);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (22,'신우섭','고양시 일산동구','010-1544-4096','hep1224@naver.com',16);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (23,'심재호','고양시 일산서구','010-5275-2813','eunseo9808@naver.com',17);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (24,'여창영','용인시 처인구','010-3325-1846','ymkim97@gmail.com',18);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (25,'염승훈','용인시 기흥구','010-3365-2020','kimjiho246@naver.com',19);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (26,'윤예지','용인시 수지구','010-9091-9517','saengseun276@naver.com',20);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (27,'윤창민','부산광역시 강서구','010-4303-4885','sky10240107@naver.com',21);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (28,'이경후','부산광역시 금정구 ','010-2245-6212','jyp9810@naver.com',22);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (29,'이광렬','부산광역시 기장군','010-9091-8540','kyl9164@naver.com',23);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (30,'이수민','부산광역시 남구','010-8302-1012','whdlsgml1599@naver.com',24);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (31,'이수진','부산광역시 동구','010-3168-7685','wlsdn1004100@naver.com',25);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (32,'이은서','부산광역시 동래구','010-4469-5548','anekd1917@naver.com',1);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (33,'임준혁','대구광역시 남구','010-1041-2930','sdw504512@naver.com',2);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (34,'정미광','대구광역시 달서구','010-8462-4555','lin980818@naver.com',3);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (35,'정우현','대구광역시 동구','010-3475-8917','dydwo706@naver.com',4);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (36,'조영래','대구광역시 북구','010-9852-7728','codns1119@naver.com',5);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (37,'조인희','대구광역시 서구','010-4505-6397','ochh508@naver.com',6);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (38,'조국희','광주광역시 광산구 ','010-4043-4007','0802skdus@naver.com',7);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (39,'조용재','광주광역시 남구','010-3078-9755','veritas4317@naver.com',8);\r\n",
					"INSERT INTO `madang`.`student` (`student_id`,`student_name`,`student_addr`,`student_phone`,`student_email`,`professor_id`) VALUES (40,'허서영','광주광역시 동구','010-5751-6670','imk1017@naver.com',9);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (1, '데이터베이스', '화목', 'AIB206', 1, 2, 2, 1, 1, 2019, 1, 1);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (2, 'C#프로그래밍', '화목', 'AIB108', 2, 2, 2, 3, 2, 2019, 1, 2);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (3, '운영체제', '화목', 'AIB112', 1, 2, 2, 5, 3, 2019, 1, 3);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (4, '자료구조및실습', '수금', 'AIB205', 3, 2, 2, 7, 4, 2019, 1, 4);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (5, '기계학습', '금', 'AIB105', 1, 2, 2, 9, 5, 2019, 2, 5);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (6, '전기회로', '월수', '충무관115', 2, 2, 2, 1, 6, 2019, 2, 6);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (7, '과학사', '금', '충무관205', 5, 2, 2, 3, 7, 2019, 2, 7);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (8, '취창업과진로설계', '화목', '충무관415', 1, 2, 2, 5, 8, 2019, 2, 8);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (9, '동양고전강독', '월', '충무관221', 2, 3, 3, 1, 9, 2019, 2, 9);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (10, '이미지메이킹', '금', '광개토관201', 5, 3, 3, 4, 10, 2019, 1, 10);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (11, '일변수미적분학', '화목', 'AIB205', 1, 3, 3, 7, 11, 2019, 1, 11);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (12, '서양철학', '수', '광개토관702', 2, 3, 3, 1, 12, 2019, 1, 12);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (13, '공업수학', '금', 'AIB110', 2, 3, 3, 4, 13, 2, 2021, 13);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (14, '컴퓨터네트워크', '화목', 'AIB201', 2, 3, 3, 7, 14, 2020, 1, 14);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (15, '진로설정과자기계발', '월수', '광개토관102', 1, 3, 3, 1, 15, 2020, 1, 15);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (16, '동역학', '화목', '율곡관102', 2, 3, 3, 4, 16, 2020, 1, 16);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (17, '컴퓨터사고기반기초코딩', '월', '학술정보원401', 1, 3, 3, 7, 17, 2020, 1, 17);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (18, '인간커뮤니케이션', '화목', '광개토관215', 2, 3, 3, 1, 18, 2020, 2, 18);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (19, '기초미적분학', '화목', 'AIB201', 1, 1, 1, 1, 19, 2020, 2, 19);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (20, '경영수학', '월수', '광개토관305', 2, 1, 1, 2, 20, 2020, 2, 20);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (21, '채권총론', '수금', '광개토관405', 1, 1, 1, 3, 21, 2020, 2, 21);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (22, '정보사회의사이버윤리', '금', '진관홀504', 2, 1, 1, 4, 22, 2020, 1, 22);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (23, '고체역학', '화목', '율곡관201', 1, 1, 1, 5, 23, 2020, 1, 23);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (24, '푸른바다생물이야기', '목', '율곡관302', 2, 1, 1, 6, 24, 2020, 1, 24);\r\n",
					"INSERT INTO `madang`.`course` (`course_id`, `course_name`, `course_date`, `course_addr`, `course_div`, `course_score`, `course_hour`, `course_period`, `department_id`, `course_year`, `course_semester`, `professor_id`) VALUES (25, '기초물리화학', '화목', '율곡관110', 1, 1, 1, 7, 25, 2020, 1, 25);\r\n",
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
			JOptionPane.showMessageDialog(null, "초기화 성공!\n");
		} catch (Exception e5) {
			JOptionPane.showMessageDialog(null, "초기화 실패 :" + e5);
		}
	}

}