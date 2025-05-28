package bbaETL;

public class DadoChegadaOriginal {
    private String continente;
    private Integer codContinente;
    private String pais;
    private Integer codPais;
    private String uf;
    private Integer codUf;
    private String via;
    private Integer codVia;
    private Integer ano;
    private String mes;
    private Integer codMes;
    private Integer chegadas;

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public Integer getCodContinente() {
        return codContinente;
    }

    public void setCodContinente(Integer codContinente) {
        this.codContinente = codContinente;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCodPais() {
        return codPais;
    }

    public void setCodPais(Integer codPais) {
        this.codPais = codPais;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getCodUf() {
        return codUf;
    }

    public void setCodUf(Integer codUf) {
        this.codUf = codUf;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public Integer getCodVia() {
        return codVia;
    }

    public void setCodVia(Integer codVia) {
        this.codVia = codVia;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getCodMes() {
        return codMes;
    }

    public void setCodMes(Integer codMes) {
        this.codMes = codMes;
    }

    public Integer getChegadas() {
        return chegadas;
    }

    public void setChegadas(Integer chegadas) {
        this.chegadas = chegadas;
    }

    @Override
    public String toString() {
        return "ChegadaTuristas{" +
                "continente='" + continente + '\'' +
                ", codContinente=" + codContinente +
                ", pais='" + pais + '\'' +
                ", codPais=" + codPais +
                ", uf='" + uf + '\'' +
                ", codUf=" + codUf +
                ", via='" + via + '\'' +
                ", codVia=" + codVia +
                ", ano='" + ano + '\'' +
                ", mes='" + mes + '\'' +
                ", codMes=" + codMes +
                ", chegadas=" + chegadas +
                '}';
    }
}
