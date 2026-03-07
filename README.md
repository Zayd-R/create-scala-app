# create-scala-app

🚀 **Get started with Scala projects quickly.**

A scaffolding CLI tool for creating Scala projects with common templates — inspired by `create-react-app`.

---

## 📚 Table of Contents

- [Why This Exists](#why-this-exists)
- [Installation](#installation)
- [Usage](#usage)
- [Available Templates](#available-templates)
    - [basic – Simple Scala Project](#basic--simple-scala-project)
    - [typelevel – HTTP Server with Database](#typelevel--http-server-with-database)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Development Status](#development-status)
- [Requirements](#requirements)
- [Philosophy](#philosophy)
- [Building from Source](#building-from-source)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [FAQ](#faq)
- [Support](#support)
- [Roadmap](#roadmap)

---

# Why This Exists

Scala has a reputation for being **difficult to set up**, especially for beginners.

This tool provides **working project templates** so you can focus on:

- Learning Scala
- Building applications
- Understanding project structure

Instead of spending hours configuring:

- `sbt`
- dependencies
- project structure

---

# Installation

Download the binary from:

https://github.com/zayd-r/create-scala-app/releases

---

## Linux

```bash
# Download latest release
wget https://github.com/zayd-r/create-scala-app/releases/latest/download/create-scala-app

# Make executable
chmod +x create-scala-app

# Move to PATH (optional)
sudo mv create-scala-app /usr/local/bin/create-scala-app
```

---

## macOS

```bash
# Download
curl -L -o create-scala-app https://github.com/YOUR_USERNAME/create-scala-app/releases/latest/download/create-scala-app-macos

# Make executable
chmod +x create-scala-app

# Move to PATH (optional)
sudo mv create-scala-app /usr/local/bin/
```

---

# Usage

```bash
# List available templates
create-scala-app -templates

# Create a new project
create-scala-app <template-name>

# Examples
create-scala-app basic
create-scala-app typelevel
```

---

# Available Templates

---

## basic – Simple Scala Project

A **minimal Scala 3 project** for learning or quick experiments.

### Includes

- Basic sbt configuration
- Simple `Main.scala`
- `.gitignore`
- `README`

### Use When

- Learning Scala
- Quick prototypes
- Command-line tools

### Example

```bash
create-scala-app basic my-first-project
cd my-first-project
sbt run
```

---

## typelevel – HTTP Server with Database

A **full-featured web service** using the Typelevel ecosystem.

### Includes

- HTTP Server — `http4s` with Ember
- Database — PostgreSQL with Doobie
- Configuration — PureConfig with env support
- Docker — `docker-compose.yml`
- Structured packages

### Tech Stack

- Cats Effect 3
- http4s
- Doobie
- Circe (JSON)
- PureConfig

### Use When

- Building REST APIs
- Microservices
- Web backends
- Learning functional programming

### Example

```bash
create-scala-app typelevel my-api
cd my-api

# Start database
docker-compose up -d

# Run server
sbt run

# Test endpoint
curl http://localhost:8080/health
```

---

# Project Structure

---

## Basic Template

```
basic/
├── build.sbt
├── project/
│   └── build.properties
├── src/
│   └── main/
│       └── scala/
│           └── Main.scala
└── README.md
```

---

## Typelevel Template

```
typeLevel/
├── build.sbt
├── docker-compose.yml
├── project/
│   └── build.properties
└── src/
    └── main/
        ├── resources/
        │   └── application.conf
        └── scala/
            ├── Main.scala
            ├── config/
            │   ├── AppConfig.scala
            │   ├── DatabaseConfig.scala
            │   └── ServerConfig.scala
            ├── modules/
            │   ├── Database.scala
            │   └── HttpApi.scala
            └── routes/
                └── http/
                    └── HealthRoutes.scala
```

---

# Configuration

The **typelevel template** supports configuration through:

1. `application.conf`
2. Environment variables

---

## Environment Variables

```bash
# Server
export SERVER_HOST="0.0.0.0"
export SERVER_PORT="8080"

# Database
export DATABASE_URL="jdbc:postgresql://localhost:5432/myapp"
export DATABASE_USER="postgres"
export DATABASE_PASSWORD="postgres"

sbt run
```

See the generated project `README` for more configuration details.

---

# Development Status

⚠️ **Early development (v0.1.0)**

### Current Limitations

- Only **2 templates** (`basic`, `typelevel`)
- Some versions are **hardcoded**
- Limited customization
- **Linux/macOS only**

### Planned Features

- More templates (Zio, FS2, Akka)
- Interactive template selection
- Scala version selection
- Custom template support
- Windows support

---

#  Dev Requirements

### Basic Template

- `sbt` **1.9+**
- `Java` **11+**

### Typelevel Template

- `sbt` **1.9+**
- `Java` **11+**
- `Docker` (for PostgreSQL)

---

# Philosophy

These templates prioritize:

> **"Working out of the box" over perfect production setups**

They are designed for:

✅ Learning  
✅ Prototyping  
✅ Understanding project structure

For production deployments you will likely add:

- Secret management
- Monitoring
- Logging
- Performance tuning
- Deployment configuration

---

# Building from Source

```bash
# Clone repository
git clone https://github.com/zayd-r/create-scala-app.git
cd create-scala-app

# Build native binary
sbt nativeLink
```

Binary output:

```
target/scala-3.3.5/create-scala-app
```

Test it:

```bash
./target/scala-3.3.5/create-scala-app basic test-project
```

---

# Contributing

Contributions are welcome!

Areas where help is needed:

- New templates (ZIO, Akka, fs2)
- Documentation improvements
- Bug reports
- Feature requests

See **CONTRIBUTING.md** for contribution guidelines.

---

# License

MIT License

See **LICENSE** for details.

---

# Acknowledgments

Inspired by:

- `create-react-app`
- `Giter8`
- The **Typelevel** and **Scala** communities

---

# FAQ

### Why not use Giter8?

Giter8 is powerful but also complex (A nice way of saying I hate it becuase it did not work when i used it :) ).

`create-scala-app` aims for:

- Simplicity
- Fast setup
- Minimal configuration

---

### Can I add custom templates?

Not yet.

Custom template support is planned.

---

### Why Scala Native?

To distribute a **single binary CLI** with no JVM required.

Generated projects still run on the JVM.

---

### Does this work on Windows?

Not yet — Windows support is planned.

---

### How do I update library versions?

Currently you must update `build.sbt` manually.

Automatic updates are planned.

---

# Support

- 🐛 Report bugs – https://github.com/YOUR_USERNAME/create-scala-app/issues
- 💡 Request features – https://github.com/YOUR_USERNAME/create-scala-app/issues
- 💬 Discussions – https://github.com/YOUR_USERNAME/create-scala-app/discussions

---

# Roadmap
```TODO ...```

✨ **Happy coding!**

Made with ❤️ for the Scala community.
