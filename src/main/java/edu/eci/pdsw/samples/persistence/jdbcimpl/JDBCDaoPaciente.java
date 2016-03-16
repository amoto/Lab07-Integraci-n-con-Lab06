/*
 * Copyright (C) 2015 hcadavid
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
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        Paciente p = null;
        try {
            String consulta= "select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen "
                    + "from PACIENTES as pac LEFT join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id "
                    + "where pac.id= ? and pac.tipo_id= ?";
            ps=con.prepareStatement(consulta);
            ps.setInt(1, idpaciente);
            ps.setString(2, tipoid);
            ResultSet result=ps.executeQuery();
            Set<Consulta> consultas=new HashSet<>();
            boolean paciente=false;
            while(result.next()){
                if(!paciente){
                    p= new Paciente(idpaciente, tipoid, result.getString(1), result.getDate(2));
                    paciente=true;
                }
                if(result.getDate(4)!=null){
                    Consulta c=new Consulta(result.getDate(4), result.getString(5));
                    consultas.add(c);
                }
            }
            if(p==null) throw new PersistenceException("El paciente con "+tipoid+" "+idpaciente+" no existe");
            p.setConsultas(consultas);
            
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente+" "+ex.getMessage(),ex);
        }
        return p;
        
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        try {
        int valor=0;
        String verificar="select count(id) from PACIENTES where id=? and tipo_id=?";
        ps=con.prepareStatement(verificar);
        ps.setInt(1,p.getId());
        ps.setString(2, p.getTipo_id());
        ResultSet resultado=ps.executeQuery();
        if(resultado.next()){
            valor=resultado.getInt(1);
        }
        if(valor!=0) throw new PersistenceException("El paciente con id: "+p.getId()+" ya esta registrado");
        String insertar="insert into PACIENTES  values(?,?,?,?)";
        ps=con.prepareStatement(insertar);
        ps.setInt(1,p.getId());
        ps.setString(2, p.getTipo_id());
        ps.setString(3, p.getNombre());
        ps.setDate(4, p.getFechaNacimiento());
        int res=ps.executeUpdate();
        if(res!=1)throw new PersistenceException("Error al registrar paciente con id: "+p.getId());
            con.commit();
        Set<Consulta> consultas=p.getConsultas();
        for(Consulta c:consultas){
            String insertarC="insert into CONSULTAS (fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) values (?,?,?,?)";
            ps=con.prepareStatement(insertarC);
            ps.setDate(1,c.getFechayHora());
            ps.setString(2,c.getResumen());
            ps.setInt(3,p.getId());
            ps.setString(4,p.getTipo_id());
            int res1=ps.executeUpdate();
            if(res1!=1)throw new PersistenceException("El paciente con id:"+p.getId()+" hubo error al registrar la consulta con id: "+c.getId());
                con.commit();
             }
        } catch (SQLException ex) {
            throw new PersistenceException("No se registro el paciente correctamente"+ex.getMessage(),ex);
        }

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        try {
            String update="update PACIENTES set nombre = ?, fecha_nacimiento = ? where id = ? , tipo_id = ?";
            ps = con.prepareStatement(update);
            ps.setString(1,p.getNombre());
            ps.setDate(2,p.getFechaNacimiento());
            ps.setInt(3, p.getId());
            ps.setString(4,p.getTipo_id());
            int res=ps.executeUpdate();
            Set<Consulta> consultasNuevo = p.getConsultas();
            Set<Consulta> consultasViejo =load(p.getId(),p.getTipo_id()).getConsultas();
            ArrayList<String> consultasN=new ArrayList<>();
            for (Consulta c:consultasViejo){
                consultasN.add(c.toString());
            }
            for (Consulta c:consultasNuevo){
                if(!consultasN.contains(c.toString())){
                    String insertarC="insert into CONSULTAS (fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) values (?,?,?,?)";
                    ps=con.prepareStatement(insertarC);
                    ps.setDate(1,c.getFechayHora());
                    ps.setString(2,c.getResumen());
                    ps.setInt(3,p.getId());
                    ps.setString(4,p.getTipo_id());
                    res+=ps.executeUpdate();
                }
            }
            if(res==0){
                throw new PersistenceException("No seha podido actuaizar el paciente");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } 
        //throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");
    }
    
    public ArrayList<Paciente> loadAll() throws PersistenceException{
        ArrayList<Paciente> pacientes=new ArrayList<Paciente>();
        PreparedStatement ps;
        try{
            String pedir="select id,tipo_id from PACIENTES";
            ps=con.prepareStatement(pedir);
            ResultSet res=ps.executeQuery();
            while(res.next()){
                pacientes.add(load(res.getInt(1),res.getString(2)));
            }
        }catch(SQLException e){
            throw new PersistenceException("Error al obtener todos los pacientes");
        }
        return pacientes;
    }
    
}
