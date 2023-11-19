package udemy.curso.securiry.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import udemy.curso.domain.entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

  @Value("${securiry.jwt.expiracao}")
  private String expiracao;

  @Value("${securiry.jwt.chave-assinatura}")
  private String chaveAssinatura;

  public String gerarToken(Usuario usuario) {
    long expString = Long.valueOf(expiracao);
    LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
    Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
    Date data = Date.from(instant);

//    HashMap<String, Object> claims = new HashMap<>();
//    claims.put("emaildousuario", "usuario@gmail.com");
//    claims.put("roles", "admin");

    return Jwts
            .builder()
            .setSubject(usuario.getLogin())
            .setExpiration(data)
            //.setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
            .compact();
  }

  private Claims obterClaims(String token) throws ExpiredJwtException {
    return Jwts.parser()
            .setSigningKey(chaveAssinatura)
            .parseClaimsJws(token)
            .getBody();
  }

  public boolean tokenValido(String token) throws ExpiredJwtException {
    try {
      Claims claims = obterClaims(token);
      Date dataExpiracao = claims.getExpiration();
      LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      return !LocalDateTime.now().isAfter(data);
    } catch (Exception e) {
      return false;
    }
  }

  public String obterLoginUsuario(String token) throws ExpiredJwtException {
    return obterClaims(token).getSubject();
  }
}
