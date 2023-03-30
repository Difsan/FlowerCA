package co.diego.model.flower;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Flower {

    private String id ;
    private String commonName;
    private String family;
    private String color;
    private String type;
    private String origin;
    private Boolean inStock;
}
