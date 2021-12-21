package br.com.arieltintel.banco.service;

import br.com.arieltintel.banco.domain.Cartao;
import br.com.arieltintel.banco.gateway.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Boolean isValido(Integer codigoSegurancaCartao, Integer numeroCartao){
        return cartaoRepository.findCartaoValido(codigoSegurancaCartao, numeroCartao) > 0;
    }

    public Boolean isSaldoSuficiente(Integer codigoSegurancaCartao, Integer numeroCartao, BigDecimal valorCompra){
        return cartaoRepository.isSaldoSuficiente(codigoSegurancaCartao, numeroCartao, valorCompra) > 0;
    }

    public Cartao getCartao(Integer numeroCartao, Integer codigoSegurancaCartao){
        return cartaoRepository.findCartao(codigoSegurancaCartao, numeroCartao);
    }

    public void atualizarSaldo(Integer codigoSegurancaCartao, Integer numeroCartao, BigDecimal valorCompra){
        cartaoRepository.atualizarSaldo(codigoSegurancaCartao, numeroCartao, valorCompra);
    }

}
