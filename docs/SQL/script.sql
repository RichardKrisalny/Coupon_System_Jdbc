
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




