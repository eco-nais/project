-- Finance
INSERT INTO users.organization_members (id, wage, working_hours, overtime_wage)
VALUES
    (5, 18.7, 7, 20),
    (6, 18.7, 7, 20),
    (7, 19, 7, 20.5),
    (8, 19, 7, 20.5),
    (9, 19, 7, 20.5),
    (10, 18.7, 8, 20),
    (11, 18.7, 8, 20),
    (12, 18.7, 8, 20),
    (13, 18.7, 8, 20),
    (14, 18.7, 6, 21),
    (15, 18.7, 6, 21.2);

INSERT INTO finance.fixed_expenses (type, expense_type,employee, overtime_hours, start_date, end_date, amount, creator, created_on, description)
VALUES
    -- FixedExpenses - January 2024
    ('SALARY', 'SALARY',  5, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY',  6, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY',  7, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY',  8, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 10, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 11, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 12, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 13, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 14, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('SALARY', 'SALARY', 15, 0, '2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    ('RENT', 'FixedExpenses', null, null, '2024-01-01', '2024-02-01', 5000, 5, '2024-02-12 12:00:00.000', 'Rent for January 2024');

INSERT INTO finance.budget_plan (status,  start_date, end_date, name, description, author, last_updated_on_date)
VALUES
    ('DRAFT', '2024-01-01', '2024-12-31', '2024 Annual Budget', 'Annual Budget Plan', 5, '2024-04-04'),
    ('DRAFT', '2025-01-01', '2025-12-31', '2025 Annual Budget', 'Annual Budget Plan', 5, '2024-04-04'),
    ('PENDING', '2024-04-01', '2024-06-30', 'Q2 2024 Budget', 'Quarterly Budget Plan', 5, '2024-04-04'),
    ('APPROVED', '2024-01-01', '2024-12-31', '2024 Annual Budget (Approved)', 'Approved Annual Budget Plan', 5, '2024-04-04'),
    ('REJECTED', '2024-01-01', '2024-12-31', '2024 Annual Budget (Rejected)', 'Rejected Annual Budget Plan', 5, '2024-04-04'),
    ('CLOSED', '2024-01-01', '2024-12-31', '2024 Annual Budget (Closed)', 'Closed Annual Budget Plan', 5, '2024-04-04'),
    ('DRAFT', '2026-01-01', '2026-12-31', '2026 Annual Budget', 'Annual Budget Plan', 5, '2024-04-04'),
    ('DRAFT', '2027-01-01', '2027-12-31', '2027 Annual Budget', 'Annual Budget Plan', 6, '2024-04-04'),
    ('PENDING', '2024-07-01', '2024-09-30', 'Q3 2024 Budget', 'Quarterly Budget Plan', 5, '2024-04-04'),
    ('APPROVED', '2025-01-01', '2025-12-31', '2025 Annual Budget (Approved)', 'Approved Annual Budget Plan', 6, '2024-04-04'),
    ('REJECTED', '2024-07-01', '2024-12-31', '2024 Annual Budget (Rejected)', 'Rejected Annual Budget Plan', 6, '2024-04-04');

INSERT INTO finance.fixed_expenses_estimation
(id, budget_plan, fixed_expense_id, type, employee, overtime_hours, start_date, end_date, amount, creator, created_on, description)
VALUES
    (-1, 1, 0,   'RENT', null, 0,'2024-01-01', '2024-02-01', 3500, 5, '2024-02-12 12:00:00.000', 'Rent for January 2024'),
    (-2, 1, 0, 'SALARY',    5, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-3, 1, 0, 'SALARY',    6, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-4, 1, 0, 'SALARY',    7, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-5, 1, 0, 'SALARY',    8, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-6, 1, 0, 'SALARY',    9, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-7, 1, 0, 'SALARY',   10, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-8, 1, 0, 'SALARY',   11, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-9, 1, 0, 'SALARY',   12, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-10, 1, 0, 'SALARY',   13, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-11, 1, 0, 'SALARY',   14, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', ''),
    (-12, 1, 0, 'SALARY',   15, 0,'2024-01-01', '2024-02-01', 2350, 5, '2024-02-12 12:00:00.000', '');

INSERT INTO finance.organization_goal (
    creator, status, title, start_date, end_date, description, rationale, priority)
VALUES
    (9, 'VALID', 'green energy', '2024-01-01', null, 'Focuses on promoting the adoption and utilization of renewable energy sources such as solar, wind, and hydroelectric power to reduce carbon emissions. ', 'By investing in green energy initiatives, the organization aims to transition towards a sustainable and environmentally friendly energy infrastructure.', 1),
    (8, 'VALID', 'green knowledge', '2024-01-01', null, 'Aims to educate and raise awareness about environmental conservation, sustainability practices, and the importance of preserving natural resources. ', 'Organization seeks to empower individuals and communities with the knowledge and skills needed to contribute to a greener and healthier planet.', 1),
    (8, 'VALID', 'clean air', '2024-01-01', null, 'This goal is dedicated to combating air pollution and improving air quality in urban and industrial areas. ', 'Organization aims to ensure that everyone has access to clean and breathable air, safeguarding public health and the environment.', 1),
    (8, 'VALID', 'green city', '2024-01-01', null, 'Envisions transforming urban spaces into sustainable and eco-friendly environments that prioritize green infrastructure', 'Organization seeks to promote sustainable urban development practices that enhance quality of life, biodiversity, and resilience to climate change.', 1),
    (9, 'VALID', 'nature', '2024-01-01', null, 'Focuses on preserving and protecting natural ecosystems, biodiversity hotspots, and endangered species. ', 'Organization aims to safeguard natural habitats and biodiversity, ensuring that future generations can continue to benefit from the beauty and resources that nature provides.', 1),
    (9, 'ARCHIVED', 'renewable energy expansion', '2023-01-01', '2024-01-01', 'Aims to accelerate the deployment and adoption of renewable energy technologies such as solar, wind, and biomass', 'The organization strives to lead the transition to a sustainable energy future by investing in renewable energy infrastructure and fostering innovation in clean energy technologies.', 1),
    (8, 'ARCHIVED', 'sustainable agriculture promotion', '2023-01-01', '2024-01-01', 'Focuses on promoting sustainable farming practices such as organic farming, agroforestry, and permaculture to enhance food security, protect ecosystems, and mitigate climate change.', 'Organization aims to empower farmers with sustainable agricultural techniques and promote the adoption of practices that restore soil health, conserve water, and preserve biodiversity.', 1),
    (8, 'ARCHIVED', 'urban greening initiative', '2023-01-01', '2024-01-01', 'Aims to enhance urban biodiversity, improve air quality, and mitigate urban heat island effects by increasing green spaces, planting trees, and implementing green infrastructure in cities.', 'Organization seeks to create healthier and more livable cities by promoting urban greening initiatives that enhance environmental sustainability, public health, and community well-being.', 1),
    (8, 'ARCHIVED', 'marine conservation program', '2023-01-01', '2024-01-01', 'Focuses on protecting and restoring marine ecosystems, conserving marine biodiversity, and promoting sustainable fisheries management to ensure the long-term health and resilience of oceans.', 'Organization aims to safeguard marine biodiversity, support sustainable fisheries, and mitigate the impacts of climate change on ocean ecosystems through science-based conservation efforts and community engagement.', 1),
    (9, 'ARCHIVED', 'environmental education outreach', '2023-01-01', '2024-01-01', 'Aims to raise awareness about environmental issues, inspire action for conservation, and promote sustainability through education, outreach programs, and public engagement initiatives.', 'Organization seeks to empower individuals and communities with knowledge and skills to address environmental challenges and foster a culture of environmental stewardship and sustainability.', 1),
    (9, 'ARCHIVED', 'renewable energy advocacy', '2022-01-01', '2023-01-01', 'Aims to advocate for policies and incentives that promote the adoption of renewable energy sources and accelerate the transition to a low-carbon economy.', 'By advocating for renewable energy policies and initiatives, the organization aims to drive systemic change and create a supportive environment for clean energy investment and innovation.', 1),
    (8, 'ARCHIVED', 'sustainability workshops', '2022-01-01', '2023-01-01', 'Focuses on organizing workshops, seminars, and training sessions to educate individuals and organizations about sustainability practices, resource conservation, and environmental stewardship.', 'Organization aims to empower participants with practical knowledge and skills to integrate sustainability principles into their personal and professional lives, fostering a culture of sustainability and responsible citizenship.', 1),
    (8, 'ARCHIVED', 'air quality monitoring program', '2022-01-01', '2023-01-01', 'Aims to establish a comprehensive air quality monitoring program to track air pollution levels, identify sources of pollution, and inform policy decisions and public health interventions.', 'Organization seeks to improve air quality, protect public health, and raise awareness about the impacts of air pollution on human health and the environment through data-driven analysis and targeted interventions.', 1),
    (8, 'ARCHIVED', 'green infrastructure development', '2022-01-01', '2023-01-01', 'Focuses on planning, designing, and implementing green infrastructure projects such as urban parks, green roofs, and permeable pavement to enhance ecosystem services, mitigate urban heat island effects, and promote climate resilience.', 'Organization aims to create sustainable and resilient communities by integrating green infrastructure into urban planning and development processes, enhancing quality of life and environmental sustainability.', 1);

INSERT INTO finance.revenues
(id, created_on, type, amount)
VALUES
    (-2, '2024-01-01 12:00:00.000', 'PROJECT_REVENUE', 30000),
    (-4, '2024-02-07 12:00:00.000', 'PROJECT_REVENUE', 10000),
    (-6, '2023-03-12 12:00:00.000', 'PROJECT_REVENUE', 60000),
    (-8, '2024-04-01 12:00:00.000', 'PROJECT_REVENUE', 20000),
    (-10, '2024-02-02 12:00:00.000', 'PROJECT_REVENUE', 35000),
    (-12, '2024-02-02 12:00:00.000', 'PROJECT_REVENUE', 40000),
    (-14, '2024-02-02 12:00:00.000', 'PROJECT_REVENUE', 90000),

    (-15, '2024-03-03 12:00:00.000', 'PROJECT_DONATION', 120),
    (-16, '2024-02-05 12:00:00.000', 'PROJECT_DONATION', 500),
    (-17, '2024-01-16 12:00:00.000', 'PROJECT_DONATION', 700),
    (-18, '2024-01-06 12:00:00.000', 'PROJECT_DONATION', 400),
    (-19, '2024-01-15 12:00:00.000', 'PROJECT_DONATION', 120),
    (-20, '2024-05-05 12:00:00.000', 'PROJECT_DONATION', 450),
    (-21, '2024-06-12 12:00:00.000', 'PROJECT_DONATION', 320),
    (-22, '2024-07-20 12:00:00.000', 'PROJECT_DONATION', 540),
    (-23, '2024-08-18 12:00:00.000', 'PROJECT_DONATION', 290),
    (-24, '2024-09-07 12:00:00.000', 'PROJECT_DONATION', 60),
    (-25, '2024-04-14 12:00:00.000', 'PROJECT_DONATION', 250),
    (-26, '2024-05-18 12:00:00.000', 'PROJECT_DONATION', 150),
    (-27, '2024-06-25 12:00:00.000', 'PROJECT_DONATION', 450),
    (-28, '2024-07-30 12:00:00.000', 'PROJECT_DONATION', 300),
    (-29, '2024-08-15 12:00:00.000', 'PROJECT_DONATION', 650),

    (-101, '2024-01-12 12:00:00.000', 'DONATION', 120),
    (-102, '2024-02-12 12:00:00.000', 'DONATION', 150),
    (-103, '2024-02-13 12:00:00.000', 'DONATION', 20),
    (-104, '2024-02-14 12:00:00.000', 'DONATION', 320),
    (-105, '2024-03-15 12:00:00.000', 'DONATION', 50),
    (-106, '2024-04-12 12:00:00.000', 'DONATION', 180),
    (-107, '2024-04-20 12:00:00.000', 'DONATION', 75),
    (-108, '2024-05-05 12:00:00.000', 'DONATION', 220),
    (-109, '2024-05-10 12:00:00.000', 'DONATION', 300),
    (-110, '2024-06-01 12:00:00.000', 'DONATION', 125),
    (-111, '2024-06-15 12:00:00.000', 'DONATION', 410),
    (-112, '2024-07-10 12:00:00.000', 'DONATION', 80),
    (-113, '2024-07-22 12:00:00.000', 'DONATION', 200),
    (-114, '2024-08-05 12:00:00.000', 'DONATION', 150),
    (-115, '2024-08-18 12:00:00.000', 'DONATION', 90),
    (-116, '2024-09-12 12:00:00.000', 'DONATION', 110),
    (-117, '2024-09-25 12:00:00.000', 'DONATION', 50),
    (-118, '2024-10-10 12:00:00.000', 'DONATION', 450),
    (-119, '2024-10-22 12:00:00.000', 'DONATION', 320),
    (-120, '2024-11-05 12:00:00.000', 'DONATION', 230),

    (-201, '2024-01-04 12:00:00.000', 'LECTURE_DONATION', 250),
    (-202, '2024-01-04 12:00:00.000', 'LECTURE_DONATION', 100),
    (-203, '2024-01-04 12:00:00.000', 'LECTURE_REVENUE', 1200),
    (-204, '2024-01-04 12:00:00.000', 'LECTURE_REVENUE', 120);

INSERT INTO finance.donations(id, donator)
VALUES
    (-101, 4),
    (-102, 4),
    (-103, 2),
    (-104, 7),
    (-105, 2),
    (-106, 5),
    (-107, 3),
    (-108, 6),
    (-109, 8),
    (-110, 9),
    (-111, 7),
    (-112, 10),
    (-113, 11),
    (-114, 12),
    (-115, 13),
    (-116, 14),
    (-117, 15),
    (-118, 2),
    (-119, 4),
    (-120, 5);

INSERT INTO finance.project_revenues(project, id)
VALUES
    (2, -2),
    (4, -4),
    (6, -6),
    (8, -8),
    (10, -10),
    (12, -12),
    (14, -14);

INSERT INTO finance.project_donations
(id, donator, project)
VALUES
    (-15, 4, 1),
    (-16, 3, 12),
    (-17, 2, 12),
    (-18, 6, 10),
    (-19, 10, 3),
    (-20, 5, 7),
    (-21, 9, 11),
    (-22, 11, 4),
    (-23, 8, 13),
    (-24, 7, 2),
    (-25, 5, 7),
    (-26, 9, 11),
    (-27, 11, 4),
    (-28, 8, 13),
    (-29, 7, 2);

INSERT INTO finance.project_budget
    (id, version, creator, project, total_revenues_amount, total_expenses_amount)
VALUES
    --(-2, 0, 5, 2, 30000, 0),
    --(-4, 0, 5, 4, 10000, 0),
    --(-6, 0, 5, 6, 60000, 0),
    (-8, 0, 5, 8, 20000, 0),
    (-10, 0, 5, 10, 35000, 0),
    (-12, 0, 5, 12, 40000, 0),
    (-14, 0, 5, 14, 90000, 0);