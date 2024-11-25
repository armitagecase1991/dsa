package maps.src.test.java.app;

import org.junit.jupiter.api.Test;

import com.dsa.treemap.app.OrderProcessing;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessingTest {

    @Test
    void testAddOrderAndProcessSingleOrder() throws Exception {
        OrderProcessing orderProcessing = new OrderProcessing();

        // Add a single order
        orderProcessing.addOrder("Customer1", 10);

        // Process the order
        String processedOrder = orderProcessing.processOrder();
        assertEquals("10,Customer1", processedOrder);
    }

    @Test
    void testAddMultipleOrdersAndProcessInPriorityOrder() throws Exception {
        OrderProcessing orderProcessing = new OrderProcessing();

        // Add multiple orders
        orderProcessing.addOrder("Customer1", 10);
        orderProcessing.addOrder("Customer2", 5);
        orderProcessing.addOrder("Customer3", 20);

        // Process the orders in priority order
        assertEquals("5,Customer2", orderProcessing.processOrder());
        assertEquals("10,Customer1", orderProcessing.processOrder());
        assertEquals("20,Customer3", orderProcessing.processOrder());
    }

    @Test
    void testProcessOrderThrowsExceptionWhenEmpty() {
        OrderProcessing orderProcessing = new OrderProcessing();

        // Try to process an empty order queue
        Exception exception = assertThrows(Exception.class, orderProcessing::processOrder);
        assertEquals(" the orders cannot be empt ", exception.getMessage());
    }

    @Test
    void testAddOrderWithSamePriorityReplacesExisting() throws Exception {
        OrderProcessing orderProcessing = new OrderProcessing();

        // Add an order with a priority
        orderProcessing.addOrder("Customer1", 10);

        // Add another order with the same priority (should replace the previous one)
        orderProcessing.addOrder("Customer2", 10);

        // Process the order (only the latest one with the same priority should be processed)
        assertEquals("10,Customer2", orderProcessing.processOrder());
    }

    @Test
    void testProcessAllOrdersAndCheckEmptyQueue() throws Exception {
        OrderProcessing orderProcessing = new OrderProcessing();

        // Add multiple orders
        orderProcessing.addOrder("Customer1", 10);
        orderProcessing.addOrder("Customer2", 5);

        // Process all orders
        orderProcessing.processOrder();
        orderProcessing.processOrder();

        // Ensure the queue is empty
        Exception exception = assertThrows(Exception.class, orderProcessing::processOrder);
        assertEquals(" the orders cannot be empt ", exception.getMessage());
    }
}
