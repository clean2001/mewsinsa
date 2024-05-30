package com.mewsinsa.product.controller;

import com.mewsinsa.auth.jwt.interceptor.Auth;
import com.mewsinsa.global.response.DetailedStatus;
import com.mewsinsa.global.response.SuccessResult;
import com.mewsinsa.global.response.SuccessResult.Builder;
import com.mewsinsa.product.controller.dto.AddProductRequestDto;
import com.mewsinsa.product.controller.dto.UpdateProductOptionRequestDto;
import com.mewsinsa.product.controller.dto.UpdateProductRequestDto;
import com.mewsinsa.product.service.ProductService;
import jakarta.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/products")
@RestController
public class ProductController {
  private final ProductService productService;

  //==Constructor==//
  public ProductController(ProductService productService) {
    this.productService = productService;
  }
  //==Constructor 끝==//

  /**
   * 상품 등록
   * @param product 등록하려는 상품 정보
   * @return SuccessResponse
   */
//  @Auth
  @PostMapping
  ResponseEntity<SuccessResult> addProduct(
      @Validated @RequestPart(value="product") AddProductRequestDto product,
      @RequestPart(value="thumbnail", required = false) MultipartFile thumbnailImage,
      @RequestPart(value="productImages", required = false) List<MultipartFile> productImages
  ) throws IOException {
    // TODO: 썸네일 사진을 스토리지에 저장
    product = productService.saveProductThumbnailImage(thumbnailImage, product);


    // TODO: 다른 상품 사진들을 스토리지에 저장



    productService.addProduct(product);
    SuccessResult result = new Builder(DetailedStatus.CREATED)
        .message("상품이 성공적으로 등록 되었습니다.")
        .data(product)
        .build();

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * @param product 수정하려는 상품 정보
   * @return SuccessResponse
   */
  @Auth
  @PutMapping("/{productId}")
  ResponseEntity<SuccessResult> updateProduct(@Validated @RequestBody UpdateProductRequestDto product) {
    productService.updateProduct(product);

    SuccessResult result = new Builder(DetailedStatus.OK)
        .message("상품이 성공적으로 수정 되었습니다.")
        .data(product)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Auth
  @PutMapping("/product-options/{productOptionId}")
  ResponseEntity<SuccessResult> updateProductOption(@Validated @RequestBody UpdateProductOptionRequestDto productOption) {
    productService.updateProductOption(productOption);

    SuccessResult result = new Builder(DetailedStatus.OK)
        .message("상품 옵션이 성공적으로 수정 되었습니다.")
        .data(productOption)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Auth
  @DeleteMapping("/product-options/{productOptionId}")
  ResponseEntity<SuccessResult> deleteProductOption(@RequestBody Long productOptionId) {
    productService.deleteProductOption(productOptionId);

    SuccessResult result = new Builder(DetailedStatus.OK)
        .message("상품 옵션이 성공적으로 삭제 되었습니다.")
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Auth
  @DeleteMapping("/{productId}")
  ResponseEntity<SuccessResult> deleteProduct(@RequestBody Long productId) {
    productService.deleteProduct(productId);
    SuccessResult result = new Builder(DetailedStatus.OK)
        .message("상품이 성공적으로 삭제 되었습니다.")
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
