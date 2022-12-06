USE carrental;
-- test entries
INSERT INTO car (frameNumber, model, manufacturer, isManual, accessories, CO2discharge, status, 3MonthsPrice, 6MonthsPrice, 12MonthsPrice, 24MonthsPrice, 36MonthsPrice) VALUES ('8HSGDS8H242', '500e', 'FIAT', '0', 'none', '0', 'OnStock', 2999, 3299, 3499, 3599, -1);
INSERT INTO car (frameNumber, model, manufacturer, isManual, accessories, CO2discharge, status, 3MonthsPrice, 6MonthsPrice, 12MonthsPrice, 24MonthsPrice, 36MonthsPrice) VALUES ('9235JFS32RK', 'C1 Le Mans', 'CITRÖEN', '1', 'none', '109', 'OnStock', 3799, 3299, 3099, 2899, 2609);
INSERT INTO car (frameNumber, model, manufacturer, isManual, accessories, CO2discharge, status, 3MonthsPrice, 6MonthsPrice, 12MonthsPrice, 24MonthsPrice, 36MonthsPrice) VALUES ('8HSGDS8H242', '108', 'PEUGEOT', '1', 'none', '111', 'OnStock', 3899, 3299, 2999, 2899, 2799);

delete from rentalagreements where vehicleNumber = 4;

delete from rentalagreements where vehicleNumber = 6;

alter table rentalagreements add column overdrivenCost double;

delete from car where vehicleNumber = 8;

alter table rentalagreements add column customerName varchar(50);

delete from rentalagreements where kilometerPerMonth = 1500;

alter table car add column color varchar(50);

alter table rentalagreements add column endDate date;