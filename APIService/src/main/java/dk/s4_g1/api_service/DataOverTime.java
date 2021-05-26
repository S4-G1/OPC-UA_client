package dk.s4_g1.api_service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DataOverTime {
    @SerializedName("batch_id")
    private int batchId;

    @SerializedName("measurement_ts")
    private String msTime = "";

    @SerializedName("temperature")
    private float temperature = 0;

    @SerializedName("humidity")
    private float humidity = 0;

    @SerializedName("vibration")
    private float vibration = 0;

    @SerializedName("produced")
    private float produced = 0;

    @SerializedName("state")
    private int state = 0;

    @SerializedName("rejected")
    private int rejected = 0;

    public DataOverTime(int batchId) {
        this.batchId = batchId;
    }

    public void setMsTime(String msTime) {
        this.msTime = msTime;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setVibration(float vibration) {
        this.vibration = vibration;
    }

    public void setProduced(float produced) {
        this.produced = produced;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public int getBatchId() {
        return batchId;
    }

    public String getMsTime() {
        return msTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getVibration() {
        return vibration;
    }

    public float getProduced() {
        return produced;
    }

    public int getState() {
        return state;
    }

    public int getRejected() {
        return rejected;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
