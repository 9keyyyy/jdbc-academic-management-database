
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class infosystem extends JFrame implements ActionListener {
   JButton STUDENT, PROFESSOR, ADMIN;
   JPanel pn, pn1;

   // 0605
   private Admin admin;
   private Student student;
   private Professor_tab professor;

   public infosystem() {
      super("학사정보시스템");
      layInit();

      setVisible(true);
      setBounds(200, 200, 300, 300); // 가로위치,세로위치,가로길이,세로길이
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
      STUDENT = new JButton("학생");
      PROFESSOR = new JButton("교수");
      ADMIN = new JButton("관리자");

      pn = new JPanel();
      pn.setLayout(new GridLayout(3, 1));

      pn.add(STUDENT);
      pn.add(PROFESSOR);
      pn.add(ADMIN);

      add("Center", pn);

      STUDENT.addActionListener(this);
      PROFESSOR.addActionListener(this);
      ADMIN.addActionListener(this);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == ADMIN) {// 0601
         if (admin != null)
            admin.dispose();// 기존 frame 없애기
         admin = new Admin();// 새로 생성
      }

      if (e.getSource() == STUDENT) {
         if (student != null)
            student.dispose();// 기존 frame 없애기
         student = new Student();// 새로 생성
      }
      if (e.getSource() == PROFESSOR) {
         if (professor != null)
            professor.dispose();// 기존 frame 없애기
         professor = new Professor_tab();//
      }
   }

   public static void main(String[] args) {
      infosystem BLS = new infosystem();

      // BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      BLS.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {

            System.out.println("프로그램 완전 종료!");
            System.exit(0);
         }
      });
   }

}