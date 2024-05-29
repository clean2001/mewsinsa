package com.mewsinsa.product.service;

import com.mewsinsa.global.file.FileStoreBean;
import com.mewsinsa.product.controller.dto.AddProductOptionDto;
import com.mewsinsa.product.controller.dto.AddProductRequestDto;
import com.mewsinsa.product.controller.dto.AddProductOptionRequestDto;
import com.mewsinsa.product.controller.dto.UpdateProductOptionRequestDto;
import com.mewsinsa.product.controller.dto.UpdateProductRequestDto;
import com.mewsinsa.product.domain.Product;
import com.mewsinsa.product.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final FileStoreBean fileStoreBean;

  //==Constructor==//
  public ProductService(ProductRepository productRepository, FileStoreBean fileStoreBean) {
    this.productRepository = productRepository;
    this.fileStoreBean = fileStoreBean;
  }
  //==Constructor 끝==//


  /**
   * @param product 등록하려는 상품 정보
   */
  @Transactional
  public void addProduct(AddProductRequestDto productDto) {

    String thumbnailImageUrl = "";
    try {
      // 상품 정보를 등록
      Product product = new Product(
          productDto.getProductName(), productDto.getBrandId(),
          productDto.getCategory(), productDto.getSubcategory(),
          productDto.getOriginalPrice(), 0L, 0L, thumbnailImageUrl
      );

      productRepository.addProduct(product);

      // 키값을 가져오기
      Long productId = product.getProductId();

      // 상품 옵션 정보를 등록
      List<AddProductOptionRequestDto> productOptions = productDto.getProductOptionList();

      for(AddProductOptionRequestDto productOption : productOptions) {
        AddProductOptionDto productOptionDto = new AddProductOptionDto(productOption.getProductOptionName(), productId, productOption.getStock());
        productRepository.addProductOption(productOptionDto);
      }

    } catch(Exception e) {
      throw new IllegalArgumentException("상품 등록에 실패하였습니다.", e);
    }
  }

  public void updateProduct(UpdateProductRequestDto product) {
    try {
      productRepository.updateProduct(product);
    } catch(Exception e) {
      throw new IllegalArgumentException("상품 수정에 실패하였습니다.", e);
    }
  }


  /**
   * @param productOption 수정하려는 옵션 정보
   */
  public void updateProductOption(UpdateProductOptionRequestDto productOption) {
    try {
      productRepository.updateProductOption(productOption);
    } catch(Exception e) {
      throw new IllegalArgumentException("상품 옵션 수정에 실패하였습니다.", e);
    }
  }

  public void deleteProductOption(Long productOptionId) {
    try {
      productRepository.deleteProductOption(productOptionId);
    } catch(Exception e) {
      throw new IllegalArgumentException("상품 옵션 삭제에 실패하였습니다.", e);
    }

  }

  public void deleteProduct(Long productId) {
    try {
      // ON DELETE CASCADE로 설정하여, 옵션도 함께 지워집니다.
      productRepository.deleteProduct(productId);
    } catch(Exception e) {
      throw new IllegalArgumentException("상품 삭제에 실패하였습니다.", e);
    }
  }

  // TODO: 썸네일 이미지 저장 구현
  public AddProductRequestDto saveProductThumbnailImage(
      MultipartFile thumbnailImage,
      AddProductRequestDto product) throws IOException {
    String thumbnailUrl = fileStoreBean.storeFile(thumbnailImage);
    product.setproductThumbnailImageUrl(thumbnailUrl);

    return product;
  }

}
