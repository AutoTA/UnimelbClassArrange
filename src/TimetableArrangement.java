
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/6/30.
 */
public class TimetableArrangement {	
    //initial setting databse setting
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/subjecttimetable?characterEncoding=utf8&useSSL=false";
    public static String user = "root";
    public static String password = "Qazmko123";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;
    
    //1. connect to the database
    public static void connect(){
    	/**
    	 * This function used to connect to the local database
    	 */
        //locate the driver
        try{
            Class.forName(driver);
            System.out.println("Loading driver successful");
        }catch(ClassNotFoundException e){
            System.out.println("Loading driver failed");
        }
        //set up connection
        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Databace connection successful");
        }catch(SQLException e){
            System.out.println("Database connection failed");
        }
    }
    
    //2. store data from database
    public static ResultSet fetchData(String subject) {
    	//used to read single subject data
        try{
            stmt = con.createStatement(); //what is this line?
            String sql = "select * from " + subject;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }catch (Exception e){
            System.out.println("Subject infomation not avaiable in database");
            return null;
        }
    }

    public static Subject storeSubjectData(ResultSet rs, String subject_name){
    	/**
    	 * This function takes an single subject's database as input
    	 * And read each record one by one
    	 * And output An array of Record object
    	 */
        System.out.println("Transfer data from databse to array");
        String classtype = null;
        String weekday = null;
        Time start = null;
        Time end = null;
        String location = null;
        
        //assume each subject only have 4 types of class
        //0 is lecture type, 1 is practical, 2 is workshop
        Subject subject = new Subject();
        
        try{
            while (rs.next()){
            	//create basic lesson information
            	classtype = rs.getString("classtype");
                weekday = rs.getString("weekday");
                start = rs.getTime("start");
                end = rs.getTime("end");
                location = rs.getString("location");
                Lesson lesson_info = new Lesson(subject_name, classtype, weekday, start, end, location);
                
                //classify this basic record into a class type
                //and put into the lessons array under classtypes list 
                boolean already_exist = false;
                int i; //the index to access arraylist inthe classtypes
                for (i = 0; i < subject.classtypes.size(); i++) {
                	if (subject.classtypes.get(i).type.equals(classtype)){
                		subject.classtypes.get(i).lessons.add(lesson_info);
                		already_exist = true;
                		break;
                	}
                }
                if (!already_exist) {
                	subject.classtypes.add(new Classtype());  	
                	subject.classtypes.get(i).lessons.add(lesson_info);
                	subject.classtypes.get(i).type = classtype;
                }
            }
            rs.close();
            return subject;
        }catch(SQLException e){
            System.out.println("Data not avaiable in database");
            return null;
        }catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }

    public static Subject[] storeAllSubjects(String[] subjectList){
    	/**
    	 * This function take a subject list as input
    	 * And return an Array of subject object
    	 * such that store timetable for subject in subjectList
    	 */
    	ArrayList<Subject> subjects = new ArrayList<Subject>();
    	for (int i = 0; i < subjectList.length; i++) {
    		Subject subject = storeSubjectData(fetchData(subjectList[i]),subjectList[i]);
    		subject.subjectname = subjectList[i];
    		subjects.add(subject);
    	}
    	return subjects.toArray(new Subject[subjects.size()]);
    }
    
    //3 rearrange the subject to generate the timetable
    
    	
    //Finally execute all the program
    public static void main(String[] args) {
       connect();
       
       //reading the data from database
       String[] subjectList = {"MAST20009", "MAST30028"};                                                       
       Subject[] subjects = storeAllSubjects(subjectList);
       showSubjects(subjects);
      
       //close the connection to the SQL
       try {
    	   con.close();
       }catch (Exception e) {
    	   System.out.println("SQL database connection has already close");
       }
       
       //processing the data
       
    }   
    
    
    //helper function
    public static void showSubjects(Subject[] subjects) {
    	System.out.println("Start showing datastructure");
    	for(int i = 0; i < subjects.length; i++) {
    		Subject subject = subjects[i];
    		System.out.println(subject.subjectname);
    		for (int j = 0; j < subject.classtypes.size(); j++) {
    			Classtype classtype = subject.classtypes.get(j);
    			System.out.println(classtype.type);
    			for (int k = 0; k < classtype.lessons.size(); k++) {
    				Lesson lesson = classtype.lessons.get(k);
    				lesson.print_info();
    			}
    		}
    	}
    }
    
    //basic class setting for the program
    public static class Lesson{
    	/**
    	 * @param subject string, the subject this lesson belongs to
    	 * @param classtype String
    	 * @param day int
    	 * @param start float
    	 * @param end float
    	 * @param location string
    	 */
    	
    	String subject;
    	String classtype;
    	String day;
    	Time start;
    	Time end;
    	String location;
    	
    	Lesson (String subject, String classtype, String day, Time start, Time end, String location){
    		this.subject = subject;
    		this.classtype = classtype;
    		this.day = day;
    		this.start = start;
    		this.end = end;
    		this.location = location;
    	}
    	
    	public void print_info() {
    		System.out.print(classtype + " ");
    		System.out.print(day + " ");
    		System.out.print(start + " ");
    		System.out.print(end + " ");
    		System.out.print(location + "\n");
    	}
    	
    }
    
    public static class Classtype{
    	String type;
    	ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    }
    
    public static class Subject{
    	String  subjectname;
    	ArrayList<Classtype> classtypes = new ArrayList<Classtype>();
    }
}
