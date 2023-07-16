package com.example.crudApi.model.Parceiro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParceiroDebito {

    private String nome;

    private String emComum1;

    private String emComum2;

    private String IdUnicoTransacao;

    private String descricao;


}