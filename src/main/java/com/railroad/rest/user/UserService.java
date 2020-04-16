package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.common.AbstractService;
import com.railroad.rest.common.Repository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class UserService extends AbstractService  {
    @Inject
    Repository<User> query;

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

    public Collection<User> findArtistByServiceProvidedId(Long id, int maxResults, int firstResults){
        return ((UserQuery)query).findArtistByServiceProvidedId(id,maxResults,firstResults);
    }

    public User findUserByEmail(String email) throws NoSuchFieldException {
        return ((UserQuery)query).findUserByEmail(email);
    }
}
