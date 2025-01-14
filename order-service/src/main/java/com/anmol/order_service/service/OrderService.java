package com.anmol.order_service.service;

import com.anmol.order_service.dto.InventoryResponse;
import com.anmol.order_service.dto.OrderLineItemsDto;
import com.anmol.order_service.dto.OrderRequest;
import com.anmol.order_service.event.OrderPlacedEvent;
import com.anmol.order_service.model.Order;
import com.anmol.order_service.model.OrderLineItems;
import com.anmol.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItemsDtoList().stream()
                        .map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();
        // Synchronous communication btw Order & Inventory Service : Stock? {block()}
        InventoryResponse[] responses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class).block(); // Sync Communication
        assert responses != null;
        boolean allProductInStock = Arrays.stream(responses).allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
            //kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
        }
        else throw new IllegalArgumentException("Product Out Of Stock, Please Try again Later");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
