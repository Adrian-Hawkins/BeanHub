ALTER TABLE [Recipe] 
ADD CONSTRAINT DF_Recipe_DateAdded DEFAULT GETDATE() FOR Date_Added;