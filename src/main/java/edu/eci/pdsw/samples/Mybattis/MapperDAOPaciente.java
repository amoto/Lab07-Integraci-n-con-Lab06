/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.Mybattis;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.mybatis.mappers.PacienteMapper;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author 2105684
 */
public class MapperDAOPaciente implements DaoPaciente {
    
    private PacienteMapper pm;
    
    public MapperDAOPaciente(SqlSession sqlss){
        //System.out.println(sqlss);
        pm= sqlss.getMapper(PacienteMapper.class);
    }
    
    @Override
    public Paciente load(int id, String tipoid) throws PersistenceException {
        if(pm.loadPacienteById(id, tipoid)==null) throw new PersistenceException("El paciente con "+tipoid+" "+id+" no existe");
        return pm.loadPacienteById(id, tipoid);
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        if(pm.loadPacienteById(p.getId(), p.getTipo_id())!=null) throw new PersistenceException("El paciente con id: "+p.getId()+" ya esta registrado");
        pm.insertPaciente(p);
        for(Consulta c: p.getConsultas()){
            pm.insertConsulta(c, p.getId(), p.getTipo_id());
        }
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
      if(pm.loadPacienteById(p.getId(), p.getTipo_id())==null) throw new PersistenceException("El paciente "+p.toString()+" no existe");
      Paciente test=pm.loadPacienteById(p.getId(), p.getTipo_id());
      LinkedList<String> consultas=new LinkedList<String>();
      for(Consulta c: test.getConsultas()){
          consultas.add(c.toString().split(",")[1]+c.toString().split(",")[2]);
          System.out.println(c.toString().split(",")[1]+c.toString().split(",")[2]);
      }
        System.out.println("antiguas");
      for(Consulta c: p.getConsultas()){
          System.out.println(c.toString().split(",")[1]+c.toString().split(",")[2]);
          if(!consultas.contains(c.toString().split(",")[1]+c.toString().split(",")[2]) || c.getId()==-1){
              System.out.println("nueva");
              pm.insertConsulta(c, p.getId(), p.getTipo_id());
          }
      }
    }

    @Override
    public ArrayList<Paciente> loadAll() throws PersistenceException {
       return (ArrayList<Paciente>) pm.loadAll();
    }
    
}
