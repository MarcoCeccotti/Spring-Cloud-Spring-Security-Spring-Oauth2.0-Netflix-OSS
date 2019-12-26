package it.marco.auth.dao.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.auth.bean.auth.Users;

@Repository
public interface Users_DAO extends JpaRepository<Users, String> {
	
	public Optional<Users> findByEmail(String email);
	
	public Optional<Users> findByUserTagAndEmail(String username, String email);
	
	public Optional<Users> findByIdAndEmail(String username, String email);
}
