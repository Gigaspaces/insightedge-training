package com.gs;

import com.gigaspaces.client.ReadModifiers;
import com.j_spaces.jdbc.driver.GConnection;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.gs.Customer;

public class JDBCV3Client {
    private final String EXPLAIN_PLAN_PREFIX = "EXPLAIN PLAN FOR ";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo")).gigaSpace();
        JDBCV3Client client = new JDBCV3Client();
        Connection connection = client.connect(gs);
        client.read1(connection);
        client.read3(connection);
        client.read2(connection);
        client.read4(connection);

    }

    protected Connection connect(GigaSpace gigaSpace) throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
       // properties.put("readModifiers", ReadModifiers.MEMORY_ONLY_SEARCH);
       return DriverManager.getConnection("jdbc:gigaspaces:v3://localhost:4174/" + gigaSpace.getSpaceName(), properties);


    }

    /*
    Simple read with ordering
     */
    protected  void read1(Connection connection) throws SQLException {
        String queryPrefix = "select * from  \"" + Customer.class.getName()+"\"" +" where ";
        String query = queryPrefix + "lastName = 'Cohen' order by firsName";
        read(connection,query);
    }


    /*
    Find per each product how many were sold
     */
    protected  void read2(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        String queryPrefix = "Select P.name, sum(PU.amount) from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId group by P.name" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }

    /*
   Find per each product how many were sold
    */
    protected  void read4(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        String queryPrefix = "Select P.id, sum(PU.amount) from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId group by P.id" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }


    /*
    Find per each product how many were sold
     */
    protected  void read3(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        String queryPrefix = "Select PU.customerId, P.name, PU.amount from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId order by P.name" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }





    protected void read(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Query : " + query);
        String explan = EXPLAIN_PLAN_PREFIX +" " +query;
        dumpResult(statement.executeQuery(explan));
        System.out.println("Results:");
        dumpResult(statement.executeQuery(query));
    }

    private List<String> dumpResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        List<String> rows = new ArrayList<>();
        for (int k=1; k<= columnsNumber; k++){
            if (k > 1) System.out.print(",  ");
            System.out.print(rsmd.getColumnName(k));
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue);
                rows.add(columnValue);
            }
            System.out.println();
        }
        return rows;
    }





}
