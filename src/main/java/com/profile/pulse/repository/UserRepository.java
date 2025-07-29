package com.profile.pulse.repository;

import com.profile.pulse.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface FakeUserRepository extends ReactiveMongoRepository<User, String> {
    // Custom query to find users by age range
    Flux<User> findByAgeBetween(int minAge, int maxAge);
    
    // Custom query to find users with bonus greater than specified value
    Flux<User> findByBonusGreaterThan(int bonus);

    // Find all users sorted by createdAt descending
    Flux<User> findAllByOrderByCreatedAtDesc();

    @Query("{ '$or': [ " +
        "{ 'name': { $regex: ?0, $options: 'i' } }, " +
        "{ 'username': { $regex: ?0, $options: 'i' } }, " +
        "{ 'phone': { $regex: ?0, $options: 'i' } }, " +
        "{ 'email': { $regex: ?0, $options: 'i' } }, " +
        "{ 'blood': { $regex: ?0, $options: 'i' } }, " +
        "{ 'company': { $regex: ?0, $options: 'i' } } " +
    "] }")
    Flux<User> searchUsers(String query);
}