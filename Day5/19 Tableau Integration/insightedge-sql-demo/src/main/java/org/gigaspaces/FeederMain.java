package org.gigaspaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;
import org.gigaspaces.model.superstore.Locations;
import org.gigaspaces.model.superstore.Orders;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Created by Vitalii Zinchenko
 */
public class FeederMain {

    private static final String ORDERS_FILE_NAME = "/Sample-Superstore_wo_locations.csv";
    private static final String LOCATIONS_FILE_NAME = "/Sample-Superstore_locations.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    @Parameter(names={"--space-url"}, required = true)
    private String spaceUrl;

    @Parameter(names={"--lookup-group"})
    private String lookupGroup;

    public static void main(String ... argv) throws IOException, URISyntaxException {
        FeederMain main = new FeederMain();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.run();
        System.out.println("Completed!");
    }

    public void run() throws IOException, URISyntaxException {
        GigaSpace space = createSpace();
        loadCsv(space, LOCATIONS_FILE_NAME, this::convertLocations);
        loadCsv(space, ORDERS_FILE_NAME, this::convertOrders);
    }

    private void loadCsv(GigaSpace space, String fileName, Function<String[], Object> converter) throws URISyntaxException, IOException {
        IOUtils.readLines(FeederMain.class.getResourceAsStream(fileName), "UTF-8").stream()
                .skip(1)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))
                .forEach(line -> write(space, line, converter));
    }

    private void write(GigaSpace space, String[] line, Function<String[], Object> converter) {
        try {
            space.write(converter.apply(line));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Orders convertOrders(String[] line) {
        Orders orders = new Orders();
        orders.setRowId(Integer.parseInt(line[0]));
        orders.setOrderId(line[1]);
        orders.setOrderDate(parseDate(line[2]));
        orders.setShipDate(parseDate(line[3]));
        orders.setShipMode(line[4]);
        orders.setCustomerId(line[5]);
        orders.setCustomerName(line[6]);
        orders.setSegment(line[7]);
        orders.setLocationId(Integer.parseInt(line[8]));
        orders.setProductId(line[9]);
        orders.setCategory(line[10]);
        orders.setSubCategory(line[11]);
        orders.setProductName(line[12]);
        orders.setSales(Double.parseDouble(line[13]));
        orders.setQuantity(Integer.parseInt(line[14]));
        orders.setDiscount(Double.parseDouble(line[15]));
        orders.setProfit(Double.parseDouble(line[16]));
        return orders;
    }

    private Locations convertLocations(String[] line) {
        Locations locations = new Locations();
        locations.setId(Integer.parseInt(line[0]));
        locations.setCountry(line[1]);
        locations.setCity(line[2]);
        locations.setState(line[3]);
        locations.setPostalCode(Integer.parseInt(line[4]));
        locations.setRegion(line[5]);
        return locations;
    }

    private Date parseDate(String date) {
        try {
            return new Date(DATE_FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private GigaSpace createSpace() {
        UrlSpaceConfigurer configurer = new UrlSpaceConfigurer(spaceUrl);
        if(lookupGroup != null) {
            configurer.lookupGroups(lookupGroup);
        }
        return new GigaSpaceConfigurer(configurer).gigaSpace();
    }
}
