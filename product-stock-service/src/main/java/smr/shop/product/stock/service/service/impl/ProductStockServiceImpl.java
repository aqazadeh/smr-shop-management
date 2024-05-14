package smr.shop.product.stock.service.service.impl;

import org.springframework.stereotype.Service;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.GetProductStockResponse;
import smr.shop.product.stock.service.helper.ProductStockServiceHelper;
import smr.shop.product.stock.service.mapper.ProductStockServiceMapper;
import smr.shop.product.stock.service.model.ProductStock;
import smr.shop.product.stock.service.repository.ProductStockRepository;
import smr.shop.product.stock.service.service.ProductStockService;

import java.util.List;
import java.util.UUID;


@Service
public class ProductStockServiceImpl implements ProductStockService {
    private final ProductStockRepository productStockRepository;
    private final ProductStockServiceMapper productStockServiceMapper;
    private final ProductStockServiceHelper productStockServiceHelper;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository,
                                   ProductStockServiceMapper productStockServiceMapper,
                                   ProductStockServiceHelper productStockServiceHelper) {
        this.productStockRepository = productStockRepository;
        this.productStockServiceMapper = productStockServiceMapper;
        this.productStockServiceHelper = productStockServiceHelper;
    }

    @Override
    public CreateProductStockRequest create(CreateProductStockRequest request) {
        ProductStock productStock = productStockServiceMapper.mapToProductStock(request);
        productStockRepository.save(productStock);
        return request;
    }

    @Override
    public List<CreateProductStockRequest> createAll(List<CreateProductStockRequest> productStockRequests) {
        List<ProductStock> productStocks = productStockRequests.stream()
                .map(productStockServiceMapper::mapToProductStock).toList();
        productStockRepository.saveAll(productStocks);
        return productStockRequests;
    }

    @Override
    public GetProductStockResponse getById(UUID id) {
        ProductStock productStock = productStockServiceHelper.getById(id);
        return productStockServiceMapper.mapToResponse(productStock);
    }

    @Override
    public GetProductStockResponse getByProductId(Long productId) {
        ProductStock productStock = productStockServiceHelper.getByProductId(productId);
        return productStockServiceMapper.mapToResponse(productStock);
    }

    @Override
    public void deleteById(UUID id) {
        productStockServiceHelper.getById(id);
        productStockRepository.deleteById(id);
    }

    @Override
    public void updateById(UUID id, UpdateProductStockRequest request) {
        ProductStock productStock = productStockServiceHelper.getById(id);
        productStockServiceMapper.mapForUpdate(productStock, request);
        productStockRepository.save(productStock);
    }
}