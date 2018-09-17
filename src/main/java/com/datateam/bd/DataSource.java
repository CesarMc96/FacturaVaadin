
package com.datateam.bd;

import java.sql.Connection;
import java.util.ArrayList;

public interface DataSource {
    
    public Object ejecutarConsulta(String consulta);
    
}
