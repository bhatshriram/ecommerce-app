package com.productservice.productservice.thirdpartyclients.fakestoreclient;

import com.productservice.productservice.dtos.FakeStoreProductDto;
import com.productservice.productservice.dtos.GenericProductDto;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component("fakeStoreClient")
public class FakeStoreClient {

    private final String fakeStoreUrl;
    private final RestTemplateBuilder restTemplateBuilder;


    FakeStoreClient(RestTemplateBuilder restTemplateBuilder,
                    @Value("${fakestore.api.url}") String fakeStoreAPIUrl,
                    @Value("${fakestore.api.paths.products}") String pathProducts) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreUrl = fakeStoreAPIUrl + pathProducts;
    }

    public FakeStoreProductDto getProductById(Long id) throws ProductNotFoundException {

        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = template.getForEntity(fakeStoreUrl + "/{id}",
                FakeStoreProductDto.class, id);

        // Convert FakeStoreProductDto to GenericProductDto.
        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product with Id " + id + " not found.");
        }

        return fakeStoreProductDto;
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate template = restTemplateBuilder.build();
        // List<FakeStoreProductDto> creates issue so use arrays.
        ResponseEntity<FakeStoreProductDto[]> responseEntity = template.getForEntity(
                fakeStoreUrl, FakeStoreProductDto[].class); // --> We cannot use List.class here so use array.

        return List.of(responseEntity.getBody());
    }

    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate template = restTemplateBuilder.build();
        RequestCallback requestCallback = template.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                template.responseEntityExtractor(FakeStoreProductDto.class);

        ResponseEntity<FakeStoreProductDto> responseEntity = template.execute(fakeStoreUrl + "/{id}",
                HttpMethod.DELETE, requestCallback, responseExtractor, id);

        return responseEntity.getBody();
    }

    public FakeStoreProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = template.postForEntity(
                fakeStoreUrl, genericProductDto, FakeStoreProductDto.class);

        return responseEntity.getBody();
    }

    public FakeStoreProductDto updateProductById(Long id, GenericProductDto genericProductDto) {
        RestTemplate template = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = template.patchForObject(
                fakeStoreUrl + "/{id}", genericProductDto, FakeStoreProductDto.class, id);

        return fakeStoreProductDto;
    }

}
