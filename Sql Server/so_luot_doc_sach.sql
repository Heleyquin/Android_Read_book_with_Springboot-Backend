USE [SACH_ONLINE]
GO

/****** Object:  View [dbo].[sach_sld]    Script Date: 28/12/2024 5:09:18 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[sach_sld]
AS
SELECT id_sach, gia_tien, active, gioi_thieu, id_quan_ly, ngay_ra_mat, ngay_tao, ngay_update, img, nxb, url_file, favor_count, so_luot_doc
FROM sach inner join count_all_favor on sach.id_sach = count_all_favor.id
inner join luot_doc_sach on sach.id_sach = luot_doc_sach.id
GO


