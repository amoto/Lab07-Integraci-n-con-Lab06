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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientesStub;
import java.sql.Date;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientesTest {
    
       
    
    @Test
    public void PacienteNuevo(){
        ServiciosPacientesStub sps=new ServiciosPacientesStub();
        try{
            Paciente yo=new Paciente(1, "cc", "Julian Devia" , new Date(1996, 7, 9));
            sps.registrarNuevoPaciente(yo);
            Paciente test=sps.consultarPaciente(1, "cc");
            assertEquals("No consulta el paciente correcto cuando se agrega paciente nuevo", yo.toString(),test.toString());
        }catch(ExcepcionServiciosPacientes ex){
            fail("Arroja excepcion agregando un nuevo paciente");
        }
        
    }
    @Test
    public void PacienteRepetido(){
        ServiciosPacientesStub sps=new ServiciosPacientesStub();
        try{
            Paciente yo=new Paciente(1, "cc", "Julian Devia" , new Date(1996, 7, 9));
            sps.registrarNuevoPaciente(yo);
            sps.registrarNuevoPaciente(yo);
            Paciente test=sps.consultarPaciente(1, "cc");
            assertEquals("No consulta el paciente correcto cuando se agrega paciente repetido", yo.toString(),test.toString());
        }catch(ExcepcionServiciosPacientes ex){
            fail("Arroja excepcion agregando un paciente repetido");
        }
    }
    
    
}
