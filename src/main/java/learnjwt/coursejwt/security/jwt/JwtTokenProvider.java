package learnjwt.coursejwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import learnjwt.coursejwt.exception.errors.InvalidJwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    // esses values são usados para pegar valores do aplication.properties
    @Value("${jwt.secret:secret}")
    private String secretKey = "secret";
    @Value("${jwt.expiration:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private UserDetailsService userDetailsService;

    // @PostConstruct é uma anotação para fazer algo após a chamada do construtor
    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // função que serve para criar um novo token, entenderemos passo-a-passo
    // para a criação do token, precisamos de um username e de uma lista de papéis
    // aos quais já implementamos
    public String createToken(String userName, List<String> roles) {
        // passo 1
        // criar os claims passando o username
        Claims claims = Jwts.claims().setSubject(userName);
        // setar os papeis nesse claim
        claims.put("roles", roles);

        // passo 2
        // criar a data atual
        Date now = new Date();
        // criar uma variavel que é o momento de vencimento do token, ou seja
        // agora + tempo de validade do token
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        // então é só retornar um Jwts
        // setando os claims, o issuer é a data atual
        // expiration é o momemento em que o token expira
        // signWith assina a com a chave secreta criptografada com o algoritmo que se desejar
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(now.getTime()+"")
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    // essa função recupera um username de um token
    private String getUsername(String token) {
        // calma calma, essa parte é fodaaaaaaaa
        // Jwts.parser().setSigningKey(secrecKey) seta a secret do Jwt
        // a linha com .parseClaimsJws(token) pega o token, a próxima linha pega o body
        // e a ultima linha é genial, pega o username dentro do body
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // função para apenas pegar o token do header da req
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    // função para validar um token
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            if(claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or inválid token");
        }
    }
}
