# --- Simplify primary key for yarn_app_heuristic_result_details
# --- !Ups

alter table yarn_app_heuristic_result_details add id int auto_increment unique not null first;
create unique index yarn_app_heuristic_result_details_i2 on yarn_app_heuristic_result_details (yarn_app_heuristic_result_id,name);
alter table yarn_app_heuristic_result_details drop primary key, add primary key(id);
drop index id on yarn_app_heuristic_result_details;

# --- !Downs

create index id on yarn_app_heuristic_result_details (id);
alter table yarn_app_heuristic_result_details drop primary key, add primary key(yarn_app_heuristic_result_id,name);
drop index yarn_app_heuristic_result_details_i2 on yarn_app_heuristic_result_details;
alter table yarn_app_heuristic_result_details drop id;
