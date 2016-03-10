
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hcadavid
 */
@ManagedBean(name="RegistroBean")
@SessionScoped
public class RegistroConsultaBean implements Serializable{
    ServiciosPacientes sp=ServiciosPacientes.getInstance();
   
        int id=0;
        String tipoId="";
        String nombre="";
        int dia=0;
        int mes=0;
        int anio=0;
    
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
        sp.registrarNuevoPaciente(new Paciente(id,tipoId,nombre,new Date(anio, mes, dia)));
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
    public void agregarConsultaPaciente(int paciente_id,String tipo_id,Date fechayHora, String resumen) throws ExcepcionServiciosPacientes{
        sp.agregarConsultaAPaciente(paciente_id, tipo_id, new Consulta(fechayHora, resumen));
    }
    
    
    
    
}

