package util;

public class TestDB {
    public static void main(String[] args) {
        try (java.sql.Connection conn = DBUtil.getConnection()) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}