import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.metadata.SpaceTypeDescriptorBuilder;
import com.gigaspaces.metadata.index.SpaceIndexType;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

public class RegisterSpaceDocument {


    public static void main(String[] args) {
        GigaSpace gigaspace = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo")).gigaSpace();
        SpaceTypeDescriptor typeDescriptor = new SpaceTypeDescriptorBuilder("Product")
                .idProperty("CatalogNumber")
                .routingProperty("Category")
                .addPropertyIndex("Name", SpaceIndexType.EQUAL)
                .addPropertyIndex("Price", SpaceIndexType.ORDERED)
                .create();
        // Register type:
        gigaspace.getTypeManager().registerTypeDescriptor(typeDescriptor);
    }
}
