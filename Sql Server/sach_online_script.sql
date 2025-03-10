USE [SACH_ONLINE]
GO
/****** Object:  Table [dbo].[lich_su_doc]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_doc](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[thoi_gian] [datetime2](6) NULL,
	[id_doc_gia] [bigint] NOT NULL,
	[id_sach] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[doc_gia]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[doc_gia](
	[id_doc_gia] [bigint] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[gioi_tinh] [bit] NOT NULL,
	[ho] [nvarchar](255) NULL,
	[ngay_tao] [datetime2](6) NULL,
	[ten] [nvarchar](255) NULL,
	[tk] [varchar](255) NULL,
 CONSTRAINT [PK__doc_gia__E3BEAA014F0ADA7F] PRIMARY KEY CLUSTERED 
(
	[id_doc_gia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[luot_doc_doc_gia]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  Table [dbo].[tac_gia]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tac_gia](
	[id_tac_gia] [bigint] IDENTITY(1,1) NOT NULL,
	[dia_chi] [nvarchar](255) NULL,
	[gioi_tinh] [bit] NOT NULL,
	[ho] [nvarchar](255) NULL,
	[ngay_sinh] [datetime2](6) NULL,
	[ten] [nvarchar](255) NULL,
 CONSTRAINT [PK__tac_gia__5A61B6BA706B4FAD] PRIMARY KEY CLUSTERED 
(
	[id_tac_gia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_tacgia]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_tacgia](
	[id_sach] [bigint] NOT NULL,
	[id_tac_gia] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_sach] ASC,
	[id_tac_gia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sach]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sach](
	[id_sach] [bigint] IDENTITY(1,1) NOT NULL,
	[nxb] [nvarchar](255) NULL,
	[img] [varchar](255) NULL,
	[active] [bit] NULL,
	[gia_tien] [numeric](38, 2) NULL,
	[gioi_thieu] [nvarchar](max) NULL,
	[ngay_ra_mat] [datetime2](6) NULL,
	[ngay_tao] [datetime2](6) NULL,
	[ngay_update] [datetime2](6) NULL,
	[ten_sach] [nvarchar](255) NULL,
	[url_file] [varchar](255) NULL,
	[id_quan_ly] [bigint] NOT NULL,
 CONSTRAINT [PK__sach__D18BB86BE7765187] PRIMARY KEY CLUSTERED 
(
	[id_sach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  View [dbo].[luot_doc_sach]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  Table [dbo].[ct_favor]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_favor](
	[id_doc_gia] [bigint] NOT NULL,
	[id_sach] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_doc_gia] ASC,
	[id_sach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[count_all_favor]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  View [dbo].[top_5_sach_doc_nhieu]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[top_5_sach_doc_nhieu]
AS
SELECT TOP 5 sach.id_sach as id, sach.nxb, sach.img, sach.active, sach.gia_tien, sach.gioi_thieu, sach.ngay_ra_mat, sach.ngay_tao, sach.ngay_update, sach.ten_sach, sach.url_file, sach.id_quan_ly, ISNULL (COUNT(lich_su_doc.id_sach), 0) as so_luot
FROM sach left join lich_su_doc ON sach.id_sach = lich_su_doc.id_sach
WHERE sach.active = 1
GROUP BY sach.id_sach, sach.nxb, sach.img, sach.active, sach.gia_tien, sach.gioi_thieu, sach.ngay_ra_mat, sach.ngay_tao, sach.ngay_update, sach.ten_sach, sach.url_file, sach.id_quan_ly
ORDER BY so_luot DESC
GO
/****** Object:  View [dbo].[top_5_sach_mien_phi]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  View [dbo].[sach_sld]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  Table [dbo].[account]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[tk] [varchar](255) NOT NULL,
	[active] [bit] NOT NULL,
	[mk] [varchar](255) NOT NULL,
	[role] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[tk] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[binhluan]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[binhluan](
	[thoi_gian_tao] [datetime2](6) NOT NULL,
	[noidung] [nvarchar](255) NULL,
	[Id_Docgia] [bigint] NOT NULL,
	[Id_Sach] [bigint] NOT NULL,
 CONSTRAINT [PK__binhluan__B26E8526235A3180] PRIMARY KEY CLUSTERED 
(
	[Id_Docgia] ASC,
	[Id_Sach] ASC,
	[thoi_gian_tao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_dang_ky]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_dang_ky](
	[thoi_gian_dang_ky] [datetime2](6) NOT NULL,
	[MA_GOI] [varchar](255) NOT NULL,
	[Id_Docgia] [bigint] NOT NULL,
	[gia_tien] [numeric](38, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id_Docgia] ASC,
	[MA_GOI] ASC,
	[thoi_gian_dang_ky] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_goi]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_goi](
	[thoi_gian] [datetime2](6) NULL,
	[MA_GOI] [varchar](255) NOT NULL,
	[Id_Sach] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id_Sach] ASC,
	[MA_GOI] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_theloai]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_theloai](
	[id_sach] [bigint] NOT NULL,
	[id_the_loai] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_sach] ASC,
	[id_the_loai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[danhgia]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[danhgia](
	[diemso] [int] NOT NULL,
	[thoi_gian_cap_nhat] [datetime2](6) NULL,
	[thoi_gian_tao] [datetime2](6) NULL,
	[Id_Docgia] [bigint] NOT NULL,
	[Id_Sach] [bigint] NOT NULL,
	[nhanxet] [nvarchar](255) NULL,
 CONSTRAINT [PK__danhgia__AE6D83165C97C474] PRIMARY KEY CLUSTERED 
(
	[Id_Docgia] ASC,
	[Id_Sach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[goidangky]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[goidangky](
	[ma_goi] [varchar](255) NOT NULL,
	[chu_thich] [nvarchar](255) NULL,
	[gia_tien] [numeric](38, 2) NOT NULL,
	[thoi_han] [int] NOT NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK__goidangk__072AC717D82ADDE9] PRIMARY KEY CLUSTERED 
(
	[ma_goi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_mua]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_mua](
	[id_mua] [bigint] IDENTITY(1,1) NOT NULL,
	[gia_tien] [numeric](38, 2) NULL,
	[thoi_gian_mua] [datetime2](6) NULL,
	[id_doc_gia] [bigint] NOT NULL,
	[id_sach] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_mua] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [user_buy_unix] UNIQUE NONCLUSTERED 
(
	[id_doc_gia] ASC,
	[id_sach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_nap]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_nap](
	[id_nap] [bigint] IDENTITY(1,1) NOT NULL,
	[thoi_gian_nap] [datetime2](6) NULL,
	[tien] [numeric](38, 2) NULL,
	[id_doc_gia] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_nap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[quan_ly]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[quan_ly](
	[id_quan_ly] [bigint] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[gioi_tinh] [bit] NOT NULL,
	[ho] [nvarchar](255) NULL,
	[ngay_tao] [datetime2](6) NULL,
	[ten] [nvarchar](255) NULL,
	[tk] [varchar](255) NULL,
 CONSTRAINT [PK__quan_ly__7E344D5DBB52FE44] PRIMARY KEY CLUSTERED 
(
	[id_quan_ly] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sach_mong_muon]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sach_mong_muon](
	[id_gio] [bigint] IDENTITY(1,1) NOT NULL,
	[id_doc_gia] [bigint] NOT NULL,
	[id_sach] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_gio] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [IX_sach_mong_muon] UNIQUE NONCLUSTERED 
(
	[id_sach] ASC,
	[id_doc_gia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[theloai]    Script Date: 28/12/2024 5:20:02 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[theloai](
	[id_the_loai] [bigint] IDENTITY(1,1) NOT NULL,
	[tentl] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK__theloai__DB66BA80440DCC3C] PRIMARY KEY CLUSTERED 
(
	[id_the_loai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [tenTheLoai_Unique] UNIQUE NONCLUSTERED 
(
	[tentl] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[binhluan] ADD  CONSTRAINT [DF_binhluan_thoi_gian_tao]  DEFAULT (getdate()) FOR [thoi_gian_tao]
GO
ALTER TABLE [dbo].[ct_dang_ky] ADD  CONSTRAINT [DF_ct_dang_ky_thoi_gian_dang_ky]  DEFAULT (getdate()) FOR [thoi_gian_dang_ky]
GO
ALTER TABLE [dbo].[ct_dang_ky] ADD  CONSTRAINT [DF_ct_dang_ky_gia_tien]  DEFAULT ((0)) FOR [gia_tien]
GO
ALTER TABLE [dbo].[ct_goi] ADD  CONSTRAINT [DF_ct_goi_thoi_gian]  DEFAULT (getdate()) FOR [thoi_gian]
GO
ALTER TABLE [dbo].[danhgia] ADD  CONSTRAINT [DF_danhgia_thoi_gian_cap_nhat]  DEFAULT (getdate()) FOR [thoi_gian_cap_nhat]
GO
ALTER TABLE [dbo].[danhgia] ADD  CONSTRAINT [DF_danhgia_thoi_gian_tao]  DEFAULT (getdate()) FOR [thoi_gian_tao]
GO
ALTER TABLE [dbo].[doc_gia] ADD  CONSTRAINT [DF_doc_gia_ngay_tao]  DEFAULT (getdate()) FOR [ngay_tao]
GO
ALTER TABLE [dbo].[goidangky] ADD  CONSTRAINT [DF_goidangky_ma_goi]  DEFAULT ((1)) FOR [ma_goi]
GO
ALTER TABLE [dbo].[goidangky] ADD  CONSTRAINT [DF_goidangky_trang_thai]  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[lich_su_doc] ADD  CONSTRAINT [DF_lich_su_doc_thoi_gian]  DEFAULT (getdate()) FOR [thoi_gian]
GO
ALTER TABLE [dbo].[lich_su_mua] ADD  CONSTRAINT [DF_lich_su_mua_thoi_gian_mua]  DEFAULT (getdate()) FOR [thoi_gian_mua]
GO
ALTER TABLE [dbo].[lich_su_nap] ADD  CONSTRAINT [DF_lich_su_nap_thoi_gian_nap]  DEFAULT (getdate()) FOR [thoi_gian_nap]
GO
ALTER TABLE [dbo].[sach] ADD  CONSTRAINT [DF_sach_active]  DEFAULT ((1)) FOR [active]
GO
ALTER TABLE [dbo].[sach] ADD  CONSTRAINT [DF_sach_ngay_ra_mat]  DEFAULT (getdate()) FOR [ngay_ra_mat]
GO
ALTER TABLE [dbo].[sach] ADD  CONSTRAINT [DF_sach_ngay_tao]  DEFAULT (getdate()) FOR [ngay_tao]
GO
ALTER TABLE [dbo].[sach] ADD  CONSTRAINT [DF_sach_ngay_update]  DEFAULT (getdate()) FOR [ngay_update]
GO
ALTER TABLE [dbo].[tac_gia] ADD  CONSTRAINT [DF_tac_gia_ngay_sinh]  DEFAULT (getdate()) FOR [ngay_sinh]
GO
ALTER TABLE [dbo].[binhluan]  WITH CHECK ADD  CONSTRAINT [FK9k6c8tidkeared43u4w3tg6bo] FOREIGN KEY([Id_Sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[binhluan] CHECK CONSTRAINT [FK9k6c8tidkeared43u4w3tg6bo]
GO
ALTER TABLE [dbo].[binhluan]  WITH CHECK ADD  CONSTRAINT [FKorxcd3mqp1c42gpw7i9nthwhq] FOREIGN KEY([Id_Docgia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[binhluan] CHECK CONSTRAINT [FKorxcd3mqp1c42gpw7i9nthwhq]
GO
ALTER TABLE [dbo].[ct_dang_ky]  WITH CHECK ADD  CONSTRAINT [FK1fuwmy1lidjy3u534hbrktrja] FOREIGN KEY([MA_GOI])
REFERENCES [dbo].[goidangky] ([ma_goi])
GO
ALTER TABLE [dbo].[ct_dang_ky] CHECK CONSTRAINT [FK1fuwmy1lidjy3u534hbrktrja]
GO
ALTER TABLE [dbo].[ct_dang_ky]  WITH CHECK ADD  CONSTRAINT [FKjn859wb18pnmuhaxr437o0uj] FOREIGN KEY([Id_Docgia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[ct_dang_ky] CHECK CONSTRAINT [FKjn859wb18pnmuhaxr437o0uj]
GO
ALTER TABLE [dbo].[ct_favor]  WITH CHECK ADD  CONSTRAINT [FKh3q6bwvmnlhth46gx50n19q2h] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[ct_favor] CHECK CONSTRAINT [FKh3q6bwvmnlhth46gx50n19q2h]
GO
ALTER TABLE [dbo].[ct_favor]  WITH CHECK ADD  CONSTRAINT [FKo5sxcydhrdof1swj4rjontae9] FOREIGN KEY([id_doc_gia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[ct_favor] CHECK CONSTRAINT [FKo5sxcydhrdof1swj4rjontae9]
GO
ALTER TABLE [dbo].[ct_goi]  WITH CHECK ADD  CONSTRAINT [FK9c17983x33ks7l6xfl6f6hsht] FOREIGN KEY([Id_Sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[ct_goi] CHECK CONSTRAINT [FK9c17983x33ks7l6xfl6f6hsht]
GO
ALTER TABLE [dbo].[ct_goi]  WITH CHECK ADD  CONSTRAINT [FKtdsswfp9byuvjdrqxvs9d6w2p] FOREIGN KEY([MA_GOI])
REFERENCES [dbo].[goidangky] ([ma_goi])
GO
ALTER TABLE [dbo].[ct_goi] CHECK CONSTRAINT [FKtdsswfp9byuvjdrqxvs9d6w2p]
GO
ALTER TABLE [dbo].[ct_tacgia]  WITH CHECK ADD  CONSTRAINT [FKe81oa1vvg6pk2vdenlm09h23q] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[ct_tacgia] CHECK CONSTRAINT [FKe81oa1vvg6pk2vdenlm09h23q]
GO
ALTER TABLE [dbo].[ct_tacgia]  WITH CHECK ADD  CONSTRAINT [FKh58ipra9fpoj8rjubrl6yipwd] FOREIGN KEY([id_tac_gia])
REFERENCES [dbo].[tac_gia] ([id_tac_gia])
GO
ALTER TABLE [dbo].[ct_tacgia] CHECK CONSTRAINT [FKh58ipra9fpoj8rjubrl6yipwd]
GO
ALTER TABLE [dbo].[ct_theloai]  WITH CHECK ADD  CONSTRAINT [FK5qxlvqr06jfb5f5fi03v8u6aq] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[ct_theloai] CHECK CONSTRAINT [FK5qxlvqr06jfb5f5fi03v8u6aq]
GO
ALTER TABLE [dbo].[ct_theloai]  WITH CHECK ADD  CONSTRAINT [FKqk06rv1y8pno77jm07akmf6mh] FOREIGN KEY([id_the_loai])
REFERENCES [dbo].[theloai] ([id_the_loai])
GO
ALTER TABLE [dbo].[ct_theloai] CHECK CONSTRAINT [FKqk06rv1y8pno77jm07akmf6mh]
GO
ALTER TABLE [dbo].[danhgia]  WITH CHECK ADD  CONSTRAINT [FK36h72wqtvrdn8dhl0nltln4ao] FOREIGN KEY([Id_Docgia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[danhgia] CHECK CONSTRAINT [FK36h72wqtvrdn8dhl0nltln4ao]
GO
ALTER TABLE [dbo].[danhgia]  WITH CHECK ADD  CONSTRAINT [FK9axh2kba2vw7qxpcvla2jv4j3] FOREIGN KEY([Id_Sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[danhgia] CHECK CONSTRAINT [FK9axh2kba2vw7qxpcvla2jv4j3]
GO
ALTER TABLE [dbo].[doc_gia]  WITH CHECK ADD  CONSTRAINT [FK5349u6oij1bij6um8c0053q56] FOREIGN KEY([tk])
REFERENCES [dbo].[account] ([tk])
GO
ALTER TABLE [dbo].[doc_gia] CHECK CONSTRAINT [FK5349u6oij1bij6um8c0053q56]
GO
ALTER TABLE [dbo].[lich_su_doc]  WITH CHECK ADD  CONSTRAINT [FK7fw3yb1d4he2qdbefq063rywl] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[lich_su_doc] CHECK CONSTRAINT [FK7fw3yb1d4he2qdbefq063rywl]
GO
ALTER TABLE [dbo].[lich_su_doc]  WITH CHECK ADD  CONSTRAINT [FKtl55wah6net4kpqydtq8gj046] FOREIGN KEY([id_doc_gia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[lich_su_doc] CHECK CONSTRAINT [FKtl55wah6net4kpqydtq8gj046]
GO
ALTER TABLE [dbo].[lich_su_mua]  WITH CHECK ADD  CONSTRAINT [FKjo4v573jdk83rl2158pm4k9qo] FOREIGN KEY([id_doc_gia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[lich_su_mua] CHECK CONSTRAINT [FKjo4v573jdk83rl2158pm4k9qo]
GO
ALTER TABLE [dbo].[lich_su_mua]  WITH CHECK ADD  CONSTRAINT [FKreyh2e7tv9tgtkwxc2cd9llw2] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[lich_su_mua] CHECK CONSTRAINT [FKreyh2e7tv9tgtkwxc2cd9llw2]
GO
ALTER TABLE [dbo].[lich_su_nap]  WITH CHECK ADD  CONSTRAINT [FK661x7lkuw976qbnevhbg9vjbw] FOREIGN KEY([id_doc_gia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[lich_su_nap] CHECK CONSTRAINT [FK661x7lkuw976qbnevhbg9vjbw]
GO
ALTER TABLE [dbo].[quan_ly]  WITH CHECK ADD  CONSTRAINT [FK9lju6r286v28vt31eqjlf4uv7] FOREIGN KEY([tk])
REFERENCES [dbo].[account] ([tk])
GO
ALTER TABLE [dbo].[quan_ly] CHECK CONSTRAINT [FK9lju6r286v28vt31eqjlf4uv7]
GO
ALTER TABLE [dbo].[sach]  WITH CHECK ADD  CONSTRAINT [FKev3uo6ucgnemt0qqumw7n491f] FOREIGN KEY([id_quan_ly])
REFERENCES [dbo].[quan_ly] ([id_quan_ly])
GO
ALTER TABLE [dbo].[sach] CHECK CONSTRAINT [FKev3uo6ucgnemt0qqumw7n491f]
GO
ALTER TABLE [dbo].[sach_mong_muon]  WITH CHECK ADD  CONSTRAINT [FK8bypbcxglq4ui8wy6gbbax86w] FOREIGN KEY([id_sach])
REFERENCES [dbo].[sach] ([id_sach])
GO
ALTER TABLE [dbo].[sach_mong_muon] CHECK CONSTRAINT [FK8bypbcxglq4ui8wy6gbbax86w]
GO
ALTER TABLE [dbo].[sach_mong_muon]  WITH CHECK ADD  CONSTRAINT [FKemmjxj51tdgxjrokhh95ufmg6] FOREIGN KEY([id_doc_gia])
REFERENCES [dbo].[doc_gia] ([id_doc_gia])
GO
ALTER TABLE [dbo].[sach_mong_muon] CHECK CONSTRAINT [FKemmjxj51tdgxjrokhh95ufmg6]
GO
ALTER TABLE [dbo].[danhgia]  WITH CHECK ADD  CONSTRAINT [CK_danhgia] CHECK  (([diemso]>=(0) AND [diemso]<=(5)))
GO
ALTER TABLE [dbo].[danhgia] CHECK CONSTRAINT [CK_danhgia]
GO
/****** Object:  StoredProcedure [dbo].[sp_get_goi_dang_ki]    Script Date: 28/12/2024 5:20:02 SA ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tong_tien_doc_gia]    Script Date: 28/12/2024 5:20:02 SA ******/
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
