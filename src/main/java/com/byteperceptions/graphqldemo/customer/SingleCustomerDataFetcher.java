package com.byteperceptions.graphqldemo.customer;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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
@Component
public class SingleCustomerDataFetcher implements DataFetcher<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer get(DataFetchingEnvironment environment) {
        Map arguments = environment.getArguments();
        return customerRepository.findOne(Long.parseLong(arguments.get("id").toString()));
    }
}
