package br.com.arieltintel.passagem.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraChaveJson {

    private String chave;
    private CompraJson compraJson;

}