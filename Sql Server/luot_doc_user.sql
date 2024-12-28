USE [SACH_ONLINE]
GO

/****** Object:  View [dbo].[luot_doc_doc_gia]    Script Date: 28/12/2024 5:09:09 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[luot_doc_doc_gia]
AS
SELECT s.id_doc_gia as id, s.ho, s.ten, s.tk, s.email, s.ngay_tao, COUNT(ld.id_doc_gia) as so_luot_doc
FROM doc_gia as s left outer join lich_su_doc as ld on s.id_doc_gia = ld.id_doc_gia
GROUP BY s.id_doc_gia, s.ho, s.ten, s.tk, s.email, s.ngay_tao
GO


