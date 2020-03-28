package com.railroad.rest.userRating;

import com.railroad.entity.UserRating;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
public class UserRatingQuery extends Repository<UserRating> {

    public UserRatingQuery() {
        super(UserRating.class);
    }

    @Override
    public Collection<UserRating> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults, firstResults, UserRating.FIND_ALL_USER_RATINGS);
    }

    @Override
    public UserRating findById(Long id) {
        return this.getSingleResults(new HashMap<String, Long>(){{ put("id", id); }}, UserRating.FIND_USER_RATING_BY_ID);
    }

    public Collection<UserRating> getUserRatingsByUserId(Long id, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Long>(){{ put("id", Long.valueOf(id)); }}, maxResults,firstResults, UserRating.FIND_USER_RATING_BY_RATEE_ID);
    }
}
