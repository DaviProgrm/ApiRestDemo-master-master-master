package ufrn.br.apirestdemo.repository;

import ufrn.br.apirestdemo.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends IGenericRepository<Usuario> {

    Optional<Usuario> findByUsuario(String usuario);

}
