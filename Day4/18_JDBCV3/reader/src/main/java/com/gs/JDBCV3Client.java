package com.gs;


import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.*;


public class JDBCV3Client extends AbsractJDBCClient{
    private final String EXPLAIN_PLAN_PREFIX = "EXPLAIN PLAN FOR ";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo").lookupGroups("xap-16.2.0")).gigaSpace();
        JDBCV3Client client = new JDBCV3Client();
        Connection connection = client.connect(gs);
        client.insetDataExample(connection);
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

    /*
    Run feeder to fill test with data, this is just an example for insert
     */
    private void insetDataExample(Connection connection) throws SQLException {
        String customerInsertSql = "INSERT INTO \"com.gs.Customer\" (id, firsName, lastName, birthday) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(customerInsertSql);
        ps.setInt(1,101); ps.setString(2,"George");ps.setString(3,"Washington");
        ps.setDate(4, java.sql.Date.valueOf("2000-11-1"));
        ps.execute();
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
        String query = queryPrefix + "lastName = 'Cohen' order by firsName DESC NULLS LAST";
        read(connection,query);
        //desc1(connection, query);
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
        String queryPrefix = "SELECT Max(P.price) as avg_price  from \"" +  Product.class.getName() + "\" as P " ;
        String condition1 = " WHERE P.price >50 " ;


        String query = queryPrefix /*+ condition1*/;
        read(connection,query);
    }

    




    public void desc1(Connection connection, String query)  throws SQLException{
        System.out.println("======================= desc1 ===========================");
        Statement statement = connection.createStatement();
        try {
            String desc = "DESCRIBE "+ query;
            dumpResult(statement.executeQuery(desc));
        }
        catch (Throwable e){
            System.out.println("Fail to run describe for query:" + e);
            e.printStackTrace();
        }

        finally {
            statement.close();
        }

    }









    }
