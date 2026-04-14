CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    description TEXT NOT NULL,
    sample_input TEXT,
    sample_output TEXT,
    examples jsonb,
    tests jsonb NOT NULL,
    difficulty VARCHAR(32) NOT NULL DEFAULT 'EASY',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);
CREATE INDEX idx_tasks_difficulty ON tasks(difficulty);

CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id),
    task_id BIGINT REFERENCES tasks(id),
    invite_token VARCHAR(128) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'LOBBY',
    start_time TIMESTAMP NULL,
    duration_seconds INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE session_participants (
    session_id BIGINT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL DEFAULT 'PARTICIPANT',
    status VARCHAR(20) NOT NULL DEFAULT 'JOINED',
    joined_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (session_id, user_id)
);
CREATE INDEX idx_participants_session ON session_participants(session_id);

