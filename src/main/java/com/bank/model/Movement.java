package com.bank.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movement {
    /**
     * Variable que almacena el id del movimiento.
     */
    @Id
    private String id;
    /**
     * Variable que almacena el idcustomer.
     */
    private String idcustomer;
    /**
     * Variable para guardar el el idproduct
     */
    private String idproduct;
    /**
     * 
     */
    private String nameproduct;
    /**
     * Variable que almacena el typo del movimiento (retiro o deposito).
     */
    private String typemovement;
    /**
     * Variable que almacena el monto o el saldo del movimiento.
     */
    private Long ammount;
    /**
     * Variable que almacena la comision cobrada por movimiento.
     */
    private Double commissioncharged;
    /**
     * Variable que almacena la fecha del movimiento.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
