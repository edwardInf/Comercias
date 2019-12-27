package pe.acex.comercias.modelos;

import com.google.gson.annotations.SerializedName;

public class CardUser {

    @SerializedName("id")
    private String idCard;

    @SerializedName("expiration_month")
    private int mes_Vencimiento;

    @SerializedName("expiration_year")
    private int year_Vencimiento;

    @SerializedName("first_six_digits")
    private String p6digitos;

    @SerializedName("last_four_digits")
    private String u4digitos;

    @SerializedName("payment_method")
    private MetodoPago metodoPago;

    @SerializedName("security_code")
    private CodSeguridad codSeg;

    private Issuer issuer;

    public class MetodoPago {

        @SerializedName("id")
        private String idMP;

        @SerializedName("name")
        private String nombreMP;

        @SerializedName("payment_type_id")
        private String tipoMP;



        public MetodoPago(String idMP, String nombreMP, String tipoMP) {
            this.idMP = idMP;
            this.nombreMP = nombreMP;
            this.tipoMP = tipoMP;
        }

        public String getIdMP() {
            return idMP;
        }

        public void setIdMP(String idMP) {
            this.idMP = idMP;
        }

        public String getNombreMP() {
            return nombreMP;
        }

        public void setNombreMP(String nombreMP) {
            this.nombreMP = nombreMP;
        }

        public String getTipoMP() {
            return tipoMP;
        }

        public void setTipoMP(String tipoMP) {
            this.tipoMP = tipoMP;
        }
    }

    public class CodSeguridad {

        @SerializedName("length")
        private int longitud;

        @SerializedName("card_location")
        private String lugar;

        public CodSeguridad(int longitud, String lugar) {
            this.longitud = longitud;
            this.lugar = lugar;
        }

        public int getLongitud() {
            return longitud;
        }

        public void setLongitud(int longitud) {
            this.longitud = longitud;
        }

        public String getLugar() {
            return lugar;
        }

        public void setLugar(String lugar) {
            this.lugar = lugar;
        }
    }

    public class Issuer {

        @SerializedName("id")
        private int idIss;

        @SerializedName("name")
        private String nameIss;

        public Issuer(int idIss, String nameIss) {
            this.idIss = idIss;
            this.nameIss = nameIss;
        }

        public int getIdIss() {
            return idIss;
        }

        public void setIdIss(int idIss) {
            this.idIss = idIss;
        }

        public String getNameIss() {
            return nameIss;
        }

        public void setNameIss(String nameIss) {
            this.nameIss = nameIss;
        }
    }


}
