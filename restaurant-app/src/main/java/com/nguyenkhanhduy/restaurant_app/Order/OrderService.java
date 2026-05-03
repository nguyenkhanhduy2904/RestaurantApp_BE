package com.nguyenkhanhduy.restaurant_app.Order;

import com.nguyenkhanhduy.restaurant_app.DeviceToken.DeviceTokenService;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfile;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileRepository;
import com.nguyenkhanhduy.restaurant_app.UserProfile.UserProfileService;
import com.nguyenkhanhduy.restaurant_app.Utils.FcmService;
import com.nguyenkhanhduy.restaurant_app.product.Product;
import com.nguyenkhanhduy.restaurant_app.product.ProductRepository;
import com.nguyenkhanhduy.restaurant_app.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;
    private final DeviceTokenService deviceTokenService;
    private final FcmService fcmService;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserProfileRepository userProfileRepository, ProductRepository productRepository, DeviceTokenService deviceTokenService, FcmService fcmService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userProfileRepository = userProfileRepository;
        this.productRepository = productRepository;
        this.deviceTokenService = deviceTokenService;
        this.fcmService = fcmService;
    }


    public OrderResponse getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return convertToResponse(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        UserProfile existedUser = userProfileRepository.findById(orderRequest.getUserId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exist"));

        Order order = new Order();
        order.setUserProfile(existedUser);
        order.setOrderAddress(orderRequest.getAddress());
        order.setOrderPhone(orderRequest.getPhone());
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setTotalProduct(0);//temporary

        orderRepository.save(order);

        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalProduct =0;
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for(OrderDetailRequest detail : orderRequest.getOrderDetailRequests()){
            Product product = productRepository.findById(detail.getProductId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setNote(detail.getNote());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setUnitPrice(product.getProductPrice());
            orderDetail.setDiscountPercent(product.getPriceReduction());// TODO: for now

            BigDecimal unitPrice = product.getProductPrice();
            BigDecimal quantity = BigDecimal.valueOf(detail.getQuantity());
            totalPrice = totalPrice.add(unitPrice.multiply(quantity));

            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            totalProduct++;

        }


        order.setTotalProduct(totalProduct);
        order.setTotalPrice(totalPrice);
        order.setFinalPrice(totalPrice);
        order.setOrderDetailList(orderDetailList);


        orderRepository.save(order);
        System.out.println(order);
        Order savedOrder = orderRepository.findById(order.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        savedOrder.getOrderDetailList().size();

        List<UserProfile> admins = userProfileRepository.findByUserRole("ADMIN");
        List<String> adminTokens = new ArrayList<>();

        for (UserProfile admin : admins) {
            List<String> tokens = deviceTokenService.getAllTokenForUser(admin.getUserId());
            adminTokens.addAll(tokens);
        }
        for (String token : adminTokens) {
            fcmService.sendNewOrderNotification(token);
        }

        return convertToResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrder() {
        return orderRepository.findAll().stream().map(OrderService::convertToResponse).toList();
    }

    public static OrderResponse convertToResponse(Order order){
        OrderResponse res = new OrderResponse();

        res.setOrderId(order.getOrderId());
        res.setAddress(order.getOrderAddress());
        res.setCreateAt(order.getCreatedAt());
        res.setPhone(order.getOrderPhone());
        res.setTotalPrice(order.getTotalPrice());
        res.setFinalPrice(order.getFinalPrice());
        res.setOrderStatus(order.getOrderStatus());
        res.setPaymentStatus(order.getPaymentStatus());
        res.setPaymentMethod(order.getPaymentMethod());

        res.setUserId(order.getUserProfile().getUserId());
        res.setUserName(order.getUserProfile().getUserName());

        List<OrderDetailResponse> ls = new ArrayList<>();

        for(OrderDetail item : order.getOrderDetailList()){
            OrderDetailResponse detailResponse = new OrderDetailResponse();
            detailResponse.setProductId(item.getProduct().getProductId());
            detailResponse.setProductName(item.getProduct().getProductName());
            detailResponse.setUnitPrice(item.getUnitPrice());
            detailResponse.setDiscountPercent(item.getDiscountPercent());
            detailResponse.setQuantity(item.getQuantity());
            detailResponse.setNote(item.getNote());

            ls.add(detailResponse);
        }

        res.setOrderDetailResponseList(ls);

        return res;

    }

    public List<OrderResponse> getOrderByUserId(Integer id) {
        List<Order> rs = orderRepository.findByUserProfile_UserId(id);
        return rs.stream().map(OrderService::convertToResponse).toList();
    }

    public OrderResponse updateOrderStatus(OrderStatusRequest orderStatusRequest) {
        Order existedOrder = orderRepository.findById(orderStatusRequest.getOrderId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not exist"));

        UserProfile existedUser = userProfileRepository.findById(orderStatusRequest.getUserId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        if(!isBlank(orderStatusRequest.getOrderStatus())){
            existedOrder.setOrderStatus(orderStatusRequest.getOrderStatus());
        }
        if(!isBlank(orderStatusRequest.getPaymentStatus())){
            existedOrder.setPaymentStatus(orderStatusRequest.getPaymentStatus());
        }
        if(!isBlank(orderStatusRequest.getPaymentMethod())){
            existedOrder.setPaymentMethod(orderStatusRequest.getPaymentMethod());
        }
        if("CANCELED".equals(orderStatusRequest.getOrderStatus())){
            existedOrder.setPaymentStatus("CANCELED");
        }
        if("CANCELED".equals(orderStatusRequest.getPaymentStatus())){
            existedOrder.setOrderStatus("CANCELED");
        }


        Order savedOrder = orderRepository.save(existedOrder);

        //find all the fcm device token of this user
        // make firebase send notif?
        List<String> ls = deviceTokenService.getAllTokenForUser(existedUser.getUserId());
        for(String token : ls){
            fcmService.sendUpdateOrderNotification(token);
        }
        return convertToResponse(savedOrder);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public void handleVnpayIpn(Map<String, String> params) {

        String orderId = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");

        Order order = orderRepository.findById(Integer.parseInt(orderId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // prevent double processing
        if (!"PENDING".equals(order.getOrderStatus())) {
            return;
        }

        if ("00".equals(responseCode)) {
//            order.setOrderStatus("PAID");
            order.setPaymentStatus("PAID");

            // send notification here (only when paid)
//            notifyAdmin(order);

        } else {
            order.setOrderStatus("FAILED");
            order.setPaymentStatus("FAILED");
        }

        orderRepository.save(order);
    }
}
