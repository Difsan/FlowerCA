package co.diego.mongo.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "flower")
public class FlowerData {

    @Id
    private String id ;
    private String commonName;
    private String family;
    private String color;
    private String type;
    private String origin;
    private Boolean inStock;

    public FlowerData(String commonName, String family,
                      String color, String type, String origin) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.commonName = commonName;
        this.family = family;
        this.color = color;
        this.type = type;
        this.origin = origin;
        this.inStock = true;
    }
}
