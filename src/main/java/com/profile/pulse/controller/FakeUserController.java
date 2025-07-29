package com.profile.pulse.controller;

import com.profile.pulse.model.FakeUser;
import com.profile.pulse.service.FakeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FakeUserController {
    private final FakeUserService userService;
    
    // SSE endpoint
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FakeUser> streamUsers(
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Integer minBonus,
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) Double minWeight) {
        
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(tick -> userService.fetchAndTransformUserData())
                .flatMap(user -> {
                    boolean shouldSave = true;

                    if (minAge != null && user.getAge() < minAge) {
                        shouldSave = false;
                    }

                    if (maxAge != null && user.getAge() > maxAge) {
                        shouldSave = false;
                    }

                    if (minBonus != null && user.getBonus() < minBonus) {
                        shouldSave = false;
                    }

                    if (bloodGroup != null && !bloodGroup.isBlank() && (user.getBlood() == null || !user.getBlood().equalsIgnoreCase(bloodGroup))) {
                        shouldSave = false;
                    }

                    if (minWeight != null && user.getWeight() < minWeight) {
                        shouldSave = false;
                    }

                    if(minAge == null && maxAge == null && bloodGroup == null && minBonus == null && minWeight == null) {
                        shouldSave = false;
                    }

                    if (shouldSave) {
                        return userService.saveUser(user);
                    } else {
                        return Mono.just(user);
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("Error in stream: " + e.getMessage());
                    // Return an error event
                    return Mono.just(new FakeUser());
                });
    }
    
    // CRUD endpoints
    @PostMapping
    public Mono<FakeUser> createUser(@RequestBody FakeUser user) {
        return userService.saveUser(user);
    }
    
    @GetMapping
    public Flux<FakeUser> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public Mono<FakeUser> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<FakeUser> updateUser(@PathVariable String id, @RequestBody FakeUser user) {
        return userService.updateUser(id, user);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
    
    @DeleteMapping
    public Mono<Void> deleteAllUsers() {
        return userService.deleteAllUsers();
    }
    
    // Custom query endpoints
    @GetMapping("/age-range")
    public Flux<FakeUser> getUsersByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        return userService.getUsersByAgeRange(min, max);
    }
    
    @GetMapping("/bonus")
    public Flux<FakeUser> getUsersWithBonusGreaterThan(
            @RequestParam int minBonus) {
        return userService.getUsersWithBonusGreaterThan(minBonus);
    }

    @GetMapping("/search")
    public Flux<FakeUser> searchUsers(@RequestParam String q) {
        return userService.searchUsers(q);
    }
}