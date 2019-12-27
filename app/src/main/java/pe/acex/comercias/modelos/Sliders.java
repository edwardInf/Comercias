package pe.acex.comercias.modelos;

public class Sliders {
    private int id;
    private Imagen image;

    public Sliders(int id, Imagen image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Imagen getImage() {
        return image;
    }

    public void setImage(Imagen image) {
        this.image = image;
    }

    public class Imagen {
        private String path;

        public Imagen(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
