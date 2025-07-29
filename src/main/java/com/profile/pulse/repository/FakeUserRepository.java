package com.profile.pulse.repository;

import com.profile.pulse.model.FakeUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface FakeUserRepository extends ReactiveMongoRepository<FakeUser, String> {
    // Custom query to find users by age range
    Flux<FakeUser> findByAgeBetween(int minAge, int maxAge);
    
    // Custom query to find users with bonus greater than specified value
    Flux<FakeUser> findByBonusGreaterThan(int bonus);

    // Find all users sorted by createdAt descending
    Flux<FakeUser> findAllByOrderByCreatedAtDesc();

    @Query("{ '$or': [ " +
        "{ 'name': { $regex: ?0, $options: 'i' } }, " +
        "{ 'username': { $regex: ?0, $options: 'i' } }, " +
        "{ 'phone': { $regex: ?0, $options: 'i' } }, " +
        "{ 'email': { $regex: ?0, $options: 'i' } }, " +
        "{ 'blood': { $regex: ?0, $options: 'i' } }, " +
        "{ 'company': { $regex: ?0, $options: 'i' } } " +
    "] }")
    Flux<FakeUser> searchUsers(String query);
}