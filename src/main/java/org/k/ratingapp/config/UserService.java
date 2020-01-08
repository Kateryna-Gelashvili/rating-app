package org.k.ratingapp.config;

import com.google.common.collect.ImmutableList;
import org.k.ratingapp.model.User;
import org.k.ratingapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByName(username);
    return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthority());
  }

  private List<SimpleGrantedAuthority> getAuthority() {
    return ImmutableList.of(new SimpleGrantedAuthority("ROLE_USER"));
  }
}