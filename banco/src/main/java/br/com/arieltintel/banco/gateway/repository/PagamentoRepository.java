package br.com.arieltintel.banco.gateway.repository;

import br.com.arieltintel.banco.domain.Pagamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends CrudRepository<Pagamento, Long> {
}
