CREATE TABLE seller (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) NOT NULL,
    email VARCHAR (2000) UNIQUE NOT NULL,
    password VARCHAR (2000) NOT NULL
);

CREATE TABLE body_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) UNIQUE NOT NULL
);

--many-to-one
CREATE TABLE car_model (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) UNIQUE NOT NULL,
    FOREIGN KEY (car_id) NOT NULL REFERENCES car(id)
);

CREATE TABLE car_photo (
    id SERIAL PRIMARY KEY,
    photo BYTEA NOT NULL
);

CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name VARCHAR(2000) UNIQUE NOT NULL,
    description TEXT,
    --one-to-one
    FOREIGN KEY (car_photo_id) NOT NULL REFERENCES car_photo(id),
    --many-to-one
    FOREIGN KEY (seller_id) NOT NULL REFERENCES seller(id),
    sold BOOLEAN
);

--many-to--many
CREATE TABLE car_body_type (
    id SERIAL PRIMARY KEY,
    car_id NOT NULL REFERENCES car(id),
    body_type_id NOT NULL REFERENCES body_type(id)
);