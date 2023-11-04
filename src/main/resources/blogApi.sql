CREATE TABLE `blogs`
(
    `created_date` date         DEFAULT NULL,
    `category_id`  bigint       DEFAULT NULL,
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `user_id`      bigint       DEFAULT NULL,
    `description`  varchar(255) DEFAULT NULL,
    `slug`         varchar(255) DEFAULT NULL,
    `title`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKdby46brdac2ug8ul7b57ea81h` (`category_id`),
    KEY `FKpg4damav6db6a6fh5peylcni5` (`user_id`),
    CONSTRAINT `FKdby46brdac2ug8ul7b57ea81h` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
    CONSTRAINT `FKpg4damav6db6a6fh5peylcni5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `categories`
(
    `created_at` datetime(6)  DEFAULT NULL,
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `slug`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `comments`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `body`         varchar(255) DEFAULT NULL,
    `created_date` datetime(6)  DEFAULT NULL,
    `blog_id`      bigint NOT NULL,
    `user_id`      bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK9aakob3a7aghrm94k9kmbrjqd` (`blog_id`),
    KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
    CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK9aakob3a7aghrm94k9kmbrjqd` FOREIGN KEY (`blog_id`) REFERENCES `blogs` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `tags`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `tags_blogs`
(
    `tag_id`  bigint NOT NULL,
    `blog_id` bigint NOT NULL,
    KEY `FK5pdx2nl4vow299p5mrr1qmga` (`blog_id`),
    KEY `FK8223dfuruaiue6fawsiqu2o3m` (`tag_id`),
    CONSTRAINT `FK5pdx2nl4vow299p5mrr1qmga` FOREIGN KEY (`blog_id`) REFERENCES `blogs` (`id`),
    CONSTRAINT `FK8223dfuruaiue6fawsiqu2o3m` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `created_at` datetime(6)  DEFAULT NULL,
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `email`      varchar(255) NOT NULL,
    `password`   varchar(255) NOT NULL,
    `username`   varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
    UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
