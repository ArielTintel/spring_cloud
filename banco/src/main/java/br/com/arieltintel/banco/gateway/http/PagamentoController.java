package br.com.arieltintel.banco.gateway.http;

import br.com.arieltintel.banco.gateway.json.PagamentoJson;
import br.com.arieltintel.banco.gateway.json.RetornoJson;
import br.com.arieltintel.banco.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(name = "Pagamento", path = "/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<RetornoJson> pagamento(@Valid @NotNull @RequestBody PagamentoJson pagamentoJson){

        pagamentoService.pagamento(pagamentoJson);

        RetornoJson retornoJson = new RetornoJson();
        retornoJson.setMensagem("Pagamento Registrado com sucesso.");

        return new ResponseEntity<RetornoJson>(retornoJson, HttpStatus.OK);

    }
}
