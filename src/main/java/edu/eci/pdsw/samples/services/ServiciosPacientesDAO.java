/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoFactory;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPaciente;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2105684
 */
public class ServiciosPacientesDAO extends ServiciosPacientes{
    
    private DaoPaciente basePaciente;
    private DaoFactory daoF;
    
    public ServiciosPacientesDAO(){
        
        try {
            
            InputStream input = getClass().getClassLoader().getResource("applicationconfig.properties").openStream();
            
            Properties properties=new Properties();
            properties.load(input);
            daoF = DaoFactory.getInstance(properties);
            //daoF.beginSession();
            basePaciente=daoF.getDaoPaciente();
        } catch (IOException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (PersistenceException ex) {
            //Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
    }
    
    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        
        Paciente p=null;
        try {
            daoF.beginSession();
            basePaciente = daoF.getDaoPaciente();
            p=basePaciente.load(idPaciente, tipoid);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }finally{
            try {
                daoF.endSession();
            } catch (PersistenceException ex) {
                Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionServiciosPacientes(ex.getMessage());
            }
        }
        return p;
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            daoF.beginSession();
            basePaciente = daoF.getDaoPaciente();
            basePaciente.save(p);
            daoF.commitTransaction();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }finally{
            try {
                daoF.endSession();
            } catch (PersistenceException ex) {
                Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionServiciosPacientes(ex.getMessage());
            }
        }
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        try {
            daoF.beginSession();
            basePaciente = daoF.getDaoPaciente();
            Paciente p=basePaciente.load(idPaciente, tipoid);
            Set<Consulta> consultas =p.getConsultas();
            consultas.add(c);
            p.setConsultas(consultas);
            basePaciente.update(p);
            daoF.commitTransaction();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }finally{
            try {
                daoF.endSession();
            } catch (PersistenceException ex) {
                Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionServiciosPacientes(ex.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Paciente> getPacientes() throws ExcepcionServiciosPacientes{
       ArrayList<Paciente> pacientes=null;
        try {
            daoF.beginSession();
            basePaciente = daoF.getDaoPaciente();
            pacientes=basePaciente.loadAll();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionServiciosPacientes(ex.getMessage());
        }finally{
           try {
               daoF.endSession();
           } catch (PersistenceException ex) {
               Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
               throw new ExcepcionServiciosPacientes(ex.getMessage());
           }
        }
        return pacientes;
    }
    
}
