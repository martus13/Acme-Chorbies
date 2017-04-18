start transaction;

use `Acme-Chorbies`;

revoke all privileges on `Acme-Chorbies`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Chorbies`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database if exists `Acme-Chorbies`;

commit;