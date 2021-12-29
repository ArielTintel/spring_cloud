package br.com.arieltintel.passagem.gateway.http;

import br.com.arieltintel.passagem.gateway.json.CompraChaveJson;
import br.com.arieltintel.passagem.gateway.json.CompraJson;
import br.com.arieltintel.passagem.gateway.json.RetornoJson;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.saida}")
    private String nomeFila;

    @PostMapping
    public ResponseEntity<RetornoJson> pagamento(@Valid @NotNull @RequestBody CompraJson compraJson) throws Exception {

        CompraChaveJson compraChaveJson = new CompraChaveJson();
        compraChaveJson.setCompraJson(compraJson);
        compraChaveJson.setChave(UUID.randomUUID().toString());

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(compraChaveJson);

        rabbitTemplate.convertAndSend(nomeFila, json);

        RetornoJson retorno = new RetornoJson();
        retorno.setMensagem("Compra registrada com sucesso. Aguarda a confirmação do pagamento.");
        retorno.setChavePesquisa(compraChaveJson.getChave());

        return new ResponseEntity<RetornoJson>(retorno, HttpStatus.OK);
    }
}
