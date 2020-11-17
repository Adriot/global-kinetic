package files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class DatabaseTransactor {
    private Logger log = LogManager.getLogger(DatabaseTransactor.class);
    private FilePropertiesConfig propertiesSetup;
    private Properties properties;
    private String driverClassPackage;
    private String dbConnectionString;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;

    public DatabaseTransactor() throws Exception {
        setupFilePropertiesConfig();
        getProperties();
        setDriverClassName();
    }

    public DatabaseTransactor(String propertiesFilePath) throws Exception {
        properties = setupFilePropertiesConfig(propertiesFilePath);
        getProperties();
        setDriverClassName();
    }

    public DatabaseTransactor(String dbConnectionString, String username, String password) throws Exception {
        setupFilePropertiesConfig();
        driverClassPackage = properties.getProperty("DRIVER_CLASS_PACKAGE");
        this.dbConnectionString = dbConnectionString;
        this.username = username;
        this.password = password;
        setDriverClassName();
    }

    public DatabaseTransactor(String driverClassPackage, String dbConnectionString, String username, String password)
            throws Exception {
        this.driverClassPackage = driverClassPackage;
        this.dbConnectionString = dbConnectionString;
        this.username = username;
        this.password = password;
        setDriverClassName();
    }

    public Connection getConnection() {
        return connection;
    }

    private void setupFilePropertiesConfig() {
        if (propertiesSetup == null) {
            propertiesSetup = new FilePropertiesConfig();
            propertiesSetup.loadProperties();
            properties = propertiesSetup.getProperties();
        }
    }

    private Properties setupFilePropertiesConfig(String propertiesFilePath) {
        if (propertiesSetup == null) {
            propertiesSetup = new FilePropertiesConfig(propertiesFilePath);
            propertiesSetup.loadProperties();
        }
        return propertiesSetup.getProperties();
    }

    private void getProperties() {
        if (properties != null) {
            driverClassPackage = properties.getProperty("DRIVER_CLASS_PACKAGE");
            dbConnectionString = properties.getProperty("DB_CONNECTION_STRING");
            username = properties.getProperty("USERNAME");
            password = properties.getProperty("PASSWORD");
        }
    }

    private void setDriverClassName() throws ClassNotFoundException {
        Class.forName(driverClassPackage);
    }

    public void connectToDatabase() throws Exception {
        try {
            log.info("Connecting To Database");
            connection = DriverManager.getConnection(dbConnectionString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("Failed to connect to database. \n" + e.getMessage());
            throw new Exception("Failed to connect to database. \n" + e.getMessage());
        }
    }

    public void connectToDatabase(String dbConnectionString, String username, String password) {
        try {
            log.info("Connecting To Database");
            this.dbConnectionString = dbConnectionString;
            this.username = username;
            this.password = password;
            connection = DriverManager.getConnection(dbConnectionString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("Failed to connect to database.");
            log.error(e.getMessage());
        }
    }

    public void createStatement() throws Exception {

        if (connection == null) {
            connectToDatabase();
        }

        try {
            log.info("Creating Query Statement");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("Failed to create query statement.");
            log.error(e.getMessage());
        }
    }

    public void closeDatabaseConnection() {
        if (connection != null){
            try{
                log.info("Closing Database Connection...");
                connection.close();
                log.info("Done Closing Database Connection.");
            } catch (Exception e){
                log.error("Failed to close database connection");
            }
        }
    }

    public ResultSet executeQuery(String query) throws Exception {
        try {
            log.info("Executing Query" + query);
            createStatement();
            if (statement != null) {
                return statement.executeQuery(query);
            }
        } catch (Exception e) {
            log.error("SQL Exception: " + e.getMessage());
            throw new Exception("SQL Exception: " + e.getMessage());
        }
        return null;
    }

    public void executeUpdate(String query) throws Exception {
        try {
            log.info("Executing Update" + query);
            createStatement();
            if (statement != null) {
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            log.error("SQL Exception: " + e.getMessage());
            throw new Exception("SQL Exception: " + e.getMessage());
        }
    }
}
