package ufrn.br.apirestdemo.service;

import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.domain.Usuario;
import ufrn.br.apirestdemo.repository.UsuarioRepository;


@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository> {
    public UsuarioService(UsuarioRepository repository) {
        super(repository);
    }
}
