package udemy.curso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import udemy.curso.domain.entity.Usuario;
import udemy.curso.domain.exception.SenhaInvalidaException;
import udemy.curso.domain.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private UsuarioRepository repository;

  public Usuario salvar(Usuario usuario) {
    return repository.save(usuario);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = repository.findByLogin(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

    String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};

    return User
            .builder()
            .username(usuario.getLogin())
            .password(usuario.getSenha())
            .roles(roles)
            .build();
  }

  public UserDetails autenticar(Usuario usuario) {
    UserDetails user = loadUserByUsername(usuario.getLogin());
    boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
    if (senhasBatem) {
      return user;
    }

    throw new SenhaInvalidaException("Senha inválida");
  }

}
