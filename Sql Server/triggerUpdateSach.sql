USE [SACH_ONLINE]
GO

/****** Object:  Trigger [dbo].[triggerUpdateSach]    Script Date: 28/12/2024 5:13:59 SA ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[triggerUpdateSach]
ON [dbo].[sach]
AFTER UPDATE
AS
BEGIN
    DELETE FROM sach_mong_muon
    WHERE id_sach IN (
        SELECT i.id_sach
        FROM Inserted i
        JOIN Deleted d ON i.id_sach = d.id_sach
        WHERE i.active = 0
    );
END;
GO

ALTER TABLE [dbo].[sach] ENABLE TRIGGER [triggerUpdateSach]
GO


