CREATE TABLE credencial_acesso (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE credencial_grupo (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    credencial_id BIGINT NOT NULL,
    grupo VARCHAR(255) NOT NULL,
    FOREIGN KEY (credencial_id) REFERENCES credencial_acesso(id) ON DELETE CASCADE
);

CREATE TABLE credencial_role (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    credencial_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    FOREIGN KEY (credencial_id) REFERENCES credencial_acesso(id) ON DELETE CASCADE
);

-- senha: 791222
INSERT INTO credencial_acesso (id, username, first_name, last_name, password, email ) VALUES (1, 'administrador', 'ADMIN', 'ADMIN', 'FVIdPLkNZizepL+fcWTvGe+ZNh/equUfC8jPy2qDzc+ykFZ0cF2ARXsZyOHzjmhe7B0GVqKUZfQQ2hKRb0sbIQ==
', 'admin@admin.com' );