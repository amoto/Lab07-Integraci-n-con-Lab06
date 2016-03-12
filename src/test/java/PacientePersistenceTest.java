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
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
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
    
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        DaoPaciente reg=daof.getDaoPaciente();
        //IMPLEMENTAR PRUEBAS
        //Deberia registrar paciente con una sola consulta
        Paciente p1=new Paciente(123,"CC","German Lopez",new Date(1994,10,10));
        Consulta c1=new Consulta(new Date(2016,1,13),"El paciente presenta fiebre alta");
        Set<Consulta> consultas1=new LinkedHashSet<Consulta>();
        consultas1.add(c1);
        p1.setConsultas(consultas1);
        reg.save(p1);
        //
        Paciente p2=new Paciente(321,"CC","Julian Devia",new Date(1996,6,9));
        Consulta c2=new Consulta(new Date(2016,3,20),"El paciente presenta una pequeña fractura en la tibia");
        Set<Consulta> consultas2=new LinkedHashSet<Consulta>();
        consultas2.add(c2);
        p2.setConsultas(consultas2);
        reg.save(p2);
        //No deberia registrar nuevo paciente ya existente con mas de una consulta
        try{
        Paciente p3=new Paciente(321,"CC","Julian Devia",new Date(1996,6,9));
        Consulta c3=new Consulta(new Date(2016,4,12),"El paciente presenta varicela");
        Consulta c4=new Consulta(new Date(2016,5,12),"El paciente esta completamente curado");
        Set<Consulta> consultas3=new LinkedHashSet<Consulta>();
        consultas3.add(c3);
        consultas3.add(c4);
        p3.setConsultas(consultas3);
        reg.save(p3);
        Assert.fail("Registro paciente ya existente");
        }catch(PersistenceException e){
            Assert.assertEquals(e.getMessage(),"Ese paciente ya esta registrado");
        }
        daof.commitTransaction();
        daof.endSession();        
    }
    
    
}
