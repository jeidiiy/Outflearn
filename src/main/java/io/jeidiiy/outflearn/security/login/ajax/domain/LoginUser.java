package io.jeidiiy.outflearn.security.login.ajax.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jeidiiy.outflearn.user.domain.User;
import lombok.Getter;

@Getter
public class LoginUser implements UserDetails {
	private final Long id;
	private final String email;
	private final String nickname;
	private final String password;
	private final boolean isAccountNonExpired = true;
	private final boolean isAccountNonLocked = true;
	private final boolean isCredentialsNonExpired = true;
	private final boolean isEnabled = true;
	private final Collection<? extends GrantedAuthority> authorities;

	private LoginUser(Long id, String email, String nickname, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.authorities = authorities;
	}

	public static LoginUser from(User user, Collection<? extends GrantedAuthority> authorities) {
		return new LoginUser(user.getId(), user.getEmail(), user.getNickname(), user.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
