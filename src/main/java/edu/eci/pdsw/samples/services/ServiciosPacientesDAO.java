/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPaciente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2105684
 */
public class ServiciosPacientesDAO extends ServiciosPacientes{
    
    private JDBCDaoPaciente basePaciente;
    
    public ServiciosPacientesDAO(JDBCDaoPaciente daoP){
        basePaciente=daoP;
    }
    
    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente p=null;
        try {
            p=basePaciente.load(idPaciente, tipoid);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            basePaciente.save(p);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Paciente> getPacientes() {
       ArrayList<Paciente> pacientes=null;
        try {
            pacientes=basePaciente.loadAll();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pacientes;
    }
    
}
