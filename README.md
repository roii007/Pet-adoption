# 🐾 宠物领养系统 — Pet Adoption System

> 基于 SSM（Spring + Spring MVC + MyBatis）架构的宠物领养 Web 应用，前后端分离设计，采用 Vue.js + Bootstrap 构建现代化用户界面。

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring MVC](https://img.shields.io/badge/Spring%20MVC-5.3.20-brightgreen.svg)](https://spring.io/)
[![MyBatis](https://img.shields.io/badge/MyBatis-3.5.10-blue.svg)](https://mybatis.org/)
[![Vue.js](https://img.shields.io/badge/Vue.js-2.6.14-green.svg)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [系统架构](#系统架构)
- [界面预览](#界面预览)
- [快速开始](#快速开始)
  - [环境要求](#环境要求)
  - [数据库配置](#数据库配置)
  - [运行项目](#运行项目)
- [API 接口文档](#api-接口文档)
- [默认账户](#默认账户)
- [相关文档](#相关文档)

---

## 项目简介

宠物领养系统是一个完整的 Web 应用平台，旨在连接流浪宠物与有爱心的领养者。系统提供宠物浏览、筛选、领养申请、管理员审核等完整业务流程，采用现代化的前后端分离架构。

### 业务流程

```
用户注册/登录 → 浏览宠物列表 → 查看宠物详情 → 提交领养申请 → 管理员审核 → 领养成功/拒绝
```

---

## 功能特性

### 👤 普通用户功能

| 模块 | 功能 |
|------|------|
| 用户系统 | 注册、登录、个人资料修改、密码修改 |
| 宠物浏览 | 按类型筛选、宠物详情查看、图片展示 |
| 领养申请 | 填写申请表（姓名、联系方式、住房类型、养宠经验等） |
| 申请跟踪 | 查看申请历史、取消待审核申请、查看审核意见 |

### 🛡️ 管理员功能

| 模块 | 功能 |
|------|------|
| 仪表盘 | 管理后台首页，功能导航 |
| 用户管理 | 查看所有用户、启用/禁用账号、删除用户 |
| 宠物管理 | 添加/编辑/删除宠物、上架/下架宠物、图片管理 |
| 申请审核 | 查看申请详情、通过/拒绝申请（附审核意见） |

---

## 技术栈

### 后端

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 8 | 开发语言 |
| Spring MVC | 5.3.20 | Web 框架，提供 REST API |
| Spring JDBC | 5.3.20 | 事务管理与数据库操作 |
| MyBatis | 3.5.10 | ORM 持久层框架 |
| MyBatis-Spring | 2.0.7 | MyBatis 与 Spring 集成 |
| MySQL | 8.0 | 关系型数据库 |
| Jackson | 2.13.0 | JSON 序列化/反序列化 |
| Commons FileUpload | 1.4 | 文件上传处理 |
| Log4j | 1.2.17 | 日志框架 |

### 前端

| 技术 | 版本 | 用途 |
|------|------|------|
| Vue.js | 2.6.14 | 前端 SPA 框架 |
| Vue Router | 3.5.3 | 前端路由（Hash 模式） |
| Axios | 0.27.2 | HTTP 请求封装 |
| Bootstrap | 4.6.1 | UI 组件库 |
| jQuery | 3.6.0 | DOM 操作（Bootstrap 依赖） |

### 构建工具

| 工具 | 用途 |
|------|------|
| Maven | 依赖管理、项目构建、WAR 打包 |
| Tomcat | Servlet 容器（部署运行） |

---

## 项目结构

```
pet-adoption/
├── pom.xml                                  # Maven 项目配置
├── README.md                                # 项目说明文档
├── 类与接口分析文档.md                        # 详细架构分析文档
│
└── src/
    └── main/
        ├── java/com/example/
        │   ├── constant/
        │   │   └── ApplicationStatus.java   # 申请状态常量
        │   ├── controller/                   # 控制器层（11 个类）
        │   │   ├── UserController.java       # 用户页面控制器
        │   │   ├── UserApiController.java    # 用户 REST API
        │   │   ├── PetController.java        # 宠物页面控制器
        │   │   ├── PetApiController.java     # 宠物 REST API
        │   │   ├── ApplicationController.java
        │   │   ├── ApplicationApiController.java
        │   │   ├── AdminApiController.java   # 管理后台 API
        │   │   ├── UploadController.java     # 图片上传接口
        │   │   ├── ImageController.java      # 本地图片管理
        │   │   └── TestController.java       # 编码测试接口
        │   ├── entity/                       # 实体类（3 个）
        │   │   ├── User.java                 # 用户实体
        │   │   ├── Pet.java                  # 宠物实体
        │   │   └── Application.java          # 领养申请实体
        │   ├── mapper/                       # MyBatis Mapper 接口（3 个）
        │   │   ├── UserMapper.java
        │   │   ├── PetMapper.java
        │   │   └── ApplicationMapper.java
        │   ├── service/                      # 业务服务层
        │   │   ├── UserService.java          # 用户服务接口
        │   │   ├── PetService.java           # 宠物服务接口
        │   │   ├── ApplicationService.java   # 申请服务接口
        │   │   └── impl/                     # 服务实现（3 个）
        │   │       ├── UserServiceImpl.java
        │   │       ├── PetServiceImpl.java
        │   │       └── ApplicationServiceImpl.java
        │   ├── filter/
        │   │   └── CorsFilter.java           # CORS 跨域过滤器
        │   └── interceptor/
        │       └── FrontendInterceptor.java  # SPA 前端路由拦截器
        │
        ├── resources/
        │   ├── db.properties                 # 数据库连接配置
        │   ├── log4j.properties              # 日志配置
        │   ├── applicationContext.xml        # Spring 根容器配置
        │   ├── mybatis-config.xml            # MyBatis 全局配置
        │   └── mapper/                       # MyBatis SQL 映射文件
        │       ├── UserMapper.xml
        │       ├── PetMapper.xml
        │       └── ApplicationMapper.xml
        │
        └── webapp/
            ├── index.html                    # SPA 单页应用入口
            ├── static/
            │   └── js/
            │       └── main.js               # 前端 JS（Vue 组件 + 路由）
            └── WEB-INF/
                ├── web.xml                   # Servlet 容器配置
                └── springmvc-servlet.xml     # Spring MVC 配置
```

---

## 系统架构

```
┌──────────────────────────────────────────────────────┐
│                    前端 SPA 层                         │
│    Vue.js + Vue Router + Axios + Bootstrap            │
│    运行在浏览器端，通过 Hash 路由实现页面切换            │
└────────────────────┬─────────────────────────────────┘
                     │ HTTP/JSON (REST API)
┌────────────────────▼─────────────────────────────────┐
│                  控制层 (Controller)                   │
│  ┌──────────────────┐  ┌─────────────────────────┐   │
│  │  @Controller      │  │  @RestController        │   │
│  │  页面视图控制器    │  │  JSON API 控制器         │   │
│  └──────────────────┘  └─────────────────────────┘   │
│  CorsFilter ← 跨域处理 → FrontendInterceptor ← SPA路由│
└────────────────────┬─────────────────────────────────┘
                     │ @Autowired 依赖注入
┌────────────────────▼─────────────────────────────────┐
│                 业务层 (Service)                       │
│  UserService  │  PetService  │  ApplicationService    │
│       ↓              ↓                ↓               │
│  UserServiceImpl  PetServiceImpl  ApplicationServiceImpl│
│  @Transactional 事务管理  │  MD5 密码加密              │
└────────────────────┬─────────────────────────────────┘
                     │ @Autowired 依赖注入
┌────────────────────▼─────────────────────────────────┐
│               持久层 (Mapper)                          │
│  UserMapper  │  PetMapper  │  ApplicationMapper      │
│       ↓              ↓                ↓               │
│  UserMapper.xml  PetMapper.xml  ApplicationMapper.xml │
│  (MyBatis XML SQL 映射)                               │
└────────────────────┬─────────────────────────────────┘
                     │ JDBC
┌────────────────────▼─────────────────────────────────┐
│              MySQL 数据库 (pet_adoption)                │
│  users  │  pets  │  applications                     │
└──────────────────────────────────────────────────────┘
```

**分层职责：**

| 层次 | 职责 | 关键注解 |
|------|------|----------|
| 表现层 (Controller) | 处理 HTTP 请求，参数校验，返回 JSON/视图 | `@Controller` / `@RestController` |
| 业务层 (Service) | 封装业务逻辑，事务管理 | `@Service` / `@Transactional` |
| 持久层 (Mapper) | 数据库 CRUD 操作 | `@Mapper` + XML |
| 实体层 (Entity) | 数据模型，业务判断方法 | 普通 POJO |

**设计特点：**
- **双轨表现层**：传统 `@Controller` 提供页面，`@RestController` 提供 JSON API
- **接口隔离**：Service 层接口与实现分离，便于测试与扩展
- **SPA 路由**：`FrontendInterceptor` 将非 API/静态资源请求统一转发至 `index.html`
- **跨域支持**：`CorsFilter` 处理 CORS 预检与响应头

---

## 界面预览

> 启动项目后访问 `http://localhost:8080` 即可看到以下页面：

### 前台页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 🏠 首页 | `#/` | 欢迎页面，展示热门宠物 |
| 🔍 宠物列表 | `#/pets` | 按类型筛选，浏览可领养宠物 |
| 📋 宠物详情 | `#/pet/:id` | 宠物详细信息，申请领养入口 |
| 📝 提交申请 | `#/apply/:petId` | 填写领养申请表单 |
| 📄 我的申请 | `#/my-applications` | 查看申请历史与状态 |
| 👤 个人中心 | `#/profile` | 修改个人信息和密码 |
| 🔑 登录 | `#/login` | 用户登录 |
| 📧 注册 | `#/register` | 用户注册 |

### 管理后台

| 页面 | 路由 | 说明 |
|------|------|------|
| 📊 仪表盘 | `#/admin` | 管理后台首页导航 |
| 👥 用户管理 | `#/admin/users` | 启用/禁用/删除用户 |
| 🐕 宠物管理 | `#/admin/pets` | 增删改查宠物信息 |
| ➕ 添加宠物 | `#/admin/pet-form` | 宠物信息表单录入 |
| 📋 申请审核 | `#/admin/applications` | 查看申请、通过/拒绝 |
| 📝 申请详情 | `#/admin/application/:id` | 完整查看申请信息 |

---

## 快速开始

### 环境要求

| 软件 | 版本要求 | 说明 |
|------|----------|------|
| JDK | 8 及以上 | Java 运行环境 |
| MySQL | 5.7 / 8.0 | 数据库 |
| Maven | 3.6+ | 项目构建 |
| Tomcat | 9.x | Web 容器（或使用 IDE 内置） |

### 数据库配置

**1. 创建数据库**

```sql
CREATE DATABASE pet_adoption DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

**2. 导入数据表**

执行以下 SQL 创建核心表结构：

```sql
-- 用户表
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'user',
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 宠物表
CREATE TABLE pets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20),
    breed VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    color VARCHAR(30),
    weight DECIMAL(10,2),
    health_status VARCHAR(100),
    vaccination INT DEFAULT 0,
    description TEXT,
    image_urls TEXT,
    status VARCHAR(20) DEFAULT '上架',
    view_count INT DEFAULT 0,
    user_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 领养申请表
CREATE TABLE applications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    pet_id INT,
    applicant_name VARCHAR(50),
    applicant_phone VARCHAR(20),
    applicant_address VARCHAR(255),
    house_type VARCHAR(50),
    has_experience INT DEFAULT 0,
    reason TEXT,
    status VARCHAR(20) DEFAULT '待审核',
    reviewer_id INT,
    review_comment TEXT,
    reviewed_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 插入默认管理员账户（密码为 123456 的 MD5 值）
INSERT INTO users (username, password, email, role, status)
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin@petadopt.com', 'admin', 1);
```

**3. 配置数据库连接**

编辑 `src/main/resources/db.properties`：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/pet_adoption?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=你的数据库密码
```

**4. 配置图片目录**

编辑 `src/main/webapp/WEB-INF/springmvc-servlet.xml` 中的图片路径：

```xml
<!-- 将路径改为你本机存放宠物图片的目录 -->
<mvc:resources mapping="/uploads/**" location="file:D:/你的图片目录/"/>
```

### 运行项目

**方式一：IDE 运行（推荐）**

1. 使用 IntelliJ IDEA 或 Eclipse 导入 Maven 项目
2. 配置 Tomcat Server
3. 部署 WAR 包或使用 exploded 部署
4. 启动服务器，访问 `http://localhost:8080`

**方式二：Maven 命令行**

```bash
# 1. 编译打包
mvn clean package

# 2. 将 target/pet-adoption-1.0-SNAPSHOT.war 部署到 Tomcat 的 webapps 目录

# 3. 启动 Tomcat，访问 http://localhost:8080/pet-adoption-1.0-SNAPSHOT/
```

---

## API 接口文档

### 用户接口 `/api/user`

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/user/login` | 用户登录，返回 token | 否 |
| POST | `/api/user/register` | 用户注册 | 否 |
| GET | `/api/user/info` | 获取当前用户信息 | 是 |
| PUT | `/api/user/update` | 更新个人信息 | 是 |
| POST | `/api/user/logout` | 退出登录 | 否 |

### 宠物接口 `/api/pets`

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/pets` | 宠物列表（支持 `type`/`status` 筛选） | 否 |
| GET | `/api/pets/{id}` | 宠物详情（自动增加浏览量） | 否 |

### 申请接口 `/api/applications`

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/applications` | 提交领养申请 | 是 |
| GET | `/api/applications/my` | 我的申请列表 | 是 |
| GET | `/api/applications/{id}` | 申请详情 | 是 |
| PUT | `/api/applications/{id}/cancel` | 取消申请 | 是 |

### 管理接口 `/api/admin`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/admin/users` | 用户列表 | admin |
| PUT | `/api/admin/users/{id}/status` | 更新用户状态 | admin |
| DELETE | `/api/admin/users/{id}` | 删除用户 | admin |
| POST | `/api/admin/pets` | 创建宠物 | admin |
| PUT | `/api/admin/pets/{id}` | 更新宠物 | admin |
| PUT | `/api/admin/pets/{id}/status` | 更新宠物状态 | admin |
| DELETE | `/api/admin/pets/{id}` | 删除宠物 | admin |
| GET | `/api/admin/applications` | 所有申请列表 | admin |
| PUT | `/api/admin/applications/{id}/status` | 审核申请 | admin |

---

## 默认账户

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 🛡️ 管理员 | `admin` | `123456` |
| 👤 普通用户 | 自行注册 | — |

---

## 相关文档

- [类与接口分析文档](类与接口分析文档.md) — 详细的类图、接口定义、数据库字段说明
- [Maven 配置](pom.xml) — 完整的项目依赖与构建配置

---

## 📄 License

MIT License — 详见 [LICENSE](LICENSE) 文件

---

> 💡 **提示**：如果你在学习 SSM 框架或 Vue.js 前端开发，这个项目是一个很好的参考实例。它涵盖了从数据库设计、后端接口开发到前端 SPA 应用的完整流程。
