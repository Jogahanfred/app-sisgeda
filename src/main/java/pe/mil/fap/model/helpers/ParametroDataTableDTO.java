package pe.mil.fap.model.helpers;

import java.io.Serializable;

public class ParametroDataTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int iDisplayStart;
    private int iDisplayLength;
    private String sSearch; 

    public int getInicio() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getTamanio() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public String getFiltro() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    } 
}
