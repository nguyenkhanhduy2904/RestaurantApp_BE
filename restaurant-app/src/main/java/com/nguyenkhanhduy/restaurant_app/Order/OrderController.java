package com.nguyenkhanhduy.restaurant_app.Order;


import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Integer id){
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id)));
    }

    @GetMapping("/user-order/{id}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrderByUserId(@PathVariable Integer id){
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderByUserId(id)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrder(){
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrder()));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest){

        return ResponseEntity.ok(ApiResponse.success(orderService.createOrder(orderRequest)));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@RequestBody OrderStatusRequest orderStatusRequest){
        return ResponseEntity.ok(ApiResponse.success(orderService.updateOrderStatus(orderStatusRequest)));
    }

}
