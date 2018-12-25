package common.solution;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

/**
 * Created by ester on 07/01/2018.
 */
public class DelayRec implements java.io.Serializable{
    // Corrospond to CSV of following structure
    //Year	Month	DayofMonth	DayOfWeek	DepTime	CRSDepTime	ArrTime	CRSArrTime	UniqueCarrier	FlightNum	TailNum	ActualElapsedTime	CRSElapsedTime	AirTime	ArrDelay	DepDelay	Origin	Dest	Distance	TaxiIn	TaxiOut	Cancelled	CancellationCode	Diverted	CarrierDelay	WeatherDelay	NASDelay	SecurityDelay	LateAircraftDelay

    // ToDo Build a class represnting part of the fields in this record
    // Must include dayOfWeek, crsDepTime, depDelay, month, dayOfMonth, year

    //Flight number + date
    String id;

    String flightNumber;


    @SpaceId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SpaceRouting
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    // ToDo add more fields, Decide on indexes
    // ToDo Add default constuctor
    // ToDo Add constructor with expected fields
    // ToDo verify you understand ID and routing see: https://docs.gigaspaces.com/xap/14.0/dev-java/space-object-id-operations.html
    // And https://docs.gigaspaces.com/xap/14.0/dev-java/pojo-attribute-annotations.html
    
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getCrsDepTime() {
        return crsDepTime;
    }

    public void setCrsDepTime(Integer crsDepTime) {
        this.crsDepTime = crsDepTime;
    }

    @SpaceIndex(type=SpaceIndexType.EXTENDED)
    public Integer getDepDelay() {
        return depDelay;
    }

    public void setDepDelay(Integer depDelay) {
        this.depDelay = depDelay;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DelayRec(String flightNumber, Integer year, Integer month, Integer dayOfMonth, Integer dayOfWeek, Integer crsDepTime, Integer depDelay, String origin, double distance) {
        this.flightNumber = flightNumber;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.crsDepTime = crsDepTime;
        this.depDelay = depDelay;
        this.origin = origin;
        this.distance = distance;
        setId(String.format(flightNumber + "_" + getDate()));
    }

    public String getDate() {return "" + year +":" +month +":" +dayOfMonth;}

    public DelayRec() {
    }

    @Override
    public String toString() {
        return "DelayRec{" +
                "id='" + id + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                ", dayOfWeek=" + dayOfWeek +
                ", crsDepTime=" + crsDepTime +
                ", depDelay=" + depDelay +
                ", origin='" + origin + '\'' +
                ", distance=" + distance +
                '}';
    }

    public int getHour() {return ((int) (crsDepTime / 100.0));}



}
