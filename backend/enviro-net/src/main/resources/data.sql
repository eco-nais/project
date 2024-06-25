-- Users
INSERT INTO users.users (email, enabled, name, surname, last_password_reset_date, password, phone_number, username, role, active, date_of_birth, gender)
VALUES
    ('peraperic@gmail.com', true, 'Pera', 'Perić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245234', 'peraperic', 'ADMINISTRATOR', true, '1990-01-01', 'MALE'),
    ('markoni@gmail.com', true, 'Marko', 'Milošević', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245321', 'markom', 'REGISTERED_USER', true, '1985-05-15', 'MALE'),
    ('tati@gmail.com', true, 'Tatjana', 'Ljubičić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245987', 'ljubi.tatjana', 'REGISTERED_USER', true, '1978-11-25', 'FEMALE'),
    ('nikolagoric@gmail.com', true, 'Nikola', 'Gorić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245876', 'nikola.g', 'REGISTERED_USER', true, '1995-09-10', 'MALE'),
    ('jelastef@gmail.com', true, 'Jelena', 'Stefanović', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245432', 'ac1', 'ACCOUNTANT', true, '1980-03-20', 'FEMALE'),
    ('helena.jankovic@gmail.com', true, 'Helena', 'Janković', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+381651231234', 'ac2', 'ACCOUNTANT', true, '1987-03-28', 'FEMALE'),
    ('goranniko@gmail.com', false, 'Goran', 'Nikolić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245678', 'niko.goran', 'BOARD_MEMBER', false, '1975-07-12', 'MALE'),
    ('markovicmil@gmail.com', true, 'Miloš', 'Marković', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245123', 'milosm', 'BOARD_MEMBER', true, '1989-12-30', 'MALE'),
    ('anamatic@gmail.com', true, 'Ana', 'Matić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245234', 'ana.m', 'BOARD_MEMBER', true, '1977-04-05', 'FEMALE'),
    ('anastasijapetrovic@gmail.com', true, 'Anastasija', 'Petrović', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245876', 'anapetrovic', 'PROJECT_MANAGER', true, '1992-08-18', 'FEMALE'),
    ('stevamil@gmail.com', true, 'Stevan', 'Milićić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245321', 'stevaj', 'PROJECT_MANAGER', true, '1983-06-25', 'MALE'),
    ('stefanstefanovic@gmail.com', true, 'Stefan', 'Belić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245987', 'stefans', 'PROJECT_COORDINATOR', true, '1987-10-15', 'MALE'),
    ('micaj@gmail.com', true, 'Milica', 'Jović', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245432', 'mica', 'PROJECT_COORDINATOR', true, '1990-02-28', 'FEMALE'),
    ('marijamarijanovic@gmail.com', false, 'Marija', 'Marijanović', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245678', 'marija.m', 'EDUCATOR', true, '1986-09-07', 'FEMALE'),
    ('ljubabuba@gmail.com', true, 'Ljubica', 'Atić', null, '$2a$10$siTymq2.Fp0J/K6vnBernO/vOXAHUXa4BCRFp14yoJvJbvE8qckGW', '+38763245123', 'ljubica.a', 'EDUCATOR', true, '1994-03-12', 'FEMALE');

INSERT INTO users.notifications (user_id, is_read, created_on, title, description, link)
VALUES
    (2, false, '2024-05-26 08:00:00.000', 'Welcome to our platform!', 'Thank you for joining us.', null),
    (2, true, '2024-05-26 08:30:00.000', 'New Feature Announcement', 'Exciting news! We''ve just released a new feature. Check it out!', null),
    (3, false, '2024-05-26 09:30:00.000', 'Important Announcement', 'Please read the latest update on our website.', null),
    (4, true, '2024-05-26 09:50:00.000', 'Holiday Closure Notice', 'Please note that our office will be closed for the upcoming holiday.', null),
    (5, false, '2024-05-26 10:45:00.000', 'Account Activation', 'Your account has been successfully activated.', null),
    (6, false, '2024-05-26 11:00:00.000', 'Account Verification Required', 'Action required: Please verify your email address to complete your account setup.', null),
    (7, false, '2024-05-26 11:20:00.000', 'Reminder: Upcoming Event', 'Don''t forget about the upcoming event tomorrow.', null),
    (8, true, '2024-05-26 12:40:00.000', 'Congratulations!', 'You''ve reached a new milestone. Keep up the great work!', null),
    (9, false, '2024-05-26 12:15:00.000', 'Project Update', 'Check out the latest progress report for the project.', null),
    (10, false, '2024-05-26 13:45:00.000', 'Survey Invitation', 'We value your opinion. Please take a moment to complete our survey.', null),
    (11, false, '2024-05-26 13:30:00.000', 'Training Session Reminder', 'Reminder: Training session scheduled for next week.', null),
    (12, false, '2024-05-26 14:00:00.000', 'Maintenance Scheduled', 'Important notice: Maintenance activity is scheduled for later this week. Stay tuned for updates.', null),
    (13, false, '2024-05-26 14:45:00.000', 'New Assignment', 'You have been assigned a new task. Please review it.', null),
    (14, true, '2024-05-26 15:30:00.000', 'Task Completed', 'Your task has been successfully completed. Well done!', null),
    (15, false, '2024-05-26 15:20:00.000', 'Feedback Request', 'We would appreciate your feedback on our services.', null);

-- Education
INSERT INTO education.lecture_category (description)
VALUES
    ('Forest Ecosystems'),
    ('Marine Biology'),
    ('Desert Wildlife'),
    ('Arctic Exploration'),
    ('Rainforest Ecology');

INSERT INTO education.lecture (difficulty, max_recommended_age, min_recommended_age, creator_id, content, name)
VALUES
    (1, 12, 8, 15, E'# Introduction to Forest Ecosystems\n' ||
                   E'This lecture provides an overview of forest ecosystems, covering various aspects such as biodiversity, ecological interactions, and human impact on forests.\n' ||
                   E'Forests are vital for the health of our planet, serving as habitats for countless species and playing a crucial role in carbon sequestration.\n' ||
                   E'In this session, we will explore the different layers of a forest, from the forest floor to the emergent layer, and discuss the unique characteristics of each.\n' ||
                   E'Additionally, we will delve into the various types of forests found around the world, including tropical rainforests, temperate forests, and boreal forests.\n' ||
                   E'Throughout the lecture, we will examine the complex relationships between plants, animals, and microorganisms that make up forest ecosystems.\n' ||
                   E'Topics such as nutrient cycling, energy flow, and ecological succession will be explored to provide a comprehensive understanding of forest dynamics.\n' ||
                   E'Furthermore, we will address the threats facing forests today, such as deforestation, habitat fragmentation, and climate change, and discuss strategies for conservation and sustainable management.\n' ||
                   E'By the end of this lecture, participants will gain a deeper appreciation for the importance of forests and the urgent need to protect them for future generations.\n',
     'Forest 101'),
    (2, 14, 10, 15, E'# Exploring Marine Habitats\n' ||
                    E'Marine habitats encompass a vast and diverse array of ecosystems, ranging from coral reefs and kelp forests to deep-sea trenches and hydrothermal vents.\n' ||
                    E'In this lecture, we will embark on a journey beneath the waves to discover the fascinating world of marine life.\n' ||
                    E'We will begin by examining the unique physical and chemical properties of the ocean that shape marine habitats and influence the distribution of species.\n' ||
                    E'From there, we will explore the incredible diversity of marine organisms, from microscopic plankton to majestic whales, and everything in between.\n' ||
                    E'Participants will learn about the different zones of the ocean, including the sunlit surface waters, the twilight zone, and the dark depths of the abyss.\n' ||
                    E'Throughout the lecture, we will discuss the various adaptations that enable marine organisms to thrive in their respective environments, from camouflage and bioluminescence to hydrodynamic shapes and specialized organs.\n' ||
                    E'We will also examine the complex ecological relationships that exist within marine ecosystems, such as predator-prey interactions, symbiotic partnerships, and competitive exclusion.\n' ||
                    E'Furthermore, we will address the human impacts on marine habitats, including pollution, overfishing, and climate change, and explore potential solutions to mitigate these threats.\n' ||
                    E'By the end of this lecture, participants will develop a deeper understanding of the importance of marine habitats and the urgent need to conserve and protect them for future generations.\n',
     'Marine Life Overview'),
    (0, 10, 6, 15, E'# Adaptations of Desert Animals\n' ||
                   E'Deserts are harsh and unforgiving environments characterized by extreme temperatures, sparse vegetation, and limited water resources.\n' ||
                   E'Despite these challenges, desert animals have evolved a remarkable array of adaptations to survive in this hostile habitat.\n' ||
                   E'In this lecture, we will explore the fascinating world of desert wildlife and uncover the secrets of their survival.\n' ||
                   E'We will begin by discussing the physical adaptations of desert animals, such as camels'' humps for water storage, kangaroos'' efficient kidneys for water conservation, and fennec foxes'' large ears for heat regulation.\n' ||
                   E'Participants will learn about the behavioral adaptations employed by desert animals to cope with the scarcity of resources, including nocturnal activity to avoid daytime heat, burrowing to escape temperature extremes, and communal living for protection.\n' ||
                   E'Furthermore, we will examine the ecological roles played by desert species in maintaining ecosystem balance, such as pollination, seed dispersal, and predator-prey dynamics.\n' ||
                   E'Throughout the lecture, we will highlight some of the iconic desert animals, including the Arabian camel, the desert tortoise, the meerkat, and the sidewinder rattlesnake.\n' ||
                   E'We will also discuss the conservation challenges facing desert ecosystems, including habitat destruction, climate change, and human encroachment, and explore potential conservation strategies.\n' ||
                   E'By the end of this lecture, participants will gain a newfound appreciation for the resilience and adaptability of desert animals and the importance of conserving their fragile habitats.\n',
        'Desert Survival'),
    (1, 16, 12, 15, E'# Life in the Arctic\n' ||
                    E'The Arctic is a vast and remote region located at the northernmost part of Earth, characterized by icy landscapes, extreme cold, and unique wildlife.\n' ||
                    E'In this lecture, we will embark on an expedition to explore the wonders of the Arctic and learn about the remarkable adaptations of its inhabitants.\n' ||
                    E'We will begin by examining the physical geography of the Arctic, including its ice caps, glaciers, and permafrost, and discuss the role of the Arctic in regulating global climate patterns.\n' ||
                    E'Participants will discover the rich biodiversity of the Arctic, from polar bears and Arctic foxes to seals, whales, and migratory birds.\n' ||
                    E'We will delve into the unique adaptations of Arctic animals, such as thick fur and blubber for insulation, specialized paws for walking on ice, and hibernation strategies to survive the long winter months.\n' ||
                    E'Furthermore, we will explore the cultural heritage of the indigenous peoples of the Arctic, including their traditional knowledge, art, and ways of life.\n' ||
                    E'Throughout the lecture, we will address the environmental challenges facing the Arctic, including melting sea ice, habitat loss, and pollution, and discuss efforts to protect this fragile ecosystem.\n' ||
                    E'By the end of this lecture, participants will develop a deeper understanding of the Arctic ecosystem and the urgent need to conserve its biodiversity and cultural heritage for future generations.\n',
     'Arctic Adventures'),
    (0, 8, 4, 15, E'# Discovering Rainforest Biodiversity\n' ||
                  E'Rainforests are some of the most biodiverse ecosystems on Earth, teeming with life and exhibiting a remarkable array of plants, animals, and microorganisms.\n' ||
                  E'In this lecture, we will venture into the heart of the rainforest to uncover its hidden treasures and explore the wonders of its biodiversity.\n' ||
                  E'We will begin by discussing the structure and layers of the rainforest, from the emergent layer to the forest floor, and examine the unique adaptations of plants and animals to life in this dense environment.\n' ||
                  E'Participants will learn about the intricate web of ecological relationships that exist within the rainforest, including symbiotic interactions, predator-prey dynamics, and nutrient cycling.\n' ||
                  E'We will highlight some of the iconic species found in the rainforest, such as jaguars, toucans, orchids, and giant ceiba trees, and discuss their ecological importance.\n' ||
                  E'Furthermore, we will explore the cultural significance of rainforests to indigenous peoples, including their traditional knowledge, spiritual beliefs, and sustainable practices.\n' ||
                  E'Throughout the lecture, we will address the threats facing rainforest ecosystems, including deforestation, habitat fragmentation, and climate change, and discuss strategies for conservation and sustainable management.\n' ||
                  E'By the end of this lecture, participants will develop a deeper appreciation for the beauty and complexity of rainforest biodiversity and the urgent need to protect these vital ecosystems.\n',
     'Rainforest Wonders');

INSERT INTO education.has_category (category_id, lecture_id)
VALUES
    (1, 1), -- Forest 101 belongs to Forest Ecosystems category
    (2, 2), -- Marine Life Overview belongs to Marine Biology category
    (3, 3), -- Desert Survival belongs to Desert Wildlife category
    (4, 4), -- Arctic Adventures belongs to Arctic Exploration category
    (5, 5), -- Rainforest Wonders belongs to Rainforest Ecology category
    (1, 3), -- Desert Survival also belongs to Forest Ecosystems category
    (3, 1); -- Forest 101 also belongs to Desert Wildlife category

-- Questions and answers for Lecture: Introduction to Forest Ecosystems
INSERT INTO education.question (order_in_lecture, type, lecture_id, content)
VALUES
    -- Questions for "Introduction to Forest Ecosystems" lecture
    (1, 0, 1, 'What are some key characteristics of forest ecosystems?'),
    (2, 1, 1, 'True or false: Forests play a minor role in carbon sequestration?'),
    (3, 2, 1, 'What is the primary threat to forests today?');

INSERT INTO education.answer (is_correct, question_id, content)
VALUES
    -- Answers for the first question
    (TRUE, 1, 'High biodiversity, carbon sequestration, ecological interactions'),
    (FALSE, 1, 'Low biodiversity, carbon emissions, ecological isolation'),
    (FALSE, 1, 'High pollution, low biodiversity, extreme temperatures'),

    -- Answers for the second question
    (FALSE, 2, 'True'),
    (TRUE, 2, 'False'),

    -- Answers for the third question
    (TRUE, 3, 'Deforestation'),
    (FALSE, 3, 'Ocean pollution'),
    (FALSE, 3, 'Desertification');

-- Questions and answers for Lecture: Exploring Marine Habitats
INSERT INTO education.question (order_in_lecture, type, lecture_id, content)
VALUES
    -- Questions for "Exploring Marine Habitats" lecture
    (1, 0, 2, 'What are some examples of marine habitats?'),
    (2, 1, 2, 'Which zone of the ocean receives the least amount of sunlight?'),
    (3, 2, 2, 'What are some human impacts on marine habitats?');

INSERT INTO education.answer (is_correct, question_id, content)
VALUES
    -- Answers for the first question
    (TRUE, 4, 'Coral reefs, kelp forests, deep-sea trenches'),
    (FALSE, 4, 'Tundra, rainforests, deserts'),
    (FALSE, 4, 'Mountains, lakes, rivers'),

    -- Answers for the second question
    (TRUE, 5, 'Abyssal zone'),
    (FALSE, 5, 'Sunlit zone'),
    (FALSE, 5, 'Twilight zone'),

    -- Answers for the third question
    (TRUE, 6, 'Pollution'),
    (TRUE, 6, 'Overfishing'),
    (TRUE, 6, 'Climate change');

-- Questions and answers for Lecture: Adaptations of Desert Animals
INSERT INTO education.question (order_in_lecture, type, lecture_id, content)
VALUES
    -- Questions for "Adaptations of Desert Animals" lecture
    (1, 0, 3, 'What are some physical adaptations of desert animals?'),
    (2, 1, 3, 'True or false: Desert animals are primarily active during the day?'),
    (3, 2, 3, 'What conservation challenges do desert ecosystems face?');

INSERT INTO education.answer (is_correct, question_id, content)
VALUES
    -- Answers for the first question
    (TRUE, 7, 'Water storage, efficient kidneys, large ears'),
    (FALSE, 7, 'Gills, fins, scales'),
    (FALSE, 7, 'Wings, feathers, beaks'),

    -- Answers for the second question
    (FALSE, 8, 'True'),
    (TRUE, 8, 'False'),

    -- Answers for the third question
    (TRUE, 9, 'Habitat destruction'),
    (TRUE, 9, 'Climate change'),
    (TRUE, 9, 'Human encroachment');

-- Questions and answers for Lecture: Life in the Arctic
INSERT INTO education.question (order_in_lecture, type, lecture_id, content)
VALUES
    -- Questions for "Life in the Arctic" lecture
    (1, 0, 4, 'What are some characteristics of the Arctic ecosystem?'),
    (2, 1, 4, 'True or false: Arctic animals do not need to adapt to extreme cold?'),
    (3, 2, 4, 'What is a significant threat to the Arctic ecosystem?');

INSERT INTO education.answer (is_correct, question_id, content)
VALUES
    -- Answers for the first question
    (TRUE, 10, 'Extreme cold, ice caps, polar bears'),
    (FALSE, 10, 'Deserts, heat, camels'),
    (FALSE, 10, 'Tropical climate, rainforests, jaguars'),

    -- Answers for the second question
    (FALSE, 11, 'True'),
    (TRUE, 11, 'False'),

    -- Answers for the third question
    (TRUE, 12, 'Melting sea ice'),
    (FALSE, 12, 'Desertification'),
    (FALSE, 12, 'Volcanic eruptions');

-- Questions and answers for Lecture: Discovering Rainforest Biodiversity
INSERT INTO education.question (order_in_lecture, type, lecture_id, content)
VALUES
    -- Questions for "Discovering Rainforest Biodiversity" lecture
    (1, 0, 5, 'What makes rainforests one of the most biodiverse ecosystems?'),
    (2, 1, 5, 'True or false: Rainforests have low ecological importance?'),
    (3, 2, 5, 'What are some conservation challenges facing rainforests?');

INSERT INTO education.answer (is_correct, question_id, content)
VALUES
    -- Answers for the first question
    (TRUE, 13, 'High number of species, dense vegetation, complex interactions'),
    (FALSE, 13, 'Low number of species, sparse vegetation, simple interactions'),
    (FALSE, 13, 'Moderate number of species, moderate vegetation, moderate interactions'),

    -- Answers for the second question
    (FALSE, 14, 'True'),
    (TRUE, 14, 'False'),

    -- Answers for the third question
    (TRUE, 15, 'Deforestation'),
    (TRUE, 15, 'Habitat fragmentation'),
    (TRUE, 15, 'Climate change');

INSERT INTO education.test_execution (finished, points, lecture_id, user_id)
VALUES
    (TRUE, 25, 1, 2),
    (TRUE, 15, 1, 3),
    (TRUE, 5, 1, 4),
    (TRUE, 23, 2, 2),
    (TRUE, 11, 2, 3),
    (FALSE, 0, 2, 4),
    (TRUE, 7, 3, 2),
    (TRUE, 2, 5, 3),
    (TRUE, 2, 5, 4);

INSERT INTO education.answered_question (lecture_id, question_id, user_id, text_answer) VALUES
    (5, 14, 3, null),
    (5, 15, 3, 'Deforestation'),
    (5, 13, 3, null);

INSERT INTO education.submitted_answers (answer_id, submission_id) VALUES
    (37, 1),
    (38, 1),
    (34, 3);

INSERT INTO education.answered_question (lecture_id, question_id, user_id, text_answer) VALUES
    (5, 14, 4, null),
    (5, 15, 4, 'asd'),
    (5, 13, 4, null);

INSERT INTO education.submitted_answers (answer_id, submission_id) VALUES
    (37, 4),
    (38, 5),
    (34, 6);
