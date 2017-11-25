DROP TABLE IF EXISTS customer;

DROP TABLE IF EXISTS employee;

CREATE TABLE customer (
	id int not null AUTO_INCREMENT,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	CONSTRAINT customer_pk PRIMARY KEY (id)
);

CREATE TABLE employee (
	id int not null AUTO_INCREMENT,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	CONSTRAINT employee_pk PRIMARY KEY (id)
);