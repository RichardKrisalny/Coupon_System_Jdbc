 drop schema couponsystem;
create schema couponSystem;
use couponSystem; 
-- 1
create table COMPANIES(
`ID` int primary key auto_increment,
`NAME` varchar(30),
`EMAIL` varchar(30),
`PASSWORD` varchar(30));
-- 2
create table CUSTOMERS(
`ID` int primary key auto_increment,
`FIRST_NAME` varchar(30),
`LAST_NAME` varchar(30),
`EMAIL` varchar(30),
`PASSWORD` varchar(30));
-- 3
create table COUPONS(
`ID` int primary key auto_increment,
`COMPANY_ID` int,
foreign key(`COMPANY_ID`) references COMPANIES(ID) on delete cascade on update cascade,
`CATEGORY` enum('SPORT','CLOTHING','ELECTRICITY','CAMPING'),
`TITLE` varchar(30),
`DESCRIPTION` varchar(30),
`START_DATE` date,
`END_DATE` date,
`AMOUNT` int,
`PRICE` double,
`IMAGE` varchar(30));
-- 4
create table CUSTOMERS_VS_COUPONS(
`CUSTOMER_ID`int,
`COUPON_ID`int,
primary key(`CUSTOMER_ID`,`COUPON_ID`),
foreign key(`CUSTOMER_ID`) references CUSTOMERS(ID)on delete cascade on update cascade,
foreign key(`COUPON_ID`) references COUPONS(ID)on delete cascade on update cascade);

insert into companies values(0,'intel','intel@gmail.com','1234');
insert into companies values(0,'IBM','ibm@gamil.com','1111');
insert into companies values(0,'SAMSUNG','SAMSUNG@gamil.com','8888');

insert into customers values(0,'arial','remez','rk@gmail.com','7777');
insert into customers values(0,'maria','levi','mk@gmail.com','1123');
insert into customers values(0,'alex','levi','AL@gmail.com','7410');
insert into customers values(0,'moshe','cohen','MC@gmail.com','8520');

insert into coupons values(0,1,'CLOTHING','aaa','abbb','2022-11-01','2022-11-30',5,30,'img');
insert into coupons values(0,2,'ELECTRICITY','b','c','2022-10-30','2022-12-30',2,125,'img');
insert into coupons values(0,3,'CLOTHING','d','f','2022-09-30','2022-12-30',70,810,'img');
insert into coupons values(0,1,'SPORT','g','h','2022-08-30','2023-01-30',30,300,'img');
insert into coupons values(0,2,'CAMPING','i','j','2022-07-10','2023-06-30',1,100,'img');
insert into coupons values(0,3,'SPORT','t','w','2022-06-30','2023-08-30',10,10,'img');
insert into coupons values(0,1,'ELECTRICITY','q','l','2022-10-12','2022-11-30',6,50,'img');

insert into customers_vs_coupons values(1,1);
insert into customers_vs_coupons values(1,2);
insert into customers_vs_coupons values(1,3);
insert into customers_vs_coupons values(1,4);
insert into customers_vs_coupons values(1,5);
insert into customers_vs_coupons values(2,1);
insert into customers_vs_coupons values(2,3);
insert into customers_vs_coupons values(2,7);
insert into customers_vs_coupons values(2,2);
insert into customers_vs_coupons values(3,1);
insert into customers_vs_coupons values(4,1);
insert into customers_vs_coupons values(4,2);
insert into customers_vs_coupons values(4,3);


select * from companies;
select * from coupons;
select * from customers;
select * from customers_vs_coupons;
