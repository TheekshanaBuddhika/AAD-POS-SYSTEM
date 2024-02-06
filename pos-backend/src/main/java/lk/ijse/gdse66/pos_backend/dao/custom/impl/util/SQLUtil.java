package lk.ijse.gdse66.pos_backend.dao.custom.impl.util;

import jakarta.annotation.Resource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil{

    @Resource(name = "java:/comp/env/jdbc/pos")

    private static DataSource ds;

    public static <T>T execute(String sql,Object... args) throws SQLException, ClassNotFoundException {

        try(Connection connection = ds.getConnection();){

            PreparedStatement pstm = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstm.setObject((i+1),args[i]);
            }
            if (sql.startsWith("SELECT") || sql.startsWith("select")){
                return (T) pstm.executeQuery();
            }else{
                return (T)  new Boolean(pstm.executeUpdate()>0);
            }

        }


    }

}
