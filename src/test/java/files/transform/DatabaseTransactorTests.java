package files.transform;

import files.DatabaseTransactor;
import org.testng.annotations.Test;

import java.sql.ResultSet;

public class DatabaseTransactorTests {
    @Test
    public void databaseTransactorDefaultConfigFile() {
        try {
            DatabaseTransactor databaseTransactor = new DatabaseTransactor();
            databaseTransactor.connectToDatabase();
            ResultSet resultSet = databaseTransactor.executeQuery("SELECT * FROM SOME_TABLE LIMIT 1");
            while (resultSet.next()) {
                String someValue = resultSet.getString("SOME_VALUE");
                System.out.println("SOME_VALUE: " + someValue);
            }
            databaseTransactor.closeDatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void databaseTransactorSpecificConfigFile() {
        try {
            DatabaseTransactor databaseTransactor = new DatabaseTransactor("src\\test\\resources\\data_config.properties");
            databaseTransactor.connectToDatabase();
            ResultSet resultSet = databaseTransactor.executeQuery("SELECT * FROM SOME_TABLE LIMIT 1");
            while (resultSet.next()) {
                String someValue = resultSet.getString("SOME_VALUE");
                System.out.println("SOME_VALUE: " + someValue);
            }
            databaseTransactor.closeDatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
