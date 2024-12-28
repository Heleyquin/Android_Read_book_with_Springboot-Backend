USE [SACH_ONLINE]
GO

/****** Object:  StoredProcedure [dbo].[sp_tong_tien_doc_gia]    Script Date: 28/12/2024 5:12:17 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[sp_tong_tien_doc_gia]
@id int
AS
BEGIN
SET NOCOUNT ON
DECLARE @tong_nap DECIMAL(18,2);
DECLARE @tong_mua DECIMAL(18,2);
DECLARE @tong_dang_ki_goi DECIMAL(18,2);
DECLARE @tong_kha_dung DECIMAL(18,2);

SELECT @tong_nap = COALESCE(SUM(tien), 0)
FROM lich_su_nap
WHERE id_doc_gia = @id

SELECT @tong_mua = COALESCE(SUM(gia_tien), 0)
FROM lich_su_mua
WHERE id_doc_gia = @id

SELECT @tong_dang_ki_goi = COALESCE(SUM(ct_dang_ky.gia_tien), 0)
FROM ct_dang_ky inner join goidangky on ct_dang_ky.MA_GOI = goidangky.MA_GOI
WHERE ct_dang_ky.Id_Docgia = @id

SET @tong_kha_dung = @tong_nap - @tong_mua - @tong_dang_ki_goi

SELECT @tong_kha_dung as 'total_available'

END
GO


