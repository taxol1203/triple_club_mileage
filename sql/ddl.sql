CREATE TABLE `User` (
    `user_id` binary(16) PRIMARY KEY NOT NULL,
    `name` varchar(200) NOT NULL,
    `point` integer DEFAULT 0
);

CREATE TABLE `places` (
  `place_id` binary(16) PRIMARY KEY NOT NULL,
  `name` varchar(200) NOT NULL
);

CREATE TABLE `reviews` (
   `review_id` binary(16) PRIMARY KEY NOT NULL,
   `content` varchar(200) NOT NULL,
   `user_id` binary(16) NOT NULL,
   `place_id` binary(16) NOT NULL,
   `is_first` boolean DEFAULT FALSE,
   `created_at` datetime
);

CREATE TABLE `photos` (
  `photo_id` binary(16) PRIMARY KEY NOT NULL,
  `url` varchar(200) NOT NULL,
  `reiew_id` binary(16) NOT NULL
);

CREATE TABLE `History` (
   `history_id` int PRIMARY KEY AUTO_INCREMENT,
   `user_id` binary(16),
   `point` int,
   `created_at` datetime
);

ALTER TABLE `reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

ALTER TABLE `photos` ADD FOREIGN KEY (`reiew_id`) REFERENCES `reviews` (`review_id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`place_id`) REFERENCES `places` (`place_id`);

ALTER TABLE `History` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);
