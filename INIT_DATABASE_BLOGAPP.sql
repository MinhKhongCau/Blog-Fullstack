USE [BlogManagement]
GO
/****** Object:  Table [dbo].[ACCOUNT]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ACCOUNT](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[FIRSTNAME] [nvarchar](100) NULL,
	[LASTNAME] [nvarchar](100) NULL,
	[EMAIL] [varchar](100) NULL,
	[PASSWORD] [varchar](100) NULL,
	[GENDER] [bit] NULL,
	[AGE] [int] NULL,
	[BIRTHDATE] [date] NULL,
	[PHOTO] [varchar](50) NULL,
	[TOKEN] [varchar](50) NULL,
	[ROLE] [int] NOT NULL,
 CONSTRAINT [PK__ACCOUNT__3214EC2793DC217A] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ACCOUNT_AUTHORITY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ACCOUNT_AUTHORITY](
	[ID_ACCOUNT] [int] NOT NULL,
	[ID_AUTHORITY] [int] NOT NULL,
 CONSTRAINT [PK_AUTHORITY] PRIMARY KEY CLUSTERED 
(
	[ID_ACCOUNT] ASC,
	[ID_AUTHORITY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AUTHORITY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AUTHORITY](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK__AUTHORIT__3214EC272CF82E0E] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CATEGORY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CATEGORY](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_NAME] [varchar](100) NOT NULL,
	[ALLOW] [bit] NOT NULL,
 CONSTRAINT [PK__CATEGORY__3214EC27BA13F8C5] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[COMMENT]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[COMMENT](
	[ID_ACCOUNT] [int] NOT NULL,
	[ID_POST] [int] NOT NULL,
	[BODY] [nvarchar](500) NULL,
	[COMMENT_AT] [datetime] NULL,
	[UPDATE_AT] [datetime] NULL,
	[DELETE_AT] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EMOJI]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EMOJI](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [varchar](100) NOT NULL,
 CONSTRAINT [PK__EMOJI__3214EC2741C3B28F] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[INTERACT]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[INTERACT](
	[ID_ACCOUNT] [int] NOT NULL,
	[ID_POST] [int] NOT NULL,
	[ID_EMOJI] [int] NOT NULL,
 CONSTRAINT [PK_INTERACT] PRIMARY KEY CLUSTERED 
(
	[ID_ACCOUNT] ASC,
	[ID_POST] ASC,
	[ID_EMOJI] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[POST](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[AUTHOR_ID] [int] NOT NULL,
	[TITLE] [nvarchar](255) NULL,
	[BODY] [text] NULL,
	[CREATE_AT] [datetime] NULL,
	[UPDATE_AT] [datetime] NULL,
	[APPROVED] [bit] NULL,
 CONSTRAINT [PK__POST__3214EC27D8B50939] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[POST_CATEGORY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[POST_CATEGORY](
	[ID_POST] [int] NOT NULL,
	[ID_CATEGORY] [int] NOT NULL,
 CONSTRAINT [PK_POST_CATEGORY] PRIMARY KEY CLUSTERED 
(
	[ID_CATEGORY] ASC,
	[ID_POST] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ROLE]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ROLE](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [varchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TAG]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TAG](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TAG_NAME] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TAG_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TAG_POST](
	[ID_POST] [int] NOT NULL,
	[ID_TAG] [int] NOT NULL,
 CONSTRAINT [PK_TAG_POST] PRIMARY KEY CLUSTERED 
(
	[ID_TAG] ASC,
	[ID_POST] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[POST] ADD  CONSTRAINT [DF_APPROVED]  DEFAULT ((0)) FOR [APPROVED]
GO
ALTER TABLE [dbo].[ACCOUNT]  WITH CHECK ADD  CONSTRAINT [FK_ROLE] FOREIGN KEY([ROLE])
REFERENCES [dbo].[ROLE] ([ID])
GO
ALTER TABLE [dbo].[ACCOUNT] CHECK CONSTRAINT [FK_ROLE]
GO
ALTER TABLE [dbo].[ACCOUNT_AUTHORITY]  WITH CHECK ADD  CONSTRAINT [FK_ACCOUNT] FOREIGN KEY([ID_ACCOUNT])
REFERENCES [dbo].[ACCOUNT] ([ID])
GO
ALTER TABLE [dbo].[ACCOUNT_AUTHORITY] CHECK CONSTRAINT [FK_ACCOUNT]
GO
ALTER TABLE [dbo].[ACCOUNT_AUTHORITY]  WITH CHECK ADD  CONSTRAINT [FK_AUTHORITY] FOREIGN KEY([ID_AUTHORITY])
REFERENCES [dbo].[AUTHORITY] ([ID])
GO
ALTER TABLE [dbo].[ACCOUNT_AUTHORITY] CHECK CONSTRAINT [FK_AUTHORITY]
GO
ALTER TABLE [dbo].[COMMENT]  WITH CHECK ADD  CONSTRAINT [FK_COMMENT_ACCOUNT] FOREIGN KEY([ID_ACCOUNT])
REFERENCES [dbo].[ACCOUNT] ([ID])
GO
ALTER TABLE [dbo].[COMMENT] CHECK CONSTRAINT [FK_COMMENT_ACCOUNT]
GO
ALTER TABLE [dbo].[COMMENT]  WITH CHECK ADD  CONSTRAINT [FK_COMMENT_POST] FOREIGN KEY([ID_POST])
REFERENCES [dbo].[POST] ([ID])
GO
ALTER TABLE [dbo].[COMMENT] CHECK CONSTRAINT [FK_COMMENT_POST]
GO
ALTER TABLE [dbo].[INTERACT]  WITH CHECK ADD  CONSTRAINT [FK_INTERACT_ACCOUNT] FOREIGN KEY([ID_ACCOUNT])
REFERENCES [dbo].[ACCOUNT] ([ID])
GO
ALTER TABLE [dbo].[INTERACT] CHECK CONSTRAINT [FK_INTERACT_ACCOUNT]
GO
ALTER TABLE [dbo].[INTERACT]  WITH CHECK ADD  CONSTRAINT [FK_INTERACT_EMOJI] FOREIGN KEY([ID_EMOJI])
REFERENCES [dbo].[EMOJI] ([ID])
GO
ALTER TABLE [dbo].[INTERACT] CHECK CONSTRAINT [FK_INTERACT_EMOJI]
GO
ALTER TABLE [dbo].[INTERACT]  WITH CHECK ADD  CONSTRAINT [FK_INTERACT_POST] FOREIGN KEY([ID_POST])
REFERENCES [dbo].[POST] ([ID])
GO
ALTER TABLE [dbo].[INTERACT] CHECK CONSTRAINT [FK_INTERACT_POST]
GO
ALTER TABLE [dbo].[POST]  WITH CHECK ADD  CONSTRAINT [FK_AUTHOR] FOREIGN KEY([AUTHOR_ID])
REFERENCES [dbo].[ACCOUNT] ([ID])
GO
ALTER TABLE [dbo].[POST] CHECK CONSTRAINT [FK_AUTHOR]
GO
ALTER TABLE [dbo].[POST_CATEGORY]  WITH CHECK ADD  CONSTRAINT [FK_CATEGORY_POST] FOREIGN KEY([ID_POST])
REFERENCES [dbo].[POST] ([ID])
GO
ALTER TABLE [dbo].[POST_CATEGORY] CHECK CONSTRAINT [FK_CATEGORY_POST]
GO
ALTER TABLE [dbo].[POST_CATEGORY]  WITH CHECK ADD  CONSTRAINT [FK_POST_CATEGORY] FOREIGN KEY([ID_CATEGORY])
REFERENCES [dbo].[CATEGORY] ([ID])
GO
ALTER TABLE [dbo].[POST_CATEGORY] CHECK CONSTRAINT [FK_POST_CATEGORY]
GO
ALTER TABLE [dbo].[TAG_POST]  WITH CHECK ADD  CONSTRAINT [FK_POST_TAG] FOREIGN KEY([ID_TAG])
REFERENCES [dbo].[TAG] ([ID])
GO
ALTER TABLE [dbo].[TAG_POST] CHECK CONSTRAINT [FK_POST_TAG]
GO
ALTER TABLE [dbo].[TAG_POST]  WITH CHECK ADD  CONSTRAINT [FK_TAG_POST] FOREIGN KEY([ID_POST])
REFERENCES [dbo].[POST] ([ID])
GO
ALTER TABLE [dbo].[TAG_POST] CHECK CONSTRAINT [FK_TAG_POST]
GO
/****** Object:  StoredProcedure [dbo].[SP_ADD_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_ADD_POST] @title nvarchar(255), @body text, @author_id int, @tag varchar(100)
AS
BEGIN
	DECLARE @new_post_id INT;

	INSERT INTO POST(AUTHOR_ID, TITLE, BODY, CREATE_AT)
	VALUES (@author_id,@title,@body,GETDATE());

	SET	@new_post_id = SCOPE_IDENTITY();

	INSERT INTO TAG(TAG_NAME)
	SELECT VALUE
	FROM string_split(@tag, '#')
	WHERE NOT EXISTS (
		SELECT 1 FROM TAG WHERE TAG_NAME = TRIM(VALUE)
	)

	INSERT INTO TAG_POST (ID_POST,ID_TAG)
	SELECT @new_post_id, ID
	FROM TAG
	WHERE TAG_NAME IN (SELECT TRIM(VALUE) FROM string_split(@tag,'#'))
END
GO
/****** Object:  StoredProcedure [dbo].[SP_COMMENT]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_COMMENT] 
	-- Add the parameters for the stored procedure here
	@accountID INT,
	@postID INT,
	@body NVARCHAR(500)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Select category id from name category
	INSERT INTO COMMENT (ID_ACCOUNT,ID_POST, BODY, COMMENT_AT)
	VALUES (@accountID, @postID, @body, GETDATE())	

END
GO
/****** Object:  StoredProcedure [dbo].[SP_DELETE_CATEGORY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_DELETE_CATEGORY] 
	-- Add the parameters for the stored procedure here
	@id_category INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	UPDATE CATEGORY
	SET ALLOW = 0
	WHERE ID = @id_category
END
GO
/****** Object:  StoredProcedure [dbo].[SP_DELETE_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_DELETE_POST] @id INT
AS
BEGIN
	DECLARE @new_post_id INT;

	UPDATE POST
	SET APPROVED = 0
	WHERE ID = @id
END
GO
/****** Object:  StoredProcedure [dbo].[SP_FIND_BY_CATEGORY]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_FIND_BY_CATEGORY] 
	-- Add the parameters for the stored procedure here
	@category_name VARCHAR(100)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Select category id from name category
	WITH v_category AS (
		SELECT ID FROM CATEGORY
		WHERE CATEGORY_NAME = @category_name
	)

	SELECT * FROM POST
	WHERE ID = (SELECT ID_POST FROM POST_CATEGORY PC
				JOIN v_category V ON V.ID = PC.ID_CATEGORY)
END
GO
/****** Object:  StoredProcedure [dbo].[SP_FIND_BY_TAG]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_FIND_BY_TAG] 
	-- Add the parameters for the stored procedure here
	@tag varchar(100)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Find all tag id match tag name
	WITH v_tag AS (
		SELECT ID FROM TAG
		WHERE TAG_NAME = @tag
	)

	-- Find all post id match tag name
	SELECT * FROM POST 
	WHERE ID = (SELECT ID FROM TAG_POST P
				JOIN v_tag V ON P.ID_TAG = V.ID)

END
GO
/****** Object:  StoredProcedure [dbo].[SP_GENARATE_APPROVED_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_GENARATE_APPROVED_POST] 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * FROM POST 
	WHERE APPROVED = 1
END
GO
/****** Object:  StoredProcedure [dbo].[SP_GENARATE_NOT_APPROVED_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_GENARATE_NOT_APPROVED_POST]
AS
GO
/****** Object:  StoredProcedure [dbo].[SP_INTERACT]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_INTERACT] 
	-- Add the parameters for the stored procedure here
	@accountID INT,
	@postID INT,
	@emojiID INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Select category id from name category
	INSERT INTO INTERACT(ID_ACCOUNT, ID_POST, ID_EMOJI)
	VALUES (@accountID, @postID, @emojiID)	

END
GO
/****** Object:  StoredProcedure [dbo].[SP_NUMBER_OF_INTERACT_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SP_NUMBER_OF_INTERACT_POST] 
	-- Add the parameters for the stored procedure here
	@postID INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Select category id from name category
	SELECT COUNT(*) FROM INTERACT
	WHERE ID_POST = @postID
	GROUP BY ID_POST

END
GO
/****** Object:  StoredProcedure [dbo].[SP_UPDATE_POST]    Script Date: 2/18/2025 8:16:25 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_UPDATE_POST] @title nvarchar(255), @body text
AS
BEGIN
	DECLARE @new_post_id INT;

	UPDATE POST
	SET TITLE = @title, BODY = @body, UPDATE_AT = GETDATE()

END
GO
