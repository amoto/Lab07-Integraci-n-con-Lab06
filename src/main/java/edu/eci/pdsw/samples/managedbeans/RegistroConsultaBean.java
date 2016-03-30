
/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.managedbeans;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hcadavid
 */
@ManagedBean(name="RegistroBean")
@SessionScoped
public class RegistroConsultaBean implements Serializable {
    
    
    
    private final ServiciosPacientes sp=ServiciosPacientes.getInstance();
    //Atributos para paciente
    int id=0;
    String tipoId="";
    String nombre="";
    int dia=0;
    int mes=0;
    int anio=0;
    private Paciente selectPaciente;
    
    //Atributos para consulta
    private int anioc;
    private int mesc;
    private int diac;
    private String resumenc;
    
    /**
     * registra a un nuevo paciente en el sistema
     * @param id el numero de identificacion del paciente
     * @param tipo_id el tipo de documento de identificacion del paciente
     * @param nombre el nombre del paciente
     * @param fechaNacimiento la fecha de nacimiento del paciente
     * @throws ExcepcionServiciosPacientes si se presenta algún error logico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public void registrarNuevoPaciente() throws ExcepcionServiciosPacientes{
        System.out.println("classpath");
        sp.registrarNuevoPaciente(new Paciente(id,tipoId,nombre,Date.valueOf(anio+"-"+mes+"-"+dia)));
    }
    
    public void setId(int nuevoId){
        id=nuevoId;
    }
    
     public int getId(){
        return id;
    }
    
    public void setNombre(String nuevoNombre){
        nombre=nuevoNombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setTipoId(String nuevoTipoId){
        tipoId=nuevoTipoId;
    }
    
    public String getTipoId(){
        return tipoId;
    }
    
    public void setDia(int nuevoDia){
        dia=nuevoDia;
    }
    
    public int getDia(){
        return dia;
    }
    
    public void setMes(int nuevoMes){
        mes=nuevoMes;
    }
    
    public int getMes(){
        return mes;
    }
    
    public void setAnio(int nuevoAnio){
        anio=nuevoAnio;
    }
    
    public int getAnio(){
        return anio;
    }
    
    /**
     * registra una consulta al paciente dado
     * @param paciente_id el numero de identifiacion del paciente
     * @param tipo_id el tipo de documento de identificacion del paciente
     * @param fechayHora la fecha y hora de la consulta
     * @param resumen el resumen de la consulta
     * @throws ExcepcionServiciosPacientes si se presenta algún error de persistencia o si el paciente no existe.
     */
    public void agregarConsultaPaciente() throws ExcepcionServiciosPacientes{
        Date d=Date.valueOf(anioc+"-"+mesc+"-"+diac);
        sp.agregarConsultaAPaciente(selectPaciente.getId(), selectPaciente.getTipo_id(), new Consulta(d, resumenc));
    }
    
    public List<Consulta> ConsultasPaciente() throws ExcepcionServiciosPacientes{
        Paciente paciente_actual = sp.consultarPaciente(selectPaciente.getId(), selectPaciente.getTipo_id());
            List<Consulta> consultas=new LinkedList<Consulta>();
        consultas.addAll(paciente_actual.getConsultas());
        return consultas;
    }

    /**
     * @return the selectedPaciente
     */
    public Paciente getSelectPaciente() {
        return selectPaciente;
    }

    /**
     * @param selectedPaciente the selectedPaciente to set
     */
    public void setSelectPaciente(Paciente selectedPaciente) {
        this.selectPaciente = selectedPaciente;
    }

    /**
     * @return the anioc
     */
    public int getAnioc() {
        return anioc;
    }

    /**
     * @param anioc the anioc to set
     */
    public void setAnioc(int anioc) {
        this.anioc = anioc;
    }

    /**
     * @return the mesc
     */
    public int getMesc() {
        return mesc;
    }

    /**
     * @param mesc the mesc to set
     */
    public void setMesc(int mesc) {
        this.mesc = mesc;
    }

    /**
     * @return the diac
     */
    public int getDiac() {
        return diac;
    }

    /**
     * @param diac the diac to set
     */
    public void setDiac(int diac) {
        this.diac = diac;
    }
    
    /**
     * @return the reumenc
     */
    public String getResumenc() {
        return resumenc;
    }

    /**
     * @param reumenc the reumenc to set
     */
    public void setResumenc(String reumenc) {
        this.resumenc = reumenc;
    }
    
    public ArrayList<Paciente> getPaciente() throws ExcepcionServiciosPacientes{
        return sp.getPacientes();
    }
}

