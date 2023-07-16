package com.example.crudApi.model.boleto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Boletos {

    private Boleto boleto;
    private Beneficiario beneficiario;
    private Partner partner;

}
