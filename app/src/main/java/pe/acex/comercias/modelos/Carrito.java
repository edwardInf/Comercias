package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Carrito {

    private int id;

    @SerializedName("ip_address")
    private String ip;

    @SerializedName("items")
    private ArrayList<Items> items;

    public Carrito(int id, String ip, ArrayList<Items> items) {
        this.id = id;
        this.ip = ip;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }
}
