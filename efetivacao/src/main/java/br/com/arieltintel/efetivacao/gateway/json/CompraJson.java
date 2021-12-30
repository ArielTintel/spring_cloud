package br.com.arieltintel.efetivacao.gateway.json;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraJson {

    private Integer codigoPassagem;
    private Integer numeroCartao;
    private Integer codigoSegurancaCartao;
    private BigDecimal valorPassagem;

}
