# Product Guidelines: MongoDB ID & Index Architect

## Tone and Voice

The library's documentation, API instructions, and error messages shall maintain an **Enterprise-Formal** tone. Communication must emphasize compliance with security standards, architectural rigor, and operational stability. Avoid colloquialisms; prioritize clarity and professional authority.

## API Design Principles

* **Type-Safety over Primitives:** The API must enforce strong typing. Avoid using `String` or `Long` for identifiers; utilize domain-specific type wrappers (e.g., `UserId`, `GeneratedId`) to prevent parameter swapping and logic errors.

* **Configuration-First Architecture:** Every behavioral aspect—from clock skew windows to ID partition logic—must be externalizable via standard Java configuration (YAML or Properties). This ensures the library can be tuned for different enterprise environments without code changes.

## Code Generation & Formatting

* **Explicit Persistence Annotations:** Generated Java source code must explicitly define all Spring Data MongoDB annotations (e.g., `@Document`, `@Id`, `@Indexed`). Relying on default behavior is prohibited to ensure long-term maintainability and readability.

* **Fluent Builder Implementation:** Generated entities and configuration objects shall implement the Fluent Builder pattern. This enhances code readability and ensures that object instantiation is expressive and less prone to positional argument errors.

* **Strict Visibility Controls:** Internal implementation details must adhere to the principle of least privilege. Use `private` or package-private modifiers wherever possible to prevent unintended coupling from the host application.

## Security & Compliance
  
  * **Mandatory Hardened Signatures:** Documentation must strictly promote the use of repository methods that include both the ID and the `UserId`. Any example or generated snippet that bypasses Row-Level Security (RLS) is a violation of these guidelines.
