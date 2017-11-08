package logic;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class TestJDBC {
   /* public static void main(String args[]) {
        try {
            System.setOut((new printString("out.txt")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/students";
            connection = DriverManager.getConnection(url, "postgres", "P@ssw0rd");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM public.students ORDER BY firstname ASC");
            while (resultSet.next()) {
                String str = resultSet.getString(1) + ":" + resultSet.getString(2);
                printString(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printString(String str)  {
        try {

            byte[] win1251b = str.getBytes("windows-1251");
            String bytesToString = new String(win1251b, "windows-1251");
            System.out.println((bytesToString));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    */
}


