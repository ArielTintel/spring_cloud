package br.com.arieltintel.efetivacao.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoJson {

    private Integer numeroCartao;
    private Integer codigoSegurancaCartao;
    private BigDecimal valorCompra;

}