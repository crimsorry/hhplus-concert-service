INSERT INTO member(member_name, charge)VALUES('김소리_테스트_1', 0);
INSERT INTO member(member_name, charge)VALUES('김소리_테스트_2', 0);
INSERT INTO concert (concert_title, concert_place) VALUES('드라큘라', '부산문화회관 대극장');
INSERT INTO concert_schedule (concert_id, open_date, start_date, end_date)
VALUES (1, '2024-11-01 14:30:00', DATEADD('DAY', -5, CURRENT_DATE), DATEADD('DAY', 10, CURRENT_DATE));
INSERT INTO concert_seat (schedule_id, seat_num, amount, seat_status) VALUES
(1, 'A01', 140000, 'STAND_BY'),
(1, 'A02', 140000, 'STAND_BY'),
(1, 'A03', 140000, 'STAND_BY'),
(1, 'A04', 140000, 'STAND_BY'),
(1, 'A05', 140000, 'STAND_BY'),
(1, 'A06', 140000, 'STAND_BY'),
(1, 'A07', 140000, 'STAND_BY'),
(1, 'A08', 140000, 'STAND_BY'),
(1, 'A09', 140000, 'STAND_BY'),
(1, 'A10', 140000, 'STAND_BY'),
(1, 'B01', 140000, 'STAND_BY'),
(1, 'B02', 140000, 'STAND_BY'),
(1, 'B03', 140000, 'STAND_BY'),
(1, 'B04', 140000, 'STAND_BY'),
(1, 'B05', 140000, 'STAND_BY'),
(1, 'B06', 140000, 'STAND_BY'),
(1, 'B07', 140000, 'STAND_BY'),
(1, 'B08', 140000, 'STAND_BY'),
(1, 'B09', 140000, 'STAND_BY'),
(1, 'B10', 140000, 'STAND_BY'),
(1, 'C01', 140000, 'STAND_BY'),
(1, 'C02', 140000, 'STAND_BY'),
(1, 'C03', 140000, 'STAND_BY'),
(1, 'C04', 140000, 'STAND_BY'),
(1, 'C05', 140000, 'STAND_BY'),
(1, 'C06', 140000, 'STAND_BY'),
(1, 'C07', 140000, 'STAND_BY'),
(1, 'C08', 140000, 'STAND_BY'),
(1, 'C09', 140000, 'STAND_BY'),
(1, 'C10', 140000, 'STAND_BY'),
(1, 'D01', 140000, 'STAND_BY'),
(1, 'D02', 140000, 'STAND_BY'),
(1, 'D03', 140000, 'STAND_BY'),
(1, 'D04', 140000, 'STAND_BY'),
(1, 'D05', 140000, 'STAND_BY'),
(1, 'D06', 140000, 'STAND_BY'),
(1, 'D07', 140000, 'STAND_BY'),
(1, 'D08', 140000, 'STAND_BY'),
(1, 'D09', 140000, 'STAND_BY'),
(1, 'D10', 140000, 'STAND_BY'),
(1, 'E01', 140000, 'STAND_BY'),
(1, 'E02', 140000, 'STAND_BY'),
(1, 'E03', 140000, 'STAND_BY'),
(1, 'E04', 140000, 'STAND_BY'),
(1, 'E05', 140000, 'STAND_BY'),
(1, 'E06', 140000, 'STAND_BY'),
(1, 'E07', 140000, 'STAND_BY'),
(1, 'E08', 140000, 'STAND_BY'),
(1, 'E09', 140000, 'STAND_BY'),
(1, 'E10', 140000, 'STAND_BY');