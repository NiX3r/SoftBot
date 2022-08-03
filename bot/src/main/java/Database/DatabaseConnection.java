package Database;

import Utils.Bot;
import Utils.SecretClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    Connection connection;

    public DatabaseConnection(){

        try {

            if(getConnection() != null && !getConnection().isClosed())
                return;

            connection = DriverManager.getConnection("jdbc:mysql://" + SecretClass.getHostname() + ":3306/" + SecretClass.getDatabase(), SecretClass.getUser(), SecretClass.getPassword());
            Utils.LogSystem.log(Bot.getPrefix(), "database connection is successfully conected", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        }
        catch(SQLException e) {
            Utils.LogSystem.log(Bot.getPrefix(), "Error: " + e, new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean isClosed() {
        try {
            return this.connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

}