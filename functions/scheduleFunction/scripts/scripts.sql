CREATE TABLE files (file_id CHAR(36) NOT NULL,
                    status_process CHAR(20) NOT NULL,
                    bucket_name VARCHAR(50) NOT NULL,
                    file_name VARCHAR(50) NOT NULL,
                    date_time_process_function_add_to_queue CHAR(35),
                    date_time_process_function_loader CHAR(35),
                    date_time_last_process_verify CHAR(35),
                    counter_records_add_to_queue INT,
                    counter_records_process INT,
                    counter_execution_sonda INT,
                    counter_execution_verify INT,
                    PRIMARY KEY (file_id))
                    CHARACTER SET utf8;