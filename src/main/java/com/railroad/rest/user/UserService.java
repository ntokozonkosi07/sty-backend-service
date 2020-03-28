package com.railroad.rest.user;

import com.railroad.entity.*;
import com.railroad.entity.reservation.Reservation;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Optional;

@Stateless
public class UserService extends AbstractService  {
    @Inject
    UserQuery query;

    public Collection<User> getUsers(Integer maxResults, Optional<Integer> firstResults){
        Integer firstRes = this.parameterValidation(maxResults, firstResults);

        return query.findAll(maxResults,firstRes);
    }
    
    public User createUser(User user) throws ConstraintViolationException {
        try {
            if (user.getId() == null) {
                return query.save(user);
            }

            return user;
        }catch(ConstraintViolationException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

	public User findUserById(@NotNull Long id) {
		
		return query.findById(id);
	}

	public User updateUser(@NotNull User user) {
        return query.update(user);
    }

//    public Collection<UserRating> findUserRatingsById(@NotNull Long id, Integer maxResults, Optional<Integer> firstResults){
//        Integer firstRes = this.parameterValidation(maxResults, firstResults);
//
//        return query.findUserRatingsById(id,maxResults, firstRes);
//    }
//
//    public Collection<Reservation> findReservationsById(Long id, Integer maxResults, Optional<Integer> firstResults) {
//        Integer firstRes = this.parameterValidation(maxResults, firstResults);
//        return query.findReservationsById(id,maxResults, firstRes);
//    }
//
//    public Collection<ServiceProvided> findServicesProvidedById(Long id, Integer maxResults, Optional<Integer> firstResults) {
//        Integer firstRes = this.parameterValidation(maxResults, firstResults);
//        return query.findServicesProvidedById(id,maxResults, firstRes);
//    }
//
    public Collection<UserRole> finRolesByUserId(Long id, int maxResults, Optional<Integer> firstResults) {
        Integer firstRes = this.parameterValidation(maxResults, firstResults);
        return query.finRolesByUserId(id,maxResults, firstRes);
    }
}
