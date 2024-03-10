CREATE TABLE [recipe_ingredients] (
  [RecipeIngredients_ID] integer IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Ingredient_ID] integer NOT NULL,
  [Recipe_ID] integer NOT NULL,
  [Quantity] integer NULL,
  [Unit_ID] integer NOT NULL
)
GO

ALTER TABLE [recipe_ingredients] ADD FOREIGN KEY ([Ingredient_ID]) REFERENCES [Ingredient] ([Ingredient_ID])
GO

ALTER TABLE [recipe_ingredients] ADD FOREIGN KEY ([Recipe_ID]) REFERENCES [Recipe] ([Recipe_ID])
GO

ALTER TABLE [recipe_ingredients] ADD FOREIGN KEY ([Unit_ID]) REFERENCES [Unit] ([Unit_ID])
GO

INSERT INTO [recipe_ingredients] ([Ingredient_ID], [Recipe_ID], [Quantity], [Unit_ID])
VALUES
(1, 2, 2, 1),
(1, 2, 2, 1),
(1, 5, 1, 1),
(2, 5, 1, 2),
(1, 6, 1, 1),
(3, 6, 1, 2);
