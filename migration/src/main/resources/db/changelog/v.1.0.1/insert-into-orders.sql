INSERT INTO orders(customer_id, restaurant_id, status, courier_id, timestamp)
VALUES (13,13, 'active',13,current_timestamp),
       (14,14, 'active',null,current_timestamp),
       (14,13, 'delivered',14,current_timestamp)
;