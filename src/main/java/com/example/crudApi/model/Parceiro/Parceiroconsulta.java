package com.example.crudApi.model.Parceiro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Parceiroconsulta {

    private String nome;
    private String saldo1;
    private String saldo2;
    private BigDecimal saldoTotalDisponivel;

}
