DROP TABLE IF EXISTS experiment_logs, experiments, lab_projects, lab_categories, researchers CASCADE;

CREATE TABLE researchers (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    specialization VARCHAR(50) NOT NULL
);

CREATE TABLE lab_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE lab_projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    category_id INT NOT NULL REFERENCES lab_categories(id) ON DELETE RESTRICT,
    lead_id INT NOT NULL REFERENCES researchers(id) ON DELETE RESTRICT
);

CREATE TABLE experiments (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PLANNED', 'RUNNING', 'COMPLETED', 'FAILED')),
    priority INT NOT NULL CHECK (priority BETWEEN 1 AND 5),
    experiment_date DATE NOT NULL,
    project_id INT NOT NULL REFERENCES lab_projects(id) ON DELETE CASCADE
);

CREATE TABLE experiment_logs (
    id SERIAL PRIMARY KEY,
    experiment_id INT NOT NULL REFERENCES experiments(id) ON DELETE CASCADE,
    researcher_id INT NOT NULL REFERENCES researchers(id) ON DELETE CASCADE,
    hours_spent NUMERIC(5, 2) NOT NULL DEFAULT 0 CHECK (hours_spent >= 0),
    UNIQUE (experiment_id, researcher_id)
);
