USE BeanHub

INSERT INTO [User] ([Username])
VALUES
  ('MerlinTheCoder'),
  ('AureliaSQL'),
  ('PixelPioneer'),
  ('BinaryBard'),
  ('RubyRogue'),
  ('SyntaxSorcerer'),
  ('DataDuchess'),
  ('CaptainCode'),
  ('GraphGoddess'),
  ('CyberKnight'),
  ('QueryQueen'),
  ('CascadingCleric'),
  ('AlchemyApprentice'),
  ('BitwiseBard'),
  ('RecursiveRuler'),
  ('SchemaScribe'),
  ('JoinsJester'),
  ('NullNinja'),
  ('SyntaxSiren'),
  ('CursorCrafter');

INSERT INTO [Ingredient] ([Ingredient_Name])
VALUES
  ('Black Beans'),
  ('Kidney Beans'),
  ('Lima Beans'),
  ('Chickpeas'),
  ('Pinto Beans'),
  ('Navy Beans'),
  ('Cannellini Beans'),
  ('Red Lentils'),
  ('Green Lentils'),
  ('Split Peas'),
  ('Mung Beans'),
  ('Adzuki Beans'),
  ('Black-Eyed Peas'),
  ('Garbanzo Beans'),
  ('Great Northern Beans'),
  ('Yellow Split Peas'),
  ('Fava Beans'),
  ('Lentil Sprouts'),
  ('Cranberry Beans'),
  ('Butter Beans');

INSERT INTO [Unit] ([Unit_Name])
VALUES
  ('Grams'),
  ('Kilograms'),
  ('Milliliters'),
  ('Liters'),
  ('Teaspoons'),
  ('Tablespoons'),
  ('Cups'),
  ('Ounces'),
  ('Pounds'),
  ('Pinches'),
  ('Handfuls'),
  ('Dash'),
  ('Sprigs'),
  ('Cloves'),
  ('Slices'),
  ('Leaves'),
  ('Whole'),
  ('Drops'),
  ('Zests'),
  ('Fillets');

INSERT INTO [Recipe] ([Recipe_Name], [Recipe_Short_Description], [Prep_Time], [Cooking_Time], [User_ID], [Recipe_Steps])
VALUES
  ('Lentil Delight', 'Hearty red lentil soup with a touch of lemon', 15, 30, 1, '1. Sauté onions and garlic. 2. Add red lentils, vegetable broth, and lemon zest. 3. Simmer until lentils are tender. 4. Garnish with fresh parsley.'),
  ('Bean Fiesta Salad', 'A colorful salad bursting with kidney beans, corn, and bell peppers', 10, 0, 2, '1. Mix kidney beans, corn, diced bell peppers, and red onion. 2. Drizzle with olive oil and lime juice. 3. Season with salt, pepper, and cumin. 4. Serve chilled.'),
  ('Chickpea Curry', 'Creamy chickpeas simmered in aromatic spices', 20, 25, 3, '1. Sauté onions, garlic, and ginger. 2. Add chickpeas, tomatoes, and coconut milk. 3. Season with curry powder, turmeric, and cayenne. 4. Simmer until flavors meld.'),
  ('Magical Lentil Stew', 'A medley of green lentils, carrots, and celery', 15, 40, 4, '1. Sauté carrots, celery, and onions. 2. Add green lentils, vegetable broth, and thyme. 3. Simmer until lentils are tender. 4. Sprinkle with love.'),
  ('Bean Sprout Stir-Fry', 'Crisp mung bean sprouts wok-tossed with garlic and soy sauce', 10, 8, 5, '1. Heat oil in a wok. 2. Add minced garlic and sliced bell peppers. 3. Toss in mung bean sprouts. 4. Drizzle with soy sauce. 5. Serve hot.'),
  ('Lentil and Spinach Salad', 'A refreshing salad with red lentils, baby spinach, and feta', 15, 0, 6, '1. Cook red lentils until tender. 2. Toss with baby spinach, crumbled feta, and lemon vinaigrette. 3. Top with toasted pine nuts.'),
  ('Bean Burrito Bowl', 'Black beans, rice, avocado, and salsa in a fiesta bowl', 10, 20, 7, '1. Layer cooked rice, black beans, diced avocado, and salsa. 2. Garnish with cilantro and lime wedges. 3. Ole!'),
  ('Lentil Shepherd’s Pie', 'Red lentils and mashed sweet potatoes in cozy harmony', 20, 35, 8, '1. Sauté onions, carrots, and garlic. 2. Add red lentils and vegetable broth. 3. Top with mashed sweet potatoes. 4. Bake until golden.'),
  ('Bean and Corn Chowder', 'A creamy blend of navy beans, corn, and thyme', 15, 30, 9, '1. Sauté onions and celery. 2. Add navy beans, corn, and vegetable broth. 3. Simmer until flavors meld. 4. Garnish with fresh thyme.'),
  ('Lentil Spinach Curry', 'A fragrant curry with green lentils and baby spinach', 20, 25, 10, '1. Sauté onions, ginger, and garlic. 2. Add green lentils, coconut milk, and curry spices. 3. Fold in baby spinach. 4. Serve with naan.'),
  ('Bean and Quinoa Salad', 'A protein-packed salad with black beans, quinoa, and lime dressing', 15, 0, 11, '1. Cook quinoa and let it cool. 2. Mix with black beans, diced bell peppers, and red onion. 3. Drizzle with lime dressing (lime juice, olive oil, salt, and pepper). 4. Garnish with fresh cilantro. 5. Serve chilled.');

  INSERT INTO [Rating] ([User_ID], [Recipe_ID], [Rating_Value], [Rating_Date_Time])
VALUES
  (11, 1, 5, '2024-03-11 18:30:00'), 
  (12, 1, 4, '2024-03-11 19:15:00'), 
  (13, 3, 3, '2024-03-11 20:00:00'), 
  (14, 4, 5, '2024-03-11 20:45:00'), 
  (15, 2, 2, '2024-03-11 21:30:00'), 
  (16, 4, 4, '2024-03-11 22:15:00'), 
  (17, 4, 5, '2024-03-11 23:00:00'), 
  (18, 8, 3, '2024-03-11 23:45:00'), 
  (19, 3, 4, '2024-03-12 00:30:00'), 
  (20, 8, 1, '2024-03-12 01:15:00');

INSERT INTO [RecipeIngredients] ([Ingredient_ID], [Recipe_ID], [Quantity], [Unit_ID])
VALUES
  (3, 1, 200, 1),  
  (8, 2, 2, 6),   
  (14, 3, 1, 7),  
  (19, 4, 3, 2),  
  (5, 5, 1, 8),    
  (11, 6, 1, 9),  
  (17, 7, 4, 3),   
  (2, 8, 1, 10),   
  (9, 9, 1, 11),   
  (20, 10, 2, 5);
  