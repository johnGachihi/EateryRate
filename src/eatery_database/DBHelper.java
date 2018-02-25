package eatery_database;

import Pojos.Eatery;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.prefs.Preferences;

public class DBHelper {

    private static Connection connection = null;

    private static final String DATABASE_URL = "jdbc:mysql://localhost/eateryrating";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void getConnection() throws SQLException{
        Properties connectionProps = new Properties();
        connectionProps.put("user", USER);
        connectionProps.put("password", PASSWORD);
        connection = DriverManager.getConnection(
                DATABASE_URL, connectionProps);
    }

    public static String getUserPassword(String userName){
        String pass = "";
        try {
            if (connection == null)
                getConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + EateryDB.RegularUserTable.USER_PASSWORD +
                            " FROM regularuser WHERE username = ?"
            );
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                pass = resultSet.getString("passcode");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pass;
    }

    public static ArrayList<Eatery> getEateriesFromSearchQuery(String searchQuery){
        ArrayList<Eatery> eateries = new ArrayList<>();
        try{
            if (connection == null)
                getConnection();

            PreparedStatement prepStmt = connection.prepareStatement(
                    "SELECT * FROM " + EateryDB.TABLE_EATERY +
                            " JOIN " + EateryDB.TABLE_LOCATION +
                                " ON " + "eatery.location_id = location.location_id" +
                            " WHERE MATCH (" +
                                EateryDB.EateryTable.EATERY_NAME + ", " +
                                EateryDB.EateryTable.EATERY_FOOD_DESCRIPTION +
                            ") " +
                            "AGAINST (? IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION);"
            );
            prepStmt.setString(1, searchQuery);
            System.out.println(prepStmt.toString());
            ResultSet resultSet = prepStmt.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
                eateries.add(new Eatery(
                        resultSet.getString(EateryDB.EateryTable.EATERY_NAME),
                        resultSet.getInt(EateryDB.EateryTable.EATERY_RATING),
                        resultSet.getString(EateryDB.EateryTable.EATERY_FOOD_DESCRIPTION),
                        resultSet.getInt(EateryDB.LocationTable.LATITUDE),
                        resultSet.getInt(EateryDB.LocationTable.LONGITUDE)
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return eateries;
    }

    public static String addUser(String username, String email, String password){
        try{
            if(connection == null)
                getConnection();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty())
                return "Insert values in all fields";

            if(isUserExistent(username))
                return "User exists";

            PreparedStatement prepStmt = connection.prepareStatement(
                    "INSERT INTO " + EateryDB.TABLE_REGULARUSER +
                            " VALUES (?, ?, ?)"
            );
            prepStmt.setString(1, username);
            prepStmt.setString(2, email);
            prepStmt.setString(3, password);

            System.out.println(prepStmt.toString());
            prepStmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return "Error occurred cannot create user";
        }
        return "";
    }

    public static boolean isUserExistent(String username){
        boolean exists = false;
        try{
            if (connection == null)
                getConnection();

            PreparedStatement prepStmt = connection.prepareStatement(
                    "SELECT * FROM " + EateryDB.TABLE_REGULARUSER +
                            " WHERE " + EateryDB.RegularUserTable.USERNAME + " = ?;"
            );
            prepStmt.setString(1, username);
            ResultSet resultSet = prepStmt.executeQuery();
            while(resultSet.next())
                exists = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return exists;
    }

    public static void updateUserPassword(String username, String newPassword){
        try{
            if (connection == null)
                getConnection();

            PreparedStatement prepStmt = connection.prepareStatement(
                    "UPDATE " + EateryDB.TABLE_REGULARUSER +
                            " SET " + EateryDB.RegularUserTable.USER_PASSWORD + " = ?" +
                            " WHERE " + EateryDB.RegularUserTable.USERNAME + " = ?"
            );
            prepStmt.setString(1, newPassword);
            prepStmt.setString(2, username);

            prepStmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteUserAccout(String username){
        try{
            if (connection == null){
                getConnection();
            }

            PreparedStatement prepStmt = connection.prepareStatement(
                    "DELETE FROM " + EateryDB.TABLE_REGULARUSER +
                            " WHERE " + EateryDB.RegularUserTable.USERNAME + " = ?"
            );
            prepStmt.setString(1, username);
            prepStmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
