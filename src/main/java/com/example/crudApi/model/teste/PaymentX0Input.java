package com.example.crudApi.model.teste;


import lombok.Getter;
import lombok.Setter;

@lombok.Data
public class PaymentX0Input {

    private Data data;
}

@Getter @Setter
class Data {
    private String identificadorTransacional;

    private Conta conta;

}

@Getter @Setter
class Conta {

    private String identificadorUnicoConta;

    private String numeroTitularConta;
}

