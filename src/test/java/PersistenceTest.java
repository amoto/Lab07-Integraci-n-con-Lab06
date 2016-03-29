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


//import static edu.eci.pdsw.samples.textview.MyBatisExample.getSqlSessionFactory;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.eci.pdsw.samples.mybatis.mappers.PacienteMapper;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.entities.Consulta;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Assert;
/**
 *
 * @author hcadavid
 */
public class PersistenceTest {

    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config-h2.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    
    @Test
    public void CE1(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pm= sqlss.getMapper(PacienteMapper.class);
        //Deberia registrar paciente nuevo con mas de una consulta
        Paciente p4 = new Paciente(1234567890,"TI","Pepito Perez",Date.valueOf("1996-07-09"));
        Consulta c5 = new Consulta(Date.valueOf("2009-10-12"),"El paciente tiene fiebre");
        Consulta c6 = new Consulta(Date.valueOf("2009-10-13"),"El paciente sigue con fiebre");
        Set<Consulta> consultas4=new HashSet<>();
        consultas4.add(c5);consultas4.add(c6);
        p4.setConsultas(consultas4);
        pm.insertPaciente(p4);
        for(Consulta c: p4.getConsultas()){
            pm.insertConsulta(c, p4.getId(), p4.getTipo_id());
        }
        Paciente test=pm.loadPacienteById(p4.getId(), p4.getTipo_id());
        LinkedList<String> cadenas=new LinkedList<>();
        boolean funciona=p4.getId()==test.getId() && p4.getTipo_id().equals(test.getTipo_id()) && p4.getNombre().equals(test.getNombre()) && p4.getFechaNacimiento().equals(test.getFechaNacimiento());
        for(Consulta c:test.getConsultas()){
            cadenas.add(c.toString().split(",")[1]+c.toString().split(",")[2]);
        }
        for(Consulta c:p4.getConsultas()){
            funciona= funciona && cadenas.contains(c.toString().split(",")[1]+c.toString().split(",")[2]);
        }
        Assert.assertTrue(funciona);
        
    }
    
    @Test
    public void CE2(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pm= sqlss.getMapper(PacienteMapper.class);
        Paciente p = new Paciente(9876, "TI", "Carmenzo", Date.valueOf("1995-07-10"));
        pm.insertPaciente(p);
        Paciente test=pm.loadPacienteById(p.getId(), p.getTipo_id());
        sqlss.close();
        Assert.assertEquals(test.toString(),p.toString());
    }
    
    @Test
    public void CE3(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pm= sqlss.getMapper(PacienteMapper.class);
        Paciente p1=new Paciente(123,"CC","German Lopez",new Date(1994,10,10));
        Consulta c1=new Consulta(new Date(2016,1,13),"El paciente presenta fiebre alta");
        Set<Consulta> consultas=new LinkedHashSet<Consulta>();
        consultas.add(c1);
        p1.setConsultas(consultas);
        pm.insertPaciente(p1);
        for(Consulta c: p1.getConsultas()){
            pm.insertConsulta(c, p1.getId(), p1.getTipo_id());
        }
        Paciente test=pm.loadPacienteById(p1.getId(), p1.getTipo_id());
        sqlss.close();
        LinkedList<String> cadenas=new LinkedList<>();
        boolean funciona=p1.getId()==test.getId() && p1.getTipo_id().equals(test.getTipo_id()) && p1.getNombre().equals(test.getNombre()) && p1.getFechaNacimiento().equals(test.getFechaNacimiento());
        for(Consulta c:test.getConsultas()){
            cadenas.add(c.toString().split(",")[1]+c.toString().split(",")[2]);
        }
        for(Consulta c:p1.getConsultas()){
            funciona= funciona && cadenas.contains(c.toString().split(",")[1]+c.toString().split(",")[2]);
        }
        Assert.assertTrue(funciona);
    }
        
    
    
}
