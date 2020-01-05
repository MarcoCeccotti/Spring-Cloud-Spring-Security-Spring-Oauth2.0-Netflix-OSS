package it.marco.gateway.service.gateway;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import it.marco.auth.bean.auth.User_banned;
import it.marco.auth.dao.auth.User_Banned_DAO;
import it.marco.marco.utils.utilities.AttemptsUtils;

@Service
public class BlackListServiceImpl implements BlackListService {
	
	@Inject
	private User_Banned_DAO users_Banned_DAO;

	@Inject
	private AttemptService attemptService;
	
	private Map<Integer, User_banned> blackList;
	private Map<Integer, User_banned> auxBlackList;
	
	@PostConstruct
	public void init() {
		blackList = new ConcurrentHashMap<Integer, User_banned>();
		auxBlackList = new ConcurrentHashMap<Integer, User_banned>();
	}
	
	@Override
	public boolean isBanned(String ip, String username) {
		
		// prima controlla nella black list
		Optional<User_banned> user_banned = blackList.values().stream().filter(v -> 
							(v.getIp() != null && v.getIp().equals(ip)) || (v.getUsername() != null && v.getUsername().equals(username)))
							.findFirst();

		// se non è presente nella black list controlla in quella ausiliaria
		if(!user_banned.isPresent()) {
			return (user_banned = auxBlackList.values().stream().filter(v -> 
						(v.getIp() != null && v.getIp().equals(ip)) || (v.getUsername() != null && v.getUsername().equals(username)))
						.findFirst()).isPresent();
		}
		
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addBanned(String username, String token, String ip, String reason) {
		
		User_banned user = new User_banned(username, token, ip, reason);
		
		auxBlackList.put(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE), user);
		users_Banned_DAO.save(user);
	}

	@Override
	public void firstRetrieve() {
		
		for (User_banned user : users_Banned_DAO.findAll()) {
			blackList.put(user.getId(), user);
		}
	}

	@Override
	public void executeRoutine() {
		
		for (User_banned user : users_Banned_DAO.findAll()) {
			auxBlackList.put(user.getId(), user);
		}
		
		MapDifference<Integer, User_banned> differences = Maps.difference(blackList, auxBlackList);
		
		for (Map.Entry<Integer, User_banned> entry : differences.entriesOnlyOnRight().entrySet()) {
			blackList.put(entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<Integer, User_banned> entry : differences.entriesOnlyOnLeft().entrySet()) {
			if (entry.getValue().getIp() != null) {
				attemptService.loginSucceeded(entry.getValue().getIp());
			}
			blackList.remove(entry.getKey());
		}
		
		blackList.clear();
		blackList.putAll(auxBlackList);
		auxBlackList.clear();
	}
	
	@Override
	public void checkToUnban() {
		
		List<User_banned> users_banned = users_Banned_DAO.findByReason(AttemptsUtils.EXPIRED);
		
		for (User_banned user_banned : users_banned) {
			if (new Timestamp(System.currentTimeMillis() + 30000L).after(user_banned.getTime())) {
				users_Banned_DAO.delete(user_banned);
			}
		}
	}
}