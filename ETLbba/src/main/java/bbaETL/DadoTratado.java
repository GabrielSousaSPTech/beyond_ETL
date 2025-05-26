package bbaETL;

import java.time.LocalDate;
import java.sql.Date;


public class DadoTratado {
    private String continente;
    private String pais;
    private String uf;
    private String via;
    private Date data;
    private Integer chegadas;

    public DadoTratado() {
    }

    public DadoTratado(String continente, String pais, String uf, String via, Date data, Integer chegadas) {
        this.continente = continente;
        this.pais = pais;
        this.uf = uf;
        this.via = via;
        this.data = data;
        this.chegadas = chegadas;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getChegadas() {
        return chegadas;
    }

    public void setChegadas(Integer chegadas) {
        this.chegadas = chegadas;
    }

    @Override
    public String toString() {
        return "DadoTratado{" +
                "continente='" + continente + '\'' +
                ", pais='" + pais + '\'' +
                ", uf='" + uf + '\'' +
                ", via='" + via + '\'' +
                ", data=" + data +
                ", chegadas=" + chegadas +
                '}';
    }
}
