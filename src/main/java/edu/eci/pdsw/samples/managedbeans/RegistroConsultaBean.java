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

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
//import javax.annotation.ManagedBean;
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
    
    //La informacion del paciente actual, del que se consultaran las consultas cuando sea requerido
    private int id_paciente;
    private String tipo_id_paciente;
    
    /**
     * registra a un nuevo paciente en el sistema
     * @param id el numero de identificacion del paciente
     * @param tipo_id el tipo de documento de identificacion del paciente
     * @param nombre el nombre del paciente
     * @param fechaNacimiento la fecha de nacimiento del paciente
     * @throws ExcepcionServiciosPacientes si se presenta algún error logico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public void registrarNuevoPaciente(int id, String tipo_id,String nombre, Date fechaNacimiento) throws ExcepcionServiciosPacientes{
        sp.registrarNuevoPaciente(new Paciente(id,tipo_id,nombre,fechaNacimiento));
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

    /**
     * @return the id_paciente
     */
    public int getId_paciente() {
        return id_paciente;
    }

    /**
     * @param id_paciente the id_paciente to set
     */
    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    /**
     * @return the tipo_id_paciente
     */
    public String getTipo_id_paciente() {
        return tipo_id_paciente;
    }

    /**
     * @param tipo_id_paciente the tipo_id_paciente to set
     */
    public void setTipo_id_paciente(String tipo_id_paciente) {
        this.tipo_id_paciente = tipo_id_paciente;
    }
    
    /**
     * Consulta todas las consulas del paciente
     * @return todas las consultas del paciente
     * @throws ExcepcionServiciosPacientes si el paciente no existe
     */
    public List<Consulta> consultasPaciente() throws ExcepcionServiciosPacientes{
        Paciente paciente_actual=sp.consultarPaciente(id_paciente, tipo_id_paciente);
        List<Consulta> consultas=new LinkedList<Consulta>();
        consultas.addAll(paciente_actual.getConsultas());
        return consultas;
    }
    
    
}
