USE BeanHubDB;
GO

CREATE TABLE [User] (
  [User_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Username] varchar(50) NOT NULL
)
GO

CREATE TABLE [Recipe] (
  [Recipe_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Recipe_Name] varchar(50) NULL,
  [Recipe_Short_Description] varchar(255) NULL,
  [Prep_Time] integer NULL,
  [Cooking_Time] integer NULL,
  [User_ID] integer NOT NULL,
  [Recipe_Steps] varchar (255) NULL
)
GO

CREATE TABLE [Rating] (
  [Rating_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [User_ID] integer NOT NULL,
  [Recipe_ID] integer NOT NULL,
  [Rating_Value] integer NULL,
  [Rating_Date_Time] datetime NULL
)
GO

CREATE TABLE [Ingredient] (
  [Ingredient_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Ingredient_Name] varchar(50) NULL,
  [Ingredient_Short_Description] varchar(255) NULL
)
GO

CREATE TABLE [Unit] (
  [Unit_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Unit_Name] varchar(50) NULL
)
GO

CREATE TABLE [RecipeIngredients] (
  [RecipeIngredients_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Ingredient_ID] integer NOT NULL,
  [Recipe_ID] integer NOT NULL,
  [Quantity] integer NULL,
  [Unit_ID] integer NOT NULL
)
GO

ALTER TABLE [Recipe] ADD FOREIGN KEY ([User_ID]) REFERENCES [User] ([User_ID])
GO

ALTER TABLE [Rating] ADD FOREIGN KEY ([User_ID]) REFERENCES [User] ([User_ID])
GO

ALTER TABLE [Rating] ADD FOREIGN KEY ([Recipe_ID]) REFERENCES [Recipe] ([Recipe_ID])
GO

ALTER TABLE [RecipeIngredients] ADD FOREIGN KEY ([Ingredient_ID]) REFERENCES [Ingredient] ([Ingredient_ID])
GO

ALTER TABLE [RecipeIngredients] ADD FOREIGN KEY ([Recipe_ID]) REFERENCES [Recipe] ([Recipe_ID])
GO

ALTER TABLE [RecipeIngredients] ADD FOREIGN KEY ([Unit_ID]) REFERENCES [Unit] ([Unit_ID])
GO