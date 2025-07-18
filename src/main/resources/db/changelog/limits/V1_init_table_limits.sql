CREATE TABLE IF NOT EXISTS user_limits (
    user_id INTEGER PRIMARY KEY,
    available_limit DECIMAL(15, 2) DEFAULT 0.00,
    reserved_limit DECIMAL(15, 2) DEFAULT 0.00,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);