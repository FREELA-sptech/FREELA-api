package freela.api.FREELAAPI.domain.services.authentication.dto;

import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {

    private final Integer id;
    private final String nome;

    private final String email;

    private final String senha;

    public UsuarioDetalhesDto(Users usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getName();
        this.email = usuario.getEmail();
        this.senha = usuario.getPassword();
    }

    public Integer getId() {return id;}
    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}