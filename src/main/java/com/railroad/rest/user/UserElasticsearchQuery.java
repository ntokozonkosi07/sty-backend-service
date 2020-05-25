package com.railroad.rest.user;

import com.railroad.entity.User;
import com.railroad.rest.common.elasticsearch.ElasticsearchRepository;

import javax.ejb.Stateless;

@Stateless
public class UserElasticsearchQuery extends ElasticsearchRepository<User> {
}
