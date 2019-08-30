package com.review.Repository;

import com.review.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user_review WHERE tooken = ?56dca109-bfe6-4aff-a6a2-c6cf89afc5cd", nativeQuery = true)
    User findUserByTooken(String tooken);

}
