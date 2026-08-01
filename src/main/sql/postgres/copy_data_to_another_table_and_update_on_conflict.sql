\copy (select column1,column2,column3 from table_a as a join table_b b on a.column = b.column) TO '/path/to/file.csv' DELIMITER ',' CSV HEADER;

begin;

CREATE TEMP TABLE tmp_table ON COMMIT DROP AS SELECT * FROM target_table WITH NO DATA; --Create temp table with schema similar to target_table

\COPY tmp_table(column1,column2,column3) FROM '/path/to/file.csv' DELIMITER ',' CSV HEADER; 

INSERT INTO target_table(column1,column2,column3) SELECT column1,column2,column3 FROM tmp_table ON CONFLICT(column1,column2) DO UPDATE SET status=EXCLUDED.column3;

commit;