package ufrn.br.apirestdemo.service;

import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.repository.ProdutoRepository;

@Service
public class ProdutoService extends GenericService<Produto, ProdutoRepository> {
    public ProdutoService(ProdutoRepository repository) {
        super(repository);
    }
}
