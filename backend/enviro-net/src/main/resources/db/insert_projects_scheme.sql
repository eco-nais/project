-- currently valid organization goals: nature, green knowledge, green energy, clean air, green city
-- Projects
INSERT INTO projects.projects (name, description, duration_months, budget, type, status, manager_id, tags)
VALUES ('Conservation of Wetlands', 'Protecting and restoring wetland ecosystems', 24, 50000.00, 'INTERNAL', 'DRAFT',
        10, '["nature", "wildlife", "water", "green energy"]'),
       ('Reforestation Initiative', 'Planting trees and restoring forest habitats', 18, 30000.00, 'EXTERNAL', 'PENDING',
        10, '["restore", "green knowledge", "greener future", "clean air", "trees", "forests"]'),
       ('Urban Green Spaces Development', 'Creating parks and green areas in urban settings', 12, 25000.00, 'INTERNAL',
        'APPROVED', 10, '["green development", "urban", "parks", "green city"]'),
       ('River Cleanup Campaign', 'Cleaning up polluted rivers and waterways', 6, 10000.00, 'EXTERNAL', 'ONGOING', 10, '["nature", "water", "pollution", "clean water"]'),
       ('Biodiversity Monitoring Program', 'Monitoring and preserving local biodiversity', 36, 75000.00, 'INTERNAL',
        'REJECTED', 10, '["biodiversity", "conservation"]'),
       ('Sustainable Agriculture Project', 'Promoting eco-friendly farming practices', 24, 60000.00, 'EXTERNAL',
        'ARCHIVED', 10, '["green knowledge", "farming", "sustainability", "green agriculture"]'),
       ('Ocean Conservation Initiative', 'Protecting marine life and habitats', 36, 80000.00, 'INTERNAL', 'ONGOING',
        10, '["ocean", "marine", "green knowledge", "conservation", "ocean protection"]'),
       ('Eco-friendly Transportation Campaign', 'Promoting sustainable transport options', 12, 20000.00, 'EXTERNAL',
        'PENDING', 10, '["green transport", "sustainability", "clean air", "green city"]'),
       ('Wildlife Habitat Restoration', 'Restoring habitats for endangered species', 24, 60000.00, 'INTERNAL',
        'ONGOING', 11, '["nature", "habitat", "wildlife", "restore"]'),
       ('Green Energy Development Project', 'Promoting renewable energy solutions', 18, 35000.00, 'EXTERNAL',
        'APPROVED', 11, '["protect nature", "green knowledge", "green energy", "renewable", "clean air", "green city"]'),
       ('Community Gardens Initiative', 'Creating communal spaces for sustainable gardening', 9, 15000.00, 'INTERNAL',
        'ONGOING', 11, '["nature", "gardens", "community", "green space", "green city"]'),
       ('Eco-tourism Promotion Program', 'Encouraging responsible tourism practices', 24, 40000.00, 'EXTERNAL',
        'PENDING', 11, '["adapt to nature", "tourism", "responsible"]'),
       ('Waste Recycling Campaign', 'Promoting waste reduction and recycling', 6, 10000.00, 'INTERNAL', 'ARCHIVED', 10, '["nature", "recycling", "waste", "green city", "green future"]'),
       ('Green Buildings Certification Project', 'Promoting eco-friendly building standards', 36, 90000.00, 'EXTERNAL',
        'APPROVED', 10, '["build green", "green certification", "green city"]'),
       ('Climate Change Adaptation Strategy', 'Developing strategies for climate resilience', 24, 70000.00, 'INTERNAL',
        'ONGOING', 10, '["climate", "resilience", "green knowledge", "green energy", "clean air", "green city"]');

INSERT INTO projects.documents (document_id, project_id, name, progress, status)
VALUES (1, 1, 'Conservation Plan', 0.25, 'In Progress - less than halfway'),        -- Conservation of Wetlands
       (1, 2, 'Reforestation Plan', 0.10, 'In Progress - less than halfway'),       -- Reforestation Initiative
       (1, 3, 'Green Spaces Proposal', 0.50, 'Halfway Done'),                       -- Urban Green Spaces Development
       (1, 4, 'Cleanup Strategy', 0.75, 'In Progress - more than halfway'),         -- River Cleanup Campaign
       (1, 5, 'Monitoring Protocol', 0.40, 'In Progress - less than halfway'),      -- Biodiversity Monitoring Program
       (1, 6, 'Agriculture Plan', 0.60, 'In Progress - more than halfway'),         -- Sustainable Agriculture Project
       (1, 7, 'Ocean Protection Plan', 0.80, 'In Progress - more than halfway'),    -- Ocean Conservation Initiative
       (1, 8, 'Transportation Proposal', 0.20,
        'In Progress - less than halfway'),                                         -- Eco-friendly Transportation Campaign
       (1, 9, 'Habitat Restoration Plan', 0.70, 'In Progress - more than halfway'), -- Wildlife Habitat Restoration
       (1, 10, 'Energy Development Plan', 0.30, 'In Progress - less than halfway'), -- Green Energy Development Project
       (1, 11, 'Gardens Blueprint', 0.90, 'Completed'),                             -- Community Gardens Initiative
       (1, 12, 'Tourism Promotion Plan', 0.15, 'In Progress - less than halfway'),  -- Eco-tourism Promotion Program
       (1, 13, 'Recycling Strategy', 1.00, 'Completed'),                            -- Waste Recycling Campaign
       (1, 14, 'Green Buildings Plan', 0.45,
        'In Progress - less than halfway'),                                         -- Green Buildings Certification Project
       (1, 15, 'Climate Adaptation Strategy', 0.55,
        'In Progress - more than halfway'); -- Climate Change Adaptation Strategy

