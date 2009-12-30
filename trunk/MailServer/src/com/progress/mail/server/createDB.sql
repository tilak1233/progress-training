DROP SCHEMA IF EXISTS mailserver_test_schema$$
CREATE SCHEMA mailserver_test_schema$$

use mailserver_test_schema$$

DROP TABLE IF EXISTS `clients`$$
CREATE TABLE `clients` (
  `email_id` varchar(30) NOT NULL,
  `lastlogin` timestamp NOT NULL default '0000-00-00 00:00:00',
  `lastlogout` timestamp NOT NULL default '0000-00-00 00:00:00',
  `lastdisplay` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

DROP TABLE IF EXISTS `mailbox`$$
CREATE TABLE `mailbox` (
  `msg_id` int(10) unsigned NOT NULL auto_increment,
  `msg_to` varchar(2000) NOT NULL,
  `msg_from` varchar(30) NOT NULL,
  `msg_subject` varchar(100) default NULL,
  `msg_body` text default NULL,
  `msg_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  USING BTREE (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=577 DEFAULT CHARSET=latin1$$


DROP FUNCTION IF EXISTS `SPLIT_STR`$$
CREATE DEFINER=`root`@`%` FUNCTION  `SPLIT_STR`(
  x VARCHAR(2000),
  delim VARCHAR(1),
  pos INT
) RETURNS varchar(50) CHARSET latin1
RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '');
$$


DROP PROCEDURE IF EXISTS `SP_UPDATE_MAILBOXES`$$
CREATE DEFINER=`root`@`%` PROCEDURE  `SP_UPDATE_MAILBOXES`(msgto varchar(2000), msgfrom varchar(30), msgsubject varchar(100), msgbody text)
BEGIN

set @flag = 1 ;
set @i = 1;


    insert into mailbox(msg_to,msg_from,msg_subject,msg_body) values(msgto,msgfrom,msgsubject,msgbody);

    WHILE @flag DO
      set @user_name = split_str(msgto,',',@i);
      if @user_name = '' then set @flag = 0;
      else set @user_name = CONCAT('mailbox_', @user_name) ;
        set @table_query = CONCAT('show tables like \'',@user_name,'\'');
        prepare table_stmt from @table_query;
        execute table_stmt;
        if FOUND_ROWS() then set @str_query=CONCAT('insert into ', @user_name,' values(',LAST_INSERT_ID(),');');
          prepare stmt from @str_query;
          execute stmt;
        end if;
      end if;
      set @i = @i + 1;
    END WHILE;

END $$

