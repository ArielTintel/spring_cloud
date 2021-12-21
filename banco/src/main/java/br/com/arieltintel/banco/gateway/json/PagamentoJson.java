package br.com.arieltintel.banco.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoJson {

    @NotNull(message = "Número do cartão é obrigatório.")
    private Integer nroCartao;

    @NotNull(message = "Código de segurança do cartão é obrigatório.")
    private Integer codigoSegurancaCartao;

    @NotNull(message = "Valor da compra é obrigatório.")
    private BigDecimal valorCompra;
}