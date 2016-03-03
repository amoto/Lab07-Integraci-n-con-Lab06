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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.util.Date;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class ConsultasTest {
    
    @Test
    public void debereriaAgregarConsulta() throws ExcepcionServiciosPacientes{
        Consulta hoy=new Consulta(new java.sql.Date(2016, 3, 3),"Varicela de julian");
        ServiciosPacientesStub spt=new ServiciosPacientesStub();
        Paciente julian=new Paciente(123,"jul321","Julian Devia",new java.sql.Date(1996, 7, 9));
        spt.registrarNuevoPaciente(julian);
        spt.agregarConsultaAPaciente(123, "jul321", hoy);
        Set<Consulta> lol=spt.consultarPaciente(123, "jul321").getConsultas();
        for(Consulta c: lol){
            if(c.getResumen().equals("Varicela de julian")){
                 assertEquals("Al paciente no se le agrego la consulta",c.toString(),hoy.toString());
            }else{
                fail("No se encontro ese paciente");
            }
        }
       
        
    }
    
    
}
