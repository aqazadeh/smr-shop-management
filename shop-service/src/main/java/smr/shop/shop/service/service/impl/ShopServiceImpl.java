package smr.shop.shop.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.shop.service.dto.request.*;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.exception.ShopException;
import smr.shop.shop.service.mapper.ShopMapper;
import smr.shop.shop.service.model.Shop;
import smr.shop.shop.service.model.valueobject.ShopAddress;
import smr.shop.shop.service.repository.ShopRepository;
import smr.shop.shop.service.service.ShopService;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;
    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopMapper shopMapper, ShopRepository shopRepository) {
        this.shopMapper = shopMapper;
        this.shopRepository = shopRepository;
    }

    @Override
    public ShopResponse createShop(CreateShopRequest request) {
        Shop shop = shopMapper.toShop(request);
        shop = shopRepository.save(shop);
        shop.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        shop.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse updateShop(Long id, UpdateShopRequest request) {
        Shop shop = findById(id);
        Shop updateShop = shopMapper.toUpdateShop(request, shop);
        shop = shopRepository.save(updateShop);
        shop.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public void deleteShop(Long id) {
        Shop shop = findById(id);
        shop.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        shopRepository.delete(shop);
    }

    @Override
    public List<ShopResponse> getAllShop(Integer page) {
        Pageable pageable = (Pageable) PageRequest.of(page, ServiceConstants.pageSize);
        return shopRepository.findAll((org.springframework.data.domain.Pageable) pageable).map(shopMapper::toShopResponse).toList();
    }

    @Override
    public ShopResponse getShopById(Long id) {
        Shop shop = findById(id);
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse updateShopStatus(Long id, UpdateShopStatusRequest request) {
        Shop shop = findById(id);
        shop.setStatus(request.getStatus());
        shop = shopRepository.save(shop);
        shop.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopAddressResponse updateShopAddress(Long id, UpdateShopAddressRequest request) {
        Shop shop = findById(id);
        ShopAddress shopAddress = shop.getAddress();
        shopMapper.toUpdateShopAddress(request, shopAddress);
        shop = shopRepository.save(shop);
        shop.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        return shopMapper.toShopAddressResponse(shopAddress);
    }

    @Override
    public ShopAddressResponse getShopAddressById(Long id, Long addressId) {
        Shop shop = findById(id);
        ShopAddress address = shop.getAddress();
        return shopMapper.toShopAddressResponse(address);
    }

    @Override
    public Shop findById(Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ShopException("Shop Not Found With Id:" + id, HttpStatus.NOT_FOUND));
    }
}
