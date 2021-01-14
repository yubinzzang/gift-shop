/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-14 11:48:41
 * @modify date 2021-01-14 11:48:41
 * @desc [description]
 */
package com.cg.authservice.repositories;

import java.util.Optional;

import com.cg.authservice.entities.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

  Optional<UserDetails> findByUserUsername(String username);
  
}
