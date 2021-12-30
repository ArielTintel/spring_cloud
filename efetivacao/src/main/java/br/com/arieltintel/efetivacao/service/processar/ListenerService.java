package br.com.arieltintel.efetivacao.service.processar;

import br.com.arieltintel.efetivacao.gateway.json.CompraChaveJson;
import br.com.arieltintel.efetivacao.gateway.json.CompraFinalizadaJson;
import br.com.arieltintel.efetivacao.service.banco.BancoService;
import br.com.arieltintel.efetivacao.service.banco.PagamentoRetorno;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ListenerService {

    @Autowired
    private BancoService bancoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.entrada}")
    private String nomeFilaRepublicar;

    @Value("${fila.finalizado}")
    private String nomeFilaFinalizado;

    @HystrixCommand(fallbackMethod = "republicOnMessage")
    @RabbitListener(queues = "${fila.entrada}")
    public void onMessage(Message message) throws JsonParseException, JsonMappingException, IOException {

        String json = new String(message.getBody(), "UTF-8");

        System.out.println("Mensagem recebida:" + json);

        ObjectMapper objectMapper = new ObjectMapper();
        CompraChaveJson compraChaveJson = objectMapper.readValue(json, CompraChaveJson.class);
        PagamentoRetorno pagamentoRetorno = bancoService.pagar(compraChaveJson);

        CompraFinalizadaJson compraFinalizadaJson = new CompraFinalizadaJson();
        compraFinalizadaJson.setCompraChaveJson(compraChaveJson);
        compraFinalizadaJson.setPagamentoOK(pagamentoRetorno.isPagamentoOK());
        compraFinalizadaJson.setMensagem(pagamentoRetorno.getMensagem());

        ObjectMapper objMapper = new ObjectMapper();
        String jsonFinalizado = objMapper.writeValueAsString(compraFinalizadaJson);

        rabbitTemplate.convertAndSend(nomeFilaFinalizado, jsonFinalizado);
    }

    public void republicOnMessage(Message message) throws JsonParseException, JsonMappingException, IOException  {
        System.out.println("Republicando mensagem.");
        rabbitTemplate.convertAndSend(nomeFilaRepublicar, message);
    }
}
