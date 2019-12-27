package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class Recientes {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("sale_price")
    private Float sale_price;
    @SerializedName("offer_price")
    private Float offer_price;
    @SerializedName("image")
    private Image image;

    public Recientes(int id, String title, Float sale_price, Float offer_price, Image image) {
        this.id = id;
        this.title = title;
        this.sale_price = sale_price;
        this.offer_price = offer_price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getSale_price() {
        return sale_price;
    }

    public Float getOffer_price() {
        return offer_price;
    }

    public Image getImage() {
        return image;
    }

    public class Image {
        @SerializedName("path")
        private String path;

        public Image(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
