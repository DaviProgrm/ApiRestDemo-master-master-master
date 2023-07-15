package ufrn.br.apirestdemo.service;

import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.domain.Compra;
import ufrn.br.apirestdemo.repository.CompraRepository;

@Service
public class CompraService extends GenericService<Compra, CompraRepository> {
    public CompraService(CompraRepository repository) {
        super(repository);
    }
}
