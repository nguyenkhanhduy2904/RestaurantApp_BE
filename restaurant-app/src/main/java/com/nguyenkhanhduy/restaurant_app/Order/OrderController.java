package com.nguyenkhanhduy.restaurant_app.Order;


import com.nguyenkhanhduy.restaurant_app.Response.ApiResponse;
import com.nguyenkhanhduy.restaurant_app.Utils.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final VnPayService vnPayService;

    @Autowired
    public OrderController(OrderService orderService, VnPayService vnPayService) {
        this.orderService = orderService;
        this.vnPayService = vnPayService;
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

    @GetMapping("/vnpay-ipn")
    public ResponseEntity<?> handleIpn(@RequestParam Map<String, String> params) {

        orderService.handleVnpayIpn(params);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/vnpay-url/{orderId}")
    public ResponseEntity<?> getVnpayUrl(@PathVariable Integer orderId) throws Exception {

        OrderResponse order = orderService.getOrderById(orderId);

        String paymentUrl = vnPayService.createPaymentUrl(
                order.getOrderId(),
                order.getFinalPrice().doubleValue()
        );

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<Void> handleReturn(@RequestParam Map<String, String> params) {

        String orderId = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");

        boolean success = "00".equals(responseCode);

        String redirectUrl =
                "https://redirect-site-a5wl.onrender.com"
                        + "?orderId=" + orderId
                        + "&status=" + (success ? "success" : "failed");

        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

}
