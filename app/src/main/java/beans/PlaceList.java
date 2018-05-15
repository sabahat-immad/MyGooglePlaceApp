package beans;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

public class PlaceList implements Serializable{

    @Key
    public String status;

    @Key
    public List<Place> results;


}
