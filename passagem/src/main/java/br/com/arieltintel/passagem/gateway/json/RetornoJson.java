package br.com.arieltintel.passagem.gateway.json;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetornoJson {

    private String mensagem;
    private String chavePesquisa;

}