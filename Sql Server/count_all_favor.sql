USE [SACH_ONLINE]
GO

/****** Object:  View [dbo].[count_all_favor]    Script Date: 28/12/2024 5:09:01 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[count_all_favor]
AS
SELECT sach.id_sach as id, sach.ten_sach, YEAR(sach.ngay_ra_mat) as nam_ra_mat,
		tac_gia, ISNULL(favor_count.favor_count, 0) as favor_count
FROM sach
LEFT JOIN (SELECT sach.ten_sach, sach.id_sach, COUNT(*) as favor_count
			FROM ct_favor INNER JOIN sach ON ct_favor.id_sach = sach.id_sach
			GROUP BY sach.ten_sach, sach.id_sach) favor_count
ON favor_count.id_sach = sach.id_sach
LEFT JOIN (SELECT s.id_sach as id,
			ISNULL(STRING_AGG(tg.ho + ' ' + tg.ten, ', '),'') as tac_gia
			FROM sach s
			LEFT JOIN ct_tacgia ct ON s.id_sach = ct.id_sach
			LEFT JOIN tac_gia tg ON ct.id_tac_gia = tg.id_tac_gia
			GROUP BY s.id_sach) ls_tg
ON sach.id_sach = ls_tg.id
GO


