package com.motaharinia.msutility.graphql;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Component;

@Component
@GraphQLApi
public class SkipNoApiError {

    @GraphQLQuery
    public void msutility(){

    }

}
