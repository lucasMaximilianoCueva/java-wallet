# 🎯 Demo Issues - SonarQube Presentation

Este proyecto contiene **problemas intencionales** para demostrar las capacidades de detección de SonarQube.

## 📊 Tipos de Issues Incluidos

### 🔴 Security Issues (Vulnerabilidades de Seguridad)

#### 1. SQL Injection
**Archivos:** `UserAuthenticator.java`, `DatabaseHelper.java`
- Concatenación de strings en queries SQL
- Permite ataques de inyección SQL
- **Severidad:** CRITICAL

#### 2. Hardcoded Credentials
**Archivos:** `UserAuthenticator.java`, `DatabaseHelper.java`, `PasswordManager.java`
- Contraseñas y credenciales en código fuente
- Riesgo de exposición de credenciales
- **Severidad:** CRITICAL

#### 3. Weak Cryptography
**Archivos:** `UserAuthenticator.java`, `PasswordManager.java`
- Uso de MD5 (algoritmo débil)
- Uso de DES (algoritmo obsoleto)
- **Severidad:** CRITICAL

#### 4. Insecure Random
**Archivos:** `UserAuthenticator.java`
- Uso de `java.util.Random` para tokens de seguridad
- Números predecibles
- **Severidad:** MAJOR

#### 5. Sensitive Data Logging
**Archivos:** `PasswordManager.java`
- Logging de contraseñas en logs
- Exposición de información sensible
- **Severidad:** CRITICAL

### 🟡 Security Hotspots (Puntos Críticos de Seguridad)

#### 1. Encryption Keys
**Archivos:** `PasswordManager.java`
- Claves de encriptación hardcodeadas
- Requiere revisión manual
- **Severidad:** HIGH

#### 2. Weak Password Validation
**Archivos:** `PasswordManager.java`
- Validación de contraseñas muy simple
- Sin requisitos de complejidad
- **Severidad:** MEDIUM

#### 3. Path Traversal
**Archivos:** `FileManager.java`
- Rutas de archivos controladas por usuario
- Permite acceso a archivos del sistema
- **Severidad:** HIGH

#### 4. Command Injection
**Archivos:** `FileManager.java`
- Ejecución de comandos con input del usuario
- Permite ejecución de código arbitrario
- **Severidad:** CRITICAL

### 🔵 Reliability Issues (Problemas de Confiabilidad)

#### 1. Resource Leaks
**Archivos:** `FileManager.java`, `DatabaseHelper.java`
- Streams y conexiones no cerradas
- Fuga de recursos
- **Severidad:** MAJOR

#### 2. Null Pointer Risks
**Archivos:** `StringUtils.java`, `DatabaseHelper.java`
- Acceso a objetos sin validación de null
- Potenciales NullPointerException
- **Severidad:** MAJOR

#### 3. Poor Exception Handling
**Archivos:** `FileManager.java`, `DatabaseHelper.java`
- Catch de Exception genérico
- printStackTrace() en producción
- **Severidad:** MINOR

#### 4. Array Index Out of Bounds
**Archivos:** `StringUtils.java`
- Acceso a arrays sin validar tamaño
- **Severidad:** MAJOR

### 🟢 Code Smells (Problemas de Mantenibilidad)

#### 1. Code Duplication
**Archivos:** `DataValidator.java`, `StringUtils.java`, `DatabaseHelper.java`
- Código duplicado en múltiples métodos
- Dificulta mantenimiento
- **Severidad:** MAJOR

#### 2. High Cyclomatic Complexity
**Archivos:** `DataValidator.java`
- Método `validateUserInput` con muchas condiciones anidadas
- Difícil de testear y mantener
- **Severidad:** MAJOR

#### 3. Magic Numbers
**Archivos:** `DataValidator.java`
- Números hardcodeados sin constantes
- **Severidad:** MINOR

## 📈 Métricas Esperadas en SonarQube

Después del análisis, deberías ver:

- **Security Issues:** ~15-20 issues
- **Security Hotspots:** ~8-10 hotspots
- **Reliability Issues:** ~10-15 issues
- **Maintainability Issues:** ~20-30 code smells
- **Duplications:** ~15-20% de código duplicado
- **Coverage:** ~60-70% (con los tests comprehensivos)

## 🎓 Valor para el Cliente

Este proyecto demuestra cómo SonarQube puede detectar:

1. **Vulnerabilidades críticas** antes de producción
2. **Problemas de seguridad** que podrían ser explotados
3. **Fugas de recursos** que afectan rendimiento
4. **Código duplicado** que aumenta costos de mantenimiento
5. **Complejidad excesiva** que dificulta el desarrollo

## ⚠️ IMPORTANTE

**Estos problemas son INTENCIONALES para demostración.**

En un proyecto real:
- ✅ Usar PreparedStatement para SQL
- ✅ Nunca hardcodear credenciales
- ✅ Usar algoritmos seguros (SHA-256, AES)
- ✅ Usar SecureRandom para tokens
- ✅ Cerrar recursos con try-with-resources
- ✅ Validar todas las entradas de usuario
- ✅ Eliminar código duplicado
- ✅ Nunca loggear información sensible

## 🚀 Próximos Pasos

1. Commit y push de los cambios
2. Esperar análisis de SonarQube
3. Revisar dashboard con el cliente
4. Mostrar cómo resolver cada tipo de issue
