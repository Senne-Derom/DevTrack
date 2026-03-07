DROP TABLE IF EXISTS todos;
DROP TABLE IF EXISTS projects;

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    purpose VARCHAR(50)
);

CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    end_date TIMESTAMP,
    progress VARCHAR(255),
    project_id BIGINT,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projects(id)
);