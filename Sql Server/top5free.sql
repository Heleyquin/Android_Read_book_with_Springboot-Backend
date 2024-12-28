USE [SACH_ONLINE]
GO

/****** Object:  View [dbo].[top_5_sach_mien_phi]    Script Date: 28/12/2024 5:09:27 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[top_5_sach_mien_phi]
AS
SELECT TOP 5 sach.id_sach as id, sach.nxb, sach.img, sach.active, sach.gia_tien, sach.gioi_thieu, sach.ngay_ra_mat, sach.ngay_tao, sach.ngay_update, sach.ten_sach, sach.url_file, sach.id_quan_ly, ISNULL (COUNT(lich_su_doc.id_sach), 0) as so_luot
FROM sach left join lich_su_doc ON sach.id_sach = lich_su_doc.id_sach
WHERE sach.gia_tien <= 0 and sach.active = 1
GROUP BY sach.id_sach, sach.nxb, sach.img, sach.active, sach.gia_tien, sach.gioi_thieu, sach.ngay_ra_mat, sach.ngay_tao, sach.ngay_update, sach.ten_sach, sach.url_file, sach.id_quan_ly
ORDER BY so_luot DESC
GO