INSERT INTO projects.document_versions (version, document_id, project_id, file_path, author_id)
VALUES (0, 1, 1, 'C:\Users\Nina\OneDrive\Desktop\Projects\Conservation of Wetlands\conservation_plan_v0.pdf', 10),  -- Conservation of Wetlands
       (1, 1, 1, 'C:\Users\Nina\OneDrive\Desktop\Projects\Conservation of Wetlands\conservation_plan_v1.pdf', 10),  -- Conservation of Wetlands
       (1, 1, 2, 'C:\Users\Nina\OneDrive\Desktop\Projects\Reforestation Initiative\reforestation_plan_v1.pdf', 13), -- Reforestation Initiative
       (0, 1, 3, '/path/to/document3.pdf', 10),   -- Urban Green Spaces Development
       (0, 1, 4, '/path/to/document4.pdf', 10),   -- River Cleanup Campaign
       (0, 1, 5, '/path/to/document5.pdf', 10),   -- Biodiversity Monitoring Program
       (0, 1, 6, '/path/to/document6.pdf', 10),   -- Sustainable Agriculture Project
       (0, 1, 7, '/path/to/document7.pdf', 10),   -- Ocean Conservation Initiative
       (0, 1, 8, '/path/to/document8.pdf', 10),   -- Eco-friendly Transportation Campaign
       (0, 1, 9, '/path/to/document9.pdf', 11),   -- Wildlife Habitat Restoration
       (0, 1, 10, '/path/to/document10.pdf', 11), -- Green Energy Development Project
       (0, 1, 11, '/path/to/document11.pdf', 11), -- Community Gardens Initiative
       (0, 1, 12, '/path/to/document12.pdf', 11), -- Eco-tourism Promotion Program
       (0, 1, 13, '/path/to/document13.pdf', 10), -- Waste Recycling Campaign
       (0, 1, 14, '/path/to/document14.pdf', 10), -- Green Buildings Certification Project
       (0, 1, 15, '/path/to/document15.pdf', 10); -- Climate Change Adaptation Strategy

INSERT INTO projects.next_versions (next_version, document_id, project_id)
VALUES (2, 1, 1),  -- Conservation of Wetlands
       (2, 1, 2),  -- Reforestation Initiative
       (1, 1, 3),  -- Urban Green Spaces Development
       (1, 1, 4),  -- River Cleanup Campaign
       (1, 1, 5),  -- Biodiversity Monitoring Program
       (1, 1, 6),  -- Sustainable Agriculture Project
       (1, 1, 7),  -- Ocean Conservation Initiative
       (1, 1, 8),  -- Eco-friendly Transportation Campaign
       (1, 1, 9),  -- Wildlife Habitat Restoration
       (1, 1, 10), -- Green Energy Development Project
       (1, 1, 11), -- Community Gardens Initiative
       (1, 1, 12), -- Eco-tourism Promotion Program
       (1, 1, 13), -- Waste Recycling Campaign
       (1, 1, 14), -- Green Buildings Certification Project
       (1, 1, 15); -- Climate Change Adaptation Strategy

INSERT INTO projects.team_members (project_id, user_id)
VALUES
    -- Conservation of Wetlands
    (1, 5),
    (1, 8),
    (1, 9),
    (1, 11),
    (1, 12),
    (1, 13),
    -- Reforestation Initiative
    (2, 9),
    (2, 12),
    (2, 13),
    (2, 14),
    (2, 15),
    -- Urban Green Spaces Development
    (3, 5),
    (3, 6),
    (3, 7),
    (3, 8);

INSERT INTO projects.assignments (document_id, project_id, user_id, task, active)
VALUES
    -- Conservation of Wetlands
    (1, 1, 13, 'WRITE', true),
    (1, 1, 11, 'REVIEW', true),
    (1, 1, 12, 'REVIEW', true),
    -- Reforestation Initiative
    (1, 2, 13, 'WRITE', true),
    (1, 2, 12, 'REVIEW', true);

INSERT INTO projects.review_requests (request_date, version, document_id, project_id, status, is_reviewed)
VALUES ('2024-05-20 10:00:00', 1, 1, 1, 'IN_PROGRESS', FALSE);

INSERT INTO projects.reviews (review_date, request_id, reviewer_id, comment, is_approved)
VALUES ('2024-05-21 15:00:00', 1, 11, 'Looks good', TRUE);