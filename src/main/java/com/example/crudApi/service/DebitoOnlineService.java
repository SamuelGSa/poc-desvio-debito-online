package com.example.crudApi.service;


import com.example.crudApi.model.Parceiro.ParceiroDebito;
import com.example.crudApi.model.Parceiro.Parceiroconsulta;
import com.example.crudApi.model.boleto.Boletos;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

@Service
public class DebitoOnlineService {

    public void process(Boletos dadosBoleto) {

        var key = dadosBoleto.getPartner().getCpfCnpj();

        if (isDebitoOnline(key)){
            consulta(dadosBoleto);
        }
    }

    // Caso deja debito online, desvia o fluxo
    private boolean isDebitoOnline(String key) {
        var debitoOnline= false;

       if (encontrarParceiro(key) != 0) {
           debitoOnline = true;
       }
       return debitoOnline;
    }

    // Método que roda em loop no parceiro.properties buscando o Id solicitado
    private static int encontrarParceiro(String key) {

        int clienteEncontrado = 0;
        int i = 1;

        while (true){
            String idCliente = carregarPropriedades("parceiros.parceiro" + i +".key");

            if (idCliente == null) {
                break;
            }
            if (idCliente.equals(key)){
                clienteEncontrado = i;
                break;
            }
            i++;
        }

        return clienteEncontrado;
    }

    // Pega o valor encontrado do Parceiro e monta na String. // Exemplo: parceiros.parceiro4.
    private static String montarParceiroEncontrado(String key){

       var parceiro = "parceiros.parceiro" + encontrarParceiro(key) + ".";

       return parceiro;
    }

    //Carrega o arquivo parceiro.properties para usar os parametros
    public static String  carregarPropriedades(String parametroSolicitado) {
        Properties properties = new Properties();
        String parametro = null;
        try {
            FileInputStream file = new FileInputStream("src\\main\\resources\\parceiros.properties");
            properties.load(file);
            file.close();

            // Acessando os parâmetros
            parametro = properties.getProperty(parametroSolicitado);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parametro;
    }

    // Realiza a consulta se o parceiro tem saldo suficiente. Se positivo, tenta realizar o débito
    private void consulta(Boletos dadosBoleto) {

        var id = gerarIdUnico(dadosBoleto);

        Parceiroconsulta parceiro1 = new Parceiroconsulta();
        parceiro1.setSaldoTotalDisponivel(BigDecimal.valueOf(1000.00));

        var valorDebitar = dadosBoleto.getBoleto().getValorTotal();
        var valorDisponivel = parceiro1.getSaldoTotalDisponivel();

        if (valorDisponivel.doubleValue() >= valorDebitar.doubleValue()) {

            try {
                debitar(dadosBoleto);

            }catch (RuntimeException e){
                System.out.println("Erro ao realizar o débito do cliente");
            }

        } else {
            throw new RuntimeException("Saldo Insuficiente!");
        }

    }

    // Gera o IdUnico, concatenando a agencia, conta e DAC do parceiro, pegando os valores do arquivo parceiro.properties
    private static String gerarIdUnico(Boletos dadosBoleto) {
        String parceiro = getStringParceiro(dadosBoleto);

        var x = "";
        var agencia = carregarPropriedades(parceiro + "agencia");
        var conta = carregarPropriedades(parceiro + "conta");
        var dac = carregarPropriedades(parceiro + "dac");

        String idUnico = x.concat(agencia).concat(conta).concat(dac);

        return idUnico;
    }

    // realiza o débito do saldo do parceiro
    private void debitar(Boletos dadosBoleto) {

        ParceiroDebito parceiroDebito = new ParceiroDebito();
        parceiroDebito.setNome(carregarPropriedades(getStringParceiro(dadosBoleto).concat("nome")));
        parceiroDebito.setEmComum1(carregarPropriedades("parceiros.primeiro"));
        parceiroDebito.setEmComum2(carregarPropriedades("parceiros.segundo"));
        parceiroDebito.setIdUnicoTransacao(gerarIdUnico(dadosBoleto));
        parceiroDebito.setDescricao(carregarPropriedades(getStringParceiro(dadosBoleto).concat("descricao")));

        try {

            // CHAMAR O SERVIÇO QUE REALIZA O DEBITO DO BOLETO

        }catch (RuntimeException e){
            System.out.println("Deu ruim ! :(");

        }

        System.out.println(parceiroDebito.getNome());
        System.out.println(parceiroDebito.getEmComum1());
        System.out.println(parceiroDebito.getEmComum2());
        System.out.println(parceiroDebito.getIdUnicoTransacao());
        System.out.println(parceiroDebito.getDescricao());
    }

    // Pega os dados do parceiro
    private static String getStringParceiro(Boletos dadosBoleto) {
        var parceiro = montarParceiroEncontrado(dadosBoleto.getPartner().getCpfCnpj());
        return parceiro;
    }

}
