-- Table for User
CREATE TABLE User (
                      id INTEGER PRIMARY KEY,
                      name TEXT NOT NULL,
                      birth_date DATE,
                      birth_time TIME,
                      birth_place TEXT,
                      email TEXT UNIQUE NOT NULL,
                      password TEXT NOT NULL
);

-- Table for Admin
CREATE TABLE Admin (
                       id INTEGER PRIMARY KEY,
                       name TEXT NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL
);

-- Table for Compatibility
CREATE TABLE Compatibility (
                               user_id INTEGER NOT NULL,
                               friend_id INTEGER NOT NULL,
                               compatibility_score BIGINT NOT NULL,
                               PRIMARY KEY (user_id, friend_id),
                               FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE,
                               FOREIGN KEY (friend_id) REFERENCES User (id) ON DELETE CASCADE
);

-- Table for Quote
CREATE TABLE Quote (
                       id INTEGER PRIMARY KEY,
                       element TEXT NOT NULL,
                       quote_text TEXT NOT NULL
);

-- Table for User_Friends (for managing friends as a many-to-many relationship)
CREATE TABLE User_Friends (
                              user_id INTEGER NOT NULL,
                              friend_id INTEGER NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE,
                              FOREIGN KEY (friend_id) REFERENCES User (id) ON DELETE CASCADE
);

-- Table for traits
CREATE TABLE IF NOT EXISTS Trait (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     element TEXT NOT NULL,         -- Store the enum as a string
                                     trait_name TEXT NOT NULL
);

-- Table for star signs
CREATE TABLE IF NOT EXISTS StarSign (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        star_name TEXT NOT NULL,
                                        element TEXT NOT NULL         -- Store the enum as a string
);

-- Table for linking StarSigns and Traits (Many-to-Many relationship)
CREATE TABLE IF NOT EXISTS StarSign_Trait (
                                              star_sign_id INTEGER NOT NULL,        -- Foreign key reference to StarSign table
                                              trait_id INTEGER NOT NULL,            -- Foreign key reference to Trait table
                                              PRIMARY KEY (star_sign_id, trait_id),
                                              FOREIGN KEY (star_sign_id) REFERENCES StarSign (id),
                                              FOREIGN KEY (trait_id) REFERENCES Trait (id)
);
