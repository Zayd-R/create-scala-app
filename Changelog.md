
All notable changes to this project will be documented in this file.

---

## [Unreleased]

### Added
- `--fresh` flag to fetch latest dependency versions from external sources

### Changed
- Restructured project templates to match final generated output (create-react-app style)
- Removed Scala version prompt; now defaults to an opinionated LTS version
- Refactored project structure for better separation of concerns across libraries
- Switched from Scala Native to GraalVM for native image builds  
  - Improves ecosystem compatibility (better support for common JVM libraries)
  - Reduces friction when integrating with existing tooling and dependencies
  - Provides more stable and predictable builds compared to current Scala Native limitations (experimental)


### Fixed
- Template system now throws an error if any `{{}}` placeholder is not replaced

### Known Issues
- Dependency resolution via libraries.io is rate-limited and may return inconsistent results
- Planned migration to Maven Central for more reliable version fetching
