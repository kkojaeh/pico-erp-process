ALTER TABLE prs_process ADD input_rate decimal(7,5) default 1 NOT NULL;
ALTER TABLE prs_process ADD item_id binary(16) NOT NULL;
ALTER TABLE prs_process ADD item_order integer default 0;

create index IDXe1vcflsrij5u2mrbpbp9wrayg
	on prs_process (item_id);
