package smr.shop.discount.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.exception.DiscountException;
import smr.shop.discount.service.mapper.DiscountServiceMapper;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.discount.service.repository.DiscountRepository;
import smr.shop.discount.service.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountServiceMapper discountServiceMapper;
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountServiceMapper discountServiceMapper, DiscountRepository discountRepository) {
        this.discountServiceMapper = discountServiceMapper;
        this.discountRepository = discountRepository;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public void createDiscount(DiscountCreateRequest request) {
        DiscountEntity discountEntity = discountServiceMapper.discountCreateRequestToDiscountEntity(request);
        discountRepository.save(discountEntity);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    public void updateDiscount(Long discountId, DiscountUpdateRequest request) {
        DiscountEntity discountEntity= findById(discountId);
        DiscountEntity updatedDiscountEntity = discountServiceMapper.discountUpdateRequestToDiscountEntity(request, discountEntity);
        discountRepository.save(updatedDiscountEntity);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteDiscount(Long discountId) {
        discountRepository.delete(findById(discountId));
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public DiscountResponse getDiscountById(Long id) {
        DiscountEntity discountEntity = findById(id);

        return discountServiceMapper.discountEntityToDiscountResponse(discountEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public DiscountEntity findById(Long id) {
        return discountRepository.findById(id).orElseThrow(() -> new DiscountException("Discount Not found With id : " + id, HttpStatus.NOT_FOUND));
    }
}
