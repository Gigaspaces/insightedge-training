package common;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;

/**
 * Created by ester on 01/01/2018.
 */
@EventDriven
@Notify
public class Listener {
    @EventTemplate
    FlightDelayed unprocessedData() {
        FlightDelayed template = new FlightDelayed();
        template.setDelayed(true);
        template.setProcessed(false);
        return template;
    }

    @SpaceDataEvent
    public FlightDelayed eventListener(FlightDelayed event) {
        event.setProcessed(true);
        publish(event.getKey());
        return event;
    }

    protected void publish(String key){
        System.out.println("Flight: " + key + " is delayed");
    }
}
