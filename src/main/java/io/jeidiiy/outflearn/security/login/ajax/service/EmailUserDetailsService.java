package io.jeidiiy.outflearn.security.login.ajax.service;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jeidiiy.outflearn.security.login.ajax.exception.EmailNotFoundException;
import io.jeidiiy.outflearn.security.login.ajax.domain.LoginUser;
import io.jeidiiy.outflearn.user.domain.User;
import io.jeidiiy.outflearn.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
		return LoginUser.of(user.getId(), user.getEmail(), user.getPassword(),
			Set.of(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
