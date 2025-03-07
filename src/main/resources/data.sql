-- Create Inventory Table
CREATE TABLE IF NOT EXISTS INVENTORY_ENTITY (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);

-- Create Order Table
CREATE TABLE IF NOT EXISTS ORDER_ENTITY (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(255) NOT NULL
);

--Sample Inventory Data
INSERT INTO INVENTORY_ENTITY (product, quantity) VALUES ('Product A', 100);
INSERT INTO INVENTORY_ENTITY (product, quantity) VALUES ('Product B', 50);
INSERT INTO INVENTORY_ENTITY (product, quantity) VALUES ('Product C', 200);

--Sample Order Data
INSERT INTO ORDER_ENTITY (product, quantity, status) VALUES ('Product A', 10, 'PENDING');
INSERT INTO ORDER_ENTITY (product, quantity, status) VALUES ('Product B', 5, 'PENDING');
INSERT INTO ORDER_ENTITY (product, quantity, status) VALUES ('Product C', 20, 'PENDING');

