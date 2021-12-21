package br.com.arieltintel.passagem.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetornoJson {

    private String mensagem;
    private String chavePesquisa;

}