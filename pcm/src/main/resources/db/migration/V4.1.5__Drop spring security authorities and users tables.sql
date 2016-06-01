ALTER TABLE `pcm`.authorities DROP FOREIGN KEY fk_authorities_users;
ALTER TABLE `pcm`.authorities DROP KEY ix_auth_username;
DROP TABLE `pcm`.authorities;
DROP TABLE `pcm`.users;