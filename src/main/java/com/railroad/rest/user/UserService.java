package com.railroad.rest.user;

import com.railroad.entity.Reservation;
import com.railroad.entity.User;
import com.railroad.entity.UserRating;
import com.railroad.entity.serviceProvided.ServiceProvided;
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

        return query.getUsers(maxResults,firstRes);
    }
    
    public User createUser(User user) throws ConstraintViolationException {
        try {
            if (user.getId() == null) {
                return query.createUser(user);
            }

            return user;
        }catch(ConstraintViolationException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

	public User findUserById(@NotNull Long id) {
		
		return query.findUserById(id);
	}

	public User updateUser(@NotNull User user) {
        return query.updateUser(user);
    }

    public User findCollectionById(Long id){
        return query.findUserById(id);
    }

    public Collection<UserRating> findUserRatingsById(@NotNull Long id, Integer maxResults, Optional<Integer> firstResults){
        Integer firstRes = this.parameterValidation(maxResults, firstResults);

        return query.findUserRatingsById(id,maxResults, firstRes);
    }

    public Collection<Reservation> findReservationsById(Long id, Integer maxResults, Optional<Integer> firstResults) {
        Integer firstRes = this.parameterValidation(maxResults, firstResults);
        return query.findReservationsById(id,maxResults, firstRes);
    }

    public Collection<ServiceProvided> findServicesProvidedById(Long id, Integer maxResults, Optional<Integer> firstResults) {
        Integer firstRes = this.parameterValidation(maxResults, firstResults);
        return query.findServicesProvidedById(id,maxResults, firstRes);
    }
}
