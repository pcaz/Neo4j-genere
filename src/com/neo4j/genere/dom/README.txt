Dom implementation

The schema is in .xsd file.

each field has a name and a dedicated space to define it
it can be optionnal with a ratio

space depends of field type  

IT MUST BE AN IDENTIFIER FIELD WITH TYPE INTEGER AND ONLY "identifier" IN IT'S SPACE

int:
"min", the floor
"max"  the ceiling

string:

create an random string
"long" is the length of the string:
"min" minimal length, must be 3 or more
"max" must be > min

create an expresion with maybe others fields
"regular"
eval(...) is a imported field, IT MUST BE ALREADY PARSED

date:
create a random date
format: 2007-5-24
"after" the date created is after this field
"before" the date createf is befotre this one

"before" must be after "after" 

sample of xml file:

genere Person.csv

<?xml version="1.0" encoding="UTF-8"?>
<database xmlns="http://Neo4j_genere/xml" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="file:///[schema location]">

	<name>Person</name>
	<number>100</number>
	<fields>
		<int>
			<name>id</name>
			<space>
				<identifiant/>
			</space>
		</int>
		<string>
			<name>name</name>
			<space>
				<long>
					<min>3</min>
					<max>8</max>
				</long>
			</space>
		</string>
		<date>
			<name>birthDate</name>
			<space>
				<after>1970-01-01</after>
				<before>1990-01-01</before>
			</space>
		</date>
		<string>
			<name>email</name>
			<optionnal>50</optionnal>
			<space>
				<regular>eval(name)@example.com</regular>
			</space>
		</string>
	</fields>
</database>
