package com.price.streamwise.consumer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.price.streamwise.consumer.model.Title;
import com.price.streamwise.consumer.repository.TitleRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TitleService {
    private final TitleRepository repo;

    public TitleService(TitleRepository repo) {
        this.repo = repo;
    }

	@CircuitBreaker(name = "consumerService", fallbackMethod = "buildFallbackTitleListCB")
	@RateLimiter(name = "consumerService", fallbackMethod = "buildFallbackTitleListRL")
    public List<Title> findAllTitles() {
        log.info("finding all titles");
        randomlyRunLong();
        return repo.findAll();
    }

    public Title findTitleById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Title addTitle(Title t) {
        return repo.save(t);
    }

    public void deleteTitle(UUID id) {
        repo.deleteById(id);
    }
    
    @SuppressWarnings("unused")
    private List<Title> buildFallbackTitleListCB(Throwable t){
        log.warn("Circuit breaker fallback invoked: {}", t==null ? "no cause" : t.toString());
        List<Title> fallbackList = new ArrayList<>();
        Title title = new Title();
        title.setTitleId(new UUID(0, 0));
        title.setType(null);
        title.setTitleName("Circuit breaker activated, no titles available.");
        fallbackList.add(title);
        return fallbackList;
    }
    
    @SuppressWarnings("unused")
    private List<Title> buildFallbackTitleListRL(Throwable t){
        log.warn("Rate limiter fallback invoked: {}", t==null ? "no cause" : t.toString());
        List<Title> fallbackList = new ArrayList<>();
        Title title = new Title();
        title.setTitleId(new UUID(0, 0));
        title.setType(null);
        title.setTitleName("Rate limit exceeded, no titles available.");
        fallbackList.add(title);
        return fallbackList;
    }

	private void randomlyRunLong(){
		// Random rand = new Random();
		// int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		// if (randomNum==3) sleep();
	}
	private void sleep(){
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
}
