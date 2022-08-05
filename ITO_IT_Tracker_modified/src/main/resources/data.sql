insert into category(category_id,category_desc) values(1,'Hardware');
insert into category(category_id,category_desc) values(2,'Software');
insert into category(category_id,category_desc) values(3,'Access Management');


insert into sub_category(sub_category_id,category_id,sub_category_desc) values(1,1,"Allocate Laptop");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(2,1,"Allocate Hardware");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(3,1,"Hardware replacement");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(4,2,"Software Installation");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(5,2,"Antivirus");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(6,2,"Email Password update");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(7,2,"Laptop Slowness issue.");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(8,2,"Software Issue");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(9,3,"Software access");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(10,3,"Wifi Access");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(11,3,"Database Access");
insert into sub_category(sub_category_id,category_id,sub_category_desc) values(12,3,"VPN Access");


insert into status(status_id,status) values(1,"open");
insert into status(status_id,status) values(2,"Assigned");
insert into status(status_id,status) values(3,"In Progress");
insert into status(status_id,status) values(4,"Completed");


insert into priority(priority_id,priority) values(1,"low");
insert into priority(priority_id,priority) values(2,"medium");
insert into priority(priority_id,priority) values(3,"high");
insert into priority(priority_id,priority) values(4,"critical");


insert into admin_team(admin_id,emailid,name) values(1,"sunny@gmail.com"," Sunny Shaw");
insert into admin_team(admin_id,emailid,name) values(2,"sudhakar@gmail.com"," Sudhakar");
insert into admin_team(admin_id,emailid,name) values(3,"manju@gmail.com"," Manjunath");
insert into admin_team(admin_id,emailid,name) values(4,"jprakash@gmail.com","Jai Prakash");


