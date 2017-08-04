package com.byteperceptions.graphqldemo.graphql;

import com.byteperceptions.graphqldemo.customer.AllCustomersDataFetcher;
import com.byteperceptions.graphqldemo.customer.SingleCustomerDataFetcher;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright Ryan Ylitalo and BytePerceptions LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@RestController
public class GraphQLQueryController {


    @Autowired
    private AllCustomersDataFetcher allCustomersDataFetcher;

    @Autowired
    private SingleCustomerDataFetcher singleCustomerDataFetcher;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException{
        Resource schemaResource = new ClassPathResource("/demo-schema.graphqls");
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(new InputStreamReader(schemaResource.getInputStream()));
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring(){
        return RuntimeWiring.newRuntimeWiring().type(("Query"), typeWiring -> typeWiring
                .dataFetcher("allCustomers", allCustomersDataFetcher)
                .dataFetcher("customer", singleCustomerDataFetcher))
                .build();
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    public ResponseEntity graphQLQuery(@RequestBody String query){
        ExecutionResult result = graphQL.execute(query);
        if(!result.getErrors().isEmpty()){
            return ResponseEntity.ok(result.getErrors());
        }
        return ResponseEntity.ok(result.getData());
    }

    @RequestMapping(value = "/mutate", method = RequestMethod.POST)
    public ResponseEntity mutate(@RequestBody String mutation){
        ExecutionResult result = graphQL.execute(mutation);
        if(!result.getErrors().isEmpty()){
            return ResponseEntity.ok(result.getErrors());
        }
        return ResponseEntity.ok(result.getData());
    }
}
