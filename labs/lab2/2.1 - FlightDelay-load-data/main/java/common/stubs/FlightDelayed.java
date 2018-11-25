package common.stubs;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * Created by ester on 01/01/2018.
 */
public class FlightDelayed implements java.io.Serializable{
    String key;
    boolean delayed;
    boolean processed = false;

    @SpaceId
    @SpaceRouting
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public FlightDelayed(String key, boolean delayed) {
        this.key = key;
        this.delayed = delayed;
    }

    public FlightDelayed() {
    }
}
