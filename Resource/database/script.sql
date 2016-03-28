USE [master]
GO
/****** Object:  Database [tunga]    Script Date: 3/27/2016 8:10:21 PM ******/
CREATE DATABASE [tunga]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'tunga', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\tunga.mdf' , SIZE = 3136KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'tunga_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.SQLEXPRESS\MSSQL\DATA\tunga_log.ldf' , SIZE = 832KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [tunga] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [tunga].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [tunga] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [tunga] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [tunga] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [tunga] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [tunga] SET ARITHABORT OFF 
GO
ALTER DATABASE [tunga] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [tunga] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [tunga] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [tunga] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [tunga] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [tunga] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [tunga] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [tunga] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [tunga] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [tunga] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [tunga] SET  DISABLE_BROKER 
GO
ALTER DATABASE [tunga] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [tunga] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [tunga] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [tunga] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [tunga] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [tunga] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [tunga] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [tunga] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [tunga] SET  MULTI_USER 
GO
ALTER DATABASE [tunga] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [tunga] SET DB_CHAINING OFF 
GO
ALTER DATABASE [tunga] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [tunga] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [tunga]
GO
/****** Object:  Table [dbo].[food]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[food](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menuId] [int] NULL,
	[name] [varchar](255) NULL,
	[price] [float] NULL,
	[image] [varchar](255) NULL,
	[description] [text] NULL,
 CONSTRAINT [PK_food] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[invoice]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[invoice](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fullName] [varchar](255) NULL,
	[address] [varchar](255) NULL,
	[phone] [varchar](15) NULL,
	[tax] [float] NULL,
	[total] [float] NULL,
	[token] [varchar](255) NULL,
	[status] [tinyint] NULL,
	[createdAt] [datetime] NULL,
 CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[invoiceFood]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoiceFood](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[invoiceId] [int] NULL,
	[foodId] [int] NULL,
	[quantity] [int] NULL,
 CONSTRAINT [PK_order_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[invoiceTable]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoiceTable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[invoiceId] [int] NULL,
	[tableId] [int] NULL,
	[price] [float] NULL,
	[fromTime] [datetime] NULL,
	[toTime] [datetime] NULL,
 CONSTRAINT [PK_reservation] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[menu]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[menu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[order] [int] NULL,
 CONSTRAINT [PK_menu] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[room]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[room](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[type] [tinyint] NULL,
 CONSTRAINT [PK_room] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[table]    Script Date: 3/27/2016 8:10:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[roomId] [int] NULL,
	[name] [varchar](255) NULL,
	[type] [int] NULL,
	[price] [float] NULL,
	[description] [text] NULL,
	[image] [varchar](255) NULL,
 CONSTRAINT [PK_table] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[food] ADD  CONSTRAINT [DF_food_price]  DEFAULT ((0)) FOR [price]
GO
ALTER TABLE [dbo].[invoice] ADD  CONSTRAINT [DF_invoice_has_paid]  DEFAULT ((0)) FOR [status]
GO
ALTER TABLE [dbo].[invoice] ADD  CONSTRAINT [DF_orders_created_at]  DEFAULT (getdate()) FOR [createdAt]
GO
ALTER TABLE [dbo].[invoiceFood] ADD  CONSTRAINT [DF_order_item_quantity]  DEFAULT ((1)) FOR [quantity]
GO
ALTER TABLE [dbo].[food]  WITH CHECK ADD  CONSTRAINT [FK_food_menu] FOREIGN KEY([menuId])
REFERENCES [dbo].[menu] ([id])
GO
ALTER TABLE [dbo].[food] CHECK CONSTRAINT [FK_food_menu]
GO
ALTER TABLE [dbo].[invoiceFood]  WITH CHECK ADD  CONSTRAINT [FK_invoice_food_invoice] FOREIGN KEY([invoiceId])
REFERENCES [dbo].[invoice] ([id])
GO
ALTER TABLE [dbo].[invoiceFood] CHECK CONSTRAINT [FK_invoice_food_invoice]
GO
ALTER TABLE [dbo].[invoiceTable]  WITH CHECK ADD  CONSTRAINT [FK_invoice_table_invoice] FOREIGN KEY([invoiceId])
REFERENCES [dbo].[invoice] ([id])
GO
ALTER TABLE [dbo].[invoiceTable] CHECK CONSTRAINT [FK_invoice_table_invoice]
GO
ALTER TABLE [dbo].[invoiceTable]  WITH CHECK ADD  CONSTRAINT [FK_invoice_table_table] FOREIGN KEY([tableId])
REFERENCES [dbo].[table] ([id])
GO
ALTER TABLE [dbo].[invoiceTable] CHECK CONSTRAINT [FK_invoice_table_table]
GO
ALTER TABLE [dbo].[table]  WITH CHECK ADD  CONSTRAINT [FK_table_room] FOREIGN KEY([roomId])
REFERENCES [dbo].[room] ([id])
GO
ALTER TABLE [dbo].[table] CHECK CONSTRAINT [FK_table_room]
GO
USE [master]
GO
ALTER DATABASE [tunga] SET  READ_WRITE 
GO
