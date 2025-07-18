INSERT INTO user_limits (user_id,available_Limit,reserved_Limit,last_update)
SELECT generate_series(1, 100), 10000.00, 0.00,CURRENT_TIMESTAMP
ON CONFLICT (user_id) DO NOTHING;