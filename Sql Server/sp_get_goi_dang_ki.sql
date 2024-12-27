USE [SACH_ONLINE]
GO

/****** Object:  StoredProcedure [dbo].[sp_get_goi_dang_ki]    Script Date: 28/12/2024 5:12:11 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROC [dbo].[sp_get_goi_dang_ki]
@id_reader int
AS
BEGIN
SET NOCOUNT ON
    SELECT TOP 1 g.ma_goi, g.chu_thich, ctdk.thoi_gian_dang_ky, g.thoi_han,
           DATEADD(DAY, g.thoi_han, ctdk.thoi_gian_dang_ky) AS expir_date, g.trang_thai as active
    FROM ct_dang_ky ctdk
    INNER JOIN goidangky g ON ctdk.MA_GOI = g.ma_goi
    WHERE ctdk.Id_Docgia = @id_reader
    ORDER BY ctdk.thoi_gian_dang_ky DESC

END
GO


