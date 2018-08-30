create table prs_preprocess (
	id varchar(50) not null,
	attachment_id varchar(50),
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
	status varchar(20),
	process_id varchar(50),
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

create table prs_process_type_preprocess_type (
	process_type_id varchar(50) not null,
	preprocess_type_id varchar(50) not null
) engine=InnoDB;

alter table prs_preprocess
	add constraint FKqqy4m5x7cbnb6eviixn7ykqbi foreign key (process_id)
	references prs_process (id);

alter table prs_preprocess
	add constraint FK1brceyb9r6eym0j675evnst5h foreign key (type_id)
	references prs_preprocess_type (id);

alter table prs_process_type_preprocess_type
	add constraint FKga4877kxhk3rix07n9aah4va4 foreign key (preprocess_type_id)
	references prs_preprocess_type (id);

alter table prs_process_type_preprocess_type
	add constraint FKp9nhldiq911cfom3lmmrpe48a foreign key (process_type_id)
	references prs_process_type (id);
