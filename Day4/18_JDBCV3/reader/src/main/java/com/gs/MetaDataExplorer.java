package com.gs;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.*;

public class MetaDataExplorer extends AbsractJDBCClient{
    public MetaDataExplorer() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo").lookupGroups("xap-16.2.0")).gigaSpace();
        MetaDataExplorer client = new MetaDataExplorer();
        Connection connection = client.connect(gs);
        System.out.println("================ information_schema_catalog_name==========");
        client.readTable(connection, "information_schema_catalog_name");
        System.out.println("==================== tables ===============================");
        client.readTable(connection, "tables");
        System.out.println("==================== columns ===============================");
        client.readTable(connection, "columns");

    }


}
