# 用户管理模块测试报告

**测试时间**: 2026-05-22
**测试环境**: JDK 17 + Spring Boot 3.2.5 + H2 内存数据库

---

## 测试用例

| # | 接口 | 用例 | 预期结果 | 实际结果 |
|---|------|------|----------|----------|
| 1 | POST /api/users/register | 正常注册 | 200, 返回用户信息+JWT | ✅ 通过 |
| 2 | POST /api/users/register | 重复用户名注册 | 400, "用户名已存在" | ✅ 通过 |
| 3 | POST /api/users/register | 重复邮箱注册 | 400, "邮箱已被注册" | ✅ 通过 |
| 4 | POST /api/users/register | 空用户名/非法邮箱/短密码 | 400, 具体校验错误 | ✅ 通过 |
| 5 | POST /api/users/login | 用户名登录 | 200, 返回JWT | ✅ 通过 |
| 6 | POST /api/users/login | 邮箱登录 | 200, 返回JWT | ✅ 通过 |
| 7 | POST /api/users/login | 错误密码 | 400, "账号或密码错误" | ✅ 通过 |
| 8 | GET /api/users/{id} | 带Token查询 | 200, 返回用户(不含密码) | ✅ 通过 |
| 9 | GET /api/users | 带Token查询全部 | 200, 返回用户列表 | ✅ 通过 |
| 10 | DELETE /api/users/{id} | 软删除 | 200, 删除成功 | ✅ 通过 |
| 11 | GET /api/users/{id} | 删除后查询 | 404, 用户已不存在 | ✅ 通过 |
| 12 | GET /api/users/1 | 无Token访问 | 403, 被拦截 | ✅ 通过 |

---

## 安全性验证

- [x] 密码 BCrypt 加密存储
- [x] 密码字段不在 API 响应中返回 (`@JsonIgnore`)
- [x] 无 Token 请求返回 403
- [x] 错误密码登录不泄露具体原因（统一提示"账号或密码错误"）
- [x] 软删除后数据不可查询

## 代码结构验证

- [x] Controller -> Service 接口 -> Service 实现 -> Repository 分层
- [x] 统一返回 `Result<T>` 格式
- [x] 全局异常处理 `@RestControllerAdvice`
- [x] JSR-303 参数校验 (`@Valid`)
- [x] JWT 生成和解析 (jjwt 0.12.5)
- [x] JWT 过滤器注册到 SecurityFilterChain
