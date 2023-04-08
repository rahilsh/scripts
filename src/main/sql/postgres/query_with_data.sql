with employees(id, name, email, phoneNumber, external_empcode) as (
  values (1, 'person1', 'person1@gmail.com', '9', '1'),
    (2, 'person2', 'person2@gmail.com', '8', '2'))
select *
from employees;
