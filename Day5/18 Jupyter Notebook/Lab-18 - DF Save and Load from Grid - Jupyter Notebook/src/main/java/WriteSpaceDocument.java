import com.gigaspaces.document.SpaceDocument;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.HashMap;
import java.util.Map;

public class WriteSpaceDocument {

    public static void main(String[] args) {
        GigaSpace gigaspace = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo")).gigaSpace();
        // 1. Create the properties:
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("CatalogNumber", "hw-1234");
        properties.put("Category", "Hardware");
        properties.put("Name", "Anvil");
        properties.put("Price", 9.99f);

        Map<String, Object> features = new HashMap<String, Object>();
        features.put("Manufacturer", "Acme");
        features.put("RequiresAssembly", false);
        features.put("Weight", 100);
        properties.put("Features", features);

        // 2. Create the document using the type name and properties:
        SpaceDocument document = new SpaceDocument("Product", properties);
        // 3. Write the document to the space:
        gigaspace.write(document);
    }
}
