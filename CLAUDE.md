# CLAUDE.md

## 技术栈

| 技术 | 版本 |
|------|------|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Security | 6.2.x |
| Spring Data JPA | 3.2.x |
| Hibernate | 6.4.x |
| H2 Database | (embedded) |
| PostgreSQL Driver | 42.7.x |
| MySQL Driver | 8.3.x |
| jjwt | 0.12.5 |
| Lombok | (latest) |
| Maven | 3.9+ |

## 项目结构

```
com.example
  ├── UserManagementApplication.java    (启动类，含 @EnableJpaAuditing)
  ├── config/
  │   ├── JwtUtil.java                 (JWT 生成/解析)
  │   └── SecurityConfig.java          (BCrypt + SecurityFilterChain)
  ├── common/
  │   ├── Result.java                  (统一响应格式 {code, message, data})
  │   └── exception/
  │       ├── BusinessException.java   (业务异常)
  │       └── GlobalExceptionHandler.java (@RestControllerAdvice)
  └── user/
      ├── controller/UserController.java
      ├── dto/
      │   ├── RegisterRequest.java     (注册 DTO，含 @Valid 校验)
      │   ├── LoginRequest.java        (登录 DTO)
      │   └── AuthResponse.java        (认证响应，含 JWT token)
      ├── domain/User.java             (JPA 实体)
      ├── repository/UserRepository.java
      └── service/
          ├── UserService.java         (接口)
          └── impl/UserServiceImpl.java
```

## 编码规范

- Controller 层只做参数校验和调用 Service，不包含业务逻辑
- Service 采用接口 + 实现分离模式（接口在 service 包，实现在 service.impl 包）
- 统一返回 `Result<T>`，不使用 ResponseEntity 裸返回
- 业务异常统一抛 `BusinessException`，由 `GlobalExceptionHandler` 处理
- 密码使用 BCrypt 加密存储
- 软删除通过 `@SQLDelete` + `@SQLRestriction` 实现
- 审计字段（created_at, updated_at）由 JPA Auditing 自动填充
