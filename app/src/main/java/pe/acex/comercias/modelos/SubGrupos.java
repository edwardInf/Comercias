package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class SubGrupos {

    @SerializedName("name")
    private String name;
    @SerializedName("category_group_id")
    private int category_group_id;

    public SubGrupos(String name, int category_group_id) {
        this.name = name;
        this.category_group_id = category_group_id;
    }

    public String getName() {
        return name;
    }

    public int getCategory_group_id() {
        return category_group_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory_group_id(int category_group_id) {
        this.category_group_id = category_group_id;
    }
}
