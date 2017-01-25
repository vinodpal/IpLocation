# IpLocation



Find a location (country, city, latitude, longitude) using an IP address

The MaxMind provides a free GeoLite database (IP Address to Location).
1. Get a free GeoLite free Databases –(http://dev.maxmind.com/geoip/legacy/geolite/)
2. Get a GeoIP client Java APIs –(http://dev.maxmind.com/geoip/geoip2/web-services/)



Create Local Database 
--IP Track
create table ip_track
(
row_id varchar(45) not null, 
session_last_token_id varchar(45),
ip_address varchar(45), 
ip_location varchar(45), 
ip_count int,
constraint PK_ip_track primary key(row_id)
)
--ip_track = 
select row_id,ip_count from ip_track where ip_address = ?

--ipInsert=
insert into ip_track 
(
	session_last_token_id, 
	ip_location,
	ip_count,
	ip_address,
	row_id
) 
	values(?,?,?,?,?)
	
-- ipUpdate=
update  ip_track
	set 
	session_last_token_id=?, 
	ip_location=?,
	ip_count=?
	where 
	ip_address = ? 
	and 
	row_id=?


--IP Track Location Table
create table ip_track_filter_location
(
ip_track_row_id varchar(45) not null,
filter_location varchar(45),
filter_count int,
constraint nbmdc_ip_track_location 
foreign key(ip_track_row_id) 
references [nbmdc_ip_track](row_id),
)

--ip_filter_locaton_count=
select filter_count 
	from 
		ip_track_filter_location
	where 
		ip_track_row_id = ?
		AND 
		filter_location = ?
		

--ip_filter_locaton_insert=
insert into ip_track_filter_location 
(
	ip_track_row_id, 
	filter_location,
	filter_count
) 
	values(?,?,?)
	
-- ip_filter_locaton_update=
update  ip_track_filter_location
	set 
	
	filter_count=?
	where 
	ip_track_row_id = ? 
	and filter_location=?
	


