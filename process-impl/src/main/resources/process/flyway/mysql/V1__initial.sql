create table prs_preprocess (
	id binary(16) not null,
	attachment_id binary(16),
	charge_cost decimal(19,2),
	comment_subject_id varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	deleted bit not null,
	deleted_date datetime,
	description longtext,
	info longtext,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	manager_id varchar(50),
	manager_name varchar(50),
	name varchar(150),
	process_id binary(16),
	status varchar(20),
	type_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table prs_preprocess_type (
	id varchar(50) not null,
	base_cost decimal(19,2),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	info_type_id varchar(200),
	info_type_name varchar(50),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	name varchar(50),
	primary key (id)
) engine=InnoDB;

create table prs_process (
	id binary(16) not null,
	adjust_cost decimal(19,2),
	adjust_cost_reason varchar(200),
	attachment_id binary(16),
	comment_subject_id varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	deleted bit not null,
	deleted_date datetime,
	description longtext,
	difficulty varchar(20),
	estimated_cost_direct_labor decimal(19,2),
	estimated_cost_indirect_expenses decimal(19,2),
	estimated_cost_indirect_labor decimal(19,2),
	estimated_cost_indirect_material decimal(19,2),
	info longtext,
	item_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	manager_id varchar(50),
	manager_name varchar(50),
	name varchar(100),
	status varchar(20),
	type_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table prs_process_type (
	id varchar(50) not null,
	base_unit_cost decimal(19,2),
	direct_labor_cost_rate decimal(7,5),
	indirect_expenses_rate decimal(7,5),
	indirect_labor_cost_rate decimal(7,5),
	indirect_material_cost_rate decimal(7,5),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	info_type_id varchar(200),
	info_type_name varchar(50),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	name varchar(50),
	primary key (id)
) engine=InnoDB;

create table prs_process_type_difficulty_grade (
	process_type_id varchar(50) not null,
	cost_rate decimal(7,5),
	description varchar(200),
	difficulty varchar(20),
	ordinal integer not null,
	difficulty_grades_order integer not null,
	primary key (process_type_id,difficulty_grades_order)
) engine=InnoDB;

create table prs_process_type_preprocess_type (
	process_type_id varchar(50) not null,
	preprocess_type_id varchar(50) not null
) engine=InnoDB;

create index IDXctkhfmd190khp0bn16hmknul3
	on prs_preprocess (process_id);

create index IDXe1vcflsrij5u2mrbpbp9wrayg
	on prs_process (item_id);

alter table prs_process_type_preprocess_type
	add constraint UK21l4x1s5lye9iubmwmteglxvq unique (process_type_id,preprocess_type_id);

alter table prs_process_type_difficulty_grade
	add constraint FK5tkdbfefall1pwy6sisw5e3ib foreign key (process_type_id)
	references prs_process_type (id);

alter table prs_process_type_preprocess_type
	add constraint FKp9nhldiq911cfom3lmmrpe48a foreign key (process_type_id)
	references prs_process_type (id);
