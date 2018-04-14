/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author amit
 */
public class code {
    
}

class connectivity {

    public Connection getConnection() throws Exception {
    
        try{
        
            String driver = "com.mysql.jdbc.Driver";
            String user = "root";
            String password = "amit";
            String url = "jdbc:mysql://localhost:3306/bankdata";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password); 
            return con;
            
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }
   
    }
    
    public void insert(String QUERY) throws Exception {
    
        try{
        
            Connection con = getConnection();
            String query = QUERY;
            Statement st = con.createStatement();
            st.executeUpdate(query);
        
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public ResultSet search(int ID) throws Exception {
    
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "select * from bankuser where userid = " + ID + ";";
            rs = st.executeQuery(query);
            return rs;
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public void setpass(int ID, String pass) throws Exception {
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "update bankuser set pin = '" + pass + "' where userid = " + ID + ";";
            st.executeUpdate(query);
        
        }catch(Exception e){
            throw e;
        }
    }
    
    public int tablesize() throws Exception {
    
        try {
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs;
            String query = "select MAX(userid) from bankuser;";
            
            rs = st.executeQuery(query);
            
            int size = 0;
            while(rs.next()){
                size = rs.getInt("MAX(userid)");
                return size;
            }
            return size;
        
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public void setbalance(int ID, long amt) throws Exception{
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "update bankuser set balance = '" + amt + "' where userid = " + ID + ";";
            st.executeUpdate(query);
        
        }catch(Exception e){
            throw e;
        }
    }
    
    public void setnotes(int nhun, int nfhun, int ntwok) throws Exception{
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "insert into notes values (0," + nhun + "," + nfhun + "," + ntwok + ");"; 
            st.executeUpdate(query);
        
        }catch(Exception e){
            throw e;
        }
    }
    
    public void clearnotes() throws Exception{
    
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "delete from notes;";
            st.executeUpdate(query);
        
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public ResultSet getnotes() throws Exception{
    
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "select * from notes where id = 0";
            ResultSet rs = st.executeQuery(query);
            return rs;
        
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public void inserttransaction(int id, long amount, char C) throws Exception {
    
        try{
            ResultSet rs = this.search(id);
            long balance = 0;
            while(rs.next()){
                balance = rs.getLong("balance");
                break;
            }
            System.out.println("GOT BALANCE");
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query1 = "insert into transactions values (0, " + id + ",'" + C + "'," + amount + "," + balance + ");";
            String query2 = "update transactions set temp = 1 where temp = 0 AND userid = " + id + ";";
            String query3 = "update transactions set temp = 2 where temp = 1 AND userid = " + id + ";";
            String query6 = "delete from transactions where temp = 4 AND userid = " + id + ";";
            String query4 = "update transactions set temp = 3 where temp = 2 AND userid = " + id + ";";
            String query5 = "update transactions set temp = 4 where temp = 3 AND userid = " + id + ";";
            //System.out.println("STARTING");
            st.executeUpdate(query6);
            //System.out.println("STARTING");
            st.executeUpdate(query5);
            //System.out.println("DONE1");
            st.executeUpdate(query4);
            //System.out.println("DONE2");
            st.executeUpdate(query3);
            //System.out.println("DONE3");
            st.executeUpdate(query2);
            //System.out.println("DONE4");
            st.executeUpdate(query1);
            //System.out.println("DONE5");
        
        }catch(Exception e){
            throw e;
        }
    
    }
    
    public ResultSet retrievetransactons(int id) throws Exception {
    
        try{
        
            Connection con = getConnection();
            Statement st = con.createStatement();
            String query = "select * from transactions where userid = " + id + " ORDER BY temp;";
            ResultSet rs = st.executeQuery(query);
            return rs;
        
        }catch(Exception e){
            throw e;
        }
    
    }

}
