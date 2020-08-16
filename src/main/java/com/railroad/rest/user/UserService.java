package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.common.AbstractService;
import com.railroad.rest.common.Repository;
import com.railroad.rest.common.elasticsearch.ElasticsearchRepository;
import lombok.var;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Optional;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UserService extends AbstractService  {
    @Inject
    Repository<User> query;

    @Inject
    ElasticsearchRepository<User> elasticSearchRepo;

    @Inject
    UserTransaction tx;

    public Collection<User> getUsers(Integer maxResults, Optional<Integer> firstResults){
        Integer firstRes = this.parameterValidation(maxResults, firstResults);

        return query.findAll(maxResults,firstRes);
    }
    
    public User createUser(User user) throws Exception {
        try {
            tx.begin();
            if (user.getId() == null) {
                User _user = query.save(user);

//                throw new Exception();
//                elasticSearchRepo.insertDoc(_user);
                tx.commit();
                return _user;
            }

            return user;
        }catch(Exception e) {
            tx.rollback();
            throw e;
        }
    }

	public User findUserById(@NotNull Long id) {
		return query.findById(id);
	}

	public String searchUser(String query) throws IOException, NoSuchAlgorithmException, URISyntaxException {
        var response = elasticSearchRepo.searchDocs(query);
        return response;
    }

	public User updateUser(@NotNull User user) throws Exception {
        User _user = query.update(user);
        elasticSearchRepo.updateDoc(_user);
        return _user;
    }

    public Collection<User> findArtistByServiceProvidedId(Long id, int maxResults, int firstResults){
        return ((UserQuery)query).findArtistByServiceProvidedId(id,maxResults,firstResults);
    }

    public User findUserByEmail(String email) throws NoSuchFieldException {
        return ((UserQuery)query).findUserByEmail(email);
    }
}
