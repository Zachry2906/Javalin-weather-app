package dojo.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputStatus {
    @JsonProperty("kota")
    private String kota;

    @JsonProperty("long")
    private Double longg; // Nama yang lebih deskriptif

    @JsonProperty("lat")
    private Double lat;

    public InputStatus() {
        // Konstruktor default
    }

    public InputStatus(String kota, Double longitude, Double lat) {
        this.kota = kota;
        this.longg = longitude;
        this.lat = lat;
    }
}
