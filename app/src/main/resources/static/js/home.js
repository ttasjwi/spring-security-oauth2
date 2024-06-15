const authorizationCode = () => {
  window.location.href = 'http://localhost:8081/oauth2/authorization/keycloak'
};

const authorizationCodeWithPKCE = () => {
  window.location.href = 'http://localhost:8081/oauth2/authorization/keycloakWithPKCE'
}
