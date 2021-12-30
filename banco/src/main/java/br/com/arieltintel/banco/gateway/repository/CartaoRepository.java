package br.com.arieltintel.banco.gateway.repository;

import br.com.arieltintel.banco.domain.Cartao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long>{

    @Query("select count(obj.id) from Cartao obj where obj.codigoSegurancaCartao = ?1 and obj.numeroCartao = ?2")
    Integer findCartaoValido(Integer codigoSegurancaCartao, Integer numeroCartao);

    @Query("select count(obj.id) from Cartao obj where obj.codigoSegurancaCartao = ?1 and obj.numeroCartao = ?2 and obj.valorCredito >= ?3")
    Integer isSaldoSuficiente(Integer codigoSegurancaCartao, Integer numeroCartao, BigDecimal valorCompra);

    @Query("from Cartao obj where obj.codigoSegurancaCartao = ?1 and obj.numeroCartao = ?2")
    Cartao findCartao(Integer codigoSegurancaCartao, Integer numeroCartao);

    @Modifying
    @Query("update Cartao set valorCredito = (valorCredito - ?3) where codigoSegurancaCartao = ?1 and numeroCartao = ?2 ")
    void atualizarSaldo(Integer codigoSegurancaCartao, Integer numeroCartao, BigDecimal valorCompra);
}
