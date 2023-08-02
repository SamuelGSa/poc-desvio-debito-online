package com.example.crudApi.controller;

import com.example.crudApi.model.boleto.Boletos;
import com.example.crudApi.model.teste.ParceiroFactory;
import com.example.crudApi.model.teste.PaymentX0Input;
import com.example.crudApi.service.DebitoOnlineService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ApiController {


    @Autowired
    private DebitoOnlineService debitoOnlineService;

    @Autowired
    ParceiroFactory factory;

    @PostMapping("/efetiva")
    public ResponseEntity<Boletos> efetiva(@RequestBody Boletos dadosBoleto){

       debitoOnlineService.process(dadosBoleto);


        return new ResponseEntity<Boletos>(HttpStatus.CREATED);
    }

    @GetMapping("teste")
    PaymentX0Input retornaObjeto(){


        return factory.criaParceiro();
    }
}
