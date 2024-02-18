INSERT INTO tbl_user
(user_id, "password", username)
select '1111111111', 'user', 'password'
WHERE
NOT EXISTS (
SELECT user_id, password, username FROM tbl_user WHERE user_id = '1111111111'
);