package com.gs;


import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCV3Client {
    private final String EXPLAIN_PLAN_PREFIX = "EXPLAIN PLAN FOR ";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo")).gigaSpace();
        JDBCV3Client client = new JDBCV3Client();
        Connection connection = client.connect(gs);
        client.read1(connection);
        client.read2(connection);
        client.read3(connection);
        client.read4(connection);
        client.read5(connection);
        client.read6(connection);
        client.read7(connection);
        client.read8(connection);
        client.read9(connection);
        client.read10(connection);
        client.read11(connection);
    }

    protected Connection connect(GigaSpace gigaSpace) throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();
       // properties.put("readModifiers", ReadModifiers.MEMORY_ONLY_SEARCH);
       return DriverManager.getConnection("jdbc:gigaspaces:v3://localhost:4174/" + gigaSpace.getSpaceName(), properties);
    }

    protected void read(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("Query : " + query);
        try {
            String explan = EXPLAIN_PLAN_PREFIX + " " + query;
            dumpResult(statement.executeQuery(explan));
        }
        catch (Throwable e){
            System.out.println("Fail to run explain plan:" + e);
            e.printStackTrace();
        }
        System.out.println("Results:");
        try {
            dumpResult(statement.executeQuery(query));

        }
        catch (Throwable t){

            System.out.println("Fail to run query:" + t);
            t.printStackTrace();
        }
        finally {
            statement.close();
        }

    }

    /*
    Simple read with ordering
     */
    protected  void read1(Connection connection) throws SQLException {
        System.out.println("======================= Read1 ===========================");

        String queryPrefix = "select * from  \"" + Customer.class.getName()+"\"" +" where ";
        String query = queryPrefix + "lastName = 'Cohen' order by firsName";
        read(connection,query);
    }


    /*
    Find per each product how many were sold
     */
    protected  void read2(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        // PU.productId, P.id
        System.out.println("======================= Read2 ===========================");

        String queryPrefix = "Select P.id as product_id, sum(PU.amount) as purchase_amount from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON PU.productId=P.id group by P.id" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }

    /*
   Find per each customer how many products he bought
    */
    protected  void read4(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        System.out.println("======================= Read4 ===========================");

        String queryPrefix = "Select PU.customerId as customer_id, C.firsName, C.lastName, sum(PU.amount) as amount from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Customer.class.getName() + "\" as C ON C.id=PU.customerId group by PU.customerId, C.firsName, C.lastName" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }


    /*
    Find the product that was sold the most (order by max_amount not supported yet)
  */
    protected  void read5(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        System.out.println("======================= Read5 ===========================");

        String queryPrefix = "Select PU.productId, P.name,  max(PU.amount) as max_amount from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " INNER JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId group by PU.productId,P.name"  ;

        String query = queryPrefix + condition;
        read(connection,query);
    }

    protected  void read6(Connection connection) throws SQLException {
        System.out.println("======================= Read6 ===========================");

        String queryPrefix = "Select  P.name,  P.price  from \"" +  Product.class.getName() + "\" as P " ;
        String condition = " where  P.price > 100"  ;

        String query = queryPrefix + condition;
        read(connection,query);
    }


    /*
    Find per each product how many were sold
     */
    protected  void read3(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        System.out.println("======================= Read3 ===========================");
        String queryPrefix = "Select PU.customerId as customer_id, P.name as product_name, PU.amount from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId order by P.name" ;

        String query = queryPrefix + condition;
        read(connection,query);
    }

    /*
   Find all products that amount of  sold is more than X
    */
    protected  void read7(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        System.out.println("======================= Read7 ===========================");

        String queryPrefix = "Select  P.name, sum(PU.amount) as total from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId group by P.name having total > 5 " ;

        String query = queryPrefix + condition;
        read(connection,query);
    }

    protected void read8(Connection connection) throws SQLException{
        System.out.println("======================= Read8 ===========================");
        String queryPrefix = "Select D.id, D.name  from \"" +  Department.class.getName() + "\" as D " ;
        String condition1 = " WHERE EXISTS (select P.name  from \"" + Product.class.getName() + "\" as P where  P.depId=D.id and P.price > 100)" ;

        String query = queryPrefix + condition1;
        read(connection,query);
    }

    protected void read9(Connection connection) throws SQLException{
        System.out.println("======================= Read9 ===========================");
        String queryPrefix = "Select D.id,D.name  from \"" +  Department.class.getName() + "\" as D " ;
        String condition1 = " WHERE EXISTS (select P.name from \""+  Product.class.getName() + "\" as P where P.depId=D.id and P.price>500)";

        String query = queryPrefix + condition1;
        read(connection,query);
    }

    protected void read10(Connection connection) throws SQLException{
        System.out.println("======================= Read10 ===========================");
        String queryPrefix = "Select P.Id, P.name, P.price  from \"" +  Product.class.getName() + "\" as P " ;
        String condition1 = "" ;
        String query = queryPrefix + condition1;
        read(connection,query);
    }

    protected void read11(Connection connection) throws SQLException{
        System.out.println("======================= Read11 ===========================");
        String queryPrefix = "SELECT Avg(P.price) as avg_price  from \"" +  Product.class.getName() + "\" as P " ;
        String condition1 = " WHERE P.price >50 " ;


        String query = queryPrefix + condition1;
        read(connection,query);
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
