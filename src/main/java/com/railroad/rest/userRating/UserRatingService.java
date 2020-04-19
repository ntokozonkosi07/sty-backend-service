package com.railroad.rest.userRating;

import com.railroad.entity.UserRating;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class UserRatingService extends AbstractService {

    @Inject
    private UserRatingQuery rq;

    public Collection<UserRating> getUserRatingsByUserId(Long id, int maxResults, int firstResults){
        return rq.getUserRatingsByUserId(id, maxResults,firstResults);
    }
}
