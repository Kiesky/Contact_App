package isen.contact_app.Dao;

import org.sqlite.SQLiteDataSource;
import  javax.sql.DataSource;




public class DataSourceFactory {

    private static SQLiteDataSource dataSource;

    private DataSourceFactory(){
        throw new IllegalStateException(("This is a static class that should not be instantiated"));
    }

    public static DataSource getDataSource(){
        if (dataSource == null){
            dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:sqlite.db");
        }

        return dataSource;
    }

}
