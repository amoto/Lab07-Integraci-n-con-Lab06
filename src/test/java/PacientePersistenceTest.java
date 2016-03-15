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

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    
    public PacientePersistenceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    public static InputStream input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
    public static Properties properties=new Properties();
    
    @Test
    public void CE1() throws IOException, PersistenceException{
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        DaoPaciente reg=daof.getDaoPaciente();
        
        //Deberia registrar paciente nuevo con mas de una consulta
        Paciente p4 = new Paciente(1234567890,"TI","Pepito Perez",Date.valueOf("1996-07-09"));
        Consulta c5 = new Consulta(Date.valueOf("2009-10-12"),"El paciente tiene fiebre");
        Consulta c6 = new Consulta(Date.valueOf("2009-10-13"),"El paciente sigue con fiebre");
        Set<Consulta> consultas4=new HashSet<>();
        consultas4.add(c5);consultas4.add(c6);
        p4.setConsultas(consultas4);
        reg.save(p4);
        Paciente test =reg.load(p4.getId(),p4.getTipo_id());
        boolean worked= test.getId()==p4.getId() && test.getTipo_id().equals(p4.getTipo_id()) && 
                test.getNombre().equals(p4.getNombre()) && test.getFechaNacimiento().equals(p4.getFechaNacimiento());
        LinkedList<String> s=new LinkedList<String>();
        for (Consulta c: test.getConsultas()){
            s.add(c.toString());
        }
        for (Consulta c : consultas4) {
            worked=worked && s.contains(c.toString());
            
        }
        daof.commitTransaction();
        daof.endSession();  
        Assert.assertTrue("Falla agregando un paciente con mas de una consulta", worked);
    }
    @Test
    public void CE2() throws IOException, PersistenceException{
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        boolean worked=false;
        try {
            daof.beginSession();
            DaoPaciente reg=daof.getDaoPaciente();
            Paciente p = new Paciente(9876, "TI", "Carmenzo", Date.valueOf("1995-07-10"));
            reg.save(p);
            Paciente test= reg.load(p.getId(),p.getTipo_id());
            worked =test.toString().equals(p.toString());
            daof.commitTransaction();
        } catch (PersistenceException ex) {
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail();
        }
        finally{
            daof.endSession(); 
        }
        Assert.assertTrue("Falla agregando paciente sin consultas",worked);
        
        //Deberia registrar paciente nuevo sin consultas
         
        
    }
    
    public void CE3() throws IOException, PersistenceException{
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        DaoPaciente reg=daof.getDaoPaciente();
        Paciente p1=new Paciente(123,"CC","German Lopez",new Date(1994,10,10));
        Consulta c1=new Consulta(new Date(2016,1,13),"El paciente presenta fiebre alta");
        Set<Consulta> consultas=new LinkedHashSet<Consulta>();
        consultas.add(c1);
        p1.setConsultas(consultas);
        reg.save(p1);
        Paciente com=reg.load(123,"CC");
	daof.commitTransaction();
        daof.endSession(); 
        Assert.assertEquals(p1.toString(),com.toString());
    }
    
    @Test
    public void CE3Segundo() throws IOException, PersistenceException{
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        DaoPaciente reg=daof.getDaoPaciente();
        Paciente p1=new Paciente(321,"CC","Julian Devia",new Date(1996,6,9));
        Consulta c1=new Consulta(new Date(2016,3,20),"El paciente presenta fractura en la tibia");
        Set<Consulta> consultas=new LinkedHashSet<Consulta>();
        consultas.add(c1);
        p1.setConsultas(consultas);
        reg.save(p1);
        Paciente com=reg.load(321,"CC");
        daof.commitTransaction();
        daof.endSession();
        Assert.assertEquals(p1.toString(),com.toString());
    }
    
    @Test
    public void CE4() throws IOException, PersistenceException{
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        Paciente p1=null;
        try{
            daof.beginSession();
        
        DaoPaciente reg=daof.getDaoPaciente();
        p1=new Paciente(666,"CC","Samuel Tapias",new Date(1997,6,9));
        Paciente p2=new Paciente(666,"CC","Samuel Tapias",new Date(1997,6,9));
        Consulta c1=new Consulta(new Date(2016,4,12),"El paciente presenta varicela");
        Consulta c2=new Consulta(new Date(2016,5,12),"El paciente esta completamente curado");
        Consulta c3=new Consulta(new Date(2017,6,3),"El paciente tiene esquizofrenia");
        Set<Consulta> consultas=new LinkedHashSet<Consulta>();
        Set<Consulta> consultas1=new LinkedHashSet<Consulta>();
        consultas.add(c1);
        consultas.add(c2);
        consultas1.add(c3);
        p1.setConsultas(consultas);
        p2.setConsultas(consultas1);
        reg.save(p1);
        reg.save(p2);
        Assert.fail("Registro paciente ya existente");
        daof.commitTransaction();
        
        
        }catch(PersistenceException e){
            Assert.assertEquals(e.getMessage(),"El paciente con id: "+p1.getId()+" ya esta registrado");
        }finally{
            daof.endSession(); 
        }
    }
    /*
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        DaoPaciente reg=daof.getDaoPaciente();       
        
        //IMPLEMENTAR PRUEBAS
        daof.commitTransaction();
        daof.endSession();        
    }*/
    
    
}
