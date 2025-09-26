package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);
}
