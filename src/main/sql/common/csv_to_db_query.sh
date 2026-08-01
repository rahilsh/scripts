#!/bin/sh

count=0

while IFS="," read id key value amount
do
    count=$((count+1))
    echo '===== Processing record no:'$count' '$id' '$key' '$value' '$amount' ======='
    jsondata="{\"${key}\":\"${value}\"}"
    echo $jsondata
    psql -d test -c "UPDATE test_schema.test_table SET attributes  = (attributes::jsonb || '${jsondata}'::jsonb), amount = ${amount} where id=${id}"
done < /path/to/update_data_in_db.csv
