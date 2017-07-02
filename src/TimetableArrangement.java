
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

    public static ResultSet fetchdata() {
        try{
            stmt = con.createStatement(); //what is this line?
            String sql = "select * from MAST20009";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }catch (Exception e){
            System.out.println("Fetching data fail");
        }
        return rs;
    }

    public static void printdata(ResultSet rs){
        System.out.println("Print out the result fromd database");

        String classtype = null;
        String weekday = null;
        Time start = null;
        Time end = null;
        String location = null;
        int semester = 0;
        try{
            while (rs.next()){
                classtype = rs.getString("classtype");
                weekday = rs.getString("weekday");
                start = rs.getTime("start");
                end = rs.getTime("end");
                location = rs.getString("location");
                System.out.println(classtype +" "+ weekday+" "+start+" "+end+" "+location);
            }
            rs.close();
            con.close();
        }catch(SQLException e){
            System.out.println("Searching info fail");
        }
    }


    public static void main(String[] args){
       connect();
       rs = fetchdata();
       printdata(rs);
    }
}
