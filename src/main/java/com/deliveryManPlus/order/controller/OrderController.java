package com.deliveryManPlus.order.controller;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import com.deliveryManPlus.order.model.dto.OrderResponseDto;
import com.deliveryManPlus.order.model.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.model.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/user/order")
    public ResponseEntity<Void> createOrder(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                            @Valid @RequestBody CreateOrderRequestDto dto) {
        orderService.createOrder(auth.getId(), dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<OrderResponseDto> updateStatus(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                                         @PathVariable Long shopId,
                                                         @PathVariable Long orderId,
                                                         @Valid @RequestBody OrderStatusUpdateDto dto) {
        OrderResponseDto orderResponseDto = orderService.updateStatus(auth.getId(), shopId, orderId, dto);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }
    @DeleteMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<Void> reject(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                                         @PathVariable Long shopId,
                                                         @PathVariable Long orderId,
                                                         @Valid @RequestBody OrderStatusRejectDto dto) {
        orderService.reject(auth.getId(), shopId, orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
