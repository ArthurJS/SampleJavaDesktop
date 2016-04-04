drop procedure if exists insertVeld;
delimiter #
create procedure insertVeld()
begin

declare v_max int unsigned default 10;
declare v_count int unsigned default 1;
declare rij int unsigned default 0;
declare kolom int unsigned default 0;
declare spelnaamvar varchar(30) default 'Sung Yong'; -- spelnaam hier invullen
declare volgordeIDvar int default 1; -- volgorde ID hier invullen
declare mapcodevar varchar(100);
set mapcodevar = (select MapCode from spelbord where VolgordeID=volgordeIDvar and Spel_Spelnaam=spelnaamvar);
-- select mapcodevar;
start transaction;
  while rij < v_max do
	set kolom=0;
		while kolom < v_max do
                    if(SELECT SUBSTRING(mapcodevar, v_count, 1) = 'o') then
                        insert into veld values(true,kolom,rij,spelnaamvar,volgordeIDvar); 
                    end if;
                    if(SELECT SUBSTRING(mapcodevar, v_count, 1) = '.') then
                        insert into veld values(false,kolom,rij,spelnaamvar,volgordeIDvar);
                    end if;
                    if(SELECT SUBSTRING(mapcodevar, v_count, 1) = '#') then
						insert into veld values(false,kolom,rij,spelnaamvar,volgordeIDvar);
                        Insert into muur(Veld_XCoord, Veld_Ycoord,Veld_spelbord_spel_spelnaam,Veld_spelbord_spel_volgordeID) value (kolom,rij,spelnaamvar,volgordeIDvar);
                    end if;
                    if (SELECT SUBSTRING(mapcodevar, v_count, 1) = '@') then
						insert into veld values(false,kolom,rij,spelnaamvar,volgordeIDvar);
                        Insert into mannetje(Veld_XCoord, Veld_Ycoord,Veld_spelbord_spel_spelnaam,Veld_spelbord_spel_volgordeID) value (kolom,rij,spelnaamvar,volgordeIDvar);
                    end if;
                    if(SELECT SUBSTRING(mapcodevar, v_count, 1)= 'K') then
						insert into veld values(false,kolom,rij,spelnaamvar,volgordeIDvar);
                        Insert into kist(Veld_XCoord, Veld_Ycoord,Veld_spelbord_spel_spelnaam,Veld_spelbord_spel_volgordeID) value (kolom,rij,spelnaamvar,volgordeIDvar);
                    end if;
            set v_count=v_count+1;
            set kolom=kolom+1;
		end while;
    set rij=rij+1;
  end while;
  commit;
end #

delimiter ;

call insertVeld();
