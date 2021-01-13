
INSERT INTO product_category(category_id, category_name, category_description, category_image_url) VALUES (100001, 'Stationery', 'Commercially manufactured writing materials', 'https://previews.123rf.com/images/stuartphoto/stuartphoto1705/stuartphoto170500652/77522615-stationery-store-meaning-office-supplies-shops-3d-illustration.jpg');

INSERT INTO product_category(category_id, category_name, category_description, category_image_url) VALUES (100002, 'Toys', 'an object for a child to play with', 'https://taiken.co/uploads/2019/05/Kawaii_Culture_001-1200x1047.jpg');

-- TODO Add 4 more category

ALTER sequence category_id_sequence restart with 100007;


INSERT INTO cart VALUES (100001);
INSERT INTO cart VALUES (100002);
INSERT INTO cart VALUES (100003);
INSERT INTO cart VALUES (100004);
INSERT INTO cart VALUES (100005);
INSERT INTO cart VALUES (100006);

INSERT INTO product_info (product_id, product_name, product_price, product_stock, product_description, product_icon, product_status, product_category) VALUES (100001, 'Teddy Bear', 1200, 99, 'A stuffed toy is a toy doll with an outer fabric sewn from a textile and stuffed with flexible material', 'https://www.wellandgood.com/wp-content/uploads/2019/01/Stocksy-Teddy-Bear-Marta-Locklear.jpg', 'ENABLED', 100002);

INSERT INTO product_info (product_id, product_name, product_price, product_stock, product_description, product_icon, product_status, product_category) VALUES (100002, 'Color Pencils', 200, 99, 'Colored drawing pencil for kids', 'https://previews.123rf.com/images/kenishirotie/kenishirotie1801/kenishirotie180100009/92727823-spectrum-color-pencils-set-arranged-in-s-curve-isolated-on-white-background.jpg', 'ENABLED', 100001);

-- TODO Add 25 more items

ALTER sequence product_id_sequence restart with 100026;

-- ORDER Table
INSERT INTO ORDER_MAIN (order_id, buyer_address, buyer_city, buyer_email, buyer_pincode, buyer_state, buyer_name, buyer_phone, order_amount, order_status, payment_type, payment_id) VALUES (100001, 'Powai, near D-Mart', 'Mumbai', 'gagandeep@mail.com', '400072', 'Maharashtra', 'Gagandeep Singh', '8419969059', 4000, 'DELIVERED', 'ONLINE', '100001');
-- TODO Add 4 more items

-- To be purchased product

INSERT INTO product_in_order (product_in_order_id, cart_id, order_id, product_id, product_name, product_description, product_icon, product_category, product_price, product_stock) VALUES (100001, 100001, null, 100001, 'Teddy Bear', 'A stuffed toy is a toy doll with an outer fabric sewn from a textile and stuffed with flexible material', 'https://www.wellandgood.com/wp-content/uploads/2019/01/Stocksy-Teddy-Bear-Marta-Locklear.jpg', 'Toys', 1200, 1);

INSERT INTO product_in_order (product_in_order_id, cart_id, order_id, product_id, product_name, product_description, product_icon, product_category, product_price, product_stock) VALUES (100002, null, 100001, 100001, 'Large Teddy Bear', 'A stuffed toy is a toy doll with an outer fabric sewn from a textile and stuffed with flexible material', 'https://www.wellandgood.com/wp-content/uploads/2019/01/Stocksy-Teddy-Bear-Marta-Locklear.jpg', 'Toys', 1200, 1);

-- TODO Add 9 more items

ALTER sequence product_order_id_sequence restart with 100010;

