package com.example.jdbctosqlconnect;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbctosqlconnectApplication {

	static String Driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost/markscard";
	static String user = "root";
	static String pass = "Omysmurf@02";
	
	public static void main(String[] args) throws ClassNotFoundException{
createTable();	
insertTable();
	}



public static String createTable() {
	 String sql1 = "CREATE TABLE STUDENT( ID int PRIMARY KEY,NAME VARCHAR(50), AGE int);";
     String sql2 = "CREATE TABLE MARKCARD( ID int PRIMARY KEY, STUDENT_ID int, CONSTRAINT PK_CONSTRAINT FOREIGN KEY (STUDENT_ID)  REFERENCES STUDENT (ID));";
  //String sql3 = "CREATE TABLE SUBJECTS( ID int PRIMARY KEY,SUBJECT_CODE VARCHAR(50), SUBJECTS VARCHAR(50));";
     String sql3= "CREATE TABLE SCORE(ID int PRIMARY KEY,MARKS_ID int, CONSTRAINT PK_CONSTRAINT1 FOREIGN KEY  (MARKS_ID) REFERENCES  MARKCARD (ID),SUBJECT_1 VARCHAR(225),MARKS_1 DOUBLE,SUBJECT_2 VARCHAR(225),MARKS_2 DOUBLE);";
try {
	  Class.forName(Driver);
      Connection con = DriverManager.getConnection(url, user, pass);
      Statement  st = con.createStatement();
      st.executeUpdate(sql1);
      st.executeUpdate(sql2);
      st.executeUpdate(sql3);
     // st.executeUpdate(sql4);
}catch(Exception e) {
	e.printStackTrace();
}
return "Table is created";
}
public static String insertTable() {
	 ArrayList<String> al = new ArrayList<>();
	 String sql1 = "INSERT INTO STUDENT VALUES('1','PRASAD',RAND()*(26-20)+20)";
     String sql2 = "INSERT INTO STUDENT VALUES('2','ANISH' ,RAND()*(26-20)+20)";
     String sql3 = "INSERT INTO STUDENT VALUES('3','DEEPAK',RAND()*(26-20)+20)";
     String sql4 = "INSERT INTO STUDENT VALUES('4','SACHIN',RAND()*(26-20)+20)";
     String sql5 = "INSERT INTO STUDENT VALUES('5','VIRAT' ,RAND()*(26-20)+20)";

     String sql6 = "INSERT INTO MARKCARD VALUES( '1','1')";
     String sql7 = "INSERT INTO MARKCARD VALUES( '2','2')";
     String sql8 = "INSERT INTO MARKCARD VALUES( '3','3')";
     String sql9 = "INSERT INTO MARKCARD VALUES( '4','4')";
     String sql10 ="INSERT INTO MARKCARD VALUES( '5','5')";

//     String sql11 = "INSERT INTO SUBJECTS VALUES('1','S','SCIENCE')";
//     String sql12 = "INSERT INTO SUBJECTS VALUES('2','M','MATHEMATICS')";
//     String sql13 = "INSERT INTO SUBJECTS VALUES('3','S','SCIENCE')";
//     String sql14 = "INSERT INTO SUBJECTS VALUES('4','M','MATHEMATICS')";
//     String sql15 = "INSERT INTO SUBJECTS VALUES('5','S','SCIENCE')";

     String sql11 = "INSERT INTO SCORE VALUES('1','1','MATHEMATICS',RAND()*100,'SCIENCE',RAND()*100)";
     String sql12 = "INSERT INTO SCORE VALUES('2','2','MATHEMATICS',RAND()*100,'SCIENCE',RAND()*100)";
     String sql13 = "INSERT INTO SCORE VALUES('3','3','MATHEMATICS',RAND()*100,'SCIENCE',RAND()*100)";
     String sql14 = "INSERT INTO SCORE VALUES('4','4','MATHEMATICS',RAND()*100,'SCIENCE',RAND()*100)";
     String sql15 = "INSERT INTO SCORE VALUES('5','5','MATHEMATICS',RAND()*100,'SCIENCE',RAND()*100)";
     al.add(sql1);
     al.add(sql2);
     al.add(sql3);
     al.add(sql4);
     al.add(sql5);
     al.add(sql6);
     al.add(sql7);
     al.add(sql8);
     al.add(sql9);
     al.add(sql10);
     al.add(sql11);
     al.add(sql12);
     al.add(sql13);
     al.add(sql14);
     al.add(sql15);
    
     try {
         Class.forName(Driver);
         Connection con = DriverManager.getConnection(url, user, pass);
         Statement st = con.createStatement();
         for(String s:al) {
        	 st.executeUpdate(s);
         }
	
         }catch(Exception e){
        	 e.printStackTrace();
         }
	return "Value inserted successfully";
     }
public static void selectStudent() {
    String sql = "SELECT * FROM STUDENT SD\n"
            + "JOIN MARKCARD MC ON SD.ID=MC.STUDENT_ID\n" + "JOIN SCORE SC ON MC.ID=SC.MARKS_ID";

    try {
        Class.forName(Driver);
        Connection con = DriverManager.getConnection(url, user, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + " " + rs.getInt(3)+" "+rs.getString(8) + "  "+rs.getDouble(9)+ "  "+rs.getString(10)+ "  "+rs.getDouble(11));

    } catch (Exception e) {
        e.printStackTrace();
    }
//    private Statement getBatchStatement(Connection connection ,List<String> sqlStatements) throws SQLException {
//        Statement statement =  connection.createStatement();
//        for (String sql : sqlStatements) {
//            statement.addBatch(sql);
//        }
//        
//        return statement;
//    }
}
}
