# VSCode 开发环境配置指南

## 1. 必需扩展

在 VSCode 扩展市场搜索并安装：

| 扩展名称 | 用途 |
|----------|------|
| **Extension Pack for Java** | Java 开发全家桶（含 Language Support, Debugger, Test Runner, Maven, Project Manager） |
| **Spring Boot Extension Pack** | Spring Boot 支持（含 Boot Dashboard, Initializr, 属性提示） |
| **Lombok Annotations Support** | Lombok 注解自动生成 getter/setter 识别 |

一键安装：打开 VSCode → `Cmd+Shift+X` → 搜索上面名称 → Install

## 2. JDK 路径配置

### 检查已安装的 JDK

```bash
/Users/cowboy/jdk-17/Contents/Home/bin/java --version
# openjdk 17.0.19
```

### 在 VSCode 中配置

打开 VSCode Settings (`Cmd+,`)，搜索 `java.jdt.ls.java.home`，填入：

```
/Users/cowboy/jdk-17/Contents/Home
```

或在项目根目录 `.vscode/settings.json` 中配置（推荐）：

```json
{
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-17",
            "path": "/Users/cowboy/jdk-17/Contents/Home"
        }
    ],
    "java.jdt.ls.java.home": "/Users/cowboy/jdk-17/Contents/Home"
}
```

## 3. Maven 命令

项目使用 Maven Wrapper，无需全局安装 Maven，直接在项目根目录 `work-1/` 下运行：

| 命令 | 作用 |
|------|------|
| `./mvnw compile` | 编译项目 |
| `./mvnw test` | 运行测试 |
| `./mvnw package` | 打包为 jar |
| `./mvnw spring-boot:run` | 启动应用 |
| `./mvnw clean` | 清理编译产物 |

> **注意**：使用 Maven Wrapper 前需设置 `JAVA_HOME` 环境变量：
> ```bash
> export JAVA_HOME=/Users/cowboy/jdk-17/Contents/Home
> ```
> 建议写入 `~/.zshrc` 永久生效：
> ```bash
> echo 'export JAVA_HOME=/Users/cowboy/jdk-17/Contents/Home' >> ~/.zshrc
> ```

## 4. Spring Boot 启动

### 方式一：命令行

```bash
cd work-1
export JAVA_HOME=/Users/cowboy/jdk-17/Contents/Home
./mvnw spring-boot:run
```

启动后访问：
- API 地址：`http://localhost:8080`
- H2 控制台：`http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:userdb`
  - 用户名: `sa`，密码：空

### 方式二：VSCode Spring Boot Dashboard

1. 点击左侧 Spring Boot 图标（叶子图标）
2. 在 `APPS` 列表中右键 `user-management`
3. 选择 `Run`

### 方式三：运行主类

打开 `UserManagementApplication.java`，点击 `main` 方法上方的 `Run` 按钮。

## 5. 验证环境

```bash
# 注册
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"123456"}'

# 登录
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"account":"test","password":"123456"}'
```

返回 JSON 包含 JWT token 即环境配置成功。
