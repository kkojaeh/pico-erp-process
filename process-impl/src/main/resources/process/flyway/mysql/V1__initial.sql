create table prs_process (
	id varchar(50) not null,
	attachment_id varchar(50),
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
	item_id varchar(50),
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

create table prs_process_difficulty_grade (
	process_id varchar(50) not null,
	cost_rate decimal(7,5),
	description varchar(200),
	difficulty varchar(20),
	ordinal integer not null,
	difficulty_grades_order integer not null,
	primary key (process_id,difficulty_grades_order)
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

create index PRS_PROCESS_ITEM_ID_IDX
	on prs_process (item_id);

alter table prs_process
	add constraint FKeyh980m5fuyvxnpv8mixnxocf foreign key (type_id)
	references prs_process_type (id);

alter table prs_process_difficulty_grade
	add constraint FKr12vqb4yduyu7digbdt4v5g6b foreign key (process_id)
	references prs_process_type (id);
