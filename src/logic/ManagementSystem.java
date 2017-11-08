package logic;

import javax.swing.text.html.HTMLDocument;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class ManagementSystem {
    private List <Group> groups;
    private Collection<Student> students;

    private static Connection connection;
    private static ManagementSystem instance;

    private ManagementSystem() throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/students";
            connection = DriverManager.getConnection(url, "postgres", "P@ssw0rd");
        }catch (ClassNotFoundException e){
            throw new Exception(e);
        }catch (SQLException e){
            throw new Exception(e);
        }
    }

    public static void printString() {
        System.out.println();
    }
    public static void printString(Object s) {
        //System.out.println(s.toString());
        try {
            System.out.println(new String(s.toString().getBytes("windows-1251"), "windows-1252"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    /*private ManagementSystem () {
        loadGroups();
        loadStudents();
    }*/

    public static synchronized ManagementSystem getInstance () throws Exception {
        if (instance == null){
            instance = new ManagementSystem();
        }
        return instance;
    }

    public List<Group> getGroups() throws SQLException{
        List<Group> groups = new ArrayList<Group>();
        Statement stmt = null;
        ResultSet rs= null;
            try {
                stmt=connection.createStatement();
                rs=stmt.executeQuery("SELECT group_id, groupName, curator, speciality FROM groups");
                while (rs.next()){
                    Group gr=new Group();
                    gr.setGroupId(rs.getInt(1));
                    gr.setNameGroup(rs.getString(2));
                    gr.setCurator(rs.getString(3));
                    gr.setSpeciality(rs.getString(4));
                groups.add(gr);
                }
            }finally {
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null) stmt.close();
            }
            return groups;
    }

    public Collection<Student> getAllStudents() throws SQLException{
        Collection<Student> students = new ArrayList<Student>();

        Statement statement=null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet=statement.executeQuery("SELECT student_id, firstName, patronymic, surName, sex, dateOfBirth, group_id, educationYear FROM students ORDER BY surName, firstName, patronymic");
            while (resultSet.next()){
                Student student = new Student(resultSet);
                students.add(student);
            }
        }finally {
            if(resultSet!=null)resultSet.close();
            if (statement!=null)statement.close();
        }

        return students;
    }

    public Collection<Student> getStudentsFromGroup(Group group, int year) throws  SQLException{
        Collection<Student> students = new ArrayList<Student>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement=connection.prepareStatement(
                    "SELECT student_id, firstName, patronymic, surName, sex, dateOfBirth, group_id, educationYear FROM students WHERE group_id=? AND educationYear=? ORDER BY surName, firstName, patronymic"
            );
            statement.setInt(1,group.getGroupId());
            statement.setInt(2,year);
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                Student student = new Student(resultSet);
                students.add(student);
            }
        }finally {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
        }
        return students;
    }

    public void moveStudentsToGroup (Group oldGroup, int oldYear, Group newGroup, int newYear) throws SQLException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE students SET group_id=?,educationYear=? WHERE group_id=? AND educationYear=?");
            statement.setInt(1,newGroup.getGroupId());
            statement.setInt(2,newYear);
            statement.setInt(3,oldGroup.getGroupId());
            statement.setInt(4,oldYear);
            statement.execute();
        }finally {
            if(statement!=null) statement.close();
        }
    }
    public void insertStudent (Student student) throws SQLException{
        PreparedStatement statement = null;
        try {
            statement=connection.prepareStatement(
                    "INSERT INTO students (firstName, patronymic, surName, sex, dateofbirth, group_id, educationYear) VALUES (?,?,?,?,?,?,?)"
            );
            statement.setString(1,student.getFirstName());
            statement.setString(2,student.getPatronymic());
            statement.setString(3,student.getSurName());
            statement.setString(4,new String ((new char[]{student.getSex()})));
            statement.setDate(5,new Date(student.getDateOfBirth().getTime()));
            statement.setInt(6,student.getGroupId());
            statement.setInt(7,student.getEducationYear());
            statement.execute();
        }finally {
            if(statement!=null) statement.close();
        }
    }
    public void deleteStudent (Student student) throws SQLException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM students WHERE student_id=?"
            );
            statement.setInt(1,student.getGroupId());
            statement.execute();
        }finally {
            if(statement!=null)statement.close();
        }
    }


    public Student getStudentById(int stId) throws SQLException {
        Student student = null;
        PreparedStatement statement = connection.prepareStatement("SELECT student_Id, firstName,patronymic,surName,sex,dateOfBirth,groupId,educationYear FROM  students WHERE student_id=?");
        statement.setInt(1,stId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            student = new Student(rs);
        }
        rs.close();
        statement.close();
        return student;

    }
}
