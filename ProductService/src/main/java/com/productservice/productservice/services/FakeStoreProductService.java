package com.productservice.productservice.services;

import com.productservice.productservice.dtos.FakeStoreProductDto;
import com.productservice.productservice.dtos.GenericProductDto;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import com.productservice.productservice.thirdpartyclients.fakestoreclient.FakeStoreClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("fakeStoreProductService")
@Primary
public class FakeStoreProductService implements ProductService {

    private final FakeStoreClient fakeStoreClient;


    FakeStoreProductService(@Qualifier("fakeStoreClient") FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public GenericProductDto getProductById(Long id) throws ProductNotFoundException {
        return convertToGenericProductDto(fakeStoreClient.getProductById(id));
    }

    @Override
    public List<GenericProductDto> getAllProducts() {

        List<GenericProductDto> res = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreClient.getAllProducts()) {
            res.add(convertToGenericProductDto(fakeStoreProductDto));
        }
        return res;
    }

    @Override
    public GenericProductDto deleteProductById(Long id) {
        return convertToGenericProductDto(fakeStoreClient.deleteProductById(id));
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        return convertToGenericProductDto(fakeStoreClient.createProduct(genericProductDto));
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto genericProductDto) {
        return convertToGenericProductDto(fakeStoreClient.updateProductById(id, genericProductDto));
    }

    private static GenericProductDto convertToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        if (Objects.isNull(fakeStoreProductDto)) {
            return genericProductDto;
        }
        genericProductDto.setId(fakeStoreProductDto.getId());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        return genericProductDto;
    }
}
