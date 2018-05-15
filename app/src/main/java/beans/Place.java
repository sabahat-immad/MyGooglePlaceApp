package beans;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class Place implements Serializable {

    @Key
    public String id;

    @Key
    public String name;

    @Key
    public String reference;

    @Key
    public String formatted_address;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Key

    public String icon;

    @Key
    public String types;

    @Key
    public String vicinity;

    public String lat;
    public String lng;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

    public String getIcon() {
        return icon;
    }

    public String getTypes() {
        return types;
    }

    public String getVicinity() {
        return vicinity;
    }
}
