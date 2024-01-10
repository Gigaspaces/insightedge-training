package com.gs;


import com.gigaspaces.internal.license.LicenseManager;
import com.j_spaces.kernel.SystemProperties;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.*;


public class JDBCV3Client extends AbsractJDBCClient{
    private final String EXPLAIN_PLAN_PREFIX = "EXPLAIN ANALYZE FOR "; // Can be replaced by EXPLAIN PLAN FOR

    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo").lookupGroups("xap-16.3.0")).gigaSpace();

        JDBCV3Client client = new JDBCV3Client();
        Connection connection = client.connect(gs);
        client.insertDataExample(connection);
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
        client.creatAndinsertDocExample(connection,"table3");
        String newTableName = client.createTableFromSelect(connection, 4);
        client.deleteTableRows(connection,"com.gs.Customer");
        if (newTableName != null)
            client.dropTable(connection, newTableName);


    }

    protected void deleteTableRows(Connection connection, String tableName) throws SQLException {
        String customerDeleteSql = "delete from \"" + tableName +"\" ";
        PreparedStatement ps = connection.prepareStatement(customerDeleteSql);
        ps.execute();
    }


    protected void dropTable(Connection connection,String tableName) throws SQLException {
        String dropSql = "drop table \"" + tableName+ "\"";
        PreparedStatement ps = connection.prepareStatement(dropSql);
        ps.execute();
    }

    /*
    Run feeder to fill test with data, this is just an example for insert
     */
    private void insertDataExample(Connection connection) throws SQLException {
        String customerInsertSql = "INSERT INTO \"com.gs.Customer\" (id, firsName, lastName, birthday) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(customerInsertSql);
        ps.setInt(1,101); ps.setString(2,"George");ps.setString(3,"Washington");
        ps.setDate(4, java.sql.Date.valueOf("2000-11-1"));
        ps.execute();
    }

    private void creatAndinsertDocExample(Connection connection,String tableName)  {
        String createTableSql = "CREATE TABLE " + tableName +" (ID int NOT NULL,LastName varchar(255) NOT NULL,FirstName varchar(255),Age int,PRIMARY KEY (ID))";
        String insertSql = "INSERT INTO " + tableName + " (ID, LastName, FirstName, Age)\n" +
                "VALUES (1, 'Choen', 'Avi', 30)\n";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(createTableSql);
            ps.execute();
            ps = connection.prepareStatement(insertSql);
            ps.execute();
            System.out.println("New Table " + tableName +" was created, and one record inserted");
        } catch (SQLException e) {
            System.out.println("Fail to run creatAndinsertDocExample:" + e);;
        }
    }

    private String createTableFromSelect(Connection connection, int customerID) {
        //Create a filtered table holding Purchases of given customer
        String newTableName = "Purchases_"+ customerID;
        String query = "CREATE TABLE "+ newTableName + " AS SELECT id, customerId, productId, amount FROM \"com.gs.Purchase\" where customerId=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.execute();
            System.out.println(newTableName + " created with Purchases of Customer: " + customerID);
            return newTableName;

        } catch (SQLException e) {
            System.out.println("Fail to run createTableFromSelect:" + e);;

        }
        return null;
    }

    protected void read(Connection connection, String query, Object[] parameters){

        System.out.println("Query : " + query);
        try {
            String explan = EXPLAIN_PLAN_PREFIX + " " + query;
            PreparedStatement preparedStatement = connection.prepareStatement(explan);
            for (int k=0; k< parameters.length; k++)
                preparedStatement.setObject(k+1, parameters[k]);

            dumpResult(preparedStatement.executeQuery());
            preparedStatement.close();
        }
        catch (Throwable e){
            System.out.println("Fail to run explain plan:" + e);
            e.printStackTrace();
        }
        System.out.println("Results:");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int k=0; k< parameters.length; k++)
                preparedStatement.setObject(k+1, parameters[k]);

            dumpResult(preparedStatement.executeQuery());
            preparedStatement.close();
        }
        catch (Throwable t){

            System.out.println("Fail to run query:" + t);
            t.printStackTrace();
        }
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
        String query = queryPrefix + "lastName = ? order by firsName DESC NULLS LAST";
        Object[] paramters = new Object[1];
        paramters[0]="Choen";
        read(connection,query,paramters);
    }

    /*
    Find per each product how many were sold and total paid
     */
    protected  void read2(Connection connection) throws SQLException {
        //" join from Purchase & Product by PU.productId" U.productId, P.name, P.price, PU.amount
        // PU.productId, P.id
        System.out.println("======================= Read2 ===========================");
        String queryPrefix = "Select P.id as product_id, P.name, P.price, sum(PU.amount) as purchase_amount, sum(PU.amount)*P.price as total from \"" +  Purchase.class.getName() + "\" as PU " ;
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON PU.productId=P.id group by P.id,P.name,P.price" ;
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

        String queryPrefix = "Select  D.name as Department, P.name,  P.price ";
        String from1 = "from (Select name,price,depId  from \"" +  Product.class.getName() + "\"  where price > ? ) AS P " ;
        String join = " Join \"" + Department.class.getName() + "\" D " ;
        String joinCondition = " ON P.depId=D.id";

        String query = queryPrefix + from1+ join + joinCondition;
        Object[] params = new Object[1]; params[0]= new Integer(100);
        read(connection,query,params);
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
        String condition = " LEFT JOIN \"" + Product.class.getName() + "\" as P ON P.id=PU.productId group by P.name having total > ? " ;

        String query = queryPrefix + condition;
        Object[] params = new Object[1]; params[0]=5;
        read(connection,query,params);
    }

    protected void read8(Connection connection) throws SQLException{
        System.out.println("======================= Read8 ===========================");
        String queryPrefix = "Select D.id, D.name  from \"" +  Department.class.getName() + "\" as D " ;
        String condition1 = " WHERE EXISTS (select P.name  from \"" + Product.class.getName() + "\" as P where  P.depId=D.id and P.price > ?)" ;

        String query = queryPrefix + condition1;
        Object[] params = new Object[1]; params[0]=100;
        read(connection,query,params);
    }

    protected void read9(Connection connection) throws SQLException{
        System.out.println("======================= Read9 ===========================");
        String queryPrefix = "Select D.id,D.name  from \"" +  Department.class.getName() + "\" as D " ;
        String condition1 = " WHERE EXISTS (select P.name from \""+  Product.class.getName() + "\" as P where P.depId=D.id and P.price>?)";

        String query = queryPrefix + condition1;
        Object[] params = new Object[1]; params[0]=500;
        read(connection,query,params);
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
        String query = "SELECT D.name, P.depId, Max(P.price) as max_price  from \"" +  Product.class.getName() + "\" as P join  \""  + Department.class.getName() +"\" D on P.depId=D.id group By D.name, P.depId" ;
        read(connection,query);
    }
}
