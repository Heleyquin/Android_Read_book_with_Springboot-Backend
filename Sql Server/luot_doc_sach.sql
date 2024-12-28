USE [SACH_ONLINE]
GO

/****** Object:  View [dbo].[luot_doc_sach]    Script Date: 28/12/2024 5:09:14 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[luot_doc_sach]
AS
SELECT s.id_sach as id, s.ten_sach, YEAR(ngay_ra_mat) as nam_ra_mat,
		ls_tg.tac_gia as tac_gia, ISNULL(so_luot_doc, 0) as so_luot_doc
FROM sach s
LEFT JOIN (SELECT id_sach, COUNT(id_sach)
			as so_luot_doc FROM lich_su_doc GROUP BY id_sach) ls_d
ON s.id_sach = ls_d.id_sach
LEFT JOIN (SELECT s.id_sach as id,
			ISNULL(STRING_AGG(tg.ho + ' ' + tg.ten, ', '), '') as tac_gia
			FROM sach s
			LEFT JOIN ct_tacgia ct ON s.id_sach = ct.id_sach
			LEFT JOIN tac_gia tg ON ct.id_tac_gia = tg.id_tac_gia
			GROUP BY s.id_sach) ls_tg
ON ls_tg.id = s.id_sach
GO


