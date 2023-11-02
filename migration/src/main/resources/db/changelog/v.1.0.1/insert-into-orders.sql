INSERT INTO orders(customer_id, restaurant_id, status, courier_id, timestamp)
VALUES (1,1, 'CUSTOMER_CREATED',null,current_timestamp),
       (2,2, 'CUSTOMER_PAID',null,current_timestamp),
       (3,1, 'DELIVERY_PENDING',null,current_timestamp)
;