package com.eldorado.elasticsearch.dao;

import com.eldorado.elasticsearch.bean.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductDao {

    private final String INDEX = "productdata";
    private final String TYPE = "products";

    private final RestHighLevelClient restHighLevelClient;

    private final ObjectMapper objectMapper;

    public ProductDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public Product insertProduct(Product product){
        product.setProductId(UUID.randomUUID().toString());
        IndexRequest indexRequest;
        indexRequest = new IndexRequest(INDEX, TYPE, product.getProductId()).source(objectMapper.convertValue(product, Map.class));
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex){
            ex.getLocalizedMessage();
        }
        return product;
    }

    public Map<String, Object> getProductById(String id){
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
            System.out.println("getResponse - " + getResponse);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        assert getResponse != null;
        return getResponse.getSourceAsMap();
    }

    public Map<String, Object> updateProductById(String id, Product product){
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
                .fetchSource(true);    // Fetch Object after its update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update product");
        try {
            String productJson = objectMapper.writeValueAsString(product);
            updateRequest.doc(productJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            return updateResponse.getGetResult().sourceAsMap();
        }catch (JsonProcessingException e){
            e.getMessage();
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        return error;
    }

    public void deleteProductById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
    }

}
