package com.example.crudApi.model.teste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParceiroFactory {

    public PaymentX0Input criaParceiro() {

        PaymentX0Input paymentX0Input = new PaymentX0Input();
        Data data = new Data();
        Conta conta = new Conta();

        data.setIdentificadorTransacional("123");
        conta.setIdentificadorUnicoConta("123123");
        conta.setNumeroTitularConta("1");

        data.setConta(conta);
        paymentX0Input.setData(data);


        return paymentX0Input;
    }
}
