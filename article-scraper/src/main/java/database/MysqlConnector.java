package database;

import application.Application;
import application.DatabaseConfiguration;
import common.helper.ExceptionNotification;
import common.helper.FileManager;
import common.model.Article;
import common.model.Newspaper;

import java.sql.*;

public class MysqlConnector {
    protected Connection connection = null;
    //protected String url;
    protected String url_template = "jdbc:mysql://@HOSTNAME:3306/@DBNAME?characterEncoding=@CHARSET&user=@USERNAME&password=@PASSWORD&useSSL=@SSL";

    protected String url_template_2 = "jdbc:mysql://@HOSTNAME:@DB_PORT/@DBNAME?characterEncoding=@CHARSET&user=@USERNAME&password=@PASSWORD&useSSL=@SSL";

    protected String table_name;


    /**
     * create connection
     */
    public MysqlConnector() {
        this.Connect();
    }

    /**
     *
     * @param host_name
     * @param db_name
     * @param user
     * @param password
     * @param port
     * @param charset
     * @param use_ssl
     */
    public MysqlConnector(String host_name, String db_name,
                           String user, String password,
                           String port, String charset,
                           String use_ssl) {

        String new_url = url_template_2.replace("@HOSTNAME", host_name)
                .replace("@DBNAME", db_name)
                .replace("@CHARSET", charset)
                .replace("@USERNAME", user)
                .replace("@PASSWORD", password)
                .replace("@SSL", use_ssl)
                .replace("@DB_PORT", port);

        // connect
        try {
            // Class.forName("java.sql.Driver");
            // or use
            Class.forName("com.mysql.jdbc.Driver");
            try {
                this.connection = DriverManager.getConnection(new_url);
            } catch (SQLException ex) {
                ExceptionNotification.printException(ex);
            }
        } catch (ClassNotFoundException ex) {
            ExceptionNotification.printException(ex);
        }
    }

    /**
     * lasted row of a table
     * @return
     */
    public ResultSet GetLastedRow() {
        String sql = "SELECT * " +
                "FROM " + this.table_name + " " +
                "ORDER BY id DESC " +
                "LIMIT 1";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);
            return null;
        }
    }


    /**
     * Lasted id of a table
     * @return
     */
    public int GetLastedId() {
        String sql = "SELECT id " +
                "FROM " + this.table_name + " " +
                "ORDER BY id DESC " +
                "LIMIT 1";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            // close resultset and statement
            rs.close();
            statement.close();

            return id;
        } catch (SQLException ex) {
            if (Application.ENABLE_LOGS) {
                FileManager.writeLogs(Application.LOG_FILE_LOCATION, ex.getMessage());
            }

            if (Application.ENABLE_DEBUG) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            return 0;
        }
    }


    /**
     * SELECT COUNT(id) FROM <TABLE_NAME>
     * @return
     */
    public int GetRowsCount() {
        String sql = "SELECT COUNT(id) " +
                "FROM " + this.table_name;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            statement.close();

            return count;
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);
            return 0;
        }
    }


    public Connection getConnection() {
        return this.connection;
    }

    /**
     * disconnect
     * @return 1 if success, otherwise 0
     */
    public int Dispose() {
        try {
            // close
            this.connection.close();
            // return true
            return 1;
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);
        }
        return 0;
    }

    /**
     * Connect to mysql database
     * @return
     */
    public int Connect() {
        url_template = url_template.replace("@HOSTNAME", DatabaseConfiguration.HOST_NAME)
                .replace("@DBNAME", DatabaseConfiguration.DB_NAME)
                .replace("@CHARSET", DatabaseConfiguration.CHARSET)
                .replace("@USERNAME", DatabaseConfiguration.USER_NAME)
                .replace("@PASSWORD", DatabaseConfiguration.PASSWORD)
                .replace("@SSL", DatabaseConfiguration.USE_SSL);

        // connect
        try {
            // Class.forName("java.sql.Driver");
            // or use
            Class.forName("com.mysql.jdbc.Driver");
            try {
                this.connection = DriverManager.getConnection(url_template);

                return 1;
            } catch (SQLException ex) {
                ExceptionNotification.printException(ex);
                return 0;
            }
        } catch (ClassNotFoundException ex) {
            ExceptionNotification.printException(ex);
            return 0;
        }
    }

    /**
     * Execute mysql query
     * @param query
     * @return
     */
    public ResultSet Select(String query) {
        try {
            // create statement
            PreparedStatement statement = connection.prepareStatement(query);

            // execute query
            statement.executeQuery(query);

            // return resultset
            ResultSet rs = statement.getResultSet();

            return rs;
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);

            return null;
        }
    }

    /**
     * Update
     * @param query
     * @return
     */
    public void UpdateOrDelete(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            // apply update
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ExceptionNotification.printException(ex);
        }
    }
}
