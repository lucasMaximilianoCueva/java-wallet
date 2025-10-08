# SonarQube Integration - Java To-Do List

## ✅ Configuración Completada

Este proyecto está configurado para análisis con **SonarCloud** usando **Maven**.

---

## 📁 Estructura del Proyecto

```
java-wallet/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── Main.java
│   └── test/
│       └── java/
│           └── MainTest.java
├── pom.xml
├── sonar-project.properties
└── .github/workflows/build.yml
```

---

## 📋 Archivos Configurados

### 1. `pom.xml` ✅
- Plugin `sonar-maven-plugin` (v3.10.0.2594)
- Plugin `jacoco-maven-plugin` (v0.8.11) para cobertura
- Configuración headless para tests de UI
- Main class configurada: `Main`

### 2. `.github/workflows/build.yml` ✅
- GitHub Actions configurado para Maven
- Cache de Maven y SonarQube
- Comando de análisis integrado

### 3. `sonar-project.properties` ✅
- Configuración de SonarQube
- Paths de código fuente y tests
- Configuración de JaCoCo

### 4. `src/test/java/MainTest.java` ✅
- Tests básicos para evitar fallos de build

---

## 🔑 Configurar Secrets en GitHub

**IMPORTANTE:** Configura estos secrets antes de hacer push:

1. Ve a: `Settings` → `Secrets and variables` → `Actions`
2. Agrega:

### SONAR_TOKEN
```
Obtener en: https://sonarcloud.io/account/security
Tipo: User Token
```

### SONAR_HOST_URL
```
Valor: https://sonarcloud.io
```

---

## 🚀 Comandos Útiles

### Build local
```bash
mvn clean compile
```

### Ejecutar aplicación
```bash
mvn clean package
java -jar target/java-todo-list-1.0-SNAPSHOT.jar
```

### Build + Tests
```bash
mvn clean verify
```

### Build + Tests + SonarQube (local)
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=lucasMaximilianoCueva_java-wallet \
  -Dsonar.organization=lucasmaximilianocu eva \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=TU_TOKEN_AQUI
```

---

## 📊 Verificar Resultados

### En GitHub Actions
1. Ve a la pestaña **Actions** en tu repositorio
2. Verifica que el workflow "Build and analyze" se ejecute correctamente
3. Revisa los logs si hay errores

### En SonarCloud
1. Ve a: https://sonarcloud.io/organizations/lucasmaximilianocu eva/projects
2. Busca el proyecto `java-wallet`
3. Revisa:
   - **Bugs**: Errores de código
   - **Vulnerabilities**: Problemas de seguridad
   - **Code Smells**: Malas prácticas
   - **Coverage**: Cobertura de tests
   - **Duplications**: Código duplicado

---

## 🔧 Personalizar Configuración

### Cambiar organización o projectKey

Edita `pom.xml`:
```xml
<properties>
    <sonar.organization>TU_ORGANIZACION</sonar.organization>
    <sonar.projectKey>TU_ORGANIZACION_TU_PROYECTO</sonar.projectKey>
</properties>
```

Y actualiza `.github/workflows/build.yml`:
```yaml
run: mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=TU_ORGANIZACION_TU_PROYECTO
```

### Excluir archivos del análisis

Edita `sonar-project.properties`:
```properties
sonar.exclusions=**/generated/**,**/dto/**,**/config/**
sonar.test.exclusions=**/integration/**
```

---

## 🐛 Troubleshooting

### Error: "HeadlessException: No X11 DISPLAY variable was set"
**Causa:** Tests de UI (Swing/AWT) fallan en CI porque no hay display gráfico.

**Solución:** Ya configurado en `pom.xml`:
```xml
<configuration>
    <argLine>-Djava.awt.headless=true ${argLine}</argLine>
</configuration>
```

### Error: "Not authorized. Please check the properties sonar.login"
**Solución:** Verifica que `SONAR_TOKEN` esté configurado correctamente en GitHub Secrets.

### Error: "Project key already exists"
**Solución:** Cambia el `sonar.projectKey` en `pom.xml`, `sonar-project.properties` y en el workflow.

### Error: "No tests were executed"
**Solución:** Ya incluido `MainTest.java` con tests básicos.

### Build local funciona pero falla en CI
**Solución:** 
1. Verifica que la estructura Maven sea correcta
2. Asegúrate de que `src/main/java` y `src/test/java` existan
3. Revisa los logs del workflow en GitHub Actions

---

## 📚 Plugins Incluidos

### 1. SonarQube Maven Plugin (v3.10.0.2594)
- Ejecuta el análisis de código
- Envía resultados a SonarCloud

### 2. JaCoCo Maven Plugin (v0.8.11)
- Genera reportes de cobertura de código
- Integrado con SonarQube

### 3. Maven Surefire Plugin (v3.0.0)
- Ejecuta tests unitarios
- Configurado en modo headless para CI

### 4. Maven JAR Plugin (v3.3.0)
- Genera JAR ejecutable
- Main class: `Main`

---

## ✅ Checklist de Verificación

Antes de hacer push:

- [x] Estructura Maven creada (`src/main/java`, `src/test/java`)
- [x] `pom.xml` configurado con plugins de SonarQube y JaCoCo
- [x] `sonar-project.properties` creado
- [x] Tests básicos creados en `src/test/java`
- [ ] `SONAR_TOKEN` configurado en GitHub Secrets
- [ ] `SONAR_HOST_URL` configurado en GitHub Secrets
- [ ] Build local exitoso: `mvn clean verify`

---

## 🎯 Próximos Pasos

1. **Configura los secrets** en GitHub (SONAR_TOKEN y SONAR_HOST_URL)

2. **Commit y push:**
   ```bash
   git add .
   git commit -m "feat: add Maven structure and SonarQube integration"
   git push
   ```

3. **Verifica el workflow** en GitHub Actions

4. **Revisa resultados** en SonarCloud

5. **Agrega más tests** para mejorar la cobertura

6. **Agrega badge** de SonarCloud al README (opcional):
   ```markdown
   [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lucasMaximilianoCueva_java-wallet&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lucasMaximilianoCueva_java-wallet)
   
   [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=lucasMaximilianoCueva_java-wallet&metric=coverage)](https://sonarcloud.io/summary/new_code?id=lucasMaximilianoCueva_java-wallet)
   ```

---

**Última actualización:** 2025-10-08  
**Build Tool:** Maven 3.x  
**Java Version:** 11  
**SonarQube Plugin:** 3.10.0.2594  
**Proyecto:** Java Swing To-Do List
