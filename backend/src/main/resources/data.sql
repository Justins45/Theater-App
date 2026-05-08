-- VENUE CREATION WHILE USING create-drop FOR POSTGRES
INSERT INTO venue (id, name, timezone)
VALUES (1, 'Mini Theater 17th Ave',
        'Edmonton/Toronto')
ON CONFLICT (id) DO NOTHING;