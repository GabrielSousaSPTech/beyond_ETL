package bbaETL;

import java.sql.Date;
import java.time.LocalDate;


public class ObjetoInsercao {
    private Date data;
    private int chegadas;
    private int idVia;
    private int idPais;
    private int idContinente;
    private int idUf;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getChegadas() {
        return chegadas;
    }

    public void setChegadas(int chegadas) {
        this.chegadas = chegadas;
    }

    public int getIdVia() {
        return idVia;
    }

    public void setIdVia(int idVia) {
        this.idVia = idVia;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }

    public int getIdUf() {
        return idUf;
    }

    public void setIdUf(int idUf) {
        this.idUf = idUf;
    }

    public ObjetoInsercao(Date data, int chegadas, int idVia, int idPais, int idContinente, int idUf) {
        this.data = data;
        this.chegadas = chegadas;
        this.idVia = idVia;
        this.idPais = idPais;
        this.idContinente = idContinente;
        this.idUf = idUf;
    }

    @Override
    public String toString() {
        return "ObjetoInsercao{" +
                "data=" + data +
                ", chegadas=" + chegadas +
                ", idVia=" + idVia +
                ", idPais=" + idPais +
                ", idContinente=" + idContinente +
                ", idUf=" + idUf +
                '}';
    }
}
