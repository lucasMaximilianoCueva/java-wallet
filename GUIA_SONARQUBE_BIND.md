# Guía SonarQube para Bind - Capacitación Completa

## Índice
1. [Instalación y Setup](#1-instalación-y-setup)
2. [Configuración de Proyectos de Ejemplo](#2-configuración-de-proyectos-de-ejemplo)
3. [Dashboard y Visualización de Issues](#3-dashboard-y-visualización-de-issues)

---

# 1. Instalación y Setup

## 1.1 Crear Cuenta en SonarCloud

### Paso 1: Registrarse
1. Ve a: https://sonarcloud.io
2. Click en **"Log in"** o **"Start now"**
3. Selecciona **"With GitHub"**
4. Autoriza SonarCloud en tu cuenta de GitHub

### Paso 2: Crear Organización
1. Una vez logueado, click en **"+"** → **"Create new organization"**
2. Selecciona tu cuenta de GitHub
3. Define el nombre de la organización (ej: `bind-security`)
   - **IMPORTANTE**: Usar minúsculas sin espacios (ej: `lucasmaximilianocueva`)
4. Elige el plan **Free** para proyectos públicos

### Paso 3: Generar Token
1. Ve a: **My Account** → **Security**
2. En "Generate Tokens":
   - Name: `github-actions`
   - Type: `User Token`
   - Expiration: `No expiration` o personalizado
3. Click **Generate**
4. **GUARDA EL TOKEN** (solo se muestra una vez)

---

## 1.2 Configurar GitHub Secrets

### Paso 1: Ir a Settings del Repositorio
```
GitHub Repository → Settings → Secrets and variables → Actions
```

### Paso 2: Agregar SONAR_TOKEN
1. Click **"New repository secret"**
2. Name: `SONAR_TOKEN`
3. Value: [Pega el token generado anteriormente]
4. Click **"Add secret"**

### Paso 3: Agregar SONAR_HOST_URL
1. Click **"New repository secret"**
2. Name: `SONAR_HOST_URL`
3. Value: `https://sonarcloud.io`
4. Click **"Add secret"**

---

## 1.3 Verificar Integración

### Después del push:
1. Ve a **Actions** en tu repositorio de GitHub
2. Verifica que el workflow se ejecute correctamente
3. Espera que termine (1-3 minutos)
4. Ve a SonarCloud para ver los resultados

---

# 2. Configuración de Proyectos de Ejemplo

## 2.1 Proyecto Ejemplo 1: Java Maven (Este Proyecto)

### Estructura Actual
```
java-wallet/
├── src/
│   ├── main/java/Main.java
│   └── test/java/MainTest.java
├── pom.xml
├── sonar-project.properties
└── .github/workflows/build.yml
```

### Configuración en pom.xml
```xml
<properties>
    <sonar.organization>TU_ORGANIZACION</sonar.organization>
    <sonar.host.url>https://sonarcloud.io</sonar.host.url>
    <sonar.projectKey>ORGANIZACION_PROYECTO</sonar.projectKey>
</properties>

<build>
    <plugins>
        <!-- SonarQube Plugin -->
        <plugin>
            <groupId>org.sonarsource.scanner.maven</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.10.0.2594</version>
        </plugin>
        
        <!-- JaCoCo para Coverage -->
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Ejecutar Análisis Local
```bash
# Build y tests
mvn clean verify

# Análisis SonarQube
mvn sonar:sonar \
  -Dsonar.projectKey=TU_PROYECTO \
  -Dsonar.organization=TU_ORG \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=TU_TOKEN
```

---

## 2.2 Proyecto Ejemplo 2: Gradle (Alternativa)

### build.gradle
```gradle
plugins {
    id 'java'
    id 'jacoco'
    id 'org.sonarqube' version '4.4.1.3373'
}

sonar {
    properties {
        property 'sonar.projectKey', 'ORGANIZACION_PROYECTO'
        property 'sonar.organization', 'TU_ORGANIZACION'
        property 'sonar.host.url', 'https://sonarcloud.io'
    }
}

jacocoTestReport {
    reports {
        xml.required = true
    }
}
```

### Ejecutar Análisis
```bash
./gradlew build sonar \
  -Dsonar.token=TU_TOKEN
```

---

## 2.3 Proyecto Ejemplo 3: JavaScript/Node.js

### package.json
```json
{
  "scripts": {
    "test": "jest --coverage",
    "sonar": "sonar-scanner"
  },
  "devDependencies": {
    "jest": "^29.0.0",
    "sonar-scanner": "^3.1.0"
  }
}
```

### sonar-project.properties
```properties
sonar.projectKey=ORGANIZACION_PROYECTO
sonar.organization=TU_ORGANIZACION
sonar.sources=src
sonar.tests=tests
sonar.javascript.lcov.reportPaths=coverage/lcov.info
sonar.testExecutionReportPaths=test-report.xml
```

### GitHub Actions (.github/workflows/sonar.yml)
```yaml
name: SonarCloud Analysis
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  sonarcloud:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0
      
      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '18'
      
      - name: Install dependencies
        run: npm ci
      
      - name: Run tests with coverage
        run: npm test
      
      - name: SonarCloud Scan
        uses: SonarSource/sonarcloud-github-action@master
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
```

---

# 3. Dashboard y Visualización de Issues

## 3.1 Navegación en SonarCloud

### Acceder al Dashboard Principal
1. Ve a: https://sonarcloud.io
2. Selecciona tu **organización**
3. Click en el **proyecto** que quieres revisar

### Dashboard Principal - Métricas Clave

#### 🐛 Bugs (Errores)
- **Definición**: Código que probablemente causará errores en producción
- **Severidad**: Blocker, Critical, Major, Minor
- **Ejemplo**: Null pointer exceptions, resource leaks

#### 🔒 Vulnerabilities (Vulnerabilidades)
- **Definición**: Problemas de seguridad
- **Categorías**: SQL Injection, XSS, Hardcoded credentials
- **Prioridad**: Críticas para resolver primero

#### 💡 Code Smells (Malas Prácticas)
- **Definición**: Código que funciona pero dificulta el mantenimiento
- **Ejemplos**: Código duplicado, métodos muy largos, complejidad ciclomática alta

#### 📊 Coverage (Cobertura)
- **Definición**: % de código cubierto por tests
- **Meta recomendada**: >80%
- **Líneas verdes**: Cubiertas por tests
- **Líneas rojas**: No cubiertas

#### 🔄 Duplications (Duplicaciones)
- **Definición**: Bloques de código repetidos
- **Meta**: <3%
- **Impacto**: Dificulta mantenimiento

---

## 3.2 Filtrar y Priorizar Issues

### Por Severidad
```
Dashboard → Issues → Filter:
- Severity: Blocker, Critical
- Type: Bug, Vulnerability
```

### Por Estado
- **Open**: Sin resolver
- **Confirmed**: Validado como issue real
- **Reopened**: Reabierto después de fix
- **Resolved**: Marcado como resuelto
- **Closed**: Cerrado

### Por Tipo
- **Bug**: Errores de código
- **Vulnerability**: Problemas de seguridad
- **Code Smell**: Malas prácticas
- **Security Hotspot**: Áreas sensibles a revisar

---

## 3.3 Resolver Issues

### Flujo de Trabajo Recomendado

#### 1. Identificar Issue
```
Dashboard → Issues → Click en el issue
```

#### 2. Entender el Problema
- Lee la **descripción** del issue
- Revisa el **código afectado** (highlighted)
- Lee las **recomendaciones** de SonarQube

#### 3. Resolver en Código
```java
// ❌ Antes (Issue detectado)
public void processData(String data) {
    String result = data.toLowerCase();  // Posible NullPointerException
}

// ✅ Después (Issue resuelto)
public void processData(String data) {
    if (data == null) {
        throw new IllegalArgumentException("Data cannot be null");
    }
    String result = data.toLowerCase();
}
```

#### 4. Verificar Resolución
- Haz **commit** del fix
- El **CI/CD** ejecuta SonarQube automáticamente
- SonarQube marca el issue como **resolved**

---

## 3.4 Quality Gates (Puertas de Calidad)

### ¿Qué es un Quality Gate?
Conjunto de condiciones que el código debe cumplir para ser considerado "de calidad".

### Quality Gate por Defecto (Sonar Way)
```
✅ Conditions:
- Coverage on New Code ≥ 80%
- Duplicated Lines on New Code ≤ 3%
- Maintainability Rating on New Code = A
- Reliability Rating on New Code = A
- Security Rating on New Code = A
- Security Hotspots Reviewed = 100%
```

### Configurar Quality Gate Personalizado

#### Paso 1: Crear Quality Gate
```
Organization Settings → Quality Gates → Create
```

#### Paso 2: Definir Condiciones
```
Add Condition → Select Metric:
- Coverage: ≥ 70%
- Bugs: = 0
- Vulnerabilities: = 0
- Code Smells: ≤ 10
```

#### Paso 3: Asignar al Proyecto
```
Project Settings → Quality Gate → Select custom gate
```

---

## 3.5 Reportes y Monitoreo

### Actividad del Proyecto
```
Dashboard → Activity
```
- **Gráfico temporal** de métricas
- **Análisis históricos**
- **Tendencias** de calidad

### Comparación de Branches
```
Dashboard → Branches
```
- Ver análisis por **rama**
- Comparar **main** vs **feature branches**

### Pull Request Analysis
```
Dashboard → Pull Requests
```
- Análisis automático de PRs
- Ver **nuevo código** agregado
- **Quality Gate** específico para PRs

---

## 3.6 Integración con GitHub

### Decoración de Pull Requests

SonarCloud agrega comentarios automáticos en PRs:
```
✅ Quality Gate passed
📊 Coverage: 85.2% (+2.3%)
🐛 Bugs: 0
🔒 Vulnerabilities: 0
💡 Code Smells: 3
```

### Configurar Decoración
```
SonarCloud → Organization Settings → GitHub
→ Enable "Decorate Pull Requests"
```

---

## 3.7 Exportar Reportes

### PDF Report
```
Dashboard → More → Download as PDF
```

### API para Reportes Personalizados
```bash
curl -u TOKEN: \
  "https://sonarcloud.io/api/measures/component?component=PROJECT_KEY&metricKeys=bugs,vulnerabilities,code_smells,coverage"
```

---

# 4. Mejores Prácticas para el Equipo

## 4.1 Workflow Diario

### Desarrolladores
1. **Antes de commit**: Ejecutar análisis local
   ```bash
   mvn clean verify sonar:sonar -Dsonar.token=TOKEN
   ```

2. **Revisar issues** en el PR antes de merge

3. **No mergear** si Quality Gate falla

### Infosec / Security Team
1. **Revisar diariamente**: Dashboard de vulnerabilidades
   ```
   Dashboard → Issues → Type: Vulnerability
   ```

2. **Priorizar** Security Hotspots
   ```
   Dashboard → Security Hotspots → Review
   ```

3. **Configurar notificaciones**:
   ```
   My Account → Notifications → Enable:
   ✅ New issues on my projects
   ✅ Quality gate status changes
   ```

---

## 4.2 Configuración de Alertas

### Email Notifications
```
My Account → Notifications → Configure:
- New issues assigned to me
- My new issues
- Quality Gate changes
```

### Webhooks para Slack/Teams
```
Organization Settings → Webhooks → Create:
URL: https://hooks.slack.com/services/YOUR_WEBHOOK
Events: Quality Gate status changed
```

---

## 4.3 Métricas de Seguimiento

### KPIs Recomendados
```
✅ Technical Debt Ratio < 5%
✅ Coverage > 80%
✅ Bugs = 0 (en código nuevo)
✅ Vulnerabilities = 0
✅ Duplications < 3%
```

### Dashboard de Ejecutivos
Exportar métricas semanalmente:
- Total de issues resueltos
- Tendencia de cobertura
- Deuda técnica reducida
- Nuevas vulnerabilidades encontradas/resueltas

---

# 5. Troubleshooting Común

## Error: "Not authorized"
**Solución**: Verificar que `SONAR_TOKEN` esté configurado correctamente.

## Quality Gate siempre falla
**Solución**: Revisar condiciones del QG y ajustar si son muy estrictas inicialmente.

## Coverage no se reporta
**Solución**: Verificar que JaCoCo esté configurado y que `jacoco.xml` se genere.

## Issues no se marcan como resueltos
**Solución**: Asegurar que el código modificado efectivamente resuelva el issue.

---

# 6. Recursos Adicionales

- **Documentación oficial**: https://docs.sonarcloud.io
- **Reglas de calidad**: https://rules.sonarsource.com
- **Community**: https://community.sonarsource.com
- **Soporte**: support@sonarsource.com

---

**Fin de la Guía de Capacitación**

Para consultas específicas sobre este proyecto, revisa:
- `SONARQUBE_SETUP.md` - Setup técnico detallado
- `README_SONARQUBE.md` - Resumen rápido

---

**Última actualización:** 2025-10-08  
**Build Tool:** Maven 3.x  
**Java Version:** 17 (requerido por SonarQube)  
**SonarQube Plugin:** 3.10.0.2594
