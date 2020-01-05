package it.marco.auth.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.marco.auth.bean.auth.User_banned;

@Repository
public interface User_Banned_DAO extends JpaRepository<User_banned, Integer> {

	List<User_banned> findByReason(String reason);
}
