# General Code Style Principles

## Readability

- Code should be easy to read and understand by humans.
- Avoid overly clever or obscure constructs.

## Consistency

- Follow existing patterns in the codebase.
- Maintain consistent formatting, naming, and structure.

## Simplicity

- Prefer simple solutions over complex ones.
- Break down complex problems into smaller, manageable parts.

## Maintainability

- Write code that is easy to modify and extend.
- Minimize dependencies and coupling.

## Documentation

- Document *why* something is done, not just *what*.
- Keep documentation up-to-date with code changes.

# Java Code Style

## 1. Source File Structure & Basics

* **File Naming:** The filename must match the case-sensitive name of the single top-level class with a `.java` extension.

* **Encoding:** All files must be encoded in UTF-8.

* **Whitespace:** Use the ASCII horizontal space character () only.

* **No Tabs:** Tab characters are strictly forbidden for indentation.

* **File Order:** Sections must appear in this order, separated by exactly one blank line:
1. Package declaration
2. Import statements
3. Exactly one top-level class

## 2. Imports & Modules

* **No Wildcards:** Never use wildcard imports, including static ones.

* **Static Imports:** Place all static imports in a single block before non-static imports. Do not use static imports for static nested classes; use normal imports.

* **Ordering:** Within a group (static vs. non-static), names must be in ASCII sort order.

* **Module Directives:** Follow the strict order: `requires`, `exports`, `opens`, `uses`, `provides` .

## 3. Formatting & Braces

* **Indentation:**
  
  * **Block Indent:** +2 spaces for every new block.
  
  * **Continuation Indent:** +4 spaces (minimum) for line-wrapping.

* **Braces:**
  
  * **Always Use Braces:** Required for `if`, `else`, `for`, `do`, and `while`, even if the body is empty or a single line.
  
  * **K&R Style:** No line break before; line break after; line break before `else`/`catch`/`finally`.
  
  * **Column Limit:** Code must not exceed 100 characters per line.
  
  * **Line-wrapping:** Prefer breaking at the highest syntactic level.

### 4. Naming Conventions

Identifiers must use only ASCII letters, digits, and underscores (no `mPrefix` or `s_suffix`).

| Entity              | Style                      | Example              |
| ------------------- | -------------------------- | -------------------- |
| Packages / Modules  | lowercase (no underscores) | `com.google.example` |
| Classes / Records   | UpperCamelCase             | `DataProcessor`      |
| Methods             | lowerCamelCase             | `calculateTotal()`   |
| Constants           | UPPER SNAKE CASE           | `MAX_RETRY_COUNT`    |
| Parameters / Fields | lowerCamelCase             | `userName`           |
|                     |                            |                      |

## 5. Programming Practices

* **Method Overloading:** Keep overloaded methods/constructors in a contiguous group; do not split them with other members.

* **Annotations:** The `@Override` annotation must be used whenever it is legal.

* **Exception Handling:** Caught exceptions must never be ignored (empty catch blocks) without a significant, documented reason.


# Java Coding Standards

Enforces technical directives for the project, covering Java. Use when generating, refactoring, or reviewing code to ensure architectural compliance.

* **Immutable Data:** Prefer Java Records (`record`) over traditional POJOs for DTOs and value objects.

* **Control Flow:** Utilize Pattern Matching for switch and instanceof.

* **Null Safety:** Use `Optional<T>` explicitly for return types; never use `Optional` as a parameter.

* **Class Name:** Java file name MUST be same as the class name.


## Virtual Threads

* **Concurrency:** Prioritize Virtual Threads (Project Loom) for high-throughput I/O.

* **To avoid "pinning":**
    * PROHIBIT `synchronized` blocks or methods.

    * MUST replace `synchronized` blocks with `AtomicReference`.

    * PROHIBIT native calls or a foreign function.

* **Mitigating Third-party dependencies pinning**.

### Observability

1. Enable `jdk.VirtualThreadPinned`.

2. Configure JFR to record pinning events (JFR event includes the stack trace).

    * Trigger event when a virtual thread is pinned for longer than a specified threshold (default is usually 20ms).


3. Periodic dump JFR analysis into your observability stack (e.g., passing JFR events to Prometheus/Grafana or Datadog).

### Isolate Native Operations (Bulkheading)

You cannot prevent JNI calls in dependencies, you must limit their "blast radius". If a thread is pinned, it consumes a Carrier Thread. If all Carrier Threads are pinned, the application halts.

* Do not run known native/JNI-heavy tasks directly on the default Virtual Thread scheduler (Fork Join Pool).


* Instead, wrap these specific calls in a `CompletableFuture` (Dedicated Platform Thread Pools) supplying to a separate, bounded Platform Thread pool.


* If you MUST use Virtual Threads for these paths, wrap the native calls in a `Semaphore`. This limits the concurrency of that specific operation, preventing a flood of native calls from exhausting all available carrier threads simultaneously.


## Strategic Dependency Management

* **Prioritize Loom-Friendly Drivers:** actively migrate to JDBC drivers and libraries that have explicitly rewritten their networking layers to use non-blocking I/O (NIO) rather than blocking JNI or synchronized blocks.


* **Loom-Specific Configuration:** Many libraries (e.g., Netty or certain DB drivers) now offer specific flags to enable "Virtual Thread friendly" modes. Ensure these are explicitly enabled in your configuration management.
