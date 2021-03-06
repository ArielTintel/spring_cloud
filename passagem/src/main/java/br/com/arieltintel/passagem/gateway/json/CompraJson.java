package br.com.arieltintel.passagem.gateway.json;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraJson {

    @NotNull(message = "Código da passagem é obrigatório")
    private Integer codigoPassagem;

    @NotNull(message = "Número do cartão é obrigatório")
    private Integer numeroCartao;

    @NotNull(message = "Código de segurança do cartão é obrigatório")
    private Integer codigoSegurancaCartao;

    @NotNull(message = "Valor da passagem é obrigatório")
    private BigDecimal valorPassagem;

}