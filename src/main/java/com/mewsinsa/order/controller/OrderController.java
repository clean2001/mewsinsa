package com.mewsinsa.order.controller;


import com.mewsinsa.auth.jwt.JwtProvider;
import com.mewsinsa.auth.jwt.interceptor.Auth;
import com.mewsinsa.global.response.DetailedStatus;
import com.mewsinsa.global.response.SuccessResult;
import com.mewsinsa.member.service.MemberService;
import com.mewsinsa.order.controller.dto.OrderDeliveryAddressDto;
import com.mewsinsa.order.controller.dto.admin.OrderInfoResponseForAdminDto;
import com.mewsinsa.order.controller.dto.admin.OrderListResponseForAdminDto;
import com.mewsinsa.order.controller.dto.OrderListResponseForMemberDto;
import com.mewsinsa.order.controller.dto.OrderRequestDto;
import com.mewsinsa.order.controller.dto.OrderResponseDto;
import com.mewsinsa.order.controller.dto.form.OrderFormRequestDto;
import com.mewsinsa.order.controller.dto.form.OrderFormResponseDto;
import com.mewsinsa.order.domain.History;
import com.mewsinsa.order.domain.Order;
import com.mewsinsa.order.domain.OrderedProduct;
import com.mewsinsa.order.exception.NonExsistentOrderException;
import com.mewsinsa.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
public class OrderController {
  private final MemberService memberService;
  private final OrderService orderService;

  public OrderController(MemberService memberService, OrderService orderService) {
    this.memberService = memberService;
    this.orderService = orderService;
  }

  @Auth
  @PostMapping("/form")
  public ResponseEntity<SuccessResult> orderForm(
      @RequestHeader(value= JwtProvider.ACCESS_HEADER_STRING, required=false) String accessToken,
      @RequestBody OrderFormRequestDto orderFormRequestDto) {
    OrderFormResponseDto orderFormResponseDto = orderService.orderForm(accessToken,
        orderFormRequestDto);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.CREATED)
        .data(orderFormResponseDto)
        .build();

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @Auth
  @PostMapping
  public ResponseEntity<SuccessResult> makeOrder(
      @RequestHeader(value= JwtProvider.ACCESS_HEADER_STRING, required=false) String accessToken,
      @Valid @RequestBody OrderRequestDto orderRequestDto) {
    Long memberId = memberService.getMemberIdByAccessToken(accessToken);

    OrderResponseDto orderResponseDto = orderService.makeOrder(orderRequestDto, memberId);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.CREATED)
        .message("주문에 성공하였습니다.")
        .data(orderResponseDto)
        .build();

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }


  @Auth
  @GetMapping
  public ResponseEntity<SuccessResult> allOrderList(@Positive @RequestParam(value = "page", defaultValue = "1") Integer page,
      @Positive @RequestParam(value = "count", defaultValue = "10") Integer count) {

    List<OrderListResponseForAdminDto> orders = orderService.allOrderList(page, count);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .data(orders)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Auth
  @GetMapping("/{orderId}")
  public ResponseEntity<SuccessResult> orderInfo(@Positive @PathVariable("orderId") Long orderId) {
    OrderInfoResponseForAdminDto orderInfo = orderService.orderInfo(orderId);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .data(orderInfo)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @Auth
  @GetMapping("/members/{memberId}")
  public ResponseEntity<SuccessResult> memberOrderList(
      @PathVariable("memberId") Long memberId,
      @Positive @RequestParam(value = "page", defaultValue = "1") Integer page,
      @Positive @RequestParam(value = "count", defaultValue = "10") Integer count,
      @RequestParam(value = "dt_from_input", required = false) String dateFrom,
      @RequestParam(value = "dt_to_input", required = false) String dateTo) {

    List<OrderListResponseForMemberDto> orders = orderService.orderListByMemberId(memberId, page, count, dateFrom, dateTo);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .data(orders)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }



  @Auth
  @GetMapping("/ordered-products/{orderedProductId}/history")
  public ResponseEntity<SuccessResult> lookUpOneHistoryTable(@PathVariable("orderedProductId") Long orderedProductId) {
    History history = orderService.lookUpOneHistoryTable(orderedProductId);

    if(history == null) {
      throw new NonExsistentOrderException("주문이 존재하지 않습니다. orderedProductId: " + orderedProductId);
    }
    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .data(history)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  @Auth
  @PostMapping("/cancel-order/{orderedProductId}")
  public ResponseEntity<SuccessResult> cancelOrder(
      @PathVariable("orderedProductId") Long orderedProductId,
      @RequestHeader(value= JwtProvider.ACCESS_HEADER_STRING, required=false) String accessToken
      ) {
    Long memberId = memberService.getMemberIdByAccessToken(accessToken);
    OrderedProduct cancelledOrderedProduct = orderService.cancelOrder(orderedProductId, memberId);

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .message("주문이 성공적으로 취소 되었습니다.")
        .data(cancelledOrderedProduct)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }


  // 배송지 정보 수정
  @Auth
  @PatchMapping("/update-address/{orderId}")
  public ResponseEntity<SuccessResult> updateDeliveryAddressInOrder(
      @PathVariable("orderId") Long orderId,
      @RequestBody OrderDeliveryAddressDto deliveryAddress) {
    Order order = orderService.updateDeliveryAddressInOrder(
        orderId,
        deliveryAddress.getReceiverName(),
        deliveryAddress.getReceiverPhone(),
        deliveryAddress.getReceiverAddress()
    );

    SuccessResult result = new SuccessResult.Builder(DetailedStatus.OK)
        .message("배송지가 정상적으로 변경되었습니다.")
        .data(order)
        .build();

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
