package br.com.arieltintel.banco.service;

import br.com.arieltintel.banco.domain.Pagamento;
import br.com.arieltintel.banco.exceptions.PagamentoException;
import br.com.arieltintel.banco.gateway.json.PagamentoJson;
import br.com.arieltintel.banco.gateway.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CartaoService cartaoService;

    @Transactional
    public void pagamento(PagamentoJson pagamentoJson){

        if (!cartaoService.isValido(pagamentoJson.getCodigoSegurancaCartao(),pagamentoJson.getNroCartao() )){
            throw new PagamentoException("Cartão inválido.");
        }

        if( !cartaoService.isSaldoSuficiente(pagamentoJson.getCodigoSegurancaCartao(), pagamentoJson.getNroCartao(), pagamentoJson.getValorCompra()) ){
            throw new PagamentoException("Cartão não possui saldo suficiente.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setValorCompra(pagamentoJson.getValorCompra());
        pagamento.setCartao(cartaoService.getCartao(pagamentoJson.getCodigoSegurancaCartao(), pagamentoJson.getNroCartao()));

        pagamentoRepository.save(pagamento);

        cartaoService.atualizarSaldo(pagamentoJson.getCodigoSegurancaCartao(), pagamentoJson.getNroCartao(), pagamentoJson.getValorCompra());

    }
}
