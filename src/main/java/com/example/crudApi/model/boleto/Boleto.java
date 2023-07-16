package com.example.crudApi.model.boleto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class Boleto {

    private BigDecimal valorTotal;
}
