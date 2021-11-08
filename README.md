# Keycloak Require Attribute Authenticator

This is a custom authenticator for [Keycloak](https://www.keycloak.org), that requires a user to have a specified attribute and attribute value. If a user doesn't have the attribute or has an incorrect value, the authenticator will fail.

**Table of Contents:**

- [Build](#build)
- [Deploy and Configure](#deploy-and-configure)
- [Development & Contributing](#development--contributing)
- [License](#license)
- [Author Information](#author-information)

---

## Build

For deploying the authenticator the code must first be compiled and packed as a JAR. Two things are required to build the code, Java JDK 11 and Maven.

If you have both Java and Maven installed locally you just have to execute: 

```sh
mvn clean install -DskipTests -Dkeycloak.version=12.0.4
```


