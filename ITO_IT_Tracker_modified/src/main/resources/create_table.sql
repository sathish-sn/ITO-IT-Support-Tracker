create table ticket(ticket_id int  AUTO_INCREMENT, category_id int ,
sub_category_id int , assignee_id int  , reported_id int, subject varchar(255) ,description varchar(255),
priority_id int , create_datetime datetime , last_modified_date datetime, primary key (ticket_id) );

create table category(category_id int , category_desc varchar(255),primary key (category_id));
create table sub_category(sub_category_id int , category_id int , sub_category_desc varchar(255),primary key (sub_category_id))    ;
create table admin_team(admin_id int , name varchar(20) , emailid varchar(30),primary key (admin_id));
create table user (user_id int AUTO_INCREMENT , name varchar(20), email_id varchar(30),
 create_date_time datetime, last_modified_date_time datetime,primary key (user_id));
create table status (status_id int , status varchar(20),primary key (status_id));
create table priority (priority_id int , priority varchar(20),primary key (priority_id));
create table comment (comment_id int ,  ticket_id int , user_id int, message varchar(1000),primary key (comment_id));

