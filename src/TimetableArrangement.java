
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;


/**
 * Created by Administrator on 2017/6/30.
 */
public class TimetableArrangement {	
    //initial setting
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/subjecttimetable?characterEncoding=utf8&useSSL=false";
    public static String user = "root";
    public static String password = "Qazmko123";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

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

    public static Classtype[] storeData(ResultSet rs){
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
        Classtype[] classtypes = new Classtype[5]; 
        
        int i = 0;
        try{
            while (rs.next()){
                classtype = rs.getString("classtype");
                weekday = rs.getString("weekday");
                start = rs.getTime("start");
                end = rs.getTime("end");
                location = rs.getString("location");
                Record data = new Record(classtype, weekday, start, end, location);
                subjectData[i++] = data;            
            }
            rs.close();
            return subjectData;
        }catch(SQLException e){
            System.out.println("Data not avaiable in database");
            return null;
        }catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }

    public static subjectRecords[] fetchAllSubjectTimeslots(String[] subjectList) {
    	 Subject[] subjects = new Subject[6];
         for (int i = 0; i < subjectList.length; i++)
      	   
    }
    
    public static class Lesson{
    	Time start;
    	Time end;
    	String location;
    }
    
    public static class Classtype{
    	Classtype{
    		this.lessons = new 
    	}
    	Lesson[] lessons;
    }
    
    public static class Subject{
    	Classtype[] classtypes;
    }
    
    
     
    public static void main(String[] args) {
       connect();
       
       //reading the data from database
       String[] subjectList = {"MAST20009", "MAST30028"};                                                       
       subjectRecords[] allRecords = fetchAllSubjectTimeslots(subjectList);
      
       //close the connection to the SQL
       try {
    	   con.close();
       }catch (Exception e) {
    	   System.out.println("SQL database connection has already close");
       }
       
       //processing the data
       
       
    }   
}
