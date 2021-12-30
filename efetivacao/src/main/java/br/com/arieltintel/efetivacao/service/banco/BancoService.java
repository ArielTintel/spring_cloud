package br.com.arieltintel.efetivacao.service.banco;

import br.com.arieltintel.efetivacao.gateway.json.BancoRetornoJson;
import br.com.arieltintel.efetivacao.gateway.json.CompraChaveJson;
import br.com.arieltintel.efetivacao.gateway.json.PagamentoJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class BancoService {

    @Value("${banco.link}")
    private String link;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PagamentoRetorno pagar(CompraChaveJson compraChaveJson) throws IOException {

        PagamentoJson pagamentoJson = new PagamentoJson();
        pagamentoJson.setNumeroCartao(compraChaveJson.getCompraJson().getNumeroCartao());
        pagamentoJson.setCodigoSegurancaCartao(compraChaveJson.getCompraJson().getCodigoSegurancaCartao());
        pagamentoJson.setValorCompra(compraChaveJson.getCompraJson().getValorPassagem());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PagamentoJson> entity = new HttpEntity<PagamentoJson>(pagamentoJson, httpHeaders);

        try {
            ResponseEntity<BancoRetornoJson> bancoRetorno = restTemplate.exchange(link, HttpMethod.POST, entity, BancoRetornoJson.class);
            return new PagamentoRetorno(bancoRetorno.getBody().getMensagem(), true);
        }catch(HttpClientErrorException ex){
            if( ex.getStatusCode() == HttpStatus.BAD_REQUEST ) {
                ObjectMapper mapper = new ObjectMapper();
                BancoRetornoJson obj = mapper.readValue(ex.getResponseBodyAsString(), BancoRetornoJson.class);
                return new PagamentoRetorno(obj.getMensagem(), false);
            }
            throw ex;
        }catch (RuntimeException ex) {
            throw ex;
        }

    }
}